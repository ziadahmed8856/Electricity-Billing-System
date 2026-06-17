package com.electricity.billing.utils;

import java.io.*;
import java.util.*;

/**
 * File handler utility for reading and writing data files
 */
public class FileHandler {
    private static final String DATA_DIR = "data/";
    private static Logger logger = Logger.getInstance();

    static {
        ensureDataDirectory();
    }

    private static void ensureDataDirectory() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
            logger.info("Data directory created at: " + DATA_DIR);
        }
    }

    /**
     * Write data to file (append mode)
     */
    public static void appendToFile(String filename, String data) {
        try (FileWriter fw = new FileWriter(DATA_DIR + filename, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            logger.error("Failed to write to file " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Read all lines from file
     */
    public static List<String> readFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        File file = new File(DATA_DIR + filename);
        
        if (!file.exists()) {
            logger.warning("File not found: " + filename);
            return lines;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to read from file " + filename + ": " + e.getMessage());
        }
        
        return lines;
    }

    /**
     * Update a specific line in file
     */
    public static boolean updateInFile(String filename, String oldData, String newData) {
        List<String> lines = readFromFile(filename);
        
        if (!lines.contains(oldData)) {
            return false;
        }

        try (FileWriter fw = new FileWriter(DATA_DIR + filename, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            for (String line : lines) {
                if (line.equals(oldData)) {
                    bw.write(newData);
                } else {
                    bw.write(line);
                }
                bw.newLine();
            }
            return true;
            
        } catch (IOException e) {
            logger.error("Failed to update file " + filename + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Delete a line from file
     */
    public static boolean deleteFromFile(String filename, String data) {
        List<String> lines = readFromFile(filename);
        
        if (!lines.contains(data)) {
            return false;
        }

        try (FileWriter fw = new FileWriter(DATA_DIR + filename, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            
            for (String line : lines) {
                if (!line.equals(data)) {
                    bw.write(line);
                    bw.newLine();
                }
            }
            return true;
            
        } catch (IOException e) {
            logger.error("Failed to delete from file " + filename + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Clear all data from file
     */
    public static void clearFile(String filename) {
        try (FileWriter fw = new FileWriter(DATA_DIR + filename, false)) {
            // File is cleared
        } catch (IOException e) {
            logger.error("Failed to clear file " + filename + ": " + e.getMessage());
        }
    }

    /**
     * Search for lines containing a specific value
     */
    public static List<String> searchInFile(String filename, String searchValue) {
        List<String> results = new ArrayList<>();
        List<String> lines = readFromFile(filename);
        
        for (String line : lines) {
            if (line.contains(searchValue)) {
                results.add(line);
            }
        }
        
        return results;
    }

    /**
     * Get file path
     */
    public static String getFilePath(String filename) {
        return DATA_DIR + filename;
    }
}
