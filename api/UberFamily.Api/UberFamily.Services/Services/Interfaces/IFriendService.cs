using System.Collections.Generic;
using UberFamily.Services.Models;

namespace UberFamily.Services.Services.Interfaces
{
    public interface IFriendService
    {
        Friend AddFriend(Friend friend);

        void RemoveFriend(int friendId);

        IEnumerable<Friend> GetFriends(int userId);

        Friend GetFriendById(int friendshipId);

        void UpdateFriend(Friend friend);
    }
}
