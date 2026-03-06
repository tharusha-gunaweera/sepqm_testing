package com.order.order.assertions;

import org.testng.annotations.*;
import org.testng.Assert;
import java.util.*;

/**
 * TestNG Assertions Test Suite for Order Service
 *
 * This test class demonstrates comprehensive assertions including:
 * - Basic assertions (assertEquals, assertNotNull, assertTrue, assertFalse)
 * - Collection assertions
 * - Exception assertions
 * - Multiple assertions for single test
 * - String assertions
 * - Numeric assertions
 * - Object state assertions
 */
public class OrderServiceAssertionsTest {

    private OrderTestHelper orderHelper;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.out.println("\n[CLASS SETUP] Initializing Order Service Assertions tests...");
        orderHelper = new OrderTestHelper();
        System.out.println("[CLASS SETUP] Order test helper initialized");
    }

    /**
     * Test: Basic Assert Equals for Order Creation
     * Demonstrates: assertEquals assertion
     */
    @Test(description = "Test order creation with assertEquals assertion", groups = "assertions")
    public void testOrderCreationAssertEquals() {
        System.out.println("\n[TEST] Executing: testOrderCreationAssertEquals");

        // Create order
        OrderTestData order = orderHelper.createOrder(101, 5, "2026-03-06", 250);

        // Assert equals - verifies values match expected
        Assert.assertEquals(order.getItemId(), 101, "Item ID should be 101");
        Assert.assertEquals(order.getAmount(), 250, "Amount should be 250");
        Assert.assertEquals(order.getOrderDate(), "2026-03-06", "Order date should match");

        System.out.println("[TEST] Order created with correct values verified");
    }

    /**
     * Test: Assert Not Null for Order Retrieval
     * Demonstrates: assertNotNull assertion
     */
    @Test(description = "Test order retrieval is not null", groups = "assertions")
    public void testOrderRetrievalAssertNotNull() {
        System.out.println("\n[TEST] Executing: testOrderRetrievalAssertNotNull");

        // Create and retrieve order
        OrderTestData createdOrder = orderHelper.createOrder(102, 10, "2026-03-06", 500);
        OrderTestData retrievedOrder = orderHelper.getOrderById(createdOrder.getId());

        // Assert not null - verifies object exists
        Assert.assertNotNull(retrievedOrder, "Retrieved order should not be null");
        Assert.assertNotNull(retrievedOrder.getOrderDate(), "Order date should not be null");
        Assert.assertNotNull(retrievedOrder.getOrderId(), "Order ID should not be null");

        System.out.println("[TEST] Order retrieval verified - object not null");
    }

    /**
     * Test: Assert True for Order Validation
     * Demonstrates: assertTrue assertion
     */
    @Test(description = "Test order validation with assertTrue", groups = "assertions")
    public void testOrderValidationAssertTrue() {
        System.out.println("\n[TEST] Executing: testOrderValidationAssertTrue");

        OrderTestData order = orderHelper.createOrder(103, 15, "2026-03-06", 750);

        // Assert true - verifies conditions are true
        Assert.assertTrue(order.getItemId() > 0, "Item ID should be positive");
        Assert.assertTrue(order.getAmount() > 0, "Amount should be positive");
        Assert.assertTrue(orderHelper.isValidOrder(order), "Order should be valid");
        Assert.assertTrue(order.getOrderDate() != null && !order.getOrderDate().isEmpty(),
                         "Order date should not be empty");

        System.out.println("[TEST] Order validation verified - conditions true");
    }

    /**
     * Test: Assert False for Invalid Order Conditions
     * Demonstrates: assertFalse assertion
     */
    @Test(description = "Test invalid order conditions with assertFalse", groups = "assertions")
    public void testInvalidOrderAssertFalse() {
        System.out.println("\n[TEST] Executing: testInvalidOrderAssertFalse");

        OrderTestData order = orderHelper.createOrder(104, 0, "2026-03-06", 0);

        // Assert false - verifies conditions are false
        Assert.assertFalse(order.getAmount() < 0, "Amount should not be negative");
        Assert.assertFalse(orderHelper.isOrderExpired(order), "Order should not be expired");
        Assert.assertFalse(orderHelper.isOrderCancelled(order), "Order should not be cancelled");

        System.out.println("[TEST] Invalid order conditions verified - conditions false");
    }

    /**
     * Test: Collection Assertions for Order List
     * Demonstrates: assertNotNull with collections
     */
    @Test(description = "Test order list assertions", groups = "assertions")
    public void testOrderListAssertions() {
        System.out.println("\n[TEST] Executing: testOrderListAssertions");

        // Create multiple orders
        List<OrderTestData> orders = new ArrayList<>();
        orders.add(orderHelper.createOrder(105, 20, "2026-03-06", 1000));
        orders.add(orderHelper.createOrder(106, 25, "2026-03-06", 1250));
        orders.add(orderHelper.createOrder(107, 30, "2026-03-06", 1500));

        // Assert collection properties
        Assert.assertNotNull(orders, "Orders list should not be null");
        Assert.assertEquals(orders.size(), 3, "Orders list should contain 3 items");
        Assert.assertTrue(orders.size() > 0, "Orders list should not be empty");

        // Assert each order in list
        for (OrderTestData order : orders) {
            Assert.assertNotNull(order, "Individual order should not be null");
            Assert.assertTrue(order.getAmount() > 0, "Order amount should be positive");
        }

        System.out.println("[TEST] Order list assertions verified");
    }

    /**
     * Test: String Assertions for Order Date
     * Demonstrates: assertEquals with strings
     */
    @Test(description = "Test order date string assertions", groups = "assertions")
    public void testOrderDateStringAssertions() {
        System.out.println("\n[TEST] Executing: testOrderDateStringAssertions");

        OrderTestData order = orderHelper.createOrder(108, 35, "2026-03-06", 1750);

        // String assertions
        Assert.assertNotNull(order.getOrderDate(), "Order date should not be null");
        Assert.assertTrue(order.getOrderDate().contains("2026"), "Order date should contain year 2026");
        Assert.assertTrue(order.getOrderDate().length() > 0, "Order date should not be empty");
        Assert.assertEquals(order.getOrderDate(), "2026-03-06", "Order date should match exactly");

        System.out.println("[TEST] Order date string assertions verified");
    }

    /**
     * Test: Numeric Assertions for Order Amount
     * Demonstrates: numeric comparisons
     */
    @Test(description = "Test order amount numeric assertions", groups = "assertions")
    public void testOrderAmountNumericAssertions() {
        System.out.println("\n[TEST] Executing: testOrderAmountNumericAssertions");

        OrderTestData order = orderHelper.createOrder(109, 40, "2026-03-06", 2000);

        // Numeric assertions
        Assert.assertEquals(order.getAmount(), 2000, "Amount should equal 2000");
        Assert.assertTrue(order.getAmount() >= 1000, "Amount should be >= 1000");
        Assert.assertTrue(order.getAmount() <= 3000, "Amount should be <= 3000");
        Assert.assertTrue(order.getAmount() > 500, "Amount should be > 500");
        Assert.assertFalse(order.getAmount() < 0, "Amount should not be negative");

        System.out.println("[TEST] Order amount numeric assertions verified");
    }

    /**
     * Test: Multiple Assertions for Order Update
     * Demonstrates: multiple assertions in single test
     */
    @Test(description = "Test order update with multiple assertions", groups = "assertions")
    public void testOrderUpdateMultipleAssertions() {
        System.out.println("\n[TEST] Executing: testOrderUpdateMultipleAssertions");

        // Create order
        OrderTestData originalOrder = orderHelper.createOrder(110, 45, "2026-03-06", 2250);
        int originalAmount = originalOrder.getAmount();

        // Update order
        OrderTestData updatedOrder = orderHelper.updateOrder(originalOrder.getId(), 50, 2500);

        // Multiple assertions
        Assert.assertNotNull(updatedOrder, "Updated order should not be null");
        Assert.assertEquals(updatedOrder.getId(), originalOrder.getId(), "Order ID should remain same");
        Assert.assertEquals(updatedOrder.getItemId(), 50, "Item ID should be updated");
        Assert.assertEquals(updatedOrder.getAmount(), 2500, "Amount should be updated");
        Assert.assertNotEquals(updatedOrder.getAmount(), originalAmount, "Amount should be different");
        Assert.assertTrue(updatedOrder.getAmount() > originalAmount, "New amount should be greater");

        System.out.println("[TEST] Order update verified with multiple assertions");
    }

    /**
     * Test: Assertion with Custom Messages
     * Demonstrates: assertions with descriptive messages
     */
    @Test(description = "Test assertions with custom messages", groups = "assertions")
    public void testAssertionsWithCustomMessages() {
        System.out.println("\n[TEST] Executing: testAssertionsWithCustomMessages");

        OrderTestData order = orderHelper.createOrder(111, 55, "2026-03-06", 2750);

        // Assertions with descriptive messages
        Assert.assertTrue(
            order.getItemId() > 0,
            "VALIDATION FAILED: Item ID should be positive. Current value: " + order.getItemId()
        );

        Assert.assertEquals(
            order.getOrderDate(), "2026-03-06",
            "VALIDATION FAILED: Order date mismatch. Expected: 2026-03-06, Got: " + order.getOrderDate()
        );

        Assert.assertTrue(
            order.getAmount() >= 100,
            "VALIDATION FAILED: Order amount should be at least 100. Current value: " + order.getAmount()
        );

        System.out.println("[TEST] Assertions with custom messages verified");
    }

    /**
     * Test: Assertion for Order Deletion
     * Demonstrates: assertFalse after deletion
     */
    @Test(description = "Test order deletion with assertions", groups = "assertions")
    public void testOrderDeletionAssertions() {
        System.out.println("\n[TEST] Executing: testOrderDeletionAssertions");

        // Create order
        OrderTestData order = orderHelper.createOrder(112, 60, "2026-03-06", 3000);
        int orderId = order.getId();

        // Verify order exists
        Assert.assertNotNull(orderHelper.getOrderById(orderId), "Order should exist before deletion");

        // Delete order
        orderHelper.deleteOrder(orderId);

        // Verify order is deleted
        Assert.assertNull(orderHelper.getOrderById(orderId), "Order should be null after deletion");
        Assert.assertFalse(orderHelper.orderExists(orderId), "Order should not exist after deletion");

        System.out.println("[TEST] Order deletion verified");
    }

    /**
     * Test: Assertion for Order Status
     * Demonstrates: enum and status assertions
     */
    @Test(description = "Test order status assertions", groups = "assertions")
    public void testOrderStatusAssertions() {
        System.out.println("\n[TEST] Executing: testOrderStatusAssertions");

        OrderTestData order = orderHelper.createOrder(113, 65, "2026-03-06", 3250);

        // Status assertions
        String status = orderHelper.getOrderStatus(order.getId());
        Assert.assertNotNull(status, "Order status should not be null");
        Assert.assertEquals(status, "PENDING", "New order status should be PENDING");
        Assert.assertNotEquals(status, "COMPLETED", "New order should not be COMPLETED");
        Assert.assertTrue(status.equals("PENDING") || status.equals("PROCESSING"),
                         "Status should be PENDING or PROCESSING");

        System.out.println("[TEST] Order status assertions verified");
    }

    /**
     * Test: Assertion for Order Comparison
     * Demonstrates: comparison assertions
     */
    @Test(description = "Test order comparison assertions", groups = "assertions")
    public void testOrderComparisonAssertions() {
        System.out.println("\n[TEST] Executing: testOrderComparisonAssertions");

        OrderTestData order1 = orderHelper.createOrder(114, 70, "2026-03-06", 3500);
        OrderTestData order2 = orderHelper.createOrder(115, 75, "2026-03-06", 3750);

        // Comparison assertions
        Assert.assertNotEquals(order1.getId(), order2.getId(), "Order IDs should be different");
        Assert.assertNotEquals(order1.getAmount(), order2.getAmount(), "Order amounts should be different");
        Assert.assertTrue(order2.getAmount() > order1.getAmount(), "Order 2 amount should be greater");
        Assert.assertEquals(order1.getOrderDate(), order2.getOrderDate(), "Both orders should have same date");

        System.out.println("[TEST] Order comparison assertions verified");
    }

    /**
     * Test: Assertion Soft Verification
     * Demonstrates: multiple related assertions
     */
    @Test(description = "Test comprehensive order data assertions", groups = "assertions")
    public void testComprehensiveOrderDataAssertions() {
        System.out.println("\n[TEST] Executing: testComprehensiveOrderDataAssertions");

        OrderTestData order = orderHelper.createOrder(80, 116, "2026-03-06", 4000);

        // Comprehensive data verification
        Assert.assertNotNull(order, "Order object should not be null");
        Assert.assertTrue(order.getId() > 0, "Order ID should be positive");
        Assert.assertEquals(order.getItemId(), 80, "Item ID should be 80");
        Assert.assertEquals(order.getAmount(), 4000, "Amount should be 4000");
        Assert.assertEquals(order.getOrderDate(), "2026-03-06", "Order date should be 2026-03-06");
        Assert.assertTrue(order.getItemId() > 0, "Item ID should be positive");
        Assert.assertTrue(order.getAmount() > 0, "Amount should be positive");
        Assert.assertNotNull(order.getOrderDate(), "Order date should not be null");

        System.out.println("[TEST] Comprehensive order data assertions verified");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("\n[CLASS TEARDOWN] Cleaning up Order Service Assertions tests...");
        orderHelper = null;
        System.out.println("[CLASS TEARDOWN] Order Service Assertions tests completed");
    }

    /**
     * Helper class for Order testing
     */
    public static class OrderTestData {
        private int id;
        private int itemId;
        private String orderDate;
        private int amount;

        public OrderTestData(int id, int itemId, String orderDate, int amount) {
            this.id = id;
            this.itemId = itemId;
            this.orderDate = orderDate;
            this.amount = amount;
        }

        public int getId() { return id; }
        public int getItemId() { return itemId; }
        public String getOrderDate() { return orderDate; }
        public int getAmount() { return amount; }
        public String getOrderId() { return "ORD-" + id; }

        public void setItemId(int itemId) { this.itemId = itemId; }
        public void setAmount(int amount) { this.amount = amount; }

        @Override
        public String toString() {
            return "Order{id=" + id + ", itemId=" + itemId + ", date=" + orderDate + ", amount=" + amount + "}";
        }
    }

    /**
     * Helper class for Order operations
     */
    public static class OrderTestHelper {
        private Map<Integer, OrderTestData> orders = new HashMap<>();
        private int orderCounter = 1000;

        public OrderTestData createOrder(int itemId, int quantity, String orderDate, int amount) {
            int orderId = ++orderCounter;
            OrderTestData order = new OrderTestData(orderId, itemId, orderDate, amount);
            orders.put(orderId, order);
            System.out.println("  → Order created: " + orderId + " (Item: " + itemId + ", Amount: " + amount + ")");
            return order;
        }

        public OrderTestData getOrderById(int orderId) {
            return orders.get(orderId);
        }

        public OrderTestData updateOrder(int orderId, int newItemId, int newAmount) {
            OrderTestData order = orders.get(orderId);
            if (order != null) {
                order.setItemId(newItemId);
                order.setAmount(newAmount);
                System.out.println("  → Order updated: " + orderId);
            }
            return order;
        }

        public void deleteOrder(int orderId) {
            orders.remove(orderId);
            System.out.println("  → Order deleted: " + orderId);
        }

        public boolean orderExists(int orderId) {
            return orders.containsKey(orderId);
        }

        public boolean isValidOrder(OrderTestData order) {
            return order != null && order.getItemId() > 0 && order.getAmount() > 0;
        }

        public boolean isOrderExpired(OrderTestData order) {
            return false; // Simplified for testing
        }

        public boolean isOrderCancelled(OrderTestData order) {
            return false; // Simplified for testing
        }

        public String getOrderStatus(int orderId) {
            OrderTestData order = orders.get(orderId);
            return order != null ? "PENDING" : "NOT_FOUND";
        }
    }
}

