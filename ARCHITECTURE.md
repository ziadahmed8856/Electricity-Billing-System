# ARCHITECTURE AND OOP DESIGN DOCUMENT

## Project Architecture Overview

This document explains the design patterns and OOP principles used in the Electricity Billing System.

---

## Architecture Layers

### 1. **Presentation Layer (GUI)**
- **Components**: LoginFrame, AdminDashboard, OperatorDashboard, CustomerDashboard
- **Technology**: Java Swing
- **Responsibility**: User interface and user interaction
- **Features**: Role-based dashboards, form validation, data visualization

### 2. **Business Logic Layer (Services)**
- **Components**: CustomerService, BillService, PaymentService, UserService, ComplaintService, TariffService
- **Responsibility**: Core business operations and data processing
- **Features**: CRUD operations, validations, business rule enforcement

### 3. **Data Access Layer (Persistence)**
- **Components**: FileHandler
- **Responsibility**: File I/O operations
- **Storage**: Text files with CSV format
- **Features**: Read, write, update, delete operations on text files

### 4. **Utility Layer**
- **Components**: Logger, EmailSimulator, Validator, IDGenerator
- **Responsibility**: Cross-cutting concerns
- **Features**: Logging, email simulation, input validation, unique ID generation

---

## OOP Principles Implementation

### 1. **Encapsulation**

**Definition**: Bundling data and methods together, hiding internal details.

**Implementation**:
```java
// Example: Customer class
public abstract class Customer {
    private String customerId;        // Private field
    private String meterCode;         // Hidden from outside
    private double totalUnpaidAmount;
    
    // Public getters/setters for controlled access
    public String getCustomerId() { return customerId; }
    public void setTotalUnpaidAmount(double amount) { 
        this.totalUnpaidAmount = amount; 
    }
}
```

**Benefits**:
- Data integrity maintained
- Internal implementation can change without affecting external code
- Validation can be applied in setters

---

### 2. **Inheritance**

**Definition**: Creating new classes based on existing classes, promoting code reuse.

**Implementation**:

#### User Hierarchy
```java
public abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    
    public abstract String getUserType();
}

public class Admin extends User {
    private String department;
    
    @Override
    public String getUserType() {
        return "ADMIN";
    }
}

public class Operator extends User {
    private String region;
    
    @Override
    public String getUserType() {
        return "OPERATOR";
    }
}
```

#### Customer Hierarchy
```java
public abstract class Customer {
    protected String customerId;
    protected String meterCode;
    protected Meter meter;
    
    public abstract String getCustomerCategory();
}

public class OldCustomer extends Customer {
    private LocalDateTime registrationDate;
    private int monthsUnpaid;
    
    @Override
    public String getCustomerCategory() {
        return "EXISTING_CUSTOMER";
    }
    
    public boolean shouldSendEmailReminder() {
        return monthsUnpaid >= 3;
    }
}

public class NewCustomer extends Customer {
    private String contractPath;
    private String connectionStatus;
    
    @Override
    public String getCustomerCategory() {
        return "NEW_CUSTOMER";
    }
    
    public void activateMeter() {
        this.connectionStatus = "ACTIVE";
    }
}
```

**Benefits**:
- Code reuse through inheritance
- Common functionality in base classes
- Specialized functionality in derived classes
- Polymorphic behavior

---

### 3. **Polymorphism**

**Definition**: Objects of different types can be used interchangeably through a common interface.

**Implementation**:

#### Method Overriding
```java
// Base class abstract method
public abstract class User {
    public abstract String getUserType();
}

// Subclass overriding
public class Admin extends User {
    @Override
    public String getUserType() {
        return "ADMIN";
    }
}

public class Operator extends User {
    @Override
    public String getUserType() {
        return "OPERATOR";
    }
}

// Usage - Polymorphic behavior
User user = getUserFromSystem();  // Could be Admin or Operator
System.out.println(user.getUserType());  // Calls appropriate method
```

#### Service Layer Polymorphism
```java
// Service methods work with base class
public class BillService {
    public static String createBill(
        String customerId,
        String meterCode,
        double unitsConsumed,
        double tariffRate,
        String region
    ) {
        // Works with any Customer type
        String customerLine = CustomerService.getCustomerById(customerId);
        // ...
    }
}
```

**Benefits**:
- Flexible code that works with different types
- Easy to extend with new types
- Cleaner code with reduced type checking

---

### 4. **Abstraction**

**Definition**: Showing only essential features while hiding implementation details.

**Implementation**:

#### Abstract Base Classes
```java
// Abstract User class
public abstract class User implements Serializable {
    // Common fields for all users
    protected String userId;
    protected String name;
    protected String email;
    
    // Abstract method - must be implemented by subclasses
    public abstract String getUserType();
}

// Abstract Customer class
public abstract class Customer implements Serializable {
    // Common fields for all customers
    protected String customerId;
    protected Meter meter;
    protected double totalUnpaidAmount;
    
    // Abstract method
    public abstract String getCustomerCategory();
}
```

#### Service Layer Abstraction
```java
// Services hide complex business logic
public class BillService {
    // Client doesn't know how bills are stored or retrieved
    public static String getBillById(String billId) {
        // Complex file operations hidden here
        List<String> lines = FileHandler.readFromFile("bills.txt");
        // ... parsing logic ...
        return billData;
    }
    
    public static double getTotalOutstandingAmount() {
        // Complex calculation hidden
        // Client just calls and gets result
    }
}
```

**Benefits**:
- Clients don't need to know implementation details
- Easy to modify implementation without affecting clients
- Reduces code complexity

---

## Design Patterns Used

### 1. **Singleton Pattern**
**Used in**: Logger class
```java
public class Logger {
    private static Logger instance;
    
    private Logger() {}
    
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}

// Usage - Always same instance
Logger logger = Logger.getInstance();
logger.info("Message");
```

### 2. **Factory Pattern**
**Used in**: IDGenerator
```java
public class IDGenerator {
    public static String generateCustomerId() {
        return "CUST" + System.currentTimeMillis();
    }
    
    public static String generateBillId() {
        return "BILL" + dateTime + UUID;
    }
}
```

### 3. **Service Locator Pattern**
**Used in**: Service layer
```java
// Services locate and use other services
public class PaymentService {
    public static String recordPayment(...) {
        // Locates and uses BillService
        String billLine = BillService.getBillById(billId);
        // Locates and uses FileHandler
        FileHandler.appendToFile("payments.txt", payment.toString());
    }
}
```

---

## Data Access Pattern

### Text File Persistence
```java
public class FileHandler {
    // All file operations centralized
    public static void appendToFile(String filename, String data) { }
    public static List<String> readFromFile(String filename) { }
    public static boolean updateInFile(String filename, String oldData, String newData) { }
    public static boolean deleteFromFile(String filename, String data) { }
}
```

### CSV Format
```
customerId|meterCode|name|address|region|phone|email|customerType|isActive|totalUnpaidAmount
CUST1|MTR001|Rajesh|123 Main|North|9876543210|rajesh@email|OLD|true|0
```

---

## Class Relationships

### Composition
```java
public class Customer {
    protected Meter meter;  // Customer has-a Meter
    
    public Meter getMeter() { return meter; }
}

public class Bill {
    private LocalDateTime billDate;    // Has date information
    private LocalDateTime dueDate;
}
```

### Association
```java
public class Payment {
    private String billId;        // Associated with Bill
    private String customerId;    // Associated with Customer
    private String operatorId;    // Associated with Operator
}

public class Complaint {
    private String customerId;    // Associated with Customer
    private String meterCode;     // Associated with Meter
}
```

---

## Validation Strategy

```java
public class Validator {
    // Business rule validations
    public static boolean isValidMeterCode(String meterCode) {
        return meterCode.matches("^[A-Z0-9]{6,15}$");
    }
    
    public static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
    
    public static boolean isValidPositiveNumber(double number) {
        return number > 0;
    }
}

// Used in services before operations
public class BillService {
    public static String createBill(...) {
        if (!Validator.isValidPositiveNumber(unitsConsumed)) {
            logger.warning("Invalid units");
            return null;
        }
        // Proceed with bill creation
    }
}
```

---

## Error Handling Strategy

```java
// Graceful error handling with logging
public class CustomerService {
    public static boolean registerNewCustomer(...) {
        if (!Validator.isValidName(name)) {
            logger.warning("Invalid name");
            return false;
        }
        
        try {
            String customerId = IDGenerator.generateCustomerId();
            NewCustomer customer = new NewCustomer(...);
            FileHandler.appendToFile("customers.txt", customer.toString());
            logger.info("Customer registered: " + customerId);
            return true;
        } catch (Exception e) {
            logger.error("Error registering customer: " + e.getMessage());
            return false;
        }
    }
}
```

---

## Logging Strategy

```java
public class Logger {
    public void info(String message) { }
    public void warning(String message) { }
    public void error(String message) { }
    public void debug(String message) { }
}

// Used throughout application
Logger logger = Logger.getInstance();
logger.info("User logged in: " + userId);
logger.warning("Invalid password attempt");
logger.error("Database connection failed");
```

---

## GUI Architecture

```java
// Frame (Window)
public class LoginFrame extends JFrame {
    private void createUI() {
        // Panels
        JPanel mainPanel = new JPanel();
        // Components
        JTextField userIdField = new JTextField();
        JButton loginButton = new JButton();
    }
}

// Dashboard (Tabbed Interface)
public class AdminDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    
    private JPanel createDashboardTab() { }
    private JPanel createBillsTab() { }
    private JPanel createUsersTab() { }
}
```

---

## Security Considerations

1. **Password Storage**: Currently plain text in files (should use hashing in production)
2. **Access Control**: Role-based authentication
3. **Validation**: All inputs validated before processing
4. **Logging**: All actions logged for audit trail

---

## Scalability Considerations

### Current Limitations
- Text file storage (not scalable for large datasets)
- Single-threaded GUI

### Recommendations for Scaling
1. Replace text files with database (MySQL, PostgreSQL)
2. Implement connection pooling
3. Add caching layer (Redis)
4. Implement multi-threading for long operations
5. Add API layer (REST/GraphQL)
6. Implement message queue for async operations

---

## Code Quality Metrics

- **Cohesion**: High - each class has single responsibility
- **Coupling**: Low - services are loosely coupled
- **Reusability**: High - inheritance and composition promote reuse
- **Maintainability**: High - clear class structure and documentation
- **Testability**: High - service layer can be unit tested

---

## Future Design Improvements

1. **Add Configuration Management**
   ```java
   public class Config {
       public static final double TARIFF_RATE = 5.50;
       public static final int PAYMENT_DEADLINE_DAYS = 30;
   }
   ```

2. **Add Observer Pattern for Notifications**
   ```java
   public interface BillObserver {
       void billGenerated(Bill bill);
       void paymentReceived(Payment payment);
   }
   ```

3. **Add Strategy Pattern for Tariff Calculation**
   ```java
   public interface TariffStrategy {
       double calculateBill(double units);
   }
   ```

4. **Add Decorator Pattern for Meter Reading Validation**
   ```java
   public abstract class MeterDecorator extends Meter {
       protected Meter meter;
   }
   ```

---

## Conclusion

The Electricity Billing System demonstrates professional-grade Java application design using:
- **SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **Design Patterns**: Singleton, Factory, Service Locator
- **OOP Principles**: Encapsulation, Inheritance, Polymorphism, Abstraction
- **Clean Code**: Clear naming, proper documentation, organized structure

This architecture provides a solid foundation for future enhancements and scaling.

---
