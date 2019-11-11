using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using UberFamily.Services.Models;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Api.Controllers
{
    [Route("api/[controller]")]
    public class UserController
        : Controller
    {
        private readonly IUserService _userService;
        
        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        [HttpPost]
        public User Create(User user)
        {
            if (string.IsNullOrWhiteSpace(user.Password) || string.IsNullOrWhiteSpace(user.Username))
                return null;
            _userService.AddUser(user);
            return _userService.GetUser(user.Username, user.Password);
        }

        [HttpPost("verify")]
        public User VerifyUser(string username, string password)
        {            
            return _userService.GetUser(username, password);
        }

        [HttpGet]
        public IEnumerable<User> GetUsers()
        {
            return _userService.GetUsers();
        }

        [HttpDelete]
        public IActionResult DeleteUser(int userId)
        {
            _userService.DeleteUser(userId);
            return Ok();
        }
    }
}
