# Copy-Paste Commands - "Item not found" Fix

## Option 1: Using PowerShell/CMD (FASTEST)

```powershell
# Navigate to the ABCCompany folder
cd "D:\Year 3 Sem 2\Distributed Systems\test project\java-spring-boot-microservices-main\ABCCompany"

# Run the SQL script
mysql -u root < setup_test_data.sql
```

**That's it!** All test data is now created.

---

## Option 2: If MySQL Command Not Found

Try with full path:

```powershell
cd "D:\Year 3 Sem 2\Distributed Systems\test project\java-spring-boot-microservices-main\ABCCompany"

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root < setup_test_data.sql
```

---

## Option 3: Using MySQL Client Directly

```powershell
# Open MySQL interactive mode
mysql -u root

# Then in MySQL prompt, copy-paste this:
source C:/Year\ 3\ Sem\ 2/Distributed\ Systems/test\ project/java-spring-boot-microservices-main/ABCCompany/setup_test_data.sql;
```

---

## After Running SQL Script

### Verify Data Was Created

```powershell
# Check products
mysql -u root -e "USE products; SELECT COUNT(*) as total FROM product;"

# Check inventory
mysql -u root -e "USE inventory; SELECT COUNT(*) as total FROM inventory;"
```

Both should return: **5**

---

## Test Your Order in Postman

After data is created, send this request:

```
POST http://localhost:8081/api/v1/addorder
Content-Type: application/json

{
  "id": 1,
  "itemId": 1,
  "orderDate": "1/1/2024",
  "amount": 1200
}
```

**Expected**: 200 OK with order data ✅

---

## If You Get Permission Denied

Your MySQL root user might have a password. Try:

```powershell
mysql -u root -p < setup_test_data.sql
# When prompted, enter your MySQL password
```

---

## Manual Data Creation (If SQL Fails)

Create data via Postman in this order:

### 1. Create Product
```
POST http://localhost:8082/api/v1/addproduct
Content-Type: application/json

{
  "id": 1,
  "productName": "Laptop",
  "forSale": 1
}
```

### 2. Create Item
```
POST http://localhost:8080/api/v1/additem
Content-Type: application/json

{
  "id": 1,
  "itemId": 1,
  "productId": 1,
  "quantity": 100
}
```

### 3. Create Order (Now Works!)
```
POST http://localhost:8081/api/v1/addorder
Content-Type: application/json

{
  "id": 1,
  "itemId": 1,
  "orderDate": "1/1/2024",
  "amount": 1200
}
```

---

## That's All! 🎉

Just run one of these commands and you're done!


