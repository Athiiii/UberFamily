﻿using Microsoft.AspNetCore.Mvc;
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
        [HttpPost]
        public User Create(User user)
        {
            return null;
        }

        [HttpPost("verify")]
        public User VerifyUser(string username, string password)
        {

            return null;
        }

        [HttpGet]
        public IEnumerable<User> GetUsers()
        {
            return null;
        }
    }
}
