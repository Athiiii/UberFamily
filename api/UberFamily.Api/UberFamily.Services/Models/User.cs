using Newtonsoft.Json;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace UberFamily.Services.Models
{
    [DataContract]
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
        [IgnoreDataMember]
        internal virtual ICollection<ChatMessage> ChatMessage { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual ICollection<Friend> FriendFirstFriendNavigation { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual ICollection<Friend> FriendSecondFriendNavigation { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual ICollection<Request> RequestDriverNavigation { get; set; }

        [JsonIgnore]
        [IgnoreDataMember]
        internal virtual ICollection<Request> RequestRequesterNavigation { get; set; }
    }
}
