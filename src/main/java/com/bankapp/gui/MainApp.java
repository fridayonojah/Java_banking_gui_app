// Main Application entry point
package com.bankapp.gui;

import com.bankapp.controller.BankController;

public class MainApp {
    public static void main(String[] args) {
        LoginUI loginView = new LoginUI();
       
        new BankController(loginView);
        loginView.setVisible(true);
    }
}
