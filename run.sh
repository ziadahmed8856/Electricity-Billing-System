#!/bin/bash
# Run script for Electricity Billing System (Linux/Mac)

echo "========================================"
echo "Electricity Billing System - Running"
echo "========================================"
echo ""

# Check if bin directory exists
if [ ! -d "bin" ]; then
    echo "Error: 'bin' directory not found!"
    echo "Please run ./compile.sh first to compile the project"
    exit 1
fi

echo "Starting Electricity Billing System..."
echo ""

# Run the application
java -cp bin com.electricity.billing.ElectricityBillingApp

if [ $? -eq 0 ]; then
    echo ""
    echo "Application closed successfully"
else
    echo ""
    echo "Error running application"
    echo "Please check bin directory and compilation"
fi
