package com.bankapp.utils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class SchedulerManager {
    private static Timer timer = new Timer(true); // background daemon thread

    public static void scheduleTransfer(String fromAccount, String toAccount, double amount, int delaySeconds) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Log the transfer action
                TransactionFileManager.recordTransaction(fromAccount, "Scheduled Transfer Out", amount);
                TransactionFileManager.recordTransaction(toAccount, "Scheduled Transfer In", amount);

                System.out.println("Scheduled transfer executed at: " + new Date());
            }
        }, delaySeconds * 1000L); // convert to milliseconds
    }
}
