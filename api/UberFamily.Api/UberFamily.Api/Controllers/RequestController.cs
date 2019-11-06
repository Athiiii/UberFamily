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
    public class RequestController
        : Controller
    {
        private IRequestService _requestService;
        private IUserService _userService;

        public RequestController(IRequestService requestService, IUserService userService)
        {
            _requestService = requestService;
            _userService = userService;
        }

        [HttpPost]
        public Request PickMeUp(int userId, string adress)
        {
            return _requestService.CreateRequest(new Request
            {
                Adress = adress,
                Open = 0,
                Requester = userId
            });
        }

        [HttpPost("driver")]
        public Request AddRequestDriver(int requestId, int userId)
        {
            var request = _requestService.GetRequestById(requestId);
            if(request != null)
            {
            request.Driver = userId;
            _requestService.UpdateRequest(request);
            }

            return request;
        }

        [HttpPut]
        public IActionResult CloseRequest(int userId)
        {
            return Ok();
        }
    }
}
