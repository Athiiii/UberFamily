using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using UberFamily.Services.Models;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Services.Services
{
    internal class FriendService
        : IFriendService
    {
        public Friend AddFriend(Friend friend)
        {
            using (var context = new UberFamilyContext())
            {
                var friendItem = context.Friend.Add(friend);
                context.SaveChanges();
                return friendItem.Entity;
            }
        }

        public Friend GetFriendById(int friendshipId)
        {
            using (var context = new UberFamilyContext())
            {
                return context.Friend.FirstOrDefault(x => x.Id == friendshipId);
            }
        }

        public IEnumerable<Friend> GetFriends(int userId)
        {
            using (var context = new UberFamilyContext())
            {
                return context.Friend.Where(x => x.FirstFriend == userId || x.SecondFriend == userId);
            }
        }

        public async void RemoveFriend(int friendId)
        {
            using (var context = new UberFamilyContext())
            {
                context.Friend.Remove(context.Friend.FirstOrDefault(x => x.Id == friendId));
                await context.SaveChangesAsync();
            }
        }

        public async void UpdateFriend(Friend friend)
        {
            using (var context = new UberFamilyContext())
            {
                var friendItem = context.Friend.FirstOrDefault(x => x.Id == friend.Id);
                friendItem.FirstFriend = friend.FirstFriend;
                friendItem.SecondFriend = friend.SecondFriend;
                await context.SaveChangesAsync();
            }
        }
    }
}
