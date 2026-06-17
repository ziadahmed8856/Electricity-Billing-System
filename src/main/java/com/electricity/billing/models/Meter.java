package com.electricity.billing.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Meter class for tracking electricity consumption
 */
public class Meter implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String meterCode;
    private String customerId;
    private double currentReading;
    private double previousReading;
    private LocalDateTime lastReadingDate;
    private LocalDateTime activationDate;
    private boolean isActive;
    private String meterType; // DIGITAL, ANALOG
    private double monthlyConsumption;

    public Meter(String meterCode, String customerId, String meterType) {
        this.meterCode = meterCode;
        this.customerId = customerId;
        this.meterType = meterType;
        this.currentReading = 0.0;
        this.previousReading = 0.0;
        this.isActive = true;
        this.activationDate = LocalDateTime.now();
        this.lastReadingDate = LocalDateTime.now();
        this.monthlyConsumption = 0.0;
    }

    // Getters and Setters
    public String getMeterCode() { return meterCode; }
    
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public double getCurrentReading() { return currentReading; }
    public void setCurrentReading(double currentReading) { 
        this.previousReading = this.currentReading;
        this.currentReading = currentReading;
        this.monthlyConsumption = this.currentReading - this.previousReading;
        this.lastReadingDate = LocalDateTime.now();
    }

    public double getPreviousReading() { return previousReading; }

    public LocalDateTime getLastReadingDate() { return lastReadingDate; }

    public LocalDateTime getActivationDate() { return activationDate; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getMeterType() { return meterType; }
    public void setMeterType(String meterType) { this.meterType = meterType; }

    public double getMonthlyConsumption() { return monthlyConsumption; }

    public void resetMonthlyReading() {
        this.previousReading = this.currentReading;
        this.monthlyConsumption = 0.0;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return meterCode + "|" + customerId + "|" + currentReading + "|" + 
               previousReading + "|" + lastReadingDate.format(formatter) + "|" + 
               activationDate.format(formatter) + "|" + isActive + "|" + 
               meterType + "|" + monthlyConsumption;
    }
}
