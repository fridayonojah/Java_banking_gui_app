package com.bankapp.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.bankapp.gui.AccountManagementUI;
import com.bankapp.gui.DashboardUI;
import com.bankapp.gui.DepositUI;
import com.bankapp.gui.LoginUI;
import com.bankapp.gui.ReportUI;
import com.bankapp.gui.ScheduledTransferUI;
import com.bankapp.gui.TransactionUI;
import com.bankapp.gui.TransferUI;
import com.bankapp.gui.WithdrawUI;
import com.bankapp.manager.DatabaseDataManager;
import com.bankapp.model.Customer;
import com.bankapp.model.SessionManager;


public class DashboardController {
    private DashboardUI dashboardView;
    private String username;
    private static int activeUserId;

    public DashboardController(DashboardUI dashboardView) {
        this.username = DashboardUI.getUsername();
        setActiveUser(username);


        this.dashboardView = dashboardView;
        this.dashboardView.getLogoutMenuItem().addActionListener(new LogoutListener());

       
        // Handle Dashboard Account management
        dashboardView.getViewAccountMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AccountManagementUI accountManagementView = new AccountManagementUI();
                accountManagementView.setVisible(true);
                new AccountManagementController(accountManagementView, username);
            }
        });


        // Handle Dashboard Transfer
        dashboardView.getTransferMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransferUI transferView = new TransferUI(username);
                transferView.setVisible(true);
                new TransferController(transferView, username);
            }
        });

        // Handle Dashboard Deposite
        dashboardView.getDepositMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DepositUI deposite = new DepositUI(username);
                deposite.setVisible(true);
                new DepositController(deposite, username);
            }
        });

        // Handle Dashboard Transfer
        dashboardView.getWithdrawMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WithdrawUI withdraw = new WithdrawUI(username);
                withdraw.setVisible(true);
                new WithdrawController(withdraw, username);
            }
        });


        // Handle Dashboard Report
        dashboardView.getReportMenuItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                ReportUI reportView = new ReportUI(getActiveUser());
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

    private static int getActiveUser(){
        return DashboardController.activeUserId;
    }

    private static int setActiveUser(String username){
        Customer customer = DatabaseDataManager.loadUserCredentials(username);
        return DashboardController.activeUserId = customer.getId();
    }


}

