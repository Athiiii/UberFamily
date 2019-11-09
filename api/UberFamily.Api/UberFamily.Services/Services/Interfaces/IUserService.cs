using System.Collections.Generic;
using UberFamily.Services.Models;

namespace UberFamily.Services.Services.Interfaces
{
    public interface IUserService
    {
        void AddUser(User user);

        User GetUser(string username, string password);

        User GetUserById(int userId);

        IEnumerable<User> GetUsers();
    }
}
