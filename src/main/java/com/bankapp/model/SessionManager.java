package com.bankapp.model;


public class SessionManager {
    private static boolean loggedIn = false;
    private static String currentUserId;

    public static void login(String userId) {
        loggedIn = true;
        currentUserId = userId;
    }

    public static void logout() {
        loggedIn = false;
        currentUserId = null;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static String getCurrentUserId() {
        return currentUserId;
    }
}

