package com.bankapp.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.bankapp.gui.AccountManagementUI;
import com.bankapp.gui.DashboardUI;
import com.bankapp.gui.LoginUI;
import com.bankapp.gui.ReportUI;
import com.bankapp.gui.ScheduledTransferUI;
import com.bankapp.gui.TransactionUI;
import com.bankapp.model.SessionManager;


public class DashboardController {
    private DashboardUI dashboardView;

    public DashboardController(DashboardUI dashboardView) {
        this.dashboardView = dashboardView;
        this.dashboardView.getLogoutMenuItem().addActionListener(new LogoutListener());

       
        // Handle Dashboard Account management
        dashboardView.getViewAccountMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountManagementUI accountManagementView = new AccountManagementUI();
                accountManagementView.setVisible(true);
                new AccountManagementController(accountManagementView, DashboardUI.getUsername());
            }
        });


        // Handle Dashboard Transactions
        dashboardView.getTransactionMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransactionUI transactionView = new TransactionUI();
                transactionView.setVisible(true);
                new TransactionController(transactionView, DashboardUI.getUsername());
            }
        });


        // Handle Dashboard Report
        dashboardView.getReportMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportUI reportView = new ReportUI();
                reportView.setVisible(true);
                new ReportController(reportView);
            }
        });

        dashboardView.getScheduleTransferMenuItem().addActionListener(e -> {
            ScheduledTransferUI scheduledTransferView = new ScheduledTransferUI();
            scheduledTransferView.setVisible(true);
            new ScheduledTransferController(scheduledTransferView);
        });

    }

    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(dashboardView, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

               // Clear session state after user logout
               SessionManager.logout();
               SessionTimeoutWatcher.stop();
               dashboardView.dispose(); // Close Dashboard
               new BankController(new LoginUI()); // Go back to login
            }
        }
    }
}

