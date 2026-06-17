@echo off
REM Run script for Electricity Billing System

echo ========================================
echo Electricity Billing System - Running
echo ========================================
echo.

REM Check if bin directory exists
if not exist "bin" (
    echo Error: 'bin' directory not found!
    echo Please run compile.bat first to compile the project
    pause
    exit /b 1
)

echo Starting Electricity Billing System...
echo.

REM Run the application
java -cp bin com.electricity.billing.ElectricityBillingApp

if %ERRORLEVEL% EQU 0 (
    echo.
    echo Application closed successfully
) else (
    echo.
    echo Error running application
    echo Please check bin directory and compilation
)

pause
