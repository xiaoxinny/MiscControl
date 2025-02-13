using System.ComponentModel.DataAnnotations;

namespace BookwormsOnline.Models;

public class User
{
    public int Id { get; set; }

    [Required] [DataType(DataType.Text)] public string FirstName { get; set; }

    [Required] [DataType(DataType.Text)] public string LastName { get; set; }

    [Required] [DataType(DataType.Text)] public string CreditCard { get; set; }

    [Required] [DataType(DataType.Text)] public string PhoneNumber { get; set; }

    [Required] [DataType(DataType.Text)] public string BillingAddress { get; set; }

    [Required] [DataType(DataType.Text)] public string ShippingAddress { get; set; }

    [Required]
    [DataType(DataType.EmailAddress)]
    public string Email { get; set; }

    [Required] [DataType(DataType.Text)] public string PasswordHash { get; set; }

    [Required] [DataType(DataType.Text)] public string PasswordSalt { get; set; }

    [Required] [DataType(DataType.Text)] public string ImageBase64 { get; set; }

    [Required] public int FailedLoginAttempts { get; set; }

    public DateTime? LockoutEnd { get; set; }
}