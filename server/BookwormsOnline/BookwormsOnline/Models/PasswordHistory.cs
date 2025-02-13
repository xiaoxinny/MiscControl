using System.ComponentModel.DataAnnotations;

namespace BookwormsOnline.Models;

public class PasswordHistory
{
    public int Id { get; set; }

    [Required]
    public int UserId { get; set; }

    [Required]
    [DataType(DataType.Text)]
    public string PasswordHash { get; set; }

    [Required]
    [DataType(DataType.Text)]
    public string PasswordSalt { get; set; }

    [Required]
    [DataType(DataType.DateTime)]
    public DateTime CreatedAt { get; set; }
}