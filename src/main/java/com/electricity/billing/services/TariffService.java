package com.electricity.billing.services;

import com.electricity.billing.models.*;
import com.electricity.billing.utils.*;
import java.util.*;

/**
 * Service class for managing tariffs
 */
public class TariffService {
    private static Logger logger = Logger.getInstance();

    /**
     * Create tariff
     */
    public static String createTariff(String region, String customerType, double ratePerUnit, 
                                     double minimumCharge) {
        if (!Validator.isValidRegion(region) || !Validator.isValidTariffRate(ratePerUnit) || 
            !Validator.isValidNonNegativeNumber(minimumCharge)) {
            logger.warning("Invalid input for tariff creation");
            return null;
        }

        String tariffId = IDGenerator.generateTariffId();
        Tariff tariff = new Tariff(tariffId, region, customerType, ratePerUnit, minimumCharge);
        
        FileHandler.appendToFile("tariffs.txt", tariff.toString());
        logger.info("Tariff created: " + tariffId + " for region: " + region);
        
        return tariffId;
    }

    /**
     * Get tariff by ID
     */
    public static String getTariffById(String tariffId) {
        List<String> lines = FileHandler.readFromFile("tariffs.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 0 && data[0].equals(tariffId)) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get tariff for region
     */
    public static String getTariffForRegion(String region) {
        List<String> lines = FileHandler.readFromFile("tariffs.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 1 && data[1].equals(region) && data[5].equals("true")) {
                return line;
            }
        }
        
        return null;
    }

    /**
     * Get all tariffs
     */
    public static List<String> getAllTariffs() {
        return FileHandler.readFromFile("tariffs.txt");
    }

    /**
     * Update tariff rate
     */
    public static boolean updateTariffRate(String tariffId, double newRate) {
        String tariffLine = getTariffById(tariffId);
        if (tariffLine == null) {
            return false;
        }

        String[] data = tariffLine.split("\\|");
        data[3] = String.valueOf(newRate);
        String updatedLine = String.join("|", data);
        
        boolean success = FileHandler.updateInFile("tariffs.txt", tariffLine, updatedLine);
        if (success) {
            logger.info("Tariff rate updated: " + tariffId);
        }
        
        return success;
    }

    /**
     * Get tariffs for region
     */
    public static List<String> getTariffsForRegion(String region) {
        List<String> results = new ArrayList<>();
        List<String> lines = FileHandler.readFromFile("tariffs.txt");
        
        for (String line : lines) {
            String[] data = line.split("\\|");
            if (data.length > 1 && data[1].equals(region)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Deactivate tariff
     */
    public static boolean deactivateTariff(String tariffId) {
        String tariffLine = getTariffById(tariffId);
        if (tariffLine == null) {
            return false;
        }

        String[] data = tariffLine.split("\\|");
        data[5] = "false";
        String updatedLine = String.join("|", data);
        
        return FileHandler.updateInFile("tariffs.txt", tariffLine, updatedLine);
    }
}
