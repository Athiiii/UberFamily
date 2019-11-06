using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace UberFamily.Services.Models
{
    public partial class ChatMessage
    {
        [JsonIgnore]
        public int Id { get; set; }
        public int Writer { get; set; }
        public int RequestId { get; set; }
        public string Message { get; set; }

        [JsonIgnore]
        internal Request Request { get; set; }

        [JsonIgnore]
        internal User WriterNavigation { get; set; }
    }
}
