using System;
using System.Collections.Generic;
using System.Text;
using UberFamily.Services.Models;
using System.Linq;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Services.Services
{
    class UserService
        : IUserService
    {
        public async void AddUser(User user)
        {
            using (var context = new UberFamilyContext())
            {
                context.User.Add(user);
                await context.SaveChangesAsync();
            }
        }

        public User GetUser(string username, string password)
        {
            using (var context = new UberFamilyContext())
            {
                return context.User.FirstOrDefault(x => x.Username == username && x.Password == password);
            }
        }

        public User GetUserById(int userId)
        {
            using (var context = new UberFamilyContext())
            {
                return context.User.FirstOrDefault(x => x.Id == userId);
            }
        }

        public IEnumerable<User> GetUsers()
        {
            using (var context = new UberFamilyContext())
            {
                return context.User.ToList();
            }
        }
    }
}
