# ============================================================================
# TestNG Fixtures Test Runner - PowerShell Version
# ============================================================================

Write-Host ""
Write-Host "============================================================================"
Write-Host "                    TestNG Fixtures Test Runner"
Write-Host "============================================================================"
Write-Host ""

# Check if Maven is in PATH
try {
    $mvnVersion = mvn --version 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "[OK] Maven found in PATH"
        Write-Host ""
    } else {
        throw "Maven not in PATH"
    }
} catch {
    Write-Host "[INFO] Maven not found in PATH. Checking common locations..."
    Write-Host ""

    $mvnPaths = @(
        "C:\apache-maven-3.9.6\bin\mvn.cmd",
        "C:\apache-maven\bin\mvn.cmd",
        "C:\DevTools\maven\bin\mvn.cmd",
        "$env:PROGRAMFILES\apache-maven-3.9.6\bin\mvn.cmd"
    )

    $mvnFound = $false
    foreach ($path in $mvnPaths) {
        if (Test-Path $path) {
            Write-Host "[OK] Found Maven at: $path"
            $env:PATH = (Split-Path $path) + ";" + $env:PATH
            $mvnFound = $true
            break
        }
    }

    if (-not $mvnFound) {
        Write-Host ""
        Write-Host "============================================================================"
        Write-Host "ERROR: Maven not found!"
        Write-Host "============================================================================"
        Write-Host ""
        Write-Host "Maven is not installed on your system. Please install it:"
        Write-Host ""
        Write-Host "1. Visit: https://maven.apache.org/download.cgi"
        Write-Host "2. Download: apache-maven-3.9.6-bin.zip"
        Write-Host "3. Extract to: C:\apache-maven-3.9.6"
        Write-Host "4. Set environment variable M2_HOME = C:\apache-maven-3.9.6"
        Write-Host "5. Add %M2_HOME%\bin to PATH"
        Write-Host "6. Restart PowerShell"
        Write-Host "7. Verify: mvn -version"
        Write-Host ""
        Write-Host "After installation, run this script again."
        Write-Host ""
        Read-Host "Press Enter to exit"
        exit 1
    }
}

Write-Host "============================================================================"
Write-Host "                        RUNNING TESTS"
Write-Host "============================================================================"
Write-Host ""
Write-Host "Command: mvn clean test"
Write-Host ""
Write-Host "============================================================================"
Write-Host ""

# Run Maven clean test
& mvn clean test

# Capture exit code
if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "============================================================================"
    Write-Host "                    ✓ ALL TESTS PASSED"
    Write-Host "============================================================================"
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "============================================================================"
    Write-Host "                    ✗ TESTS FAILED"
    Write-Host "============================================================================"
    Write-Host ""
}

Read-Host "Press Enter to exit"
exit $LASTEXITCODE

