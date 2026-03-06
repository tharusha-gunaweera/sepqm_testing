@echo off
REM =====================================================
REM Start all Microservices
REM =====================================================

echo.
echo ================== STARTING MICROSERVICES ==================
echo.
echo IMPORTANT: Make sure the following are running FIRST:
echo   1. MySQL Server (port 3306)
echo   2. Apache Kafka (port 9092)
echo   3. Zookeeper (port 2181)
echo.

pause

cd /d "D:\Year 3 Sem 2\Distributed Systems\test project\java-spring-boot-microservices-main\ABCCompany"

echo.
echo Starting Discovery Server (Eureka) on port 8761...
echo This MUST start first. Keep this window open.
echo When you see "Started DiscoveryServerApplication", press Ctrl+C in the NEXT step
echo.
timeout /t 3

start cmd /k "title Discovery Server & mvn -pl discoveryserver spring-boot:run"

echo.
echo Waiting for Discovery Server to start...
timeout /t 10

echo.
echo Starting Product Service (dynamic port)...
start cmd /k "title Product Service & mvn -pl product spring-boot:run"

echo.
echo Waiting for Product Service to register...
timeout /t 10

echo.
echo Starting Inventory Service (dynamic port)...
start cmd /k "title Inventory Service & mvn -pl inventory spring-boot:run"

echo.
echo Waiting for Inventory Service to register...
timeout /t 10

echo.
echo Starting Order Service (port 8081)...
start cmd /k "title Order Service & mvn -pl order spring-boot:run"

echo.
echo Waiting for Order Service to register...
timeout /t 10

echo.
echo Starting API Gateway (dynamic port)...
start cmd /k "title API Gateway & mvn -pl apigateway spring-boot:run"

echo.
echo ================== ALL SERVICES STARTED ==================
echo.
echo Services should now be running. Check status:
echo   - Eureka Dashboard: http://localhost:8761
echo   - Order Health: http://localhost:8081/actuator/health
echo.
echo To test services, open another terminal and run:
echo   - curl http://localhost:8081/actuator/health
echo   - curl http://localhost:8761/eureka/apps
echo.
pause

