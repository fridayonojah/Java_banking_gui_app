package com.bankapp.utils;

import javax.swing.JOptionPane;

public class Notifier {

    // Simple popup alert
    public static void showNotification(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Future support for email, etc.
    public static void sendEmailNotification(String email, String message) {
        // Stub: For future use
        System.out.println("Email to " + email + ": " + message);
    }
}
