using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using UberFamily.Services.Models;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Services.Services
{
    class RequestService
        : IRequestService
    {
        public Request CreateRequest(Request request)
        {
            using (var context = new UberFamilyContext())
            {
                var requestObj = context.Request.Add(request);
                context.SaveChanges();

                return requestObj.Entity;
            }
        }

        public Request GetRequestById(int requestId)
        {
            using (var context = new UberFamilyContext()) 
            {
                return context.Request.FirstOrDefault(x => x.Id == requestId);
            }
        }

        public async void UpdateRequest(Request request)
        {
            using (var context = new UberFamilyContext())
            {
                var requestItem = context.Request.FirstOrDefault(x => x.Id == request.Id);
                requestItem.Open = request.Open;
                requestItem.Requester = request.Requester;
                requestItem.Adress = request.Adress;
                requestItem.Driver = request.Driver;
                await context.SaveChangesAsync();
            }
        }
    }
}
