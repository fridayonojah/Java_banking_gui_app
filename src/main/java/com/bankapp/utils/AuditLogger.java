package com.bankapp.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditLogger {

    private static final String LOG_FILE = "logs/audit_log.txt";

    public static void log(String username, String action, String status) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            String logEntry = String.format("[%s] User: %s | Action: %s | Status: %s",
                    LocalDateTime.now(), username, action, status);
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Audit logging failed: " + e.getMessage());
        }
    }
}
