package com.electricity.billing.gui;

import com.electricity.billing.services.*;
import com.electricity.billing.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Customer Dashboard
 */
public class CustomerDashboard extends JFrame {
    private String customerId;
    private Logger logger = Logger.getInstance();
    private JTabbedPane tabbedPane;

    public CustomerDashboard(String customerId) {
        this.customerId = customerId;
        setTitle("Electricity Billing System - Customer Dashboard");
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
        tabbedPane.addTab("Pay Bill", createPayBillTab());
        tabbedPane.addTab("View Bills", createViewBillsTab());
        tabbedPane.addTab("Meter Reading", createMeterReadingTab());
        tabbedPane.addTab("Complaints", createComplaintsTab());

        add(tabbedPane);
    }

    private JPanel createDashboardTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        String customerLine = CustomerService.getCustomerById(customerId);
        
        if (customerLine != null) {
            String[] data = customerLine.split("\\|");
            String name = data[2];
            String meterCode = data[1];
            double unpaidAmount = Double.parseDouble(data[9]);

            JLabel welcomeLabel = new JLabel("Welcome, " + name + "!");
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            panel.add(welcomeLabel, gbc);

            List<String> unpaidBills = CustomerService.getUnpaidBillsForCustomer(meterCode);
            
            JLabel statsLabel = new JLabel("<html>" +
                    "Customer ID: " + customerId + "<br>" +
                    "Meter Code: " + meterCode + "<br>" +
                    "Unpaid Bills: " + unpaidBills.size() + "<br>" +
                    "Total Unpaid Amount: Rs. " + String.format("%.2f", unpaidAmount) +
                    "</html>");
            statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            gbc.gridy = 1;
            panel.add(statsLabel, gbc);
        }

        return panel;
    }

    private JPanel createPayBillTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField billIdField = new JTextField(20);
        JTextField amountField = new JTextField(20);

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

        JButton payButton = new JButton("Pay Now");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(payButton, gbc);

        JTextArea resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);

        payButton.addActionListener(e -> {
            try {
                String billId = billIdField.getText().trim();
                double amount = Double.parseDouble(amountField.getText().trim());

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
                String meterCode = billData[2];

                String paymentId = PaymentService.recordPayment(billId, customerId, meterCode, 
                                                               amount, "ONLINE", "CUSTOMER");

                if (paymentId != null) {
                    resultArea.setText("Payment successful!\n" +
                                     "Payment ID: " + paymentId + "\n" +
                                     "Amount: Rs. " + amount);
                    
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

    private JPanel createViewBillsTab() {
        JPanel panel = new JPanel(new BorderLayout());

        String customerLine = CustomerService.getCustomerById(customerId);
        JTextArea billsArea = new JTextArea(20, 80);
        billsArea.setEditable(false);
        billsArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        if (customerLine != null) {
            String[] data = customerLine.split("\\|");
            String meterCode = data[1];

            List<String> bills = BillService.getBillsForMeter(meterCode);
            StringBuilder text = new StringBuilder();
            text.append("YOUR BILLS\n");
            text.append("=".repeat(100)).append("\n");
            
            if (bills.isEmpty()) {
                text.append("No bills found");
            } else {
                for (String bill : bills) {
                    text.append(bill).append("\n");
                }
            }
            
            billsArea.setText(text.toString());
        }

        panel.add(new JScrollPane(billsArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMeterReadingTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField readingField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Meter Reading:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(readingField, gbc);

        JButton submitButton = new JButton("Submit Reading");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        inputPanel.add(submitButton, gbc);

        JTextArea resultArea = new JTextArea(15, 50);
        resultArea.setEditable(false);

        submitButton.addActionListener(e -> {
            try {
                String customerLine_inner = CustomerService.getCustomerById(customerId);
                if (customerLine_inner != null) {
                    String[] data = customerLine_inner.split("\\|");
                    double reading = Double.parseDouble(readingField.getText().trim());
                    
                    if (reading < 0) {
                        resultArea.setText("Error: Meter reading cannot be negative");
                        return;
                    }
                    
                    resultArea.setText("Meter reading submitted successfully!\n" +
                                     "Reading: " + reading + " units\n" +
                                     "Thank you!");
                    
                    readingField.setText("");
                }
            } catch (NumberFormatException ex) {
                resultArea.setText("Error: Invalid meter reading");
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createComplaintsTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JComboBox<String> categoryCombo = new JComboBox<>(
            new String[]{"BILLING_ISSUE", "METER_FAULT", "CONNECTION_ISSUE", "OTHER"});
        JTextArea descriptionArea = new JTextArea(5, 30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(categoryCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(new JScrollPane(descriptionArea), gbc);

        JButton submitButton = new JButton("File Complaint");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        inputPanel.add(submitButton, gbc);

        JTextArea resultArea = new JTextArea(10, 50);
        resultArea.setEditable(false);

        submitButton.addActionListener(e -> {
            String customerLine_inner = CustomerService.getCustomerById(customerId);
            if (customerLine_inner != null) {
                String[] data = customerLine_inner.split("\\|");
                String meterCode = data[1];
                String category = (String) categoryCombo.getSelectedItem();
                String description = descriptionArea.getText().trim();

                if (description.isEmpty()) {
                    resultArea.setText("Error: Please enter complaint description");
                    return;
                }

                String complaintId = ComplaintService.fileComplaint(customerId, meterCode, 
                                                                   description, category);

                if (complaintId != null) {
                    resultArea.setText("Complaint filed successfully!\n" +
                                     "Complaint ID: " + complaintId + "\n" +
                                     "Our team will review and respond within 48 hours.");
                    descriptionArea.setText("");
                } else {
                    resultArea.setText("Error: Failed to file complaint");
                }
            }
        });

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        return panel;
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
