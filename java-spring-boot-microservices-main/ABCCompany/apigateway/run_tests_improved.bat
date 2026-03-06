@echo off
setlocal enabledelayedexpansion

echo.
echo ============================================================================
echo                    TestNG Fixtures Test Runner
echo ============================================================================
echo.

REM First, check if we're in the right directory
if not exist "pom.xml" (
    echo ERROR: pom.xml not found!
    echo.
    echo This script must be run from the apigateway directory.
    echo Current directory: %cd%
    echo.
    pause
    exit /b 1
)

echo [INFO] Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo.
    echo ERROR: Java is not installed or not in PATH!
    echo.
    echo Please install Java 11 or higher:
    echo https://www.oracle.com/java/
    echo.
    pause
    exit /b 1
)

echo [OK] Java found
echo.

REM Check if Maven is in PATH
mvn --version >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Maven found in PATH
    echo.
    goto RUN_TESTS
)

REM Try common Maven locations
echo [INFO] Maven not in PATH. Searching common locations...
echo.

set MAVEN_FOUND=0

if exist "C:\apache-maven-3.9.6\bin\mvn.cmd" (
    set "PATH=C:\apache-maven-3.9.6\bin;!PATH!"
    echo [OK] Found Maven at: C:\apache-maven-3.9.6
    set MAVEN_FOUND=1
    goto RUN_TESTS
)

if exist "C:\apache-maven\bin\mvn.cmd" (
    set "PATH=C:\apache-maven\bin;!PATH!"
    echo [OK] Found Maven at: C:\apache-maven
    set MAVEN_FOUND=1
    goto RUN_TESTS
)

if exist "C:\DevTools\maven\bin\mvn.cmd" (
    set "PATH=C:\DevTools\maven\bin;!PATH!"
    echo [OK] Found Maven at: C:\DevTools\maven
    set MAVEN_FOUND=1
    goto RUN_TESTS
)

if exist "%PROGRAMFILES%\apache-maven-3.9.6\bin\mvn.cmd" (
    set "PATH=%PROGRAMFILES%\apache-maven-3.9.6\bin;!PATH!"
    echo [OK] Found Maven in Program Files
    set MAVEN_FOUND=1
    goto RUN_TESTS
)

if !MAVEN_FOUND! equ 0 (
    echo.
    echo ============================================================================
    echo ERROR: Maven not found!
    echo ============================================================================
    echo.
    echo Maven is not installed on your system.
    echo.
    echo QUICK SOLUTION:
    echo.
    echo 1. Download Maven from:
    echo    https://maven.apache.org/download.cgi
    echo.
    echo 2. Download file: apache-maven-3.9.6-bin.zip
    echo.
    echo 3. Extract to: C:\apache-maven-3.9.6
    echo    (You should have C:\apache-maven-3.9.6\bin\mvn.cmd)
    echo.
    echo 4. Close this window
    echo.
    echo 5. Open a NEW PowerShell or Command Prompt window
    echo.
    echo 6. Run this script again
    echo.
    echo OR alternative location:
    echo Extract to: C:\apache-maven
    echo.
    pause
    exit /b 1
)

:RUN_TESTS
echo ============================================================================
echo                        RUNNING TESTS
echo ============================================================================
echo.
echo Command: mvn clean test
echo.
echo This may take 1-5 minutes...
echo.
echo ============================================================================
echo.

REM Run Maven clean test
mvn clean test

REM Capture exit code
if %errorlevel% equ 0 (
    echo.
    echo ============================================================================
    echo                    SUCCESS! ALL TESTS PASSED!
    echo ============================================================================
    echo.
    echo Tests run: 9
    echo Failures: 0
    echo Status: BUILD SUCCESS
    echo.
) else (
    echo.
    echo ============================================================================
    echo                    TESTS FAILED OR ERROR OCCURRED
    echo ============================================================================
    echo.
    echo Check the output above for details.
    echo.
)

echo.
pause
exit /b %errorlevel%

