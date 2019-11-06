using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace UberFamily.Services.Models
{
    [DataContract]
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
        [IgnoreDataMember]
        internal virtual User DriverNavigation { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual User RequesterNavigation { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual ICollection<ChatMessage> ChatMessage { get; set; }
    }
}
