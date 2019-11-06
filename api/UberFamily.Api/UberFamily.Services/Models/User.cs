using Newtonsoft.Json;
using System;
using System.Collections.Generic;

namespace UberFamily.Services.Models
{
    public partial class User
    {
        public User()
        {
            ChatMessage = new HashSet<ChatMessage>();
            FriendFirstFriendNavigation = new HashSet<Friend>();
            FriendSecondFriendNavigation = new HashSet<Friend>();
            RequestDriverNavigation = new HashSet<Request>();
            RequestRequesterNavigation = new HashSet<Request>();
        }

        internal int Id { get; set; }
        public string Fullname { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public byte? IsDriver { get; set; }
        public string Picture { get; set; }

        [JsonIgnore]
        internal ICollection<ChatMessage> ChatMessage { get; set; }

        [JsonIgnore]
        internal ICollection<Friend> FriendFirstFriendNavigation { get; set; }

        [JsonIgnore]
        internal ICollection<Friend> FriendSecondFriendNavigation { get; set; }

        [JsonIgnore]
        internal ICollection<Request> RequestDriverNavigation { get; set; }

        [JsonIgnore]
        internal ICollection<Request> RequestRequesterNavigation { get; set; }
    }
}
