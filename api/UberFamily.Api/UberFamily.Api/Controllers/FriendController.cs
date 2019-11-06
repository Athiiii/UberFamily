using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace UberFamily.Api.Controllers
{
    [Route("api/[controller]")]
    public class FriendController
        : Controller
    {
        [HttpPost]
        public IActionResult AddFriend(int userId, int friendId)
        {
            return Ok();
        }
    }
}
