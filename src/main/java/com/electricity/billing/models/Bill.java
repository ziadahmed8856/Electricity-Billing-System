package com.electricity.billing.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Bill class for tracking electricity bills
 */
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String billId;
    private String customerId;
    private String meterCode;
    private LocalDateTime billDate;
    private LocalDateTime dueDate;
    private double unitsConsumed;
    private double tariffRate;
    private double billAmount;
    private double paidAmount;
    private String billStatus; // PENDING, PAID, OVERDUE
    private String region;

    public Bill(String billId, String customerId, String meterCode, 
                double unitsConsumed, double tariffRate, String region) {
        this.billId = billId;
        this.customerId = customerId;
        this.meterCode = meterCode;
        this.unitsConsumed = unitsConsumed;
        this.tariffRate = tariffRate;
        this.billAmount = unitsConsumed * tariffRate;
        this.region = region;
        this.billDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now().plusDays(30); // 30 days payment deadline
        this.paidAmount = 0.0;
        this.billStatus = "PENDING";
    }

    // Getters and Setters
    public String getBillId() { return billId; }

    public String getCustomerId() { return customerId; }

    public String getMeterCode() { return meterCode; }

    public LocalDateTime getBillDate() { return billDate; }

    public LocalDateTime getDueDate() { return dueDate; }

    public double getUnitsConsumed() { return unitsConsumed; }

    public double getTariffRate() { return tariffRate; }

    public double getBillAmount() { return billAmount; }

    public double getPaidAmount() { return paidAmount; }
    public void setPaidAmount(double amount) { this.paidAmount = amount; }

    public double getOutstandingAmount() {
        return Math.max(0, billAmount - paidAmount);
    }

    public String getBillStatus() { return billStatus; }
    public void setBillStatus(String status) { this.billStatus = status; }

    public String getRegion() { return region; }

    public void addPayment(double amount) {
        this.paidAmount += amount;
        if (this.paidAmount >= this.billAmount) {
            this.billStatus = "PAID";
        }
    }

    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate) && 
               !billStatus.equals("PAID");
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return billId + "|" + customerId + "|" + meterCode + "|" + 
               billDate.format(formatter) + "|" + dueDate.format(formatter) + "|" + 
               unitsConsumed + "|" + tariffRate + "|" + billAmount + "|" + 
               paidAmount + "|" + billStatus + "|" + region;
    }
}
