@echo off
setlocal enabledelayedexpansion
REM ============================================================================
REM Maven Test Runner - Automatically finds and runs Maven
REM ============================================================================

echo.
echo ============================================================================
echo                    TestNG Fixtures Test Runner
echo ============================================================================
echo.

REM Check if Maven is in PATH
mvn --version >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] Maven found in PATH
    echo.
    goto RUN_TESTS
)

REM Check for Maven in common locations
echo [INFO] Checking for Maven in common locations...
echo.

if exist "C:\apache-maven-3.9.6\bin\mvn.cmd" (
    set MVN_CMD=C:\apache-maven-3.9.6\bin\mvn.cmd
    echo [OK] Found Maven at C:\apache-maven-3.9.6
    goto SET_PATH
)

if exist "C:\apache-maven\bin\mvn.cmd" (
    set MVN_CMD=C:\apache-maven\bin\mvn.cmd
    echo [OK] Found Maven at C:\apache-maven
    goto SET_PATH
)

if exist "C:\DevTools\maven\bin\mvn.cmd" (
    set MVN_CMD=C:\DevTools\maven\bin\mvn.cmd
    echo [OK] Found Maven at C:\DevTools\maven
    goto SET_PATH
)

if exist "%PROGRAMFILES%\apache-maven-3.9.6\bin\mvn.cmd" (
    set MVN_CMD=%PROGRAMFILES%\apache-maven-3.9.6\bin\mvn.cmd
    echo [OK] Found Maven in Program Files
    goto SET_PATH
)

echo.
echo [ERROR] Maven not found!
echo.
echo ============================================================================
echo MAVEN INSTALLATION REQUIRED
echo ============================================================================
echo.
echo Maven is not installed on your system. Please install it:
echo.
echo 1. Visit: https://maven.apache.org/download.cgi
echo 2. Download: apache-maven-3.9.6-bin.zip
echo 3. Extract to: C:\apache-maven-3.9.6
echo 4. Set environment variable M2_HOME = C:\apache-maven-3.9.6
echo 5. Add %%M2_HOME%%\bin to PATH
echo 6. Restart PowerShell/Command Prompt
echo 7. Verify: mvn -version
echo.
echo After installation, run this script again.
echo.
pause
exit /b 1

:SET_PATH
set PATH=%MVN_CMD%\..;%PATH%
echo [OK] Maven path set
echo.

:RUN_TESTS
echo ============================================================================
echo                        RUNNING TESTS
echo ============================================================================
echo.
echo Command: mvn clean test
echo.
echo ============================================================================
echo.

REM Run Maven clean test
mvn clean test

REM Capture exit code
if %errorlevel% equ 0 (
    echo.
    echo ============================================================================
    echo                    ✓ ALL TESTS PASSED
    echo ============================================================================
    echo.
) else (
    echo.
    echo ============================================================================
    echo                    ✗ TESTS FAILED
    echo ============================================================================
    echo.
)

pause
exit /b %errorlevel%


