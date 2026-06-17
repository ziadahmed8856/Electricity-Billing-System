# Electricity Billing System - Complete Documentation

## Project Overview

This is a complete **Electricity Billing System** developed as a desktop application using **Java** with a graphical user interface (GUI) built with Swing. The system manages electricity bills for customers, operators, and administrators with full OOP principles, text-based data persistence, and role-based access control.

---

## Features

### 1. **Old Customer Module**
- View and pay electricity bills
- Enter monthly meter readings
- Submit complaints about bills
- Track unpaid bills
- Receive automatic email reminders after 3 months of unpaid bills

### 2. **New Customer Module**
- Register new customers with complete details
- Generate unique meter codes
- Simulate contract file upload
- Automatic meter activation
- Receive activation email notifications

### 3. **Operator Module**
- Collect payments from customers
- Print bills using meter code
- View all bills filtered by region
- Validate meter readings
- Define and manage electricity tariffs
- Manage customer subscriptions (activate/deactivate)

### 4. **Admin Module**
- View all bills filtered by region
- Monitor total collected money
- Generate consumption statistics per region
- Add/Update/Delete system users
- Manage system configuration

### 5. **Additional Features**
- **Role-Based Access Control**: Admin, Operator, Customer roles
- **Input Validation**: All inputs validated
- **Logging System**: All actions logged to files
- **Email Simulation**: Automated email notifications
- **Data Persistence**: Text-file based data storage (CSV format)
- **Bill Calculation**: Automatic monthly bill generation based on tariffs

---

## Project Architecture

### Directory Structure

```
Electricity Billing System/
├── src/
│   └── main/
│       └── java/
│           └── com/electricity/billing/
│               ├── ElectricityBillingApp.java      (Main entry point)
│               ├── models/                          (Data models)
│               │   ├── User.java                    (Base user class)
│               │   ├── Admin.java                   (Admin extends User)
│               │   ├── Operator.java                (Operator extends User)
│               │   ├── Customer.java                (Base customer class)
│               │   ├── OldCustomer.java             (OldCustomer extends Customer)
│               │   ├── NewCustomer.java             (NewCustomer extends Customer)
│               │   ├── Bill.java
│               │   ├── Payment.java
│               │   ├── Complaint.java
│               │   ├── Meter.java
│               │   └── Tariff.java
│               ├── services/                        (Business logic)
│               │   ├── CustomerService.java
│               │   ├── BillService.java
│               │   ├── PaymentService.java
│               │   ├── UserService.java
│               │   ├── ComplaintService.java
│               │   └── TariffService.java
│               ├── utils/                           (Utility classes)
│               │   ├── Logger.java
│               │   ├── FileHandler.java
│               │   ├── EmailSimulator.java
│               │   ├── Validator.java
│               │   └── IDGenerator.java
│               └── gui/                             (GUI components)
│                   ├── LoginFrame.java              (Login screen)
│                   ├── AdminDashboard.java          (Admin dashboard)
│                   ├── OperatorDashboard.java       (Operator dashboard)
│                   └── CustomerDashboard.java       (Customer dashboard)
├── data/                                             (Text-based data files)
│   ├── customers.txt
│   ├── bills.txt
│   ├── payments.txt
│   ├── complaints.txt
│   ├── users.txt
│   └── tariffs.txt
├── logs/                                             (System logs)
│   └── system.log
└── README.md                                         (This file)
```

---

## OOP Principles Used

1. **Encapsulation**: All class fields are private with public getters/setters
2. **Inheritance**: 
   - User class extended by Admin and Operator
   - Customer class extended by OldCustomer and NewCustomer
3. **Polymorphism**: 
   - Abstract methods in User and Customer classes
   - Method overriding in child classes
4. **Abstraction**: 
   - Abstract base classes (User, Customer)
   - Service classes abstract business logic from GUI

---

## Data Models

### User Hierarchy
```
User (abstract)
├── Admin
└── Operator
```

### Customer Hierarchy
```
Customer (abstract)
├── OldCustomer
└── NewCustomer
```

### Other Models
- Bill: Stores bill information with status tracking
- Payment: Records payment transactions
- Complaint: Manages customer complaints
- Meter: Tracks electricity meter readings
- Tariff: Stores electricity rates by region

---

## File Format (CSV-based)

### customers.txt
```
customerId|meterCode|name|address|region|phone|email|customerType|isActive|totalUnpaidAmount
```

### bills.txt
```
billId|customerId|meterCode|billDate|dueDate|unitsConsumed|tariffRate|billAmount|paidAmount|billStatus|region
```

### payments.txt
```
paymentId|billId|customerId|meterCode|paymentAmount|paymentDate|paymentMethod|operatorId|paymentStatus
```

### users.txt
```
userId|name|email|phone|password|role|isActive|additionalField1|additionalField2
```

### complaints.txt
```
complaintId|customerId|meterCode|description|complaintDate|complaintStatus|category|resolution|resolutionDate
```

### tariffs.txt
```
tariffId|region|customerType|ratePerUnit|minimumCharge|isActive
```

---

## Login Credentials

### Default Admin User
- **User ID**: ADM1
- **Password**: admin123
- **Role**: ADMIN

### Sample Operators
- **User ID**: OPR1
- **Password**: operator123
- **Role**: OPERATOR
- **Region**: North

---

## Running the Application

### Prerequisites
- Java 8 or higher installed
- Windows/Linux/Mac OS

### Compilation

```bash
cd "Electricity Billing System"

# Compile all Java files
javac -d bin src/main/java/com/electricity/billing/**/*.java
```

### Execution

```bash
# Navigate to project directory
cd "Electricity Billing System"

# Run the application
java -cp bin com.electricity.billing.ElectricityBillingApp
```

### Alternative: Using IDE (IntelliJ IDEA / Eclipse)

1. Open the project folder as a Java project
2. Set the source directory: `src/main/java`
3. Set the output directory: `bin`
4. Right-click on `ElectricityBillingApp.java` → Run

---

## Usage Workflow

### For Customers
1. Login with customer ID and password (Role: CUSTOMER)
2. Dashboard shows unpaid bills and pending amount
3. **Pay Bills**: Enter bill ID and payment amount
4. **View Bills**: See all bills for their meter
5. **Meter Reading**: Submit monthly meter readings
6. **Complaints**: File complaints about billing or technical issues

### For Operators
1. Login with operator ID and password (Role: OPERATOR)
2. Dashboard shows collections today
3. **Collect Payments**: Enter bill ID, amount, and payment method
4. **Print Bill**: Search by meter code to view bills
5. **View Bills**: Filter bills by status

### For Admins
1. Login with admin ID and password (Role: ADMIN)
2. Dashboard shows system statistics
3. **Bills Tab**: View and filter bills by region
4. **Users Tab**: Add admin/operator users
5. **Reports Tab**: View consumption statistics by region

---

## Key Services

### CustomerService
- `registerNewCustomer()`: Register new customer
- `addOldCustomer()`: Add existing customer
- `getCustomerByMeterCode()`: Search by meter code
- `getUnpaidBillsForCustomer()`: Get unpaid bills

### BillService
- `createBill()`: Generate new bill
- `getBillsForMeter()`: Get bills for specific meter
- `updateBillStatus()`: Update bill status
- `getOutstandingAmount()`: Calculate outstanding amount

### PaymentService
- `recordPayment()`: Record payment transaction
- `getTotalCollectedAmount()`: Total collections
- `getPaymentsByOperator()`: Operator-wise payments

### UserService
- `authenticateUser()`: User login
- `createAdmin()`: Create admin user
- `createOperator()`: Create operator user
- `changePassword()`: Change user password

### ComplaintService
- `fileComplaint()`: File new complaint
- `resolveComplaint()`: Resolve complaint
- `getComplaintsForCustomer()`: Get customer complaints

### TariffService
- `createTariff()`: Create tariff rate
- `getTariffForRegion()`: Get region tariff
- `updateTariffRate()`: Update tariff

---

## Email Simulation

The system simulates email sending for:
1. **Bill Notifications**: When bill is generated
2. **Payment Confirmation**: After successful payment
3. **Unpaid Bill Reminder**: After 3 months of unpaid bills
4. **Meter Activation**: When meter is activated for new customer
5. **Complaint Acknowledgement**: When complaint is filed

Emails are logged to `logs/system.log` file.

---

## Validation Rules

- **Meter Code**: 6-15 alphanumeric characters
- **Name**: 3-100 characters
- **Email**: Valid email format
- **Phone**: 10-15 digits
- **Tariff Rate**: 0.01 - 100
- **Password**: Minimum 6 characters
- **Meter Reading**: 0 - 999999

---

## Logging

All system actions are logged to `logs/system.log` including:
- User authentication
- Bill creation/updates
- Payments processed
- Customer registrations
- Error messages

---

## Sample Data

The system comes with pre-loaded sample data:
- 5 sample customers (mix of old and new)
- 6 sample bills
- 2 sample payments
- 2 sample operators
- 4 regional tariffs
- 2 sample complaints

---

## Future Enhancements

1. Database integration (MySQL/PostgreSQL)
2. Advanced reporting with charts
3. Mobile app for customers
4. Real SMTP email integration
5. Payment gateway integration
6. Bulk bill generation
7. Meter meter reading API
8. SMS notifications
9. Export to PDF/Excel
10. Multi-language support

---

## Troubleshooting

### Application won't start
- Ensure Java 8+ is installed
- Check PATH environment variable
- Verify all files are in correct directory

### Login fails
- Check User ID and password in data/users.txt
- Ensure user is active (isActive = true)
- Try default admin credentials

### Data files not found
- Ensure `data/` directory exists
- Check file permissions
- Sample files will be created automatically

### GUI not appearing
- Check if X11 is available (Linux)
- Try running with: `java -Djava.awt.headless=false ...`

---

## License

This project is created for educational purposes.

---

## Support

For issues or questions, check the log files in `logs/system.log`.

---

## Version

**Version**: 1.0.0  
**Last Updated**: December 2024  
**Language**: Java  
**GUI Framework**: Swing

---

## Author Notes

This system demonstrates enterprise-level software development principles including:
- Clean code architecture
- Separation of concerns
- Data persistence patterns
- Role-based access control
- Comprehensive error handling
- Professional logging
- User-friendly GUI

The system is fully functional and production-ready for small-scale electricity billing operations.

## How to Use (Quick)

1. Clone the repository (or skip if you already have the project files):

```bash
git clone https://github.com/ziadahmed8856/Electricity-Billing-System.git
cd "Electricity Billing System"
```

2. Compile the project (create `bin`):

```bash
# Windows
compile.bat

# Linux / macOS
./compile.sh
```

3. Run the application:

```bash
# Windows
run.bat

# Linux / macOS
./run.sh
```

4. Login:
- Admin: User ID `ADM1`, Password `admin123` (Role: ADMIN)
- Operator: User ID `OPR1`, Password `operator123` (Role: OPERATOR)

5. Explore dashboards:
- Admin: manage users, view bills, run reports
- Operator: collect payments, print bills, view region reports
- Customer: view/pay bills, submit readings, file complaints


  ## Course Information

**Course:**CS 213   Programming – 2

**Faculty:** Faculty of Computing & Artificial Intelligence

**University:** Capital University (Formerly Helwan University)

**Semester:** Autumn 2025–2026


Notes:
- The app uses local text files in the `data/` folder to store records.
- Email sending is simulated and logged in `logs/system.log`.
- If `git push` is required, configure Git credentials (personal access token) in your environment.

Repository (uploaded): https://github.com/ziadahmed8856/Electricity-Billing-System.git

---
