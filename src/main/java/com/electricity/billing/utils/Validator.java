package com.electricity.billing.utils;

/**
 * Validation utility for input validation
 */
public class Validator {
    
    /**
     * Validate meter code format
     */
    public static boolean isValidMeterCode(String meterCode) {
        if (meterCode == null || meterCode.trim().isEmpty()) {
            return false;
        }
        // Meter code should be alphanumeric and 6-15 characters
        return meterCode.matches("^[A-Z0-9]{6,15}$");
    }

    /**
     * Validate name
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.length() >= 3 && name.length() <= 100;
    }

    /**
     * Validate email
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * Validate phone number
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        // Accept 10-15 digit phone numbers
        return phone.matches("^[0-9]{10,15}$");
    }

    /**
     * Validate positive number
     */
    public static boolean isValidPositiveNumber(double number) {
        return number > 0;
    }

    /**
     * Validate non-negative number
     */
    public static boolean isValidNonNegativeNumber(double number) {
        return number >= 0;
    }

    /**
     * Validate meter reading
     */
    public static boolean isValidMeterReading(double reading) {
        return reading >= 0 && reading <= 999999;
    }

    /**
     * Validate tariff rate
     */
    public static boolean isValidTariffRate(double rate) {
        return rate > 0 && rate <= 100; // Reasonable rate limit
    }

    /**
     * Validate user ID
     */
    public static boolean isValidUserId(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return userId.matches("^[A-Z0-9]{5,10}$");
    }

    /**
     * Validate password
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        // Minimum 6 characters
        return password.length() >= 6;
    }

    /**
     * Validate address
     */
    public static boolean isValidAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            return false;
        }
        return address.length() >= 5 && address.length() <= 200;
    }

    /**
     * Validate region
     */
    public static boolean isValidRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            return false;
        }
        return region.length() >= 3 && region.length() <= 50;
    }

    /**
     * Validate user role
     */
    public static boolean isValidRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            return false;
        }
        return role.equals("ADMIN") || role.equals("OPERATOR") || role.equals("CUSTOMER");
    }
}
