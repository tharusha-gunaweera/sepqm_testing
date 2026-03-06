@echo off
REM Diagnostic script to identify the issue
echo.
echo ======================================================
echo          DIAGNOSTIC TEST
echo ======================================================
echo.

echo [1] Current directory:
cd
echo.

echo [2] Checking for pom.xml:
if exist "pom.xml" (
    echo OK - pom.xml exists
    echo Location: %cd%\pom.xml
) else (
    echo ERROR - pom.xml NOT FOUND
    echo This script must be run from the apigateway directory
)
echo.

echo [3] Testing Java:
java -version 2>&1
if errorlevel 0 (
    echo OK - Java found
) else (
    echo ERROR - Java not found
    echo Please install Java from: https://www.oracle.com/java/
)
echo.

echo [4] Testing Maven:
mvn -version 2>&1
if errorlevel 0 (
    echo OK - Maven found
) else (
    echo ERROR - Maven not found
    echo Please download Maven from: https://maven.apache.org/download.cgi
)
echo.

echo ======================================================
echo Summary:
echo If all tests above show OK, run: mvn clean test
echo If any test fails, fix that dependency first
echo ======================================================
echo.
pause

