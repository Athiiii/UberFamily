using System.Collections.Generic;
using UberFamily.Services.Models;

namespace UberFamily.Services.Services.Interfaces
{
    public interface IRequestService
    {
        Request CreateRequest(Request request);

        void UpdateRequest(Request request);

        Request GetRequestById(int requestId);

        IEnumerable<Request> GetOpenRequests();

        IEnumerable<Request> GetRequests();

        void DeleteRequest(int requestId);
    }
}
