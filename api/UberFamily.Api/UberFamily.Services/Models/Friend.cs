using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace UberFamily.Services.Models
{
    [DataContract]
    public partial class Friend
    {
        public int Id { get; set; }
        public int FirstFriend { get; set; }
        public int SecondFriend { get; set; }
        
        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual User FirstFriendNavigation { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual User SecondFriendNavigation { get; set; }
    }
}
