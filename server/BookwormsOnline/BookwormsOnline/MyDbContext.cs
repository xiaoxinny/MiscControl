using Microsoft.EntityFrameworkCore;
using BookwormsOnline.Models;

namespace BookwormsOnline;

public class MyDbContext(IConfiguration configuration) : DbContext
{
    private readonly IConfiguration _configuration = configuration;

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        string? connectionString = _configuration.GetConnectionString("MyConnection");

        if (connectionString != null)
        {
            optionsBuilder.UseMySQL(connectionString);
        }
    }
    
    public required DbSet<User> Users { get; set; }
    public required DbSet<Sessions> Sessions { get; set; }
    public required DbSet<AuditLog> AuditLogs { get; set; }
    public required DbSet<PasswordHistory> PasswordHistories { get; set; }
}
