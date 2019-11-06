using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using UberFamily.Services.Models;

namespace UberFamily.Api.Controllers
{
    [Route("api/[controller]")]
    public class UserController
        : Controller
    {
        [HttpPost("")]
        public IActionResult Create(User user)
        {
            return Ok();
        }

        [HttpPost("verify")]
        public IActionResult VerifyUser(string username, string password)
        {

            return Ok();
        }
    }
}
