using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using UberFamily.Services.Models;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Services.Services
{
    internal class ChatMessageService
        : IChatMessageService
    {
        public async void AddMessage(ChatMessage message)
        {
            using (var context = new UberFamilyContext())
            {
                context.ChatMessage.Add(message);
                await context.SaveChangesAsync();
            }
        }

        public IEnumerable<ChatMessage> GetMessages(int requestId)
        {
            using (var context = new UberFamilyContext())
            {
                return context.ChatMessage.Where(x => x.RequestId == requestId).ToList();
            }
        }
    }
}
