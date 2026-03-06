# Quick Setup and Run Guide

## Issue: Maven Not Installed

To run this project, you need to install Maven first.

### Install Maven on Windows

**Option 1: Using Chocolatey (Easiest)**
```powershell
# Open PowerShell as Administrator
choco install maven
```

**Option 2: Manual Installation**
1. Download Maven from: https://maven.apache.org/download.cgi
2. Download the "Binary zip archive" (e.g., apache-maven-3.9.x-bin.zip)
3. Extract to: `C:\maven` (or any folder without spaces)
4. Add to Environment Variables:
   - `MAVEN_HOME = C:\maven`
   - Add `C:\maven\bin` to PATH
5. Verify: Open new PowerShell and type `mvn -v`

### Install Other Requirements

**MySQL:**
- Download: https://dev.mysql.com/downloads/mysql/
- Install and start MySQL service
- Create databases:
```bash
mysql -u root -p -e "CREATE DATABASE orders; CREATE DATABASE products; CREATE DATABASE inventory;"
```

**Kafka:**
- Download: https://kafka.apache.org/downloads
- Extract to `C:\kafka`
- Start Zookeeper: `C:\kafka\bin\windows\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties`
- Start Kafka: `C:\kafka\bin\windows\kafka-server-start.bat C:\kafka\config\server.properties`

---

## After Installing Maven

Navigate to project directory and run:

```powershell
cd "D:\Year 3 Sem 2\Distributed Systems\test project\java-spring-boot-microservices-main\ABCCompany"

# Build all modules
mvn clean install -DskipTests

# Start Discovery Server
mvn -pl discoveryserver spring-boot:run

# In new terminal - Start Product Service
mvn -pl product spring-boot:run

# In new terminal - Start Inventory Service
mvn -pl inventory spring-boot:run

# In new terminal - Start Order Service
mvn -pl order spring-boot:run

# In new terminal - Start API Gateway
mvn -pl apigateway spring-boot:run
```

After all services are running, check:
- Eureka Dashboard: http://localhost:8761
- Order Service Health: http://localhost:8081/actuator/health

---

## Alternative: Using IDE (Easier for Beginners)

1. Open project in IntelliJ IDEA or Eclipse
2. IDE will automatically detect Maven and download dependencies
3. Run each service's `*Application.java` class

This is usually easier than command line!

---

## If You Still Have Issues

1. Verify Java 17 installed: `java -version`
2. Verify Maven installed: `mvn -v`
3. Verify MySQL running: `mysql -u root -p -e "SHOW DATABASES;"`
4. Verify Kafka running: Check if Kafka console windows are open
5. Check ports aren't in use: Check if 8761, 8081 are available

