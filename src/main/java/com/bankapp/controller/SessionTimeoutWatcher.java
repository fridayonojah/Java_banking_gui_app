package com.bankapp.controller;

import javax.swing.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import com.bankapp.gui.LoginUI;
import com.bankapp.model.SessionManager;


public class SessionTimeoutWatcher {
    private static Timer timer;
    private static int timeoutSeconds = 300; //setting 5 minutes = 300 seconds

    public static void start(JFrame frame) {
        stop(); // make sure old timer is stopped

        timer = new Timer(true);

        TimerTask logoutTask = new TimerTask() {
            @Override
            public void run() {
                if (SessionManager.isLoggedIn()) {
                    SessionManager.logout();
                    JOptionPane.showMessageDialog(frame, "Session timed out due to inactivity.");
                    frame.dispose();
                    new LoginUI().setVisible(true);
                }
            }
        };

        timer.schedule(logoutTask, timeoutSeconds * 1000L);

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                resetTimer(frame);
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                resetTimer(frame);
            }
        });
    }

    public static void resetTimer(JFrame frame) {
        if (timer != null) {
            timer.cancel();
        }
        start(frame);
    }

    public static void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
