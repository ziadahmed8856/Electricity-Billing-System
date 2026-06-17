#!/bin/bash
# Compilation script for Electricity Billing System (Linux/Mac)

echo "========================================"
echo "Electricity Billing System - Build Script"
echo "========================================"
echo ""

# Create bin directory if it doesn't exist
if [ ! -d "bin" ]; then
    echo "Creating bin directory..."
    mkdir -p bin
fi

echo "Compiling Java files..."
echo ""

# Compile all Java files
javac -d bin -sourcepath src/main/java src/main/java/com/electricity/billing/utils/*.java src/main/java/com/electricity/billing/models/*.java src/main/java/com/electricity/billing/services/*.java src/main/java/com/electricity/billing/gui/*.java src/main/java/com/electricity/billing/*.java

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "Compilation Successful!"
    echo "Binary files created in 'bin' directory"
    echo "========================================"
    echo ""
    echo "You can now run the application using:"
    echo "   ./run.sh"
else
    echo ""
    echo "========================================"
    echo "Compilation Failed!"
    echo "Please check the errors above"
    echo "========================================"
fi
