package com.electricity.billing.utils;

/**
 * Email simulation utility - simulates sending emails
 * In production, use proper SMTP library
 */
public class EmailSimulator {
    private static Logger logger = Logger.getInstance();

    public static void sendEmail(String to, String subject, String body) {
        logger.info("=== EMAIL SIMULATION ===");
        logger.info("TO: " + to);
        logger.info("SUBJECT: " + subject);
        logger.info("BODY: " + body);
        logger.info("EMAIL SENT SUCCESSFULLY (Simulated)");
        logger.info("========================");
    }

    public static void sendBillNotification(String customerEmail, String customerName, String meterCode, double amount) {
        String subject = "Your Electricity Bill Notification";
        String body = "Dear " + customerName + ",\n\n" +
                      "Your electricity bill for meter " + meterCode + " is ready.\n" +
                      "Amount Due: Rs. " + String.format("%.2f", amount) + "\n" +
                      "Please pay within 30 days to avoid penalties.\n\n" +
                      "Thank you for being our valued customer.\n" +
                      "Electricity Billing System";
        
        sendEmail(customerEmail, subject, body);
    }

    public static void sendPaymentConfirmation(String customerEmail, String customerName, String meterCode, double amount) {
        String subject = "Payment Confirmation - Electricity Bill";
        String body = "Dear " + customerName + ",\n\n" +
                      "We confirm receipt of your payment of Rs. " + String.format("%.2f", amount) + " for meter " + meterCode + ".\n" +
                      "Transaction Date: " + java.time.LocalDateTime.now() + "\n" +
                      "Your bill has been updated.\n\n" +
                      "Thank you for your payment.\n" +
                      "Electricity Billing System";
        
        sendEmail(customerEmail, subject, body);
    }

    public static void sendUnpaidBillReminder(String customerEmail, String customerName, String meterCode, 
                                              double totalAmount, int monthsUnpaid) {
        String subject = "URGENT: Unpaid Electricity Bill Reminder";
        String body = "Dear " + customerName + ",\n\n" +
                      "REMINDER: You have an outstanding electricity bill for meter " + meterCode + ".\n" +
                      "Total Amount Pending: Rs. " + String.format("%.2f", totalAmount) + "\n" +
                      "Months Unpaid: " + monthsUnpaid + "\n\n" +
                      "Please settle your bill immediately to avoid disconnection.\n" +
                      "For any disputes or clarifications, contact our office.\n\n" +
                      "Regards,\n" +
                      "Electricity Billing System";
        
        sendEmail(customerEmail, subject, body);
    }

    public static void sendMeterActivationEmail(String customerEmail, String customerName, String meterCode) {
        String subject = "Meter Activation Confirmation";
        String body = "Dear " + customerName + ",\n\n" +
                      "Your electricity meter has been successfully activated.\n" +
                      "Meter Code: " + meterCode + "\n" +
                      "Activation Date: " + java.time.LocalDateTime.now() + "\n\n" +
                      "You can now start using electricity services.\n" +
                      "For any issues, please contact our support team.\n\n" +
                      "Welcome!\n" +
                      "Electricity Billing System";
        
        sendEmail(customerEmail, subject, body);
    }

    public static void sendComplaintAcknowledgement(String customerEmail, String customerName, String complaintId) {
        String subject = "Complaint Acknowledgement - Reference ID: " + complaintId;
        String body = "Dear " + customerName + ",\n\n" +
                      "Thank you for submitting your complaint.\n" +
                      "Complaint Reference ID: " + complaintId + "\n" +
                      "Status: OPEN\n\n" +
                      "Our team will investigate and respond within 48 hours.\n\n" +
                      "Regards,\n" +
                      "Electricity Billing System";
        
        sendEmail(customerEmail, subject, body);
    }
}
