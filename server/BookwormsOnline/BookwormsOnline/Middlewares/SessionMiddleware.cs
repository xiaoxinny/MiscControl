namespace BookwormsOnline.Middlewares;

public class SessionMiddleware
{
    private readonly RequestDelegate _next;

    public SessionMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task InvokeAsync(HttpContext context, MyDbContext dbContext)
    {
        var sessionKey = context.Session.GetString("SessionKey");
        if (sessionKey != null)
        {
            Console.WriteLine("Session key found: " + sessionKey);
            var session = dbContext.Sessions.SingleOrDefault(s => s.SessionKey == sessionKey);
            if (session == null || session.Expiry < DateTime.UtcNow)
            {
                Console.WriteLine("Session expired or not found for key: " + sessionKey);
                context.Session.Remove("SessionKey");
                context.Response.Redirect("/login");
                return;
            }
            session.UpdatedAt = DateTime.UtcNow;
            dbContext.SaveChanges();
            Console.WriteLine("Session updated for key: " + sessionKey);
        }
        else
        {
            Console.WriteLine("No session key found.");
        }
        await _next(context);
    }
}