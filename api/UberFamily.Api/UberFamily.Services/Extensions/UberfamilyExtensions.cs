using Microsoft.Extensions.DependencyInjection;
using System;
using System.Collections.Generic;
using System.Text;
using UberFamily.Services.Services;
using UberFamily.Services.Services.Interfaces;

namespace UberFamily.Services.Extensions
{
    public static class UberfamilyExtensions
    {
        public static IServiceCollection AddUberfamilyServices(this IServiceCollection services)
        {
            services
                .AddScoped<IChatMessageService, ChatMessageService>()
                .AddScoped<IFriendService, FriendService>()
                .AddScoped<IRequestService, RequestService>()
                .AddScoped<IUserService, UserService>();

            return services;
        }

    }
}
