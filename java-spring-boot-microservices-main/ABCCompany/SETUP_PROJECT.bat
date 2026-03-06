@echo off
REM =====================================================
REM ABC Company Microservices - Automated Setup Script
REM =====================================================

echo.
echo ================== CHECKING PREREQUISITES ==================
echo.

REM Check Java
echo Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 from: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)
echo [OK] Java is installed

REM Check Maven
echo Checking Maven installation...
mvn -v >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed or not in PATH
    echo Please install Maven from: https://maven.apache.org/download.cgi
    echo Or use: choco install maven (if you have Chocolatey)
    pause
    exit /b 1
)
echo [OK] Maven is installed

REM Check MySQL
echo Checking MySQL installation...
mysql -u root -p root -e "SELECT 1;" >nul 2>&1
if %errorlevel% neq 0 (
    echo WARNING: MySQL connection failed
    echo Make sure MySQL is running with username 'root' and password 'root'
) else (
    echo [OK] MySQL is running
)

REM Check Kafka
echo Checking Kafka...
if not exist "C:\kafka\bin\windows\kafka-server-start.bat" (
    echo WARNING: Kafka not found at C:\kafka
    echo Make sure Kafka is installed and running before starting services
) else (
    echo [OK] Kafka found
)

echo.
echo ================== BUILDING PROJECT ==================
echo.

cd /d "D:\Year 3 Sem 2\Distributed Systems\test project\java-spring-boot-microservices-main\ABCCompany"

echo Building all modules... This may take a few minutes
mvn clean install -DskipTests

if %errorlevel% neq 0 (
    echo ERROR: Build failed
    pause
    exit /b 1
)

echo.
echo ================== BUILD SUCCESSFUL ==================
echo.
echo Next steps:
echo 1. Make sure MySQL and Kafka are running
echo 2. Run START_SERVICES.bat to start all microservices
echo.
pause

