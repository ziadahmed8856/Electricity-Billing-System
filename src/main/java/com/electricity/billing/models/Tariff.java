package com.electricity.billing.models;

import java.io.Serializable;

/**
 * Tariff class for managing electricity rates
 */
public class Tariff implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String tariffId;
    private String region;
    private String customerType; // RESIDENTIAL, COMMERCIAL, INDUSTRIAL
    private double ratePerUnit; // Price per kWh
    private double minimumCharge;
    private boolean isActive;

    public Tariff(String tariffId, String region, String customerType, 
                  double ratePerUnit, double minimumCharge) {
        this.tariffId = tariffId;
        this.region = region;
        this.customerType = customerType;
        this.ratePerUnit = ratePerUnit;
        this.minimumCharge = minimumCharge;
        this.isActive = true;
    }

    // Getters and Setters
    public String getTariffId() { return tariffId; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getCustomerType() { return customerType; }

    public double getRatePerUnit() { return ratePerUnit; }
    public void setRatePerUnit(double rate) { this.ratePerUnit = rate; }

    public double getMinimumCharge() { return minimumCharge; }
    public void setMinimumCharge(double charge) { this.minimumCharge = charge; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public double calculateBill(double units) {
        double bill = units * ratePerUnit;
        return Math.max(bill, minimumCharge);
    }

    @Override
    public String toString() {
        return tariffId + "|" + region + "|" + customerType + "|" + 
               ratePerUnit + "|" + minimumCharge + "|" + isActive;
    }
}
