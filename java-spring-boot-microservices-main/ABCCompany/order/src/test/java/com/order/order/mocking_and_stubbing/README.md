# Mocking and Stubbing Test Implementation - Order Service

## Overview
This folder contains comprehensive TestNG test cases demonstrating **Mocking and Stubbing** using the **Mockito framework** in the Order microservice.

## What is Mocking and Stubbing?

### **Mocking**
Mocking is creating a fake object (mock) that mimics the behavior of a real object. Mocks are used to:
- Replace external dependencies (like databases, APIs, or other services)
- Verify that methods were called correctly
- Test your code in isolation

### **Stubbing**
Stubbing is configuring mocks to return predefined responses when methods are called. Stubs are used to:
- Define the behavior of mock objects
- Return specific values when mocked methods are called
- Simulate different scenarios (success, failure, edge cases)

## Key Concepts Demonstrated

### 1. **@Mock Annotation**
Creates a mock object of a class or interface:
```java
@Mock
private WebClient inventoryWebClient;

@Mock
private OrderRepo orderRepo;
```

### 2. **@InjectMocks Annotation**
Automatically injects mock dependencies into the class under test:
```java
@InjectMocks
private OrderService orderService;
```

### 3. **when() and thenReturn() - Stubbing**
Configures the behavior of mock methods:
```java
when(orderRepo.findAll()).thenReturn(mockOrders);
when(responseSpec.bodyToMono(InventoryDTO.class)).thenReturn(Mono.just(inventoryDTO));
```

### 4. **verify() - Verification**
Verifies that mocked methods were called with expected arguments:
```java
verify(orderRepo, times(1)).save(any(Orders.class));
verify(inventoryWebClient, atLeastOnce()).get();
```

### 5. **ArgumentMatchers**
Flexible argument matching in verifications and stubbing:
```java
any()              // Matches any argument
any(String.class)  // Matches any String
eq(value)          // Matches exact value
```

### 6. **Verification Modes**
Different ways to verify method calls:
- `times(n)` - Method called exactly n times
- `atLeastOnce()` - Method called at least once
- `never()` - Method never called
- `atLeast(n)` - Method called at least n times
- `atMost(n)` - Method called at most n times

## Test Cases Implemented

### Test 1: Successfully Save Order
**File**: `OrderServiceMockingTest.java::testSaveOrderSuccess()`
- **Description**: Tests successful order creation with valid inventory and product data
- **Mocking**: WebClient for inventory and product services, OrderRepository
- **Stubbing**: Returns valid InventoryDTO and ProductDTO
- **Verification**: Verifies services were called and order was saved

### Test 2: Inventory Item Not Found
**File**: `OrderServiceMockingTest.java::testSaveOrderInventoryNotFound()`
- **Description**: Tests order fails when inventory service returns null
- **Mocking**: WebClient returns empty Mono
- **Verification**: Ensures product service is never called (short-circuit)

### Test 3: Product Not Found
**File**: `OrderServiceMockingTest.java::testSaveOrderProductNotFound()`
- **Description**: Tests order fails when product service returns null
- **Mocking**: Inventory returns success, but product returns null
- **Verification**: Both services called, but order not saved

### Test 4: Insufficient Quantity
**File**: `OrderServiceMockingTest.java::testSaveOrderInsufficientQuantity()`
- **Description**: Tests order fails when item quantity is zero
- **Mocking**: Services return valid data but with zero quantity
- **Verification**: Repository save is never called

### Test 5: Get All Orders
**File**: `OrderServiceMockingTest.java::testGetAllOrdersMocked()`
- **Description**: Tests retrieving all orders using mocked repository
- **Mocking**: OrderRepository and ModelMapper
- **Verification**: Correct number of orders returned

### Test 6: Argument Captor
**File**: `OrderServiceMockingTest.java::testSaveOrderArgumentCaptor()`
- **Description**: Demonstrates ArgumentCaptor for detailed argument verification
- **Usage**: Captures actual arguments passed to mocks for inspection

### Test 7: Multiple Calls Stubbing
**File**: `OrderServiceMockingTest.java::testGetAllOrdersMultipleCalls()`
- **Description**: Tests different responses for consecutive calls to same mock
- **Stubbing**: Uses chained `thenReturn()` for multiple responses

## Dependencies Added

The following dependencies were added to `pom.xml`:

```xml
<!-- Mockito for Mocking and Stubbing -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-inline</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
</dependency>
```

## How to Run

### Run All Tests
```powershell
mvn clean test
```

### Run Only Mocking and Stubbing Tests
```powershell
mvn clean test -Dgroups="mocking_and_stubbing"
```

### Run Specific Test Class
```powershell
mvn clean test -Dtest=OrderServiceMockingTest
```

### Run Specific Test Method
```powershell
mvn clean test -Dtest=OrderServiceMockingTest#testSaveOrderSuccess
```

## Test Results Example

```
========== TestNG Mocking and Stubbing Test Suite Started ==========
Mocked Dependencies: inventoryWebClient, productWebClient, orderRepo, modelMapper

--- Test Method Starting ---
TEST 1: Testing successful order save with mocked dependencies
✓ Order saved successfully with mocked services
✓ All mocked methods were called as expected

--- Test Method Starting ---
TEST 2: Testing order save fails when inventory returns null
✓ Order correctly failed with error response: Item not found
✓ Product service was not called (as expected when inventory fails)

[... more tests ...]

========== TestNG Mocking and Stubbing Test Suite Completed ==========

Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
```

## Common Mocking Patterns

### Pattern 1: Mock Simple Return Values
```java
when(orderRepo.findById(1)).thenReturn(Optional.of(order));
```

### Pattern 2: Mock Void Methods
```java
doNothing().when(orderRepo).delete(any());
// or
doThrow(new RuntimeException()).when(orderRepo).delete(any());
```

### Pattern 3: Mock Constructor
```java
when(modelMapper.map(any(), any())).thenReturn(mockDTO);
```

### Pattern 4: Chain Multiple Stubs
```java
when(orderRepo.findAll())
    .thenReturn(list1)
    .thenReturn(list2)
    .thenReturn(list3);
```

### Pattern 5: Match Multiple Argument Criteria
```java
when(orderRepo.save(argThat(order -> order.getOrderQty() > 0)))
    .thenReturn(savedOrder);
```

## Benefits of Mocking and Stubbing

✅ **Isolation**: Test code in isolation from external dependencies
✅ **Speed**: Avoid slow network calls and database queries
✅ **Reliability**: Deterministic tests that always pass
✅ **Error Simulation**: Easily simulate error conditions
✅ **Verification**: Verify that dependencies are called correctly
✅ **TDD Support**: Write tests before implementing functionality

## When to Use Mocking

- ✅ Testing service layer logic
- ✅ External API calls (HTTP, databases, message queues)
- ✅ Third-party library integration
- ✅ Complex object dependencies

## When NOT to Use Mocking

- ❌ Unit testing utility functions
- ❌ Testing actual database interactions (use test databases)
- ❌ Testing actual HTTP calls (might need integration tests)
- ❌ Over-mocking (reduce testability)

## Best Practices

1. **Mock External Dependencies Only**: Mock WebClient, Repository, etc., not your business logic
2. **Keep Mocks Simple**: Avoid complex stub setups
3. **Verify Important Calls**: Use verify() for critical interactions
4. **Use Argument Matchers**: Makes tests more flexible
5. **Reset Mocks Between Tests**: Prevents test interference
6. **Test Edge Cases**: Use mocks to simulate failure scenarios
7. **Don't Over-Mock**: Over-mocking can make tests brittle

## Related Files

- **Test File**: `OrderServiceMockingTest.java`
- **Service Under Test**: `../../main/java/com/order/order/service/OrderService.java`
- **TestNG Configuration**: `../../resources/testng.xml`
- **POM Configuration**: `../../../pom.xml`

## Integration with CI/CD

These tests will automatically run during the Maven build process and are integrated with:
- GitHub Actions (if configured)
- Jenkins (if configured)
- Other CI/CD pipelines

## Troubleshooting

### Issue: Tests Not Running
**Solution**: Ensure testng.xml includes the test class:
```xml
<class name="com.order.order.mocking_and_stubbing.OrderServiceMockingTest" />
```

### Issue: Mock Not Initialized
**Solution**: Ensure `@BeforeClass` method is calling `reset()` on all mocks:
```java
reset(inventoryWebClient, productWebClient, orderRepo, modelMapper);
```

### Issue: Verification Fails
**Solution**: Check that verification matches the exact call:
```java
verify(orderRepo, times(1)).save(any(Orders.class));
```

## References

- **Mockito Documentation**: https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
- **TestNG Documentation**: https://testng.org/
- **Spring Test Documentation**: https://spring.io/projects/spring-framework

## Author Notes

This implementation demonstrates enterprise-level testing practices using Mockito and TestNG. It covers:
- Basic mocking concepts
- WebClient mocking for microservice communication
- Repository mocking for data access
- Verification patterns
- Error handling scenarios
- Multiple stubbing strategies

These patterns can be applied to other services (Inventory, Product, APIGateway) and repositories throughout the project.

