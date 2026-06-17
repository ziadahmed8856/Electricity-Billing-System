package com.electricity.billing.models;

import java.time.LocalDateTime;

/**
 * Old Customer class
 * Extends Customer class - demonstrates Inheritance
 */
public class OldCustomer extends Customer {
    private static final long serialVersionUID = 1L;
    
    private LocalDateTime registrationDate;
    private int monthsUnpaid; // Track how many months unpaid
    private boolean hasContract;
    private double monthsUnpaidThreshold; // Email reminder threshold

    public OldCustomer(String customerId, String meterCode, String name, 
                       String address, String region, String phone, String email,
                       LocalDateTime registrationDate) {
        super(customerId, meterCode, name, address, region, phone, email);
        this.customerType = "OLD";
        this.registrationDate = registrationDate;
        this.monthsUnpaid = 0;
        this.hasContract = true;
        this.monthsUnpaidThreshold = 3; // Send email reminder after 3 months unpaid
    }

    public LocalDateTime getRegistrationDate() { return registrationDate; }

    public int getMonthsUnpaid() { return monthsUnpaid; }
    public void setMonthsUnpaid(int months) { this.monthsUnpaid = months; }

    public void incrementMonthsUnpaid() { this.monthsUnpaid++; }
    public void resetMonthsUnpaid() { this.monthsUnpaid = 0; }

    public boolean hasContract() { return hasContract; }
    public void setHasContract(boolean hasContract) { this.hasContract = hasContract; }

    public boolean shouldSendEmailReminder() {
        return monthsUnpaid >= monthsUnpaidThreshold && totalUnpaidAmount > 0;
    }

    @Override
    public String getCustomerCategory() {
        return "EXISTING_CUSTOMER";
    }

    @Override
    public String toString() {
        return super.toString() + "|" + registrationDate + "|" + monthsUnpaid + 
               "|" + hasContract;
    }
}
