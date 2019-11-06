using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace UberFamily.Services.Models
{
    [DataContract]
    public partial class ChatMessage
    {
        internal int Id { get; set; }
        public int Writer { get; set; }
        public int RequestId { get; set; }
        public string Message { get; set; }
        public byte Approved { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual Request Request { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual User WriterNavigation { get; set; }
    }
}
