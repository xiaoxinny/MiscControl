using System.ComponentModel.DataAnnotations;

namespace BookwormsOnline.Models;

public class AuditLog
{
    public int Id { get; set; }

    [Required]
    public int? UserId { get; set; }

    [Required]
    [DataType(DataType.EmailAddress)]
    public string Email { get; set; }

    [Required]
    [DataType(DataType.Text)]
    public string Activity { get; set; }

    [Required]
    [DataType(DataType.DateTime)]
    public DateTime Timestamp { get; set; }
}