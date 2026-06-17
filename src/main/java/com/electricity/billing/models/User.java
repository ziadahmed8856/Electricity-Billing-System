package com.electricity.billing.models;

import java.io.Serializable;

/**
 * Base User class with abstract methods for different user roles
 * Demonstrates Encapsulation and Polymorphism
 */
public abstract class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String userId;
    protected String name;
    protected String email;
    protected String phone;
    protected String password;
    protected String role; // ADMIN, OPERATOR, CUSTOMER
    protected boolean isActive;

    public User(String userId, String name, String email, String phone, 
                String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.isActive = true;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    // Abstract method for subclasses
    public abstract String getUserType();

    @Override
    public String toString() {
        return userId + "|" + name + "|" + email + "|" + phone + 
               "|" + password + "|" + role + "|" + isActive;
    }
}
