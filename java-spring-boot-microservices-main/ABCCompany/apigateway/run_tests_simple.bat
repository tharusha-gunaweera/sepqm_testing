@echo off
REM Very simple test runner - doesn't close window
echo.
echo ======================================================
echo          TestNG Fixtures Test Runner
echo ======================================================
echo.
cd /d "%~dp0"
echo Current directory: %cd%
echo.
echo Checking for pom.xml...
if exist "pom.xml" (
    echo OK - pom.xml found
) else (
    echo ERROR - pom.xml not found
    pause
    exit /b 1
)
echo.
echo Checking Java...
java -version
echo.
echo Checking Maven...
mvn -version
echo.
echo ======================================================
echo Running: mvn clean test
echo ======================================================
echo.
call mvn clean test
echo.
echo ======================================================
echo Test execution completed!
echo Exit code: %errorlevel%
echo ======================================================
echo.
pause

