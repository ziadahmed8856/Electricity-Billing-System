package com.electricity.billing;

import com.electricity.billing.gui.LoginFrame;
import com.electricity.billing.services.*;
import com.electricity.billing.utils.*;
import javax.swing.*;

/**
 * Main Application Entry Point
 */
public class ElectricityBillingApp {
    private static Logger logger = Logger.getInstance();

    public static void main(String[] args) {
        logger.info("=== Electricity Billing System Started ===");
        
        // Initialize system
        initializeSystem();
        
        // Launch GUI
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }

    private static void initializeSystem() {
        logger.info("Initializing system...");
        
        // Create default admin user if not exists
        String adminUser = UserService.getUserById("ADM1");
        if (adminUser == null) {
            String adminId = UserService.createAdmin("System Admin", "admin@electricity.com", 
                                                     "9999999999", "admin123", 
                                                     "Management", "SUPER");
            logger.info("Default admin user created: " + adminId);
        }

        // Create default tariffs if not exists
        String northTariff = TariffService.getTariffForRegion("North");
        if (northTariff == null) {
            TariffService.createTariff("North", "RESIDENTIAL", 5.50, 100.0);
            TariffService.createTariff("South", "RESIDENTIAL", 5.75, 100.0);
            TariffService.createTariff("East", "RESIDENTIAL", 5.25, 100.0);
            TariffService.createTariff("West", "RESIDENTIAL", 6.00, 100.0);
            logger.info("Default tariffs created for all regions");
        }

        logger.info("System initialized successfully");
    }
}
