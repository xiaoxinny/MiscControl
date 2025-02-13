using System.ComponentModel.DataAnnotations;

namespace BookwormsOnline.Models;

public class Sessions
{
    public int Id { get; set; }

    [Required]
    public int AccountId { get; set; }

    [Required]
    public string SessionKey { get; set; }

    [Required]
    public DateTime Expiry { get; set; }

    [Required]
    public DateTime CreatedAt { get; set; }

    [Required]
    public DateTime UpdatedAt { get; set; }
}