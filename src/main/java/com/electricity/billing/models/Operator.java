package com.electricity.billing.models;

/**
 * Operator User class
 * Extends User with operator-specific functionality
 */
public class Operator extends User {
    private static final long serialVersionUID = 1L;
    
    private String region;
    private double collectionsToday;
    private int billsProcessed;

    public Operator(String userId, String name, String email, String phone, 
                    String password, String region) {
        super(userId, name, email, phone, password, "OPERATOR");
        this.region = region;
        this.collectionsToday = 0.0;
        this.billsProcessed = 0;
    }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public double getCollectionsToday() { return collectionsToday; }
    public void setCollectionsToday(double collectionsToday) { 
        this.collectionsToday = collectionsToday; 
    }

    public int getBillsProcessed() { return billsProcessed; }
    public void setBillsProcessed(int billsProcessed) { 
        this.billsProcessed = billsProcessed; 
    }

    @Override
    public String getUserType() {
        return "OPERATOR";
    }

    @Override
    public String toString() {
        return super.toString() + "|" + region + "|" + collectionsToday + 
               "|" + billsProcessed;
    }
}
