using System.Collections.Generic;
using UberFamily.Services.Models;

namespace UberFamily.Services.Services.Interfaces
{
    public interface IFriendService
    {
        void AddFriend(Friend friend);

        void RemoveFriend(Friend friend);

        IEnumerable<Friend> GetFriends();

        Friend GetFriendById(int friendshipId);

        void UpdateFriend(Friend friend);
    }
}
