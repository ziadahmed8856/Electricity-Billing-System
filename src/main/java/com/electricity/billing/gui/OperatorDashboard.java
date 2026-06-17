package com.electricity.billing.gui;

import com.electricity.billing.services.*;
import com.electricity.billing.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Operator Dashboard
 */
public class OperatorDashboard extends JFrame {
    private String operatorId;
    private Logger logger = Logger.getInstance();
    private JTabbedPane tabbedPane;

    public OperatorDashboard(String operatorId) {
        this.operatorId = operatorId;
        setTitle("Electricity Billing System - Operator Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        createUI();
    }

    private void createUI() {
        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.addActionListener(e -> logout());
        fileMenu.add(logoutItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardTab());
        tabbedPane.addTab("Collect Payment", createPaymentTab());
        tabbedPane.addTab("Print Bill", createPrintBillTab());
        tabbedPane.addTab("View Bills", createViewBillsTab());

        add(tabbedPane);
    }

    private JPanel createDashboardTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome, Operator!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        // Operator Statistics
        List<String> operatorPayments = PaymentService.getPaymentsByOperator(operatorId);
        double collectionsToday = 0;
        for (String payment : operatorPayments) {
            String[] data = payment.split("\\|");
            collectionsToday += Double.parseDouble(data[4]);
        }

        JLabel statsLabel = new JLabel("<html>" +
                "Operator ID: " + operatorId + "<br>" +
                "Payments Collected Today: " + operatorPayments.size() + "<br>" +
                "Total Amount Collected: Rs. " + String.format("%.2f", collectionsToday) +
                "</html>");
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(statsLabel, gbc);

        return panel;
    }

    private JPanel createPaymentTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField billIdField = new JTextField(20);
        JTextField amountField = new JTextField(20);
        JComboBox<String> methodCombo = new JComboBox<>(new String[]{"CASH", "CHEQUE", "ONLINE"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Bill ID:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(billIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Method:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(methodCombo, gbc);

        JButton submitButton = new JButton("Process Payment");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        inputPanel.add(submitButton, gbc);

        JTextArea resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);

        submitButton.addActionListener(e -> {
            try {
                String billId = billIdField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                String method = (String) methodCombo.getSelectedItem();

                if (billId.isEmpty()) {
                    resultArea.setText("Error: Bill ID is required");
                    return;
                }

                String billLine = BillService.getBillById(billId);
                if (billLine == null) {
                    resultArea.setText("Error: Bill not found");
                    return;
                }

                String[] billData = billLine.split("\\|");
                String customerId = billData[1];
                String meterCode = billData[2];

                String paymentId = PaymentService.recordPayment(billId, customerId, meterCode, 
                                                               amount, method, operatorId);

                if (paymentId != null) {
                    resultArea.setText("Payment processed successfully!\n" +
                                     "Payment ID: " + paymentId + "\n" +
                                     "Amount: Rs. " + amount + "\n" +
                                     "Method: " + method);
                    
                    billIdField.setText("");
                    amountField.setText("");
                } else {
                    resultArea.setText("Error: Failed to process payment");
                }
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Invalid amount");
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createPrintBillTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        JTextField meterCodeField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        inputPanel.add(new JLabel("Meter Code:"));
        inputPanel.add(meterCodeField);
        inputPanel.add(searchButton);

        JTextArea billArea = new JTextArea(20, 80);
        billArea.setEditable(false);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        searchButton.addActionListener(e -> {
            String meterCode = meterCodeField.getText().trim();
            if (meterCode.isEmpty()) {
                billArea.setText("Please enter a meter code");
                return;
            }

            List<String> bills = BillService.getBillsForMeter(meterCode);
            StringBuilder text = new StringBuilder();
            text.append("BILLS FOR METER: ").append(meterCode).append("\n");
            text.append("=".repeat(100)).append("\n");
            
            if (bills.isEmpty()) {
                text.append("No bills found");
            } else {
                for (String bill : bills) {
                    text.append(bill).append("\n");
                }
            }
            
            billArea.setText(text.toString());
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(billArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createViewBillsTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel();
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{"All", "PENDING", "PAID", "OVERDUE"});
        JButton filterButton = new JButton("Filter");

        filterPanel.add(new JLabel("Status:"));
        filterPanel.add(statusCombo);
        filterPanel.add(filterButton);

        JTextArea billsArea = new JTextArea(20, 80);
        billsArea.setEditable(false);
        billsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        filterButton.addActionListener(e -> {
            String status = (String) statusCombo.getSelectedItem();
            StringBuilder text = new StringBuilder();

            if (status.equals("All")) {
                for (String bill : BillService.getAllBills()) {
                    text.append(bill).append("\n");
                }
            } else {
                for (String bill : BillService.getBillsByStatus(status)) {
                    text.append(bill).append("\n");
                }
            }

            billsArea.setText(text.toString());
        });

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(billsArea), BorderLayout.CENTER);

        return panel;
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
