using System;
using System.Collections.Generic;

namespace UberFamily.Services.Models
{
    public partial class ChatMessage
    {
        public int Id { get; set; }
        public int Writer { get; set; }
        public int RequestId { get; set; }
        public string Message { get; set; }

        public virtual Request Request { get; set; }
        public virtual User WriterNavigation { get; set; }
    }
}
