using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using UberFamily.Services.Models;

namespace UberFamily.Api.Controllers
{
    [Route("api/[controller]")]
    public class RequestController
        : Controller
    {
        [HttpPost]
        public Request PickMeUp(int userId, string adress)
        {
            return null;
        }

        [HttpPost("driver")]
        public Request AddRequestDriver(int userId)
        {
            return null;
        }

        [HttpPut]
        public IActionResult CloseRequest(int userId)
        {
            return Ok();
        }
    }
}
