package com.bankapp.controller;

import com.bankapp.model.Customer;
import com.bankapp.utils.AuditLogger;
import com.bankapp.utils.Notifier;
import com.bankapp.utils.PasswordUtils;
import com.bankapp.gui.DashboardUI;
import com.bankapp.gui.RegistrationUI;
import com.bankapp.manager.DatabaseDataManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RegistrationController {
    private RegistrationUI RegistrationUI;

    public RegistrationController(RegistrationUI RegistrationUI) {
        this.RegistrationUI = RegistrationUI;
        this.RegistrationUI.addRegistrationListener(new RegistrationListener());
    }

    class RegistrationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = RegistrationUI.getUsername();
            String password = RegistrationUI.getPassword();
            String email = RegistrationUI.getEmail();

            Customer customer = DatabaseDataManager.loadUserCredentials(username);
            
            // Check if customer email or username already exist
            if (customer != null) {
                Notifier.showNotification("User with this email or username already exist!", "Login Attempt");
            }

            // Hash Password Here
            String passHash = PasswordUtils.hashPassword(password);
            
            // Save customer info in the database
            if (DatabaseDataManager.createUser(username, passHash, email)) {
                AuditLogger.log(customer.getUsername(), "Customer info saved successfuly", "Success");
                Notifier.showNotification("Hi " + customer.getUsername() + "your details was saved successfully ", "Registration Alert");

                RegistrationUI.dispose(); // Close Registration screen
                DashboardUI dashboardView = new DashboardUI(username);
                dashboardView.setVisible(true);
                new DashboardController(dashboardView);


            } else {
                AuditLogger.log(customer.getUsername(), "Registration Attempt", "Registration Failed!");
                Notifier.showNotification("Apologies, your details were not saved!", "Registration Failed");
            }
        }
    }
}

