using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace UberFamily.Services.Models
{
    public partial class Friend
    {
        public int Id { get; set; }
        public int FirstFriend { get; set; }
        public int SecondFriend { get; set; }
        public byte? Approved { get; set; }

        [JsonIgnore]
        internal User FirstFriendNavigation { get; set; }

        [JsonIgnore]
        internal User SecondFriendNavigation { get; set; }
    }
}
