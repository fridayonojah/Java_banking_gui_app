package com.bankapp.reports;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class ReportChartGenerator {

    public static JPanel createTransactionBarChart(String accountId, int[] deposits, int[] withdrawals, String[] months) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (int i = 0; i < months.length; i++) {
            dataset.addValue(deposits[i], "Deposits", months[i]);
            dataset.addValue(withdrawals[i], "Withdrawals", months[i]);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Monthly Transaction Summary - " + accountId,
                "Month",
                "Amount",
                dataset
        );

        return new ChartPanel(barChart);
    }
}
