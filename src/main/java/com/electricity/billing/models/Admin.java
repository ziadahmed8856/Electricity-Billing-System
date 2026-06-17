package com.electricity.billing.models;

/**
 * Admin User class
 * Extends User with admin-specific functionality
 */
public class Admin extends User {
    private static final long serialVersionUID = 1L;
    
    private String department;
    private String adminLevel; // SUPER, REGULAR

    public Admin(String userId, String name, String email, String phone, 
                 String password, String department, String adminLevel) {
        super(userId, name, email, phone, password, "ADMIN");
        this.department = department;
        this.adminLevel = adminLevel;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getAdminLevel() { return adminLevel; }
    public void setAdminLevel(String adminLevel) { this.adminLevel = adminLevel; }

    @Override
    public String getUserType() {
        return "ADMIN";
    }

    @Override
    public String toString() {
        return super.toString() + "|" + department + "|" + adminLevel;
    }
}
