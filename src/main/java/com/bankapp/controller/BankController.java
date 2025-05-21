package com.bankapp.controller;

import com.bankapp.model.Customer;
import com.bankapp.model.SessionManager;
import com.bankapp.utils.AuditLogger;
import com.bankapp.utils.Notifier;
import com.bankapp.utils.PasswordUtils;
import com.bankapp.gui.DashboardUI;
import com.bankapp.gui.LoginUI;
import com.bankapp.manager.DatabaseDataManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BankController {
    private LoginUI LoginUI;

    public BankController(LoginUI LoginUI) {
        this.LoginUI = LoginUI;
        this.LoginUI.addLoginListener(new LoginListener());
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = LoginUI.getUsername();
            String password = LoginUI.getPassword();

            // get user info by username
            Customer customer = DatabaseDataManager.loadUserCredentials(username); 
           
            if (customer != null &&  PasswordUtils.verifyPassword(password, customer.getPassword())) {
                AuditLogger.log(customer.getUsername(), "Login Attempt", "Success");
                Notifier.showNotification("Login Successful!", "Login Alert");

                // after successful login
                DashboardUI dashboardView = new DashboardUI(username);
                dashboardView.setVisible(true);

                // Setting Dashboard session watcher
                SessionManager.login(username);
                SessionTimeoutWatcher.start(dashboardView);

                new DashboardController(dashboardView);
                LoginUI.dispose(); // Close Login screen

            } else {
                AuditLogger.log(username, "Login Attempt", "Failed");
                Notifier.showNotification("Invalid Credentials Provided!", "Login Alert");
            }
        }
    }
}

