using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using UberFamily.Services.Models;

namespace UberFamily.Api.Controllers
{
    [Route("api[controller]")]
    public class MessageController
        : Controller
    {
        [HttpPost]
        public IActionResult AddMessage(ChatMessage message)
        {
            return Ok();
        }

        [HttpGet]
        public IEnumerable<ChatMessage> GetMessage(int requestId)
        {
            return null;
        }
    }
}
