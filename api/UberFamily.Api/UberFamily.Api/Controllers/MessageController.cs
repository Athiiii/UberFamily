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
    public class MessageController
        : Controller
    {
        private readonly IChatMessageService _messageService;

        public MessageController(IChatMessageService messageService)
        {
            _messageService = messageService;
        }



        [HttpGet]
        public IEnumerable<ChatMessage> GetMessage(int requestId)
        {
            return _messageService.GetMessages(requestId);
        }

        [HttpPost]
        public IActionResult AddMessage(int writer, int requestId, string message)
        {
            _messageService.AddMessage(new ChatMessage
            {
                Message = message,
                RequestId = requestId,
                Writer = writer
            });
            return Ok();
        }
    }
}
