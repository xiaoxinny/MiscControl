using System.Net;
using System.Security.Cryptography;
using System.Text;
using System.Text.RegularExpressions;
using BookwormsOnline.DTOs;
using BookwormsOnline.Models;
using Microsoft.AspNetCore.Mvc;

namespace BookwormsOnline.Controllers;

[ApiController]
[Route("[controller]")]
public class UserController : ControllerBase
{
    private readonly MyDbContext _context;
    private readonly IConfiguration _configuration;
    private readonly IHttpContextAccessor _contextAccessor;

    public UserController(MyDbContext context, IConfiguration configuration, IHttpContextAccessor contextAccessor)
    {
        _context = context;
        _configuration = configuration;
        _contextAccessor = contextAccessor;
    }


    [HttpPost("registration")]
    [ValidateAntiForgeryToken]
    public IActionResult AddUser([FromBody] RegistrationDTO registrationDto)
    {
        if (_context.Users.Any(u => u.Email == registrationDto.Email.Trim()))
        {
            Console.WriteLine("Email already exists.");
            return BadRequest(new { message = "Email already exists." });
        }

        if (registrationDto.Password != registrationDto.ConfirmPassword)
        {
            Console.WriteLine("Passwords do not match.");
            return BadRequest(new { message = "Passwords do not match." });
        }

        if (!IsValidPassword(registrationDto.Password))
        {
            Console.WriteLine("Passwords must be at least 12 characters long and contain at least an upper case letter, lower case letter, digit and a symbol.");
            return BadRequest(new
            {
                message =
                    "Passwords must be at least 12 characters long and contain at least an upper case letter, lower case letter, digit and a symbol."
            });
        }

        var salt = GenerateSalt();
        var hashedPassword = HashPassword(registrationDto.Password, salt);
        var newUser = new User()
        {
            FirstName = registrationDto.FirstName.Trim(),
            LastName = registrationDto.LastName.Trim(),
            CreditCard = Encrypt(registrationDto.CreditCard.Trim()),
            PhoneNumber = Encrypt(registrationDto.PhoneNumber.Trim()),
            BillingAddress = Encrypt(registrationDto.BillingAddress.Trim()),
            ShippingAddress = Encrypt(registrationDto.ShippingAddress.Trim()),
            Email = registrationDto.Email.Trim(),
            PasswordHash = hashedPassword,
            PasswordSalt = salt,
            ImageBase64 = registrationDto.ImageBase64.Trim(),
        };

        _context.Users.Add(newUser);
        _context.SaveChanges();

        var passwordHistory = new PasswordHistory
        {
            UserId = newUser.Id,
            PasswordHash = hashedPassword,
            PasswordSalt = salt,
            CreatedAt = DateTime.UtcNow
        };

        _context.PasswordHistories.Add(passwordHistory);
        _context.SaveChanges();

        return Ok(new { message = "User successfully created." });
    }

    private bool IsValidPassword(string password)
    {
        if (password.Length < 12)
        {
            return false;
        }

        var regex = new Regex(@"^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{12,}$");
        return regex.IsMatch(password);
    }

    private string GenerateSalt()
    {
        var saltBytes = new byte[16];
        using (var rng = new RNGCryptoServiceProvider())
        {
            rng.GetBytes(saltBytes);
        }

        return Convert.ToBase64String(saltBytes);
    }

    private string HashPassword(string password, string salt)
    {
        using (var sha256 = SHA256.Create())
        {
            var saltedPassword = password + salt;
            var saltedPasswordBytes = Encoding.UTF8.GetBytes(saltedPassword);
            var hashBytes = sha256.ComputeHash(saltedPasswordBytes);
            return Convert.ToBase64String(hashBytes);
        }
    }

    private string Encrypt(string plainText)
    {
        using (var aes = Aes.Create())
        {
            aes.Key = Encoding.UTF8.GetBytes(_configuration["EncryptionKey"]);
            aes.IV = new byte[16];

            var encryptor = aes.CreateEncryptor(aes.Key, aes.IV);
            using (var ms = new MemoryStream())
            {
                using (var cs = new CryptoStream(ms, encryptor, CryptoStreamMode.Write))
                {
                    using (var sw = new StreamWriter(cs))
                    {
                        sw.Write(plainText);
                    }
                }

                return Convert.ToBase64String(ms.ToArray());
            }
        }
    }

    private string Decrypt(string cipherText)
    {
        using (var aes = Aes.Create())
        {
            aes.Key = Encoding.UTF8.GetBytes(_configuration["EncryptionKey"]);
            aes.IV = new byte[16];

            var decryptor = aes.CreateDecryptor(aes.Key, aes.IV);
            using (var ms = new MemoryStream(Convert.FromBase64String(cipherText)))
            {
                using (var cs = new CryptoStream(ms, decryptor, CryptoStreamMode.Read))
                {
                    using (var sr = new StreamReader(cs))
                    {
                        return sr.ReadToEnd();
                    }
                }
            }
        }
    }

    [HttpGet("{id}")]
    public IActionResult GetUser(int id)
    {
        var user = _context.Users.Find(id);
        if (user == null)
        {
            return NotFound(new { message = "User not found." });
        }

        var decryptedUser = new UserResponseDTO
        {
            Id = user.Id,
            FirstName = user.FirstName,
            LastName = user.LastName,
            CreditCard = Decrypt(user.CreditCard),
            PhoneNumber = Decrypt(user.PhoneNumber),
            BillingAddress = Decrypt(user.BillingAddress),
            ShippingAddress = Decrypt(user.ShippingAddress),
            Email = user.Email,
            ImageBase64 = user.ImageBase64
        };

        return Ok(decryptedUser);
    }

    [HttpPost("login")]
    [ValidateAntiForgeryToken]
    public IActionResult Login([FromBody] LoginDTO loginDto)
    {
        loginDto.Email = WebUtility.HtmlEncode(loginDto.Email);
        loginDto.Password = WebUtility.HtmlEncode(loginDto.Password);

        var user = _context.Users.SingleOrDefault(u => u.Email == loginDto.Email.Trim());
        if (user == null)
        {
            LogFailedLoginAttempt(-1, loginDto.Email);
            return Unauthorized(new { message = "Invalid email or password." });
        }

        if (user.LockoutEnd.HasValue && user.LockoutEnd.Value > DateTime.UtcNow)
        {
            return Unauthorized(new { message = "Account is locked. Please try again later." });
        }

        if (user.FailedLoginAttempts >= 3)
        {
            user.LockoutEnd = DateTime.UtcNow.AddSeconds(15);
            _context.SaveChanges();
            return Unauthorized(new { message = "Account locked due to multiple failed login attempts. Please try again later." });
        }

        var hashedPassword = HashPassword(loginDto.Password, user.PasswordSalt);
        if (hashedPassword != user.PasswordHash)
        {
            user.FailedLoginAttempts++;
            _context.SaveChanges();
            LogFailedLoginAttempt(-1, loginDto.Email);
            return Unauthorized(new { message = "Invalid email or password." });
        }

        user.FailedLoginAttempts = 0;
        user.LockoutEnd = null;
        _context.SaveChanges();

        var sessionKey = Guid.NewGuid().ToString();
        var session = new Sessions
        {
            AccountId = user.Id,
            SessionKey = sessionKey,
            Expiry = DateTime.UtcNow.AddMinutes(30),
            CreatedAt = DateTime.UtcNow,
            UpdatedAt = DateTime.UtcNow
        };
        _context.Sessions.Add(session);
        _context.SaveChanges();

        _contextAccessor.HttpContext.Session.SetString("SessionKey", sessionKey);

        LogUserActivity(user.Id, user.Email, "User logged in");

        return Ok(new { message = "Login successful." });
    }

    private void LogFailedLoginAttempt(int userId, string email)
    {
        var log = new AuditLog
        {
            UserId = userId,
            Email = email,
            Activity = "Failed login attempt",
            Timestamp = DateTime.UtcNow
        };
        _context.AuditLogs.Add(log);
        _context.SaveChanges();
    }

    private void LogUserActivity(int userId, string email, string activity)
    {
        var log = new AuditLog
        {
            UserId = userId,
            Email = email,
            Activity = activity,
            Timestamp = DateTime.UtcNow
        };
        _context.AuditLogs.Add(log);
        _context.SaveChanges();
    }

    [HttpPost("logout")]
    public IActionResult Logout()
    {
        var sessionKey = _contextAccessor.HttpContext.Session.GetString("SessionKey");
        if (sessionKey != null)
        {
            var session = _context.Sessions.SingleOrDefault(s => s.SessionKey == sessionKey);
            if (session != null)
            {
                _context.Sessions.Remove(session);
                _context.SaveChanges();
            }
            _contextAccessor.HttpContext.Session.Remove("SessionKey");
        }

        return Ok(new { message = "Logout successful."});
    }

    [HttpPost("change-password")]
    [ValidateAntiForgeryToken]
    public IActionResult ChangePassword([FromBody] ChangePasswordDTO changePasswordDto)
    {
        var user = _context.Users.SingleOrDefault(u => u.Email == changePasswordDto.Email.Trim());
        if (user == null)
        {
            return NotFound(new { message = "User not found." });
        }

        var hashedOldPassword = HashPassword(changePasswordDto.OldPassword, user.PasswordSalt);
        if (hashedOldPassword != user.PasswordHash)
        {
            return Unauthorized(new { message = "Old password is incorrect." });
        }

        var passwordHistories = _context.PasswordHistories
            .Where(ph => ph.UserId == user.Id)
            .OrderByDescending(ph => ph.CreatedAt)
            .Take(2)
            .ToList();

        var newSalt = GenerateSalt();
        var newHashedPassword = HashPassword(changePasswordDto.NewPassword, newSalt);

        foreach (var history in passwordHistories)
        {
            if (history.PasswordHash == newHashedPassword)
            {
                return BadRequest(new { message = "New password cannot be the same as the last two passwords." });
            }
        }

        user.PasswordHash = newHashedPassword;
        user.PasswordSalt = newSalt;
        _context.SaveChanges();

        var newPasswordHistory = new PasswordHistory
        {
            UserId = user.Id,
            PasswordHash = newHashedPassword,
            PasswordSalt = newSalt,
            CreatedAt = DateTime.UtcNow
        };

        _context.PasswordHistories.Add(newPasswordHistory);
        _context.SaveChanges();

        return Ok(new { message = "Password changed successfully." });
    }
}