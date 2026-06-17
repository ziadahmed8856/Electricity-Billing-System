package com.electricity.billing.services;

import com.electricity.billing.models.*;
import com.electricity.billing.utils.*;
import java.util.*;

/**
 * Service class for managing users (Admin, Operator, Customer)
 */
public class UserService {
    private static Logger logger = Logger.getInstance();

    /**
     * Create admin user
     */
    public static String createAdmin(String name, String email, String phone, String password, 
                                     String department, String adminLevel) {
        if (!Validator.isValidName(name) || !Validator.isValidEmail(email) || 
            !Validator.isValidPhone(phone) || !Validator.isValidPassword(password)) {
            logger.warning("Invalid input for admin creation");
            return null;
        }

        String userId = IDGenerator.generateUserId("ADMIN");
        Admin admin = new Admin(userId, name, email, phone, password, department, adminLevel);
        
        FileHandler.appendToFile("users.txt", admin.toString());
        logger.info("Admin user created: " + userId);
        
        return userId;
    }

    /**
     * Create operator user
     */
    public static String createOperator(String name, String email, String phone, String password, 
                                       String region) {
        if (!Validator.isValidName(name) || !Validator.isValidEmail(email) || 
            !Validator.isValidPhone(phone) || !Validator.isValidPassword(password) ||
            !Validator.isValidRegion(region)) {
            logger.warning("Invalid input for operator creation");
            return null;
        }

        String userId = IDGenerator.generateUserId("OPERATOR");
        Operator operator = new Operator(userId, name, email, phone, password, region);
        
        FileHandler.appendToFile("users.txt", operator.toString());
        logger.info("Operator user created: " + userId);
        
        return userId;
    }

    /**
     * Authenticate user
     */
    public static String authenticateUser(String userId, String password) {
        List<String> lines = FileHandler.readFromFile("users.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 4 && data[0].equals(userId)) {
                if (data[4].equals(password) && Boolean.parseBoolean(data[8])) {
                    logger.info("User authenticated: " + userId);
                    return data[5]; // Return role
                } else if (!data[4].equals(password)) {
                    logger.warning("Invalid password for user: " + userId);
                } else {
                    logger.warning("User is inactive: " + userId);
                }
            }
        }
        
        logger.warning("User not found: " + userId);
        return null;
    }

    /**
     * Get user by ID
     */
    public static String getUserById(String userId) {
        List<String> lines = FileHandler.readFromFile("users.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 0 && data[0].equals(userId)) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get all users
     */
    public static List<String> getAllUsers() {
        return FileHandler.readFromFile("users.txt");
    }

    /**
     * Get users by role
     */
    public static List<String> getUsersByRole(String role) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("users.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 5 && data[5].equals(role)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Update user
     */
    public static boolean updateUser(String oldData, String newData) {
        boolean success = FileHandler.updateInFile("users.txt", oldData, newData);
        if (success) {
            logger.info("User updated successfully");
        }
        return success;
    }

    /**
     * Deactivate user
     */
    public static boolean deactivateUser(String userId) {
        String userLine = getUserById(userId);
        if (userLine == null) {
            return false;
        }

        String[] data = userLine.split("\\|");
        data[8] = "false"; // Set isActive to false
        String updatedLine = String.join("|", data);
        
        return FileHandler.updateInFile("users.txt", userLine, updatedLine);
    }

    /**
     * Change password
     */
    public static boolean changePassword(String userId, String oldPassword, String newPassword) {
        String userLine = getUserById(userId);
        if (userLine == null) {
            return false;
        }

        String[] data = userLine.split("\\|");
        if (!data[4].equals(oldPassword)) {
            logger.warning("Old password is incorrect for user: " + userId);
            return false;
        }

        if (!Validator.isValidPassword(newPassword)) {
            logger.warning("New password doesn't meet requirements");
            return false;
        }

        data[4] = newPassword;
        String updatedLine = String.join("|", data);
        
        boolean success = FileHandler.updateInFile("users.txt", userLine, updatedLine);
        if (success) {
            logger.info("Password changed for user: " + userId);
        }
        
        return success;
    }

    /**
     * Delete user
     */
    public static boolean deleteUser(String userId) {
        String userLine = getUserById(userId);
        if (userLine == null) {
            return false;
        }

        boolean success = FileHandler.deleteFromFile("users.txt", userLine);
        if (success) {
            logger.info("User deleted: " + userId);
        }
        
        return success;
    }
}
