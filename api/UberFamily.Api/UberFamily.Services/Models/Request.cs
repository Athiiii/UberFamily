using System;
using System.Collections.Generic;

namespace UberFamily.Services.Models
{
    public partial class Request
    {
        public Request()
        {
            ChatMessage = new HashSet<ChatMessage>();
        }

        public int Id { get; set; }
        public int Requester { get; set; }
        public int? Driver { get; set; }
        public byte Open { get; set; }
        public string Adress { get; set; }

        public virtual User DriverNavigation { get; set; }
        public virtual User RequesterNavigation { get; set; }
        public virtual ICollection<ChatMessage> ChatMessage { get; set; }
    }
}
