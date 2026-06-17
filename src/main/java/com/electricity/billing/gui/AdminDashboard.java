package com.electricity.billing.gui;

import com.electricity.billing.services.*;
import com.electricity.billing.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Admin Dashboard
 */
public class AdminDashboard extends JFrame {
    private String adminId;
    private Logger logger = Logger.getInstance();
    private JTabbedPane tabbedPane;

    public AdminDashboard(String adminId) {
        this.adminId = adminId;
        setTitle("Electricity Billing System - Admin Dashboard");
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
        tabbedPane.addTab("Bills", createBillsTab());
        tabbedPane.addTab("Users", createUsersTab());
        tabbedPane.addTab("Reports", createReportsTab());

        add(tabbedPane);
    }

    private JPanel createDashboardTab() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel welcomeLabel = new JLabel("Welcome to Admin Dashboard");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        // Statistics
        double totalCollected = PaymentService.getTotalCollectedAmount();
        double totalOutstanding = BillService.getTotalOutstandingAmount();
        int totalCustomers = CustomerService.getAllCustomers().size();
        int totalUsers = UserService.getAllUsers().size();

        JLabel statsLabel = new JLabel("<html>" +
                "Total Customers: " + totalCustomers + "<br>" +
                "Total Users: " + totalUsers + "<br>" +
                "Total Collected: Rs. " + String.format("%.2f", totalCollected) + "<br>" +
                "Outstanding Amount: Rs. " + String.format("%.2f", totalOutstanding) +
                "</html>");
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(statsLabel, gbc);

        return panel;
    }

    private JPanel createBillsTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel filterPanel = new JPanel();
        JComboBox<String> regionCombo = new JComboBox<>(new String[]{"All", "North", "South", "East", "West"});
        JButton filterButton = new JButton("Filter");

        filterPanel.add(new JLabel("Region:"));
        filterPanel.add(regionCombo);
        filterPanel.add(filterButton);

        JTextArea billsArea = new JTextArea(20, 80);
        billsArea.setEditable(false);
        billsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        filterButton.addActionListener(e -> {
            String region = (String) regionCombo.getSelectedItem();
            StringBuilder text = new StringBuilder();
            
            if (region.equals("All")) {
                for (String bill : BillService.getAllBills()) {
                    text.append(bill).append("\n");
                }
            } else {
                for (String bill : BillService.getBillsByRegion(region)) {
                    text.append(bill).append("\n");
                }
            }
            
            billsArea.setText(text.toString());
        });

        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(billsArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createUsersTab() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton addAdminButton = new JButton("Add Admin");
        JButton addOperatorButton = new JButton("Add Operator");
        JButton viewAllButton = new JButton("View All");

        addAdminButton.addActionListener(e -> showAddAdminDialog());
        addOperatorButton.addActionListener(e -> showAddOperatorDialog());
        viewAllButton.addActionListener(e -> showAllUsers());

        buttonPanel.add(addAdminButton);
        buttonPanel.add(addOperatorButton);
        buttonPanel.add(viewAllButton);

        JTextArea usersArea = new JTextArea(20, 80);
        usersArea.setEditable(false);
        usersArea.setFont(new Font("Monospaced", Font.PLAIN, 11));

        panel.add(buttonPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(usersArea), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportsTab() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel reportTitle = new JLabel("Consumption Statistics by Region");
        reportTitle.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(reportTitle, gbc);

        String[] regions = {"North", "South", "East", "West"};
        gbc.gridwidth = 1;
        
        for (int i = 0; i < regions.length; i++) {
            List<String> regionBills = BillService.getBillsByRegion(regions[i]);
            double totalConsumption = 0;
            for (String bill : regionBills) {
                String[] data = bill.split("\\|");
                if (data.length > 5) {
                    totalConsumption += Double.parseDouble(data[5]);
                }
            }
            
            JLabel regionLabel = new JLabel(regions[i] + ": " + totalConsumption + " units");
            regionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            gbc.gridy = i + 1;
            panel.add(regionLabel, gbc);
        }

        return panel;
    }

    private void showAddAdminDialog() {
        JDialog dialog = new JDialog(this, "Add Admin User", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField deptField = new JTextField(20);
        JComboBox<String> levelCombo = new JComboBox<>(new String[]{"SUPER", "REGULAR"});

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        panel.add(deptField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Level:"), gbc);
        gbc.gridx = 1;
        panel.add(levelCombo, gbc);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            String userId = UserService.createAdmin(nameField.getText(), emailField.getText(),
                    phoneField.getText(), new String(passwordField.getPassword()),
                    deptField.getText(), (String) levelCombo.getSelectedItem());
            
            if (userId != null) {
                JOptionPane.showMessageDialog(dialog, "Admin created successfully: " + userId);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to create admin", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(createButton, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showAddOperatorDialog() {
        JDialog dialog = new JDialog(this, "Add Operator User", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField regionField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Region:"), gbc);
        gbc.gridx = 1;
        panel.add(regionField, gbc);

        JButton createButton = new JButton("Create");
        createButton.addActionListener(e -> {
            String userId = UserService.createOperator(nameField.getText(), emailField.getText(),
                    phoneField.getText(), new String(passwordField.getPassword()),
                    regionField.getText());
            
            if (userId != null) {
                JOptionPane.showMessageDialog(dialog, "Operator created successfully: " + userId);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to create operator", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(createButton, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showAllUsers() {
        JDialog dialog = new JDialog(this, "All Users", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);

        JTextArea usersArea = new JTextArea();
        usersArea.setEditable(false);
        usersArea.setFont(new Font("Monospaced", Font.PLAIN, 10));

        StringBuilder text = new StringBuilder();
        for (String user : UserService.getAllUsers()) {
            text.append(user).append("\n");
        }
        usersArea.setText(text.toString());

        dialog.add(new JScrollPane(usersArea));
        dialog.setVisible(true);
    }

    private void logout() {
        dispose();
        new LoginFrame().setVisible(true);
    }
}
