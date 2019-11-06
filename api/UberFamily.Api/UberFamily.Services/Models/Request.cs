using Newtonsoft.Json;
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

        internal int Id { get; set; }
        public int Requester { get; set; }
        public int? Driver { get; set; }
        public byte Open { get; set; }
        public string Adress { get; set; }

        [JsonIgnore]
        internal User DriverNavigation { get; set; }

        [JsonIgnore]
        internal User RequesterNavigation { get; set; }

        [JsonIgnore]
        internal ICollection<ChatMessage> ChatMessage { get; set; }
    }
}
