@echo off
REM Compilation script for Electricity Billing System

echo ========================================
echo Electricity Billing System - Build Script
echo ========================================
echo.

REM Create bin directory if it doesn't exist
if not exist "bin" (
    echo Creating bin directory...
    mkdir bin
)

echo Compiling Java files...
echo.

REM Compile all Java files
javac -d bin -sourcepath src/main/java src/main/java/com/electricity/billing/utils/*.java src/main/java/com/electricity/billing/models/*.java src/main/java/com/electricity/billing/services/*.java src/main/java/com/electricity/billing/gui/*.java src/main/java/com/electricity/billing/*.java

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo Compilation Successful!
    echo Binary files created in 'bin' directory
    echo ========================================
    echo.
    echo You can now run the application using:
    echo   run.bat
    pause
) else (
    echo.
    echo ========================================
    echo Compilation Failed!
    echo Please check the errors above
    echo ========================================
    pause
)
