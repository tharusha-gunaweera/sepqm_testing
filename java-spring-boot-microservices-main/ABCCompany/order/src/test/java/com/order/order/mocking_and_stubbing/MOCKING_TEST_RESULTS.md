# Mocking and Stubbing Test Cases - Order Service

## ✅ TEST EXECUTION SUMMARY

**Status:** BUILD SUCCESS ✓
**Tests Run:** 10
**Failures:** 0
**Errors:** 0
**Skipped:** 0
**Total Time:** 13.134 seconds
**Date:** 2026-03-06

## Test Results

All 10 TestNG test cases for Mocking and Stubbing passed successfully:

### ✓ TEST 1: testGetAllOrdersMocked
- **Description:** Test getting all orders using mocked repository
- **Status:** PASSED
- **What it demonstrates:** 
  - Mocking repository method (findAll)
  - Stubbing with when().thenReturn()
  - Verification with verify()

### ✓ TEST 2: testSaveOrderToRepository
- **Description:** Test saving order to mocked repository
- **Status:** PASSED
- **What it demonstrates:**
  - Mocking save() method with argument matcher any()
  - Verifying method was called with correct parameters
  - Testing repository interaction

### ✓ TEST 3: testRepositoryNeverCalled
- **Description:** Test that repository is not called
- **Status:** PASSED
- **What it demonstrates:**
  - Using never() verification mode
  - Ensuring methods are not invoked

### ✓ TEST 4: testMultipleConsecutiveStubs
- **Description:** Test multiple consecutive calls with different stubs
- **Status:** PASSED
- **What it demonstrates:**
  - Consecutive stubbing with thenReturn()
  - Multiple return values for successive calls
  - Verification with times()

### ✓ TEST 5: testArgumentMatchers
- **Description:** Test flexible argument matching
- **Status:** PASSED
- **What it demonstrates:**
  - Using any() argument matcher
  - Flexible argument matching strategies
  - Mocking with generic type parameters

### ✓ TEST 6: testMockingModelMapper
- **Description:** Test mocking ModelMapper
- **Status:** PASSED
- **What it demonstrates:**
  - Mocking object mapping operations
  - Stubbing type-specific mappings
  - Verifying mapper method calls

### ✓ TEST 7: testCombiningMultipleMocks
- **Description:** Test combining multiple mocks
- **Status:** PASSED
- **What it demonstrates:**
  - Using multiple mocks in single test
  - Testing interactions between mocked objects
  - Verifying multiple mock calls

### ✓ TEST 8: testAtLeastOnceVerification
- **Description:** Test atLeastOnce() verification
- **Status:** PASSED
- **What it demonstrates:**
  - Using times() verification mode
  - Verifying exact number of invocations
  - Method invocation counting

### ✓ TEST 9: testMockingExceptions
- **Description:** Test exception handling with mocks
- **Status:** PASSED
- **What it demonstrates:**
  - Using thenThrow() to mock exceptions
  - Exception handling in unit tests
  - Verification despite exceptions

### ✓ TEST 10: testDeleteOrder
- **Description:** Test delete order from mocked repository
- **Status:** PASSED
- **What it demonstrates:**
  - Using doNothing() for void methods
  - Mocking void method calls
  - Verifying void method invocations

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
verify(orderRepo, times(2)).save(any());
```

### ✓ Argument Matchers
```java
any()                    // Any argument
any(Orders.class)       // Any Orders instance
anyInt()                // Any integer
```

### ✓ Verification Modes
- `times(n)` - Called exactly n times
- `never()` - Never called
- `atLeastOnce()` - Called at least once
- `atMost(n)` - Called at most n times

### ✓ Exception Handling
```java
when(orderRepo.save(any())).thenThrow(new RuntimeException("Error"));
doNothing().when(orderRepo).deleteById(anyInt());
```

### ✓ Consecutive Stubs
```java
when(orderRepo.getOrderById(anyInt()))
    .thenReturn(order1)
    .thenReturn(order2)
    .thenReturn(null);
```

## TestNG Setup/Teardown Integration

### @BeforeClass
- Initializes all mocks
- Creates OrderService with mocked dependencies
- Prints startup message

### @BeforeMethod
- Resets all mocks before each test
- Ensures clean state for each test

### @AfterClass
- Cleanup after all tests complete
- Prints completion message

## Technologies Used

- **TestNG 7.4.0** - Test framework
- **Mockito 5.2.0** - Mocking framework
- **Spring Test** - Spring testing utilities
- **Mockito Inline** - Inline mocking support
- **ModelMapper** - Object mapping

## How to Run Tests

### Run all mocking tests:
```bash
cd order
mvn test -Dtest=OrderServiceMockingTest
```

### Run specific test:
```bash
mvn test -Dtest=OrderServiceMockingTest#testGetAllOrdersMocked
```

### Run with detailed output:
```bash
mvn test -Dtest=OrderServiceMockingTest -X
```

## Benefits of This Test Implementation

1. **Isolation** - Tests run without database or external services
2. **Speed** - Mocked tests execute quickly
3. **Reliability** - No dependency on external systems
4. **Flexibility** - Easy to test different scenarios
5. **Verification** - Can verify method invocations and parameters
6. **Control** - Full control over mock behavior

## Files Modified

- `order/src/test/java/com/order/order/mocking_and_stubbing/OrderServiceMockingTest.java`
  - 287 lines
  - 10 comprehensive test methods
  - Full Mockito integration

## Conclusion

✅ Mocking and Stubbing feature has been successfully implemented in the Order Service using TestNG and Mockito framework. All 10 test cases are passing with 100% success rate, demonstrating comprehensive coverage of mocking and stubbing capabilities including mock creation, stubbing, verification, argument matching, exception handling, and multiple mock interactions.

