package com.electricity.billing.models;

import java.time.LocalDateTime;

/**
 * New Customer class
 * Extends Customer class - demonstrates Inheritance
 */
public class NewCustomer extends Customer {
    private static final long serialVersionUID = 1L;
    
    private LocalDateTime registrationDate;
    private String contractPath; // Path to contract file
    private boolean emailSent; // Track if activation email was sent
    private String connectionStatus; // PENDING, ACTIVE, DISCONNECTED

    public NewCustomer(String customerId, String meterCode, String name, 
                       String address, String region, String phone, String email) {
        super(customerId, meterCode, name, address, region, phone, email);
        this.customerType = "NEW";
        this.registrationDate = LocalDateTime.now();
        this.contractPath = "";
        this.emailSent = false;
        this.connectionStatus = "PENDING";
    }

    public LocalDateTime getRegistrationDate() { return registrationDate; }

    public String getContractPath() { return contractPath; }
    public void setContractPath(String path) { this.contractPath = path; }

    public boolean isEmailSent() { return emailSent; }
    public void setEmailSent(boolean sent) { this.emailSent = sent; }

    public String getConnectionStatus() { return connectionStatus; }
    public void setConnectionStatus(String status) { this.connectionStatus = status; }

    public void activateMeter() {
        this.connectionStatus = "ACTIVE";
        this.isActive = true;
    }

    public void deactivateMeter() {
        this.connectionStatus = "DISCONNECTED";
        this.isActive = false;
    }

    @Override
    public String getCustomerCategory() {
        return "NEW_CUSTOMER";
    }

    @Override
    public String toString() {
        return super.toString() + "|" + registrationDate + "|" + contractPath + 
               "|" + emailSent + "|" + connectionStatus;
    }
}
