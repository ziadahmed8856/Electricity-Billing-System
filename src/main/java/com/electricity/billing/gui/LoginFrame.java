package com.electricity.billing.gui;

import com.electricity.billing.services.*;
import com.electricity.billing.utils.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Login Frame for user authentication
 */
public class LoginFrame extends JFrame {
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JButton loginButton;
    private JButton exitButton;
    private Logger logger = Logger.getInstance();

    public LoginFrame() {
        setTitle("Electricity Billing System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        createUI();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Title
        JLabel titleLabel = new JLabel("Electricity Billing System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(titleLabel, gbc);

        // User ID
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("User ID:"), gbc);

        userIdField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(userIdField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        // Role
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("Role:"), gbc);

        roleCombo = new JComboBox<>(new String[]{"ADMIN", "OPERATOR", "CUSTOMER"});
        gbc.gridx = 1;
        mainPanel.add(roleCombo, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void handleLogin() {
        String userId = userIdField.getText().trim();
        String password = new String(passwordField.getPassword());
        String role = (String) roleCombo.getSelectedItem();

        if (userId.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter User ID and Password", 
                                        "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String authenticatedRole = UserService.authenticateUser(userId, password);
        
        if (authenticatedRole != null && authenticatedRole.equals(role)) {
            logger.info("User logged in: " + userId + " with role: " + role);
            openDashboard(userId, role);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials or role mismatch", 
                                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            logger.warning("Failed login attempt for user: " + userId);
        }
    }

    private void openDashboard(String userId, String role) {
        if (role.equals("ADMIN")) {
            new AdminDashboard(userId).setVisible(true);
        } else if (role.equals("OPERATOR")) {
            new OperatorDashboard(userId).setVisible(true);
        } else if (role.equals("CUSTOMER")) {
            new CustomerDashboard(userId).setVisible(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
