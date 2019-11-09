﻿using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
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
        public IActionResult Create(User user)
        {
            if (string.IsNullOrWhiteSpace(user.Password) || string.IsNullOrWhiteSpace(user.Username))
                return BadRequest();
            _userService.AddUser(user);
            return Ok();
        }

        [HttpPost("verify")]
        public User VerifyUser(string username, string password)
        {            
            return _userService.GetUser(username, password);
        }

        [HttpGet]
        public IActionResult GetUsers()
        {
            var r = _userService.GetUsers();
            return Json(r);
        }
    }
}