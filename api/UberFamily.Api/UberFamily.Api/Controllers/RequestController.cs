using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace UberFamily.Api.Controllers
{
    [Route("api/[controller]")]
    public class RequestController
        : Controller
    {
        [HttpPost]
        public IActionResult PickMeUp(int userId, string adress)
        {
            return Ok();
        }

        [HttpPost("driver")]
        public IActionResult AddRequestDriver(int userId)
        {
            return Ok();
        }

        [HttpPut]
        public IActionResult CloseRequest(int userId)
        {
            return Ok();
        }
    }
}
