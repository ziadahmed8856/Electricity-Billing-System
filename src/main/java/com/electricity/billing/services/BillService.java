package com.electricity.billing.services;

import com.electricity.billing.models.*;
import com.electricity.billing.utils.*;
import java.util.*;

/**
 * Service class for managing bills
 */
public class BillService {
    private static Logger logger = Logger.getInstance();

    /**
     * Create bill for customer
     */
    public static String createBill(String customerId, String meterCode, double unitsConsumed, 
                                   double tariffRate, String region) {
        if (!Validator.isValidPositiveNumber(unitsConsumed) || !Validator.isValidTariffRate(tariffRate)) {
            logger.warning("Invalid input for bill creation");
            return null;
        }

        String billId = IDGenerator.generateBillId();
        Bill bill = new Bill(billId, customerId, meterCode, unitsConsumed, tariffRate, region);
        
        FileHandler.appendToFile("bills.txt", bill.toString());
        logger.info("Bill created: " + billId + " for meter: " + meterCode);
        
        return billId;
    }

    /**
     * Get bill by bill ID
     */
    public static String getBillById(String billId) {
        List<String> lines = FileHandler.readFromFile("bills.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 0 && data[0].equals(billId)) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get bills for customer
     */
    public static List<String> getBillsForCustomer(String customerId) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("bills.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 1 && data[1].equals(customerId)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get bills for meter
     */
    public static List<String> getBillsForMeter(String meterCode) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("bills.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 2 && data[2].equals(meterCode)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get bills by region
     */
    public static List<String> getBillsByRegion(String region) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("bills.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 10 && data[10].equals(region)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get unpaid bills
     */
    public static List<String> getUnpaidBills() {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("bills.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 9 && data[9].equals("PENDING")) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Update bill status
     */
    public static boolean updateBillStatus(String billId, String newStatus) {
        String billLine = getBillById(billId);
        if (billLine == null) {
            return false;
        }

        String[] data = billLine.split("\\|");
        data[9] = newStatus;
        String updatedLine = String.join("|", data);
        
        boolean success = FileHandler.updateInFile("bills.txt", billLine, updatedLine);
        if (success) {
            logger.info("Bill status updated: " + billId + " -> " + newStatus);
        }
        
        return success;
    }

    /**
     * Get total outstanding amount
     */
    public static double getTotalOutstandingAmount() {
        List<String> lines = FileHandler.readFromFile("bills.txt");
        double total = 0.0;
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 9 && data[9].equals("PENDING")) {
                try {
                    double billAmount = Double.parseDouble(data[7]);
                    double paidAmount = Double.parseDouble(data[8]);
                    total += (billAmount - paidAmount);
                } catch (NumberFormatException e) {
                    logger.error("Error parsing bill data: " + e.getMessage());
                }
            }
        }
        
        return total;
    }

    /**
     * Get bills by status
     */
    public static List<String> getBillsByStatus(String status) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("bills.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 9 && data[9].equals(status)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get all bills
     */
    public static List<String> getAllBills() {
        return FileHandler.readFromFile("bills.txt");
    }
}
