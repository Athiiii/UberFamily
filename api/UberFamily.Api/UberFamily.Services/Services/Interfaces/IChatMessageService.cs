using System.Collections.Generic;
using UberFamily.Services.Models;

namespace UberFamily.Services.Services.Interfaces
{
    public interface IChatMessageService
    {
        IEnumerable<ChatMessage> GetMessages(int requestId);

        void AddMessage(ChatMessage message);

    }
}
