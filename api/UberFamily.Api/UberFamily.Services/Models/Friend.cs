using System;
using System.Collections.Generic;

namespace UberFamily.Services.Models
{
    public partial class Friend
    {
        public int Id { get; set; }
        public int FirstFriend { get; set; }
        public int SecondFriend { get; set; }

        public virtual User FirstFriendNavigation { get; set; }
        public virtual User SecondFriendNavigation { get; set; }
    }
}
