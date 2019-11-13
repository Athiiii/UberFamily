using System.Collections.Generic;
using System.Linq;
using UberFamily.Services.Models;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Services.Services
{
    class UserService
        : IUserService
    {
        IFriendService _friendService;

        public UserService(IFriendService friendService)
        {
            _friendService = friendService;
        }

        public async void AddUser(User user)
        {
            using (var context = new UberFamilyContext())
            {
                context.User.Add(user);
                await context.SaveChangesAsync();
            }
        }

        public async void DeleteUser(int userId)
        {
            using (var context = new UberFamilyContext())
            {
                context.User.Remove(context.User.FirstOrDefault(x => x.Id == userId));
                await context.SaveChangesAsync();
            }
        }

        public IEnumerable<User> GetApprovedFriends(bool approved, int userId)
        {
            using (var context = new UberFamilyContext())
            {
                var user = context.User.FirstOrDefault(x => x.Id == userId);
                var friendList = _friendService.GetFriends(userId);
                if (approved)
                    friendList = _friendService.GetFriends(userId).Where(x => x.Approved == 1).ToList();
                var userList = new List<User>();
                foreach (var item in friendList)
                {
                    if (item.FirstFriend != userId && userList.FirstOrDefault(x => x.Id == item.FirstFriend) == null)
                        userList.Add(context.User.First(x => x.Id == item.FirstFriend));

                    if (item.SecondFriend != userId && userList.FirstOrDefault(x => x.Id == item.SecondFriend) == null)
                        userList.Add(context.User.First(x => x.Id == item.SecondFriend));
                }
                return userList.Select(x => new User
                {
                    Id = x.Id,
                    Username = x.Username,
                    Port = x.Port,
                    IpAddress = x.IpAddress
                }).ToList();
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

        public void UpdateConnectivity(string ipAdress, string port, int userId)
        {
            using (var context = new UberFamilyContext())
            {
                var userDb = context.User.FirstOrDefault(x => x.Id == userId);
                if (userDb != null)
                {
                    userDb.Port = port;
                    userDb.IpAddress = ipAdress;
                    context.SaveChanges();
                }
            }
        }

        public void UpdateUser(User user)
        {
            using (var context = new UberFamilyContext()) 
            {
                var userDb = context.User.FirstOrDefault(x => x.Id == user.Id);
                if(userDb != null)
                {
                    userDb.IsDriver = user.IsDriver;
                    userDb.Password = user.Password;
                    userDb.Fullname = user.Fullname;
                    context.SaveChanges();
                }
            }
        }
    }
}
