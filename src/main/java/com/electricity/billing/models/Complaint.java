package com.electricity.billing.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Complaint class for customer complaints
 */
public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String complaintId;
    private String customerId;
    private String meterCode;
    private String description;
    private LocalDateTime complaintDate;
    private String complaintStatus; // OPEN, IN_PROGRESS, RESOLVED, CLOSED
    private String category; // BILLING_ISSUE, METER_FAULT, CONNECTION_ISSUE, etc.
    private String resolution;
    private LocalDateTime resolutionDate;

    public Complaint(String complaintId, String customerId, String meterCode, 
                     String description, String category) {
        this.complaintId = complaintId;
        this.customerId = customerId;
        this.meterCode = meterCode;
        this.description = description;
        this.category = category;
        this.complaintDate = LocalDateTime.now();
        this.complaintStatus = "OPEN";
        this.resolution = "";
        this.resolutionDate = null;
    }

    // Getters and Setters
    public String getComplaintId() { return complaintId; }

    public String getCustomerId() { return customerId; }

    public String getMeterCode() { return meterCode; }

    public String getDescription() { return description; }

    public LocalDateTime getComplaintDate() { return complaintDate; }

    public String getComplaintStatus() { return complaintStatus; }
    public void setComplaintStatus(String status) { this.complaintStatus = status; }

    public String getCategory() { return category; }

    public String getResolution() { return resolution; }
    public void setResolution(String resolution) { this.resolution = resolution; }

    public LocalDateTime getResolutionDate() { return resolutionDate; }
    public void setResolutionDate(LocalDateTime date) { this.resolutionDate = date; }

    public void resolve(String resolution) {
        this.resolution = resolution;
        this.complaintStatus = "RESOLVED";
        this.resolutionDate = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String resDateStr = resolutionDate != null ? resolutionDate.format(formatter) : "NULL";
        return complaintId + "|" + customerId + "|" + meterCode + "|" + 
               description + "|" + complaintDate.format(formatter) + "|" + 
               complaintStatus + "|" + category + "|" + resolution + "|" + resDateStr;
    }
}
