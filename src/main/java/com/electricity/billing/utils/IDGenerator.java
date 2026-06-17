package com.electricity.billing.utils;

import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ID Generator utility for generating unique IDs
 */
public class IDGenerator {
    
    /**
     * Generate unique customer ID
     */
    public static String generateCustomerId() {
        return "CUST" + System.currentTimeMillis();
    }

    /**
     * Generate unique meter code
     */
    public static String generateMeterCode() {
        return "MTR" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

    /**
     * Generate unique bill ID
     */
    public static String generateBillId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now().format(formatter);
        return "BILL" + date + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    /**
     * Generate unique payment ID
     */
    public static String generatePaymentId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "PAY" + timestamp + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }

    /**
     * Generate unique complaint ID
     */
    public static String generateComplaintId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = LocalDateTime.now().format(formatter);
        return "COMP" + date + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    /**
     * Generate unique user ID
     */
    public static String generateUserId(String role) {
        return role.substring(0, 3).toUpperCase() + System.currentTimeMillis();
    }

    /**
     * Generate unique tariff ID
     */
    public static String generateTariffId() {
        return "TAR" + UUID.randomUUID().toString().replace("-", "").substring(0, 10).toUpperCase();
    }
}
