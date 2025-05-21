package com.bankapp.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.bankapp.gui.ScheduledTransferUI;
import com.bankapp.manager.DatabaseDataManager;


public class ScheduledTransferController {
    private ScheduledTransferUI view;

    public ScheduledTransferController(ScheduledTransferUI view) {
        this.view = view;
        this.view.getScheduleButton().addActionListener(new ScheduleListener());
    }

    class ScheduleListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fromAccount = view.getFromAccountField().getText().trim();
            String toAccount = view.getToAccountField().getText().trim();
            String amountText = view.getAmountField().getText().trim();
            String delayText = view.getDelayField().getText().trim();

            if (fromAccount.isEmpty() || toAccount.isEmpty() || amountText.isEmpty() || delayText.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please fill all fields.");
                return;
            }

            try {
                double amount = Double.parseDouble(amountText);
                int delaySeconds = Integer.parseInt(delayText);

                DatabaseDataManager.scheduleTransfer(fromAccount, toAccount, amount, delaySeconds);

                JOptionPane.showMessageDialog(view, "Transfer scheduled successfully!");
                view.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid amount or delay entered.");
            }
        }
    }
}
