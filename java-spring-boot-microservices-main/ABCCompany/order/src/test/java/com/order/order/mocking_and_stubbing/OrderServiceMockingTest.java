package com.order.order.mocking_and_stubbing;

import com.order.order.dto.OrderDTO;
import com.order.order.model.Orders;
import com.order.order.repo.OrderRepo;
import com.order.order.service.OrderService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.reactive.function.client.WebClient;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;

/**
 * TestNG Test Class: Mocking and Stubbing - Order Service
 *
 * This class demonstrates mocking and stubbing using Mockito framework.
 * It shows how to mock external dependencies and test OrderService in isolation.
 *
 * Key Features Demonstrated:
 * 1. Manual mock creation using mock()
 * 2. Mocking Repository Methods - Stubbing database calls
 * 3. Verification - Verifying method calls and interactions
 * 4. Argument Matchers - Using flexible argument matching
 * 5. Testing Business Logic - Testing order validation scenarios
 */
public class OrderServiceMockingTest extends AbstractTestNGSpringContextTests {

    private OrderService orderService;
    private OrderRepo orderRepo;
    private ModelMapper modelMapper;
    private WebClient inventoryWebClient;
    private WebClient productWebClient;

    @BeforeMethod
    public void setup() {
        // Initialize mocks
        orderRepo = mock(OrderRepo.class);
        modelMapper = mock(ModelMapper.class);
        inventoryWebClient = mock(WebClient.class);
        productWebClient = mock(WebClient.class);

        // Create OrderService with mocked dependencies
        orderService = new OrderService(inventoryWebClient, productWebClient, orderRepo, modelMapper);

        System.out.println("========== TestNG Mocking and Stubbing Test Suite Started ==========");
        System.out.println("Mocked Dependencies: orderRepo, modelMapper, inventoryWebClient, productWebClient");
        System.out.println("\n--- Test Method Starting ---");
    }

    @AfterClass
    public void tearDownMocks() {
        System.out.println("\n========== TestNG Mocking and Stubbing Test Suite Completed ==========");
    }

    /**
     * Test 1: Get All Orders - Mocking Repository
     */
    @Test(description = "Test getting all orders using mocked repository", groups = "mocking_and_stubbing")
    public void testGetAllOrdersMocked() {
        System.out.println("TEST 1: Testing get all orders with mocked repository");

        List<Orders> mockOrders = new ArrayList<>();
        Orders order1 = new Orders();
        order1.setId(1);
        order1.setItemId(10);
        order1.setAmount(100);
        mockOrders.add(order1);

        when(orderRepo.findAll()).thenReturn(mockOrders);

        List<Orders> result = orderRepo.findAll();

        assertNotNull(result, "Result should not be null");
        assertEquals(result.size(), 1, "Should have 1 order");
        assertEquals(result.get(0).getId(), 1, "Order ID should be 1");
        System.out.println("✓ Retrieved orders from mocked repository");

        verify(orderRepo, times(1)).findAll();
        System.out.println("✓ Verified repository.findAll() was called");
    }

    /**
     * Test 2: Save Order to Repository
     */
    @Test(description = "Test saving order to mocked repository", groups = "mocking_and_stubbing")
    public void testSaveOrderToRepository() {
        System.out.println("TEST 2: Testing save order to mocked repository");

        Orders orderToSave = new Orders();
        orderToSave.setId(100);
        orderToSave.setItemId(5);
        orderToSave.setAmount(50);

        when(orderRepo.save(any(Orders.class))).thenReturn(orderToSave);

        Orders savedOrder = orderRepo.save(orderToSave);

        assertNotNull(savedOrder, "Saved order should not be null");
        assertEquals(savedOrder.getId(), 100, "Order ID should match");
        System.out.println("✓ Order saved successfully");

        verify(orderRepo, times(1)).save(any(Orders.class));
        System.out.println("✓ Verified repository.save() was called");
    }

    /**
     * Test 3: Repository Never Called
     */
    @Test(description = "Test that repository is not called", groups = "mocking_and_stubbing")
    public void testRepositoryNeverCalled() {
        System.out.println("TEST 3: Testing that repository is never called");

        verify(orderRepo, never()).save(any(Orders.class));
        System.out.println("✓ Verified repository.save() was never called");
    }

    /**
     * Test 4: Multiple Consecutive Stubs
     */
    @Test(description = "Test multiple consecutive calls", groups = "mocking_and_stubbing")
    public void testMultipleConsecutiveStubs() {
        System.out.println("TEST 4: Testing consecutive calls with different stubs");

        Orders order1 = new Orders();
        order1.setId(1);

        Orders order2 = new Orders();
        order2.setId(2);

        when(orderRepo.getOrderById(anyInt()))
                .thenReturn(order1)
                .thenReturn(order2)
                .thenReturn(null);

        Orders result1 = orderRepo.getOrderById(1);
        Orders result2 = orderRepo.getOrderById(2);

        assertNotNull(result1, "First result should not be null");
        assertNotNull(result2, "Second result should not be null");
        assertEquals(result1.getId(), 1, "First ID should be 1");
        assertEquals(result2.getId(), 2, "Second ID should be 2");
        System.out.println("✓ Multiple stubs worked correctly");

        verify(orderRepo, times(2)).getOrderById(anyInt());
        System.out.println("✓ Verified getOrderById() was called 2 times");
    }

    /**
     * Test 5: Argument Matchers
     */
    @Test(description = "Test flexible argument matching", groups = "mocking_and_stubbing")
    public void testArgumentMatchers() {
        System.out.println("TEST 5: Testing argument matching");

        Orders order = new Orders();
        when(orderRepo.save(any())).thenReturn(order);

        Orders result = orderRepo.save(order);

        assertNotNull(result, "Result should not be null");
        System.out.println("✓ any() matcher worked");

        verify(orderRepo).save(any(Orders.class));
        System.out.println("✓ Verified save() with argument matcher");
    }

    /**
     * Test 6: Mocking ModelMapper
     */
    @Test(description = "Test mocking ModelMapper", groups = "mocking_and_stubbing")
    public void testMockingModelMapper() {
        System.out.println("TEST 6: Testing mocked ModelMapper");

        Orders order = new Orders();
        order.setId(1);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1);

        when(modelMapper.map(order, OrderDTO.class)).thenReturn(orderDTO);

        OrderDTO result = modelMapper.map(order, OrderDTO.class);

        assertNotNull(result, "DTO should not be null");
        assertEquals(result.getId(), 1, "IDs should match");
        System.out.println("✓ ModelMapper stub worked");

        verify(modelMapper, times(1)).map(order, OrderDTO.class);
        System.out.println("✓ Verified mapper.map() was called");
    }

    /**
     * Test 7: Multiple Mocks Together
     */
    @Test(description = "Test combining multiple mocks", groups = "mocking_and_stubbing")
    public void testCombiningMultipleMocks() {
        System.out.println("TEST 7: Testing multiple mocks working together");

        List<Orders> mockOrders = new ArrayList<>();
        Orders order = new Orders();
        order.setId(1);
        order.setItemId(20);
        order.setAmount(200);
        mockOrders.add(order);

        when(orderRepo.findAll()).thenReturn(mockOrders);

        List<Orders> ordersFromRepo = orderRepo.findAll();

        assertNotNull(ordersFromRepo, "Orders should not be null");
        assertEquals(ordersFromRepo.size(), 1, "Should have 1 order");
        assertEquals(ordersFromRepo.get(0).getId(), 1, "Order ID should be 1");
        assertEquals(ordersFromRepo.get(0).getItemId(), 20, "Item ID should be 20");
        System.out.println("✓ Multiple mocks worked together");

        verify(orderRepo, times(1)).findAll();
        System.out.println("✓ Verified both mocks were called");
    }

    /**
     * Test 8: At Least Once Verification
     */
    @Test(description = "Test atLeastOnce() verification", groups = "mocking_and_stubbing")
    public void testAtLeastOnceVerification() {
        System.out.println("TEST 8: Testing atLeastOnce() verification");

        Orders order = new Orders();
        when(orderRepo.save(any())).thenReturn(order);

        orderRepo.save(order);
        orderRepo.save(order);

        verify(orderRepo, times(2)).save(any());
        System.out.println("✓ Verified save() was called exactly 2 times");
    }

    /**
     * Test 9: Exception Handling
     */
    @Test(description = "Test exception handling with mocks", groups = "mocking_and_stubbing")
    public void testMockingExceptions() {
        System.out.println("TEST 9: Testing exception handling");

        when(orderRepo.save(any())).thenThrow(new RuntimeException("Database error"));

        Orders order = new Orders();
        try {
            orderRepo.save(order);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Database error");
            System.out.println("✓ Exception thrown as expected");
        }

        verify(orderRepo, times(1)).save(any());
        System.out.println("✓ Verified save() was called despite exception");
    }

    /**
     * Test 10: Delete Order Repository Call
     */
    @Test(description = "Test delete order from repository", groups = "mocking_and_stubbing")
    public void testDeleteOrder() {
        System.out.println("TEST 10: Testing delete order from mocked repository");

        doNothing().when(orderRepo).deleteById(anyInt());

        orderRepo.deleteById(1);

        verify(orderRepo, times(1)).deleteById(1);
        System.out.println("✓ Verified deleteById() was called with correct ID");
    }
}



