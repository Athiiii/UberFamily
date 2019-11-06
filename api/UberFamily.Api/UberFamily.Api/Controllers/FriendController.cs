using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using UberFamily.Services.Models;

namespace UberFamily.Api.Controllers
{
    [Route("api/[controller]")]
    public class FriendController
        : Controller
    {
        [HttpPost]
        public Friend AddFriend(int userId, int friendId)
        {
            return null;
        }

        [HttpDelete]
        public IActionResult RemoveFriend(int friendId)
        {
            return Ok();
        }

        [HttpGet]
        public IEnumerable<Friend> GetFriends(int userId)
        {
            return null;
        }

        [HttpPut]
        public IActionResult ApproveRequest(int friendshipId, bool approved)
        {
            return Ok();
        }
    }
}
