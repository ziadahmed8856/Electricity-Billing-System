package com.electricity.billing.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Payment class for tracking payments
 */
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String paymentId;
    private String billId;
    private String customerId;
    private String meterCode;
    private double paymentAmount;
    private LocalDateTime paymentDate;
    private String paymentMethod; // CASH, CHEQUE, ONLINE
    private String operatorId;
    private String paymentStatus; // SUCCESS, FAILED, PENDING

    public Payment(String paymentId, String billId, String customerId, 
                   String meterCode, double amount, String method, String operatorId) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.customerId = customerId;
        this.meterCode = meterCode;
        this.paymentAmount = amount;
        this.paymentMethod = method;
        this.operatorId = operatorId;
        this.paymentDate = LocalDateTime.now();
        this.paymentStatus = "SUCCESS";
    }

    // Getters and Setters
    public String getPaymentId() { return paymentId; }

    public String getBillId() { return billId; }

    public String getCustomerId() { return customerId; }

    public String getMeterCode() { return meterCode; }

    public double getPaymentAmount() { return paymentAmount; }

    public LocalDateTime getPaymentDate() { return paymentDate; }

    public String getPaymentMethod() { return paymentMethod; }

    public String getOperatorId() { return operatorId; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String status) { this.paymentStatus = status; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return paymentId + "|" + billId + "|" + customerId + "|" + meterCode + "|" + 
               paymentAmount + "|" + paymentDate.format(formatter) + "|" + 
               paymentMethod + "|" + operatorId + "|" + paymentStatus;
    }
}
