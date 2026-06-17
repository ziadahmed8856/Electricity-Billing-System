package com.electricity.billing.services;

import com.electricity.billing.models.*;
import com.electricity.billing.utils.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Service class for managing customers
 */
public class CustomerService {
    private static Logger logger = Logger.getInstance();

    /**
     * Register new customer
     */
    public static boolean registerNewCustomer(String name, String address, String region, 
                                              String phone, String email) {
        // Validate input
        if (!Validator.isValidName(name) || !Validator.isValidAddress(address) || 
            !Validator.isValidRegion(region) || !Validator.isValidPhone(phone) || 
            !Validator.isValidEmail(email)) {
            logger.warning("Invalid input for new customer registration");
            return false;
        }

        String customerId = IDGenerator.generateCustomerId();
        String meterCode = IDGenerator.generateMeterCode();
        
        NewCustomer customer = new NewCustomer(customerId, meterCode, name, address, region, phone, email);
        
        FileHandler.appendToFile("customers.txt", customer.toString());
        logger.info("New customer registered: " + customerId + " with meter: " + meterCode);
        
        // Send activation email
        EmailSimulator.sendMeterActivationEmail(email, name, meterCode);
        customer.setEmailSent(true);
        
        return true;
    }

    /**
     * Add old customer (existing customer)
     */
    public static boolean addOldCustomer(String name, String address, String region, 
                                        String phone, String email) {
        if (!Validator.isValidName(name) || !Validator.isValidAddress(address) || 
            !Validator.isValidRegion(region) || !Validator.isValidPhone(phone) || 
            !Validator.isValidEmail(email)) {
            logger.warning("Invalid input for old customer registration");
            return false;
        }

        String customerId = IDGenerator.generateCustomerId();
        String meterCode = IDGenerator.generateMeterCode();
        
        OldCustomer customer = new OldCustomer(customerId, meterCode, name, address, region, phone, 
                                              email, LocalDateTime.now());
        
        FileHandler.appendToFile("customers.txt", customer.toString());
        logger.info("Old customer added: " + customerId);
        
        return true;
    }

    /**
     * Get customer by meter code
     */
    public static String getCustomerByMeterCode(String meterCode) {
        List<String> lines = FileHandler.readFromFile("customers.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 1 && data[1].equals(meterCode)) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get customer by customer ID
     */
    public static String getCustomerById(String customerId) {
        List<String> lines = FileHandler.readFromFile("customers.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 0 && data[0].equals(customerId)) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get all customers in a region
     */
    public static List<String> getCustomersByRegion(String region) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("customers.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 4 && data[4].equals(region)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Update customer
     */
    public static boolean updateCustomer(String oldData, String newData) {
        boolean success = FileHandler.updateInFile("customers.txt", oldData, newData);
        if (success) {
            logger.info("Customer updated successfully");
        }
        return success;
    }

    /**
     * Get unpaid bills for customer
     */
    public static List<String> getUnpaidBillsForCustomer(String meterCode) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("bills.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 9 && data[2].equals(meterCode) && data[9].equals("PENDING")) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Check if customer has unpaid bills for 3 months
     */
    public static boolean hasThreeMonthsUnpaid(String meterCode) {
        List<String> unpaidBills = getUnpaidBillsForCustomer(meterCode);
        return unpaidBills.size() >= 3;
    }

    /**
     * Get all customers
     */
    public static List<String> getAllCustomers() {
        return FileHandler.readFromFile("customers.txt");
    }

    /**
     * Deactivate customer
     */
    public static boolean deactivateCustomer(String customerId) {
        String customerLine = getCustomerById(customerId);
        if (customerLine == null) {
            return false;
        }

        String[] data = customerLine.split("\\|");
        data[8] = "false"; // Set isActive to false
        String updatedLine = String.join("|", data);
        
        return FileHandler.updateInFile("customers.txt", customerLine, updatedLine);
    }
}
