# Quick Reference: Mocking and Stubbing Test Suite

## 🎯 Quick Start

### Run All Tests
```powershell
cd order
mvn clean test
```

### Run Only Mocking Tests
```powershell
mvn clean test -Dtest=OrderServiceMockingTest
```

### Run Specific Test
```powershell
mvn clean test -Dtest=OrderServiceMockingTest#testGetAllOrdersMocked
```

---

## 📋 10 Test Cases Included

| Test | Purpose | Key Feature |
|------|---------|-------------|
| testGetAllOrdersMocked | Repository mocking | Basic stubbing |
| testSaveOrderToRepository | Save operation | Mock return values |
| testRepositoryNeverCalled | Verify no calls | never() verification |
| testMultipleConsecutiveStubs | Different responses | Multiple thenReturn() |
| testVerifyExactArguments | Exact matching | Argument verification |
| testArgumentMatchers | Flexible matching | any(), anyInt() |
| testMockingModelMapper | Object mapping | Mapper mocking |
| testCombiningMultipleMocks | Multiple mocks | Integration testing |
| testAtLeastOnceVerification | Loose verification | atLeastOnce() |
| testMockingExceptions | Error scenarios | Exception throwing |

---

## 🔑 Key Mockito Patterns

### 1. Create Mock
```java
OrderRepo orderRepo = mock(OrderRepo.class);
```

### 2. Stub Method (Setup Response)
```java
when(orderRepo.findAll()).thenReturn(mockOrders);
```

### 3. Verify Method Called
```java
verify(orderRepo, times(1)).findAll();
```

### 4. Argument Matchers
```java
any()                    // Matches anything
any(Orders.class)        // Matches any Orders
anyInt()                 // Matches any integer
eq(value)                // Exact match
```

### 5. Verification Modes
```java
times(2)                 // Exactly 2 times
never()                  // Never called
atLeastOnce()            // At least once
atLeast(2)               // At least 2 times
atMost(5)                // At most 5 times
```

### 6. Multiple Stubs (Different Responses)
```java
when(orderRepo.findById(anyInt()))
    .thenReturn(order1)
    .thenReturn(order2)
    .thenReturn(null);
```

### 7. Exception Throwing
```java
when(orderRepo.save(any())).thenThrow(new RuntimeException("Error"));
```

### 8. Void Methods
```java
doNothing().when(orderRepo).deleteById(anyInt());
```

---

## 📁 File Locations

| File | Path |
|------|------|
| Test Class | `order/src/test/java/com/order/order/mocking_and_stubbing/OrderServiceMockingTest.java` |
| Documentation | `order/src/test/java/com/order/order/mocking_and_stubbing/README.md` |
| TestNG Config | `order/src/test/resources/testng.xml` |
| POM Config | `order/pom.xml` |

---

## ✅ What's Implemented

- ✅ 10 comprehensive test cases
- ✅ Mockito 5.2.0 integration
- ✅ TestNG 7.4.0 support
- ✅ Order service mocking
- ✅ Repository mocking
- ✅ ModelMapper mocking
- ✅ WebClient mocking
- ✅ Exception handling
- ✅ Verification patterns
- ✅ Argument matchers

---

## 🎓 Learning Outcomes

After studying this test suite, you'll understand:

1. **Mock Creation** - How to create fake objects
2. **Stubbing** - How to configure mock behavior
3. **Verification** - How to verify interactions
4. **Argument Matching** - How to match any argument type
5. **Exception Scenarios** - How to test error cases
6. **Multiple Mocks** - How to use multiple mocks together
7. **Verification Modes** - Different ways to verify calls
8. **Best Practices** - Testing patterns and approaches

---

## 🔗 Integration Status

| Feature | Status | Service |
|---------|--------|---------|
| Fixtures | ✅ Complete | APIGateway, Product |
| Assertions | ✅ Complete | Order, Inventory |
| Test Reporting | ✅ Complete | Order |
| **Mocking & Stubbing** | **✅ Complete** | **Order** |

---

## 📊 Test Statistics

- **Total Tests:** 10
- **Test Lines:** 323
- **Frameworks:** Mockito + TestNG
- **Mocked Classes:** 4 (OrderRepo, ModelMapper, WebClient x2)
- **Verification Patterns:** 7+
- **Documentation:** Comprehensive

---

## 🚀 Next Steps

1. Run the test suite
2. Study each test method
3. Experiment with different assertions
4. Apply patterns to other services
5. Extend with additional test scenarios

---

**Last Updated:** March 6, 2026  
**Status:** ✅ Ready for Use

