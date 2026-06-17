package com.electricity.billing.services;

import com.electricity.billing.models.*;
import com.electricity.billing.utils.*;
import java.util.*;

/**
 * Service class for managing complaints
 */
public class ComplaintService {
    private static Logger logger = Logger.getInstance();

    /**
     * File complaint
     */
    public static String fileComplaint(String customerId, String meterCode, String description, 
                                       String category) {
        if (!Validator.isValidMeterCode(meterCode) || description == null || description.isEmpty()) {
            logger.warning("Invalid input for complaint filing");
            return null;
        }

        String complaintId = IDGenerator.generateComplaintId();
        Complaint complaint = new Complaint(complaintId, customerId, meterCode, description, category);
        
        FileHandler.appendToFile("complaints.txt", complaint.toString());
        logger.info("Complaint filed: " + complaintId);
        
        // Send acknowledgement email
        String customerLine = CustomerService.getCustomerById(customerId);
        if (customerLine != null) {
            String[] data = customerLine.split("\\|");
            if (data.length > 6) {
                String email = data[6];
                String name = data[2];
                EmailSimulator.sendComplaintAcknowledgement(email, name, complaintId);
            }
        }
        
        return complaintId;
    }

    /**
     * Get complaint by ID
     */
    public static String getComplaintById(String complaintId) {
        List<String> lines = FileHandler.readFromFile("complaints.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 0 && data[0].equals(complaintId)) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get complaints for customer
     */
    public static List<String> getComplaintsForCustomer(String customerId) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("complaints.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 1 && data[1].equals(customerId)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get complaints for meter
     */
    public static List<String> getComplaintsForMeter(String meterCode) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("complaints.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 2 && data[2].equals(meterCode)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get open complaints
     */
    public static List<String> getOpenComplaints() {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("complaints.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 5 && data[5].equals("OPEN")) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Resolve complaint
     */
    public static boolean resolveComplaint(String complaintId, String resolution) {
        String complaintLine = getComplaintById(complaintId);
        if (complaintLine == null) {
            return false;
        }

        String[] data = complaintLine.split("\\|");
        data[5] = "RESOLVED";
        data[7] = resolution;
        data[8] = java.time.LocalDateTime.now().toString();
        
        String updatedLine = String.join("|", data);
        boolean success = FileHandler.updateInFile("complaints.txt", complaintLine, updatedLine);
        
        if (success) {
            logger.info("Complaint resolved: " + complaintId);
        }
        
        return success;
    }

    /**
     * Get all complaints
     */
    public static List<String> getAllComplaints() {
        return FileHandler.readFromFile("complaints.txt");
    }

    /**
     * Get complaints by status
     */
    public static List<String> getComplaintsByStatus(String status) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("complaints.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 5 && data[5].equals(status)) {
                results.add(line);
            }
        }
        
        return results;
    }
}
