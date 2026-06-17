package com.electricity.billing.services;

import com.electricity.billing.models.*;
import com.electricity.billing.utils.*;
import java.util.*;

/**
 * Service class for managing payments
 */
public class PaymentService {
    private static Logger logger = Logger.getInstance();

    /**
     * Record payment for bill
     */
    public static String recordPayment(String billId, String customerId, String meterCode, 
                                       double paymentAmount, String paymentMethod, String operatorId) {
        if (!Validator.isValidPositiveNumber(paymentAmount)) {
            logger.warning("Invalid payment amount");
            return null;
        }

        String paymentId = IDGenerator.generatePaymentId();
        Payment payment = new Payment(paymentId, billId, customerId, meterCode, 
                                     paymentAmount, paymentMethod, operatorId);
        
        FileHandler.appendToFile("payments.txt", payment.toString());
        
        // Update bill
        String billLine = BillService.getBillById(billId);
        if (billLine != null) {
            String[] data = billLine.split("\\|");
            double currentPaid = Double.parseDouble(data[8]);
            data[8] = String.valueOf(currentPaid + paymentAmount);
            
            // Update bill status if fully paid
            double billAmount = Double.parseDouble(data[7]);
            if (currentPaid + paymentAmount >= billAmount) {
                data[9] = "PAID";
            }
            
            String updatedBill = String.join("|", data);
            BillService.updateBillStatus(billId, data[9]);
        }
        
        logger.info("Payment recorded: " + paymentId + " Amount: " + paymentAmount);
        
        return paymentId;
    }

    /**
     * Get payment by payment ID
     */
    public static String getPaymentById(String paymentId) {
        List<String> lines = FileHandler.readFromFile("payments.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 0 && data[0].equals(paymentId)) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get payments for customer
     */
    public static List<String> getPaymentsForCustomer(String customerId) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("payments.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 2 && data[2].equals(customerId)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get payments for bill
     */
    public static List<String> getPaymentsForBill(String billId) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("payments.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 1 && data[1].equals(billId)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get payments by operator
     */
    public static List<String> getPaymentsByOperator(String operatorId) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("payments.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 7 && data[7].equals(operatorId)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get total collected today by operator
     */
    public static double getTotalCollectedByOperatorToday(String operatorId) {
        List<String> payments = getPaymentsByOperator(operatorId);
        double total = 0.0;
        
        for (String payment : payments) {
            String[] data = payment.split("\\|");
            try {
                total += Double.parseDouble(data[4]);
            } catch (NumberFormatException e) {
                logger.error("Error parsing payment data: " + e.getMessage());
            }
        }
        
        return total;
    }

    /**
     * Get total collected amount
     */
    public static double getTotalCollectedAmount() {
        List<String> lines = FileHandler.readFromFile("payments.txt");
        double total = 0.0;
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            try {
                total += Double.parseDouble(data[4]);
            } catch (NumberFormatException e) {
                logger.error("Error parsing payment data: " + e.getMessage());
            }
        }
        
        return total;
    }

    /**
     * Get all payments
     */
    public static List<String> getAllPayments() {
        return FileHandler.readFromFile("payments.txt");
    }
}
