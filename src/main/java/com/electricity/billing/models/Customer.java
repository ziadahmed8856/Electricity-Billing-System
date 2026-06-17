package com.electricity.billing.models;

import java.io.Serializable;

/**
 * Base Customer class with Encapsulation
 * Demonstrates Inheritance and Polymorphism
 */
public abstract class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String customerId;
    protected String meterCode;
    protected String name;
    protected String address;
    protected String region;
    protected String phone;
    protected String email;
    protected String customerType; // OLD, NEW
    protected boolean isActive;
    protected Meter meter;
    protected double totalUnpaidAmount;

    public Customer(String customerId, String meterCode, String name, 
                    String address, String region, String phone, String email) {
        this.customerId = customerId;
        this.meterCode = meterCode;
        this.name = name;
        this.address = address;
        this.region = region;
        this.phone = phone;
        this.email = email;
        this.isActive = true;
        this.totalUnpaidAmount = 0.0;
        this.meter = new Meter(meterCode, customerId, "DIGITAL");
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    
    public String getMeterCode() { return meterCode; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCustomerType() { return customerType; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public Meter getMeter() { return meter; }

    public double getTotalUnpaidAmount() { return totalUnpaidAmount; }
    public void setTotalUnpaidAmount(double amount) { this.totalUnpaidAmount = amount; }

    public void addUnpaidAmount(double amount) {
        this.totalUnpaidAmount += amount;
    }

    public void reduceUnpaidAmount(double amount) {
        this.totalUnpaidAmount = Math.max(0, this.totalUnpaidAmount - amount);
    }

    // Abstract method
    public abstract String getCustomerCategory();

    @Override
    public String toString() {
        return customerId + "|" + meterCode + "|" + name + "|" + address + "|" + 
               region + "|" + phone + "|" + email + "|" + customerType + "|" + 
               isActive + "|" + totalUnpaidAmount;
    }
}
