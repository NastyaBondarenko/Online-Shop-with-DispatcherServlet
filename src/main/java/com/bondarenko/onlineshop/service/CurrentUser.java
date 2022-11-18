package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.User;

public class CurrentUser {
    private static final ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static User getCurrentUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void clear() {
        USER_THREAD_LOCAL.remove();
    }
}