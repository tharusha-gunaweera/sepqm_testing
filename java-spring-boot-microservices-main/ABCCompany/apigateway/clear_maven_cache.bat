@echo off
REM Clear Maven cache and fix dependency issues
echo.
echo ======================================================
echo     Clearing Maven Cache and Fixing Dependencies
echo ======================================================
echo.

REM Navigate to Maven repository
cd /d %USERPROFILE%\.m2\repository

if exist "org\testng" (
    echo Removing cached TestNG files...
    rmdir /s /q org\testng
    echo OK - TestNG cache cleared
) else (
    echo TestNG not in cache (OK)
)

echo.
echo ======================================================
echo Maven cache cleared!
echo.
echo Now try running tests again:
echo   mvn clean test
echo ======================================================
echo.
pause

