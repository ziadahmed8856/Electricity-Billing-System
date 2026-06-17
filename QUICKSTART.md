# QUICK START GUIDE

## System Requirements
- Java 8 or higher
- 100 MB free disk space
- Windows, Linux, or macOS

---

## Quick Start (Windows)

### Step 1: Compile the Project
Double-click `compile.bat` to compile all Java files.

### Step 2: Run the Application
Double-click `run.bat` to start the application.

---

## Quick Start (Linux/Mac)

### Step 1: Make scripts executable
```bash
chmod +x compile.sh run.sh
```

### Step 2: Compile the Project
```bash
./compile.sh
```

### Step 3: Run the Application
```bash
./run.sh
```

---

## Login Credentials

### Admin Login
- **User ID**: ADM1
- **Password**: admin123
- **Role**: ADMIN

### Operator Login
- **User ID**: OPR1
- **Password**: operator123
- **Role**: OPERATOR
- **Region**: North

### Sample Customer Login
Use any customer ID from data/customers.txt (currently no customer password system)

---

## Default Sample Data Included

### Customers (5)
- CUST1 - Rajesh Kumar (North Region)
- CUST2 - Priya Singh (South Region)
- CUST3 - Amit Patel (East Region)
- CUST4 - Neha Sharma (West Region)
- CUST5 - Vikram Reddy (North Region)

### Bills (6)
- Sample bills for all customers
- Mix of PAID and PENDING bills

### Users (3)
- 1 Admin user
- 2 Operator users

### Tariffs (4)
- Regional rates for North, South, East, West

---

## Troubleshooting

### "javac: command not found"
- Java is not installed or not in PATH
- Install Java from oracle.com
- Add Java bin folder to PATH

### Application won't start
1. Delete the `bin` folder
2. Run `compile.bat` again
3. Run `run.bat`

### No GUI window appears
- Try running with: `java -cp bin -Djava.awt.headless=false com.electricity.billing.ElectricityBillingApp`
- On Linux, ensure X11 is available

### Data files missing
- The system creates data folder automatically
- Sample data is pre-populated
- Check logs/system.log for details

---

## Features Overview

### Admin Dashboard
- View all bills by region
- View system statistics
- Add new admin and operator users
- Generate consumption reports

### Operator Dashboard
- Collect customer payments
- Print bills by meter code
- View bills filtered by status
- Track daily collections

### Customer Dashboard
- View personal bills
- Pay bills online
- Submit meter readings
- File complaints
- Track unpaid bills

---

## Supported Operations

### Customers
- Register new customer
- View bills
- Pay bills
- Submit meter readings
- File complaints
- Track unpaid amount

### Operators
- Collect payments
- Print bills
- View bills by region
- Validate readings
- Manage tariffs

### Admins
- Manage all users
- View all bills
- Generate reports
- Manage tariffs
- View statistics

---

## Data Files Location
All data is stored in the `data/` folder as text files:
- data/customers.txt
- data/bills.txt
- data/payments.txt
- data/complaints.txt
- data/users.txt
- data/tariffs.txt

## Logs Location
System logs are stored in: `logs/system.log`

---

## Need Help?

1. Check logs/system.log for error messages
2. Review README.md for detailed documentation
3. Verify data files are present in data/ folder
4. Ensure Java version is 8 or higher

---
