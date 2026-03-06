# PowerShell script to fix Maven cache issue and run tests

Write-Host ""
Write-Host "========================================================"
Write-Host "     Clearing Maven Cache and Running Tests"
Write-Host "========================================================"
Write-Host ""

$mavenCache = "$env:USERPROFILE\.m2\repository"
$testngPath = Join-Path $mavenCache "org\testng"

# Check and clear TestNG cache
if (Test-Path $testngPath) {
    Write-Host "[INFO] Found cached TestNG - removing..."
    Remove-Item -Recurse -Force $testngPath
    Write-Host "[OK] TestNG cache cleared"
} else {
    Write-Host "[INFO] TestNG not in cache"
}

Write-Host ""
Write-Host "========================================================"
Write-Host "     Running: mvn clean test"
Write-Host "========================================================"
Write-Host ""

# Run Maven
mvn clean test

Write-Host ""
if ($LASTEXITCODE -eq 0) {
    Write-Host "========================================================"
    Write-Host "                  BUILD SUCCESSFUL!"
    Write-Host "========================================================"
} else {
    Write-Host "========================================================"
    Write-Host "                    BUILD FAILED"
    Write-Host "========================================================"
}

Write-Host ""
Read-Host "Press Enter to exit"

