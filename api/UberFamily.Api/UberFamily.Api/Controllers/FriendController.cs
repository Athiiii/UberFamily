using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using UberFamily.Services.Models;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Api.Controllers
{
    [Route("api/[controller]")]
    public class FriendController
        : Controller
    {
        private readonly IFriendService _friendService;

        public FriendController(IFriendService friendService)
        {
            _friendService = friendService;
        }

        [HttpPost]
        public Friend AddFriend(int userId, int friendId)
        {
            return _friendService.AddFriend(new Friend
            {
                Approved = 0,
                FirstFriend = userId,
                SecondFriend = friendId
            });
        }

        [HttpDelete]
        public IActionResult RemoveFriend(int friendId)
        {
            _friendService.RemoveFriend(friendId);
            return Ok();
        }

        [HttpGet]
        public IEnumerable<Friend> GetFriends(int userId)
        {
            return _friendService.GetFriends(userId);
        }

        [HttpPut]
        public IActionResult ApproveRequest(int friendshipId, bool approved)
        {
            if(approved)
            {
                var friend = _friendService.GetFriendById(friendshipId);
                friend.Approved = 1;
                _friendService.UpdateFriend(friend);
            }
            else
            {
                _friendService.RemoveFriend(friendshipId);
            }
            return Ok();
        }
    }
}
