# Mocking and Stubbing Implementation - Project Summary

## ✅ Implementation Complete

The **Mocking and Stubbing** test feature has been successfully implemented for the Order Service using TestNG and Mockito framework.

## Overview

**Feature Name:** Mocking and Stubbing  
**Service:** Order Microservice  
**Test Class:** `OrderServiceMockingTest.java`  
**Location:** `order/src/test/java/com/order/order/mocking_and_stubbing/`  
**Framework:** Mockito 5.2.0 + TestNG 7.4.0  

## What is Mocking and Stubbing?

### Mocking
Creating fake objects that replace real dependencies, allowing code to be tested in isolation without external services.

### Stubbing
Configuring mocks to return predefined responses, simulating different scenarios (success, failure, edge cases).

## Test Suite Details

### Total Test Cases: 10

1. **testGetAllOrdersMocked** - Mocking repository to return orders
2. **testSaveOrderToRepository** - Testing save operation with mocked repository
3. **testRepositoryNeverCalled** - Verifying methods are not called
4. **testMultipleConsecutiveStubs** - Different responses for consecutive calls
5. **testVerifyExactArguments** - Verification with exact argument matching
6. **testArgumentMatchers** - Flexible argument matching with any()
7. **testMockingModelMapper** - Mocking object mapping operations
8. **testCombiningMultipleMocks** - Multiple mocks working together
9. **testAtLeastOnceVerification** - atLeastOnce() verification mode
10. **testMockingExceptions** - Exception handling in mocks

## Key Mockito Features Demonstrated

### ✓ Mock Creation
```java
OrderRepo orderRepo = mock(OrderRepo.class);
ModelMapper modelMapper = mock(ModelMapper.class);
```

### ✓ Stubbing (when/thenReturn)
```java
when(orderRepo.findAll()).thenReturn(mockOrders);
when(orderRepo.save(any())).thenReturn(savedOrder);
```

### ✓ Verification (verify)
```java
verify(orderRepo, times(1)).findAll();
verify(orderRepo, never()).save(any());
verify(orderRepo, atLeastOnce()).findById(anyInt());
```

### ✓ Argument Matchers
```java
any()                    // Any argument
any(Orders.class)       // Any Orders instance
anyInt()                // Any integer
eq(value)               // Exact value matching
```

### ✓ Verification Modes
- `times(n)` - Called exactly n times
- `never()` - Never called
- `atLeastOnce()` - Called at least once
- `atMost(n)` - Called at most n times
- `atLeast(n)` - Called at least n times

### ✓ Exception Handling
```java
when(orderRepo.save(any())).thenThrow(new RuntimeException("Error"));
doNothing().when(orderRepo).deleteById(anyInt());
```

### ✓ Consecutive Stubs
```java
when(orderRepo.findById(anyInt()))
    .thenReturn(order1)
    .thenReturn(order2)
    .thenReturn(null);
```

## Dependencies Added

### Maven Dependencies
```xml
<!-- Mockito Core - 5.2.0 -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>

<!-- Mockito Inline - 5.2.0 -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-inline</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>
```

## File Structure

```
order/
├── src/
│   ├── main/java/com/order/order/
│   │   └── service/OrderService.java (Service under test)
│   └── test/java/com/order/order/
│       ├── mocking_and_stubbing/
│       │   ├── OrderServiceMockingTest.java (✅ 10 test cases)
│       │   └── README.md (Detailed documentation)
│       ├── assertions/
│       │   └── OrderServiceAssertionsTest.java (Assertion feature)
│       └── test_reporting/
│           └── OrderServiceTestReportingTest.java (Test reporting feature)
└── pom.xml (Updated with Mockito dependencies)
```

## TestNG Configuration

**File:** `order/src/test/resources/testng.xml`

```xml
<suite name="Order Service Test Suite">
    <test name="Order Service Assertions Tests">
        <classes>
            <class name="com.order.order.assertions.OrderServiceAssertionsTest" />
        </classes>
    </test>
    <test name="Order Service Test Reporting Tests">
        <classes>
            <class name="com.order.order.test_reporting.OrderServiceTestReportingTest" />
        </classes>
    </test>
    <test name="Order Service Mocking and Stubbing Tests">
        <classes>
            <class name="com.order.order.mocking_and_stubbing.OrderServiceMockingTest" />
        </classes>
    </test>
</suite>
```

## How to Run Tests

### Run All Tests
```powershell
cd D:\Year 3 Sem 2\Distributed Systems\test project\sepqm_testing\java-spring-boot-microservices-main\ABCCompany\order
mvn clean test
```

### Run Only Mocking and Stubbing Tests
```powershell
mvn clean test -Dtest=OrderServiceMockingTest
```

### Run Specific Test Method
```powershell
mvn clean test -Dtest=OrderServiceMockingTest#testGetAllOrdersMocked
```

### Run with Maven Skip Tests (Build Only)
```powershell
mvn clean compile
```

## Benefits of Mocking and Stubbing

✅ **Isolation** - Test in isolation from external dependencies  
✅ **Speed** - No database calls, no network requests  
✅ **Reliability** - Deterministic tests that always pass  
✅ **Error Simulation** - Easy to simulate error scenarios  
✅ **Verification** - Verify how components interact  
✅ **TDD Support** - Write tests before implementation  

## Test Patterns Implemented

### Pattern 1: Basic Mock Creation
```java
OrderRepo orderRepo = mock(OrderRepo.class);
when(orderRepo.findAll()).thenReturn(mockOrders);
```

### Pattern 2: Argument Verification
```java
verify(orderRepo, times(1)).save(any(Orders.class));
```

### Pattern 3: Multiple Stubs
```java
when(orderRepo.findById(anyInt()))
    .thenReturn(order1)
    .thenReturn(order2);
```

### Pattern 4: Exception Mocking
```java
when(orderRepo.save(any())).thenThrow(new RuntimeException("Error"));
```

### Pattern 5: Void Method Mocking
```java
doNothing().when(orderRepo).deleteById(anyInt());
```

## Best Practices Applied

1. ✓ Mock only external dependencies (repository, mapper, webclient)
2. ✓ Use argument matchers for flexibility
3. ✓ Reset mocks between tests (@BeforeMethod)
4. ✓ Verify critical interactions
5. ✓ Keep mocks simple and focused
6. ✓ Test both success and failure scenarios
7. ✓ Use descriptive test names and descriptions
8. ✓ Document each test's purpose

## Integration with Project

### TestNG Features Demonstrated in Project

| Feature | Service | Status |
|---------|---------|--------|
| Fixtures/Setup-Teardown | APIGateway, Product | ✅ Completed |
| Assertions | Order, Inventory | ✅ Completed |
| Test Reporting | Order | ✅ Completed |
| **Mocking and Stubbing** | **Order** | **✅ Completed** |
| BDD (Behavior-Driven Development) | Available | ⏳ Pending |
| CI/CD Usage | Available | ⏳ Pending |

## Summary Statistics

- **Total Test Cases:** 10
- **Framework:** Mockito 5.2.0 + TestNG 7.4.0
- **Service Under Test:** OrderService
- **Dependencies Mocked:** OrderRepo, ModelMapper, WebClient
- **Verification Patterns:** 5+ different modes
- **Test Coverage Areas:** Repository operations, Object mapping, Verification modes

## Files Created/Modified

### Created
- ✅ `OrderServiceMockingTest.java` (314 lines)
- ✅ `mocking_and_stubbing/README.md` (Detailed docs)

### Modified
- ✅ `order/pom.xml` (Added Mockito dependencies)
- ✅ `order/src/test/resources/testng.xml` (Added test class)

## Next Steps for Group Project

1. Apply mocking patterns to other services (Inventory, Product, APIGateway)
2. Implement BDD (Behavior-Driven Development) feature
3. Set up CI/CD integration for automatic test runs
4. Create integration tests combining multiple services
5. Add performance and load testing

## References

- **Mockito Documentation:** https://javadoc.io/doc/org.mockito/mockito-core/latest/
- **TestNG Guide:** https://testng.org/doc/documentation-main.html
- **Spring Testing:** https://spring.io/projects/spring-test

## Notes

- Mockito version 5.2.0 is compatible with Java 17
- TestNG 7.4.0 provides excellent test reporting
- All tests use manual mock setup (no @Mock annotations) for greater control
- Tests are isolated and can run in any order
- Each test includes descriptive console output for easy tracking

---

**Implemented By:** AI Assistant  
**Date:** March 6, 2026  
**Status:** ✅ Complete and Ready for Use

