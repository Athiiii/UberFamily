using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

namespace UberFamily.Services.Models
{
    public partial class uberFamilyContext : DbContext
    {
        public uberFamilyContext()
        {
        }

        public uberFamilyContext(DbContextOptions<uberFamilyContext> options)
            : base(options)
        {
        }

        public virtual DbSet<ChatMessage> ChatMessage { get; set; }
        public virtual DbSet<Friend> Friend { get; set; }
        public virtual DbSet<Request> Request { get; set; }
        public virtual DbSet<User> User { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. See http://go.microsoft.com/fwlink/?LinkId=723263 for guidance on storing connection strings.
                optionsBuilder.UseMySQL("server=104.207.133.76;port=3306;user=monty;password=Bensey90;database=uberFamily");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasAnnotation("ProductVersion", "2.2.4-servicing-10062");

            modelBuilder.Entity<ChatMessage>(entity =>
            {
                entity.ToTable("ChatMessage", "uberFamily");

                entity.HasIndex(e => e.RequestId)
                    .HasName("fk_Chat_Request1");

                entity.HasIndex(e => e.Writer)
                    .HasName("fk_Chat_User1");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("int(11)")
                    .ValueGeneratedNever();

                entity.Property(e => e.Message)
                    .HasColumnName("message")
                    .IsUnicode(false);

                entity.Property(e => e.RequestId)
                    .HasColumnName("request_id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Writer)
                    .HasColumnName("writer")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.Request)
                    .WithMany(p => p.ChatMessage)
                    .HasForeignKey(d => d.RequestId)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_Chat_Request1");

                entity.HasOne(d => d.WriterNavigation)
                    .WithMany(p => p.ChatMessage)
                    .HasForeignKey(d => d.Writer)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_Chat_User1");
            });

            modelBuilder.Entity<Friend>(entity =>
            {
                entity.ToTable("Friend", "uberFamily");

                entity.HasIndex(e => e.FirstFriend)
                    .HasName("fk_Friend_User");

                entity.HasIndex(e => e.SecondFriend)
                    .HasName("fk_Friend_User1");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.FirstFriend)
                    .HasColumnName("firstFriend")
                    .HasColumnType("int(11)");

                entity.Property(e => e.SecondFriend)
                    .HasColumnName("secondFriend")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.FirstFriendNavigation)
                    .WithMany(p => p.FriendFirstFriendNavigation)
                    .HasForeignKey(d => d.FirstFriend)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_Friend_User");

                entity.HasOne(d => d.SecondFriendNavigation)
                    .WithMany(p => p.FriendSecondFriendNavigation)
                    .HasForeignKey(d => d.SecondFriend)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_Friend_User1");
            });

            modelBuilder.Entity<Request>(entity =>
            {
                entity.ToTable("Request", "uberFamily");

                entity.HasIndex(e => e.Driver)
                    .HasName("fk_Request_User2");

                entity.HasIndex(e => e.Requester)
                    .HasName("fk_Request_User1");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("int(11)")
                    .ValueGeneratedNever();

                entity.Property(e => e.Adress)
                    .HasColumnName("adress")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.Driver)
                    .HasColumnName("driver")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Open)
                    .HasColumnName("open")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.Requester)
                    .HasColumnName("requester")
                    .HasColumnType("int(11)");

                entity.HasOne(d => d.DriverNavigation)
                    .WithMany(p => p.RequestDriverNavigation)
                    .HasForeignKey(d => d.Driver)
                    .HasConstraintName("fk_Request_User2");

                entity.HasOne(d => d.RequesterNavigation)
                    .WithMany(p => p.RequestRequesterNavigation)
                    .HasForeignKey(d => d.Requester)
                    .OnDelete(DeleteBehavior.ClientSetNull)
                    .HasConstraintName("fk_Request_User1");
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.ToTable("User", "uberFamily");

                entity.Property(e => e.Id)
                    .HasColumnName("id")
                    .HasColumnType("int(11)");

                entity.Property(e => e.Fullname)
                    .HasColumnName("fullname")
                    .HasMaxLength(255)
                    .IsUnicode(false);

                entity.Property(e => e.IsDriver)
                    .HasColumnName("isDriver")
                    .HasColumnType("tinyint(4)");

                entity.Property(e => e.Password)
                    .HasColumnName("password")
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.Picture)
                    .HasColumnName("picture")
                    .IsUnicode(false);

                entity.Property(e => e.Username)
                    .HasColumnName("username")
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });
        }
    }
}
