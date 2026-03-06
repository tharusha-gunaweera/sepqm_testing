package com.order.order.test_reporting;

import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TestNG Test Reporting Test Suite for Order Service
 *
 * This test class demonstrates comprehensive test reporting including:
 * - Detailed test execution logging with Reporter.log()
 * - Performance metrics (execution time, throughput)
 * - Test result tracking and analysis
 * - Custom HTML report generation via Reporter
 * - Test execution summary and statistics
 * - Failure/Success reporting with detailed messages
 * - Test grouping and categorization
 * - Performance benchmarking
 * - Test result metadata
 */
public class OrderServiceTestReportingTest {

    private OrderReportingHelper orderHelper;
    private long testStartTime;
    private int totalTestsExecuted;
    private int passedTests;
    private int failedTests;
    private List<TestExecutionMetrics> executionMetrics;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup(ITestContext context) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("[SUITE SETUP] Starting Order Service Test Reporting Suite");
        System.out.println("Suite Name: " + context.getSuite().getName());
        System.out.println("Start Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("=".repeat(80));

        Reporter.log("<h3 style='color: blue; border: 2px solid blue; padding: 10px;'>Order Service Test Reporting Suite Started</h3>");
        Reporter.log("<p><b>Suite Name:</b> " + context.getSuite().getName() + "</p>");
        Reporter.log("<p><b>Start Time:</b> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</p>");

        totalTestsExecuted = 0;
        passedTests = 0;
        failedTests = 0;
        executionMetrics = new ArrayList<>();
    }

    @BeforeClass(alwaysRun = true)
    public void setUp(ITestContext context) {
        System.out.println("\n[CLASS SETUP] Initializing Order Service Test Reporting tests");
        testStartTime = System.currentTimeMillis();
        orderHelper = new OrderReportingHelper();

        Reporter.log("<div style='background-color: #e7f3fe; border-left: 4px solid #2196F3; padding: 10px; margin: 10px 0;'>");
        Reporter.log("<b>Test Class Setup:</b> " + context.getCurrentXmlTest().getName());
        Reporter.log("</div>");

        System.out.println("[CLASS SETUP] Order test helper initialized - Test Reporting Suite");
    }

    /**
     * Test: Order Creation with Detailed Reporting
     * Demonstrates: Detailed test logging and metrics
     */
    @Test(description = "Test order creation with detailed reporting", groups = "test_reporting")
    public void testOrderCreationWithReporting() {
        Reporter.log("<h4 style='color: green;'>Test: Order Creation with Detailed Reporting</h4>");

        long startTime = System.currentTimeMillis();
        Reporter.log("<p><b>Test Started at:</b> " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "</p>");

        try {
            // Create multiple orders and log each step
            Reporter.log("<p><b>Creating Order Set:</b></p>");
            Reporter.log("<ol>");

            OrderTestData order1 = orderHelper.createOrder(201, 10, "2026-03-06", 1000);
            Reporter.log("<li>Order 1 Created - ID: " + order1.getId() + ", Amount: " + order1.getAmount() + "</li>");

            OrderTestData order2 = orderHelper.createOrder(202, 15, "2026-03-06", 1500);
            Reporter.log("<li>Order 2 Created - ID: " + order2.getId() + ", Amount: " + order2.getAmount() + "</li>");

            OrderTestData order3 = orderHelper.createOrder(203, 20, "2026-03-06", 2000);
            Reporter.log("<li>Order 3 Created - ID: " + order3.getId() + ", Amount: " + order3.getAmount() + "</li>");

            Reporter.log("</ol>");

            // Assertions
            Assert.assertNotNull(order1, "Order 1 should not be null");
            Assert.assertNotNull(order2, "Order 2 should not be null");
            Assert.assertNotNull(order3, "Order 3 should not be null");

            Reporter.log("<p style='color: green;'><b>✓ All assertions passed</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testOrderCreationWithReporting", executionTime, true);

            Reporter.log("<p><b>Test Execution Time:</b> " + executionTime + " ms</p>");
            Reporter.log("<p style='color: green;'><b>✓ Test PASSED</b></p>");

            totalTestsExecuted++;
            passedTests++;

        } catch (Exception e) {
            Reporter.log("<p style='color: red;'><b>✗ Test FAILED: " + e.getMessage() + "</b></p>");
            failedTests++;
            throw e;
        }
    }

    /**
     * Test: Order Retrieval Performance Reporting
     * Demonstrates: Performance metrics and benchmarking
     */
    @Test(description = "Test order retrieval with performance reporting", groups = "test_reporting")
    public void testOrderRetrievalPerformanceReporting() {
        Reporter.log("<h4 style='color: green;'>Test: Order Retrieval Performance Reporting</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Retrieving orders and measuring performance...</b></p>");
            Reporter.log("<table border='1' cellpadding='5' cellspacing='0'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>Order ID</th><th>Retrieval Time (ms)</th><th>Status</th></tr>");

            // Create orders
            OrderTestData order = orderHelper.createOrder(204, 25, "2026-03-06", 2500);

            // Retrieve with performance tracking
            long retrievalStart = System.currentTimeMillis();
            OrderTestData retrievedOrder = orderHelper.getOrderById(order.getId());
            long retrievalTime = System.currentTimeMillis() - retrievalStart;

            Reporter.log("<tr><td>" + retrievedOrder.getId() + "</td><td>" + retrievalTime + "</td><td style='color: green;'>Success</td></tr>");
            Reporter.log("</table>");

            // Performance assertion
            Assert.assertTrue(retrievalTime < 1000, "Retrieval should complete within 1 second");
            Reporter.log("<p style='color: green;'><b>✓ Performance requirement met (< 1000ms)</b></p>");

            // Calculate throughput
            int orderCount = 5;
            long throughputStart = System.currentTimeMillis();
            for (int i = 0; i < orderCount; i++) {
                orderHelper.createOrder(300 + i, 30 + i, "2026-03-06", 3000 + (i * 100));
            }
            long throughputTime = System.currentTimeMillis() - throughputStart;
            double throughput = (orderCount * 1000.0) / throughputTime;

            Reporter.log("<p><b>Throughput Measurement:</b></p>");
            Reporter.log("<p>Orders Created: " + orderCount + " in " + throughputTime + " ms</p>");
            Reporter.log("<p>Throughput: " + String.format("%.2f", throughput) + " orders/second</p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testOrderRetrievalPerformanceReporting", executionTime, true);

            Reporter.log("<p><b>Total Test Time:</b> " + executionTime + " ms</p>");
            Reporter.log("<p style='color: green;'><b>✓ Test PASSED</b></p>");

            totalTestsExecuted++;
            passedTests++;

        } catch (Exception e) {
            Reporter.log("<p style='color: red;'><b>✗ Test FAILED: " + e.getMessage() + "</b></p>");
            failedTests++;
            throw e;
        }
    }

    /**
     * Test: Order Update with Detailed State Reporting
     * Demonstrates: State tracking and change logging
     */
    @Test(description = "Test order update with state change reporting", groups = "test_reporting")
    public void testOrderUpdateStateReporting() {
        Reporter.log("<h4 style='color: green;'>Test: Order Update with State Change Reporting</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Order State Change Tracking:</b></p>");

            OrderTestData originalOrder = orderHelper.createOrder(205, 35, "2026-03-06", 3500);

            Reporter.log("<div style='background-color: #fff3cd; padding: 10px; margin: 10px 0;'>");
            Reporter.log("<b>BEFORE Update:</b><br/>");
            Reporter.log("Order ID: " + originalOrder.getId() + "<br/>");
            Reporter.log("Item ID: " + originalOrder.getItemId() + "<br/>");
            Reporter.log("Amount: " + originalOrder.getAmount() + "<br/>");
            Reporter.log("</div>");

            // Update order
            OrderTestData updatedOrder = orderHelper.updateOrder(originalOrder.getId(), 40, 4000);

            Reporter.log("<div style='background-color: #d4edda; padding: 10px; margin: 10px 0;'>");
            Reporter.log("<b>AFTER Update:</b><br/>");
            Reporter.log("Order ID: " + updatedOrder.getId() + "<br/>");
            Reporter.log("Item ID: " + updatedOrder.getItemId() + " (Changed from " + originalOrder.getItemId() + ")<br/>");
            Reporter.log("Amount: " + updatedOrder.getAmount() + " (Changed from " + originalOrder.getAmount() + ")<br/>");
            Reporter.log("</div>");

            // Verify changes
            Assert.assertEquals(updatedOrder.getItemId(), 40, "Item ID should be updated");
            Assert.assertEquals(updatedOrder.getAmount(), 4000, "Amount should be updated");

            Reporter.log("<p style='color: green;'><b>✓ Order state changes verified</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testOrderUpdateStateReporting", executionTime, true);

            Reporter.log("<p><b>Execution Time:</b> " + executionTime + " ms</p>");
            Reporter.log("<p style='color: green;'><b>✓ Test PASSED</b></p>");

            totalTestsExecuted++;
            passedTests++;

        } catch (Exception e) {
            Reporter.log("<p style='color: red;'><b>✗ Test FAILED: " + e.getMessage() + "</b></p>");
            failedTests++;
            throw e;
        }
    }

    /**
     * Test: Order Batch Processing Report
     * Demonstrates: Batch operation logging and statistics
     */
    @Test(description = "Test order batch processing with detailed reporting", groups = "test_reporting")
    public void testOrderBatchProcessingReport() {
        Reporter.log("<h4 style='color: green;'>Test: Order Batch Processing Report</h4>");

        long startTime = System.currentTimeMillis();

        try {
            int batchSize = 10;
            Reporter.log("<p><b>Batch Processing Report:</b></p>");
            Reporter.log("<p>Batch Size: " + batchSize + " orders</p>");

            Reporter.log("<table border='1' cellpadding='5' cellspacing='0' style='width: 100%;'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>#</th><th>Order ID</th><th>Amount</th><th>Status</th><th>Time (ms)</th></tr>");

            List<OrderTestData> createdOrders = new ArrayList<>();

            for (int i = 1; i <= batchSize; i++) {
                long itemStart = System.currentTimeMillis();
                OrderTestData order = orderHelper.createOrder(400 + i, 45 + i, "2026-03-06", 4500 + (i * 100));
                long itemTime = System.currentTimeMillis() - itemStart;
                createdOrders.add(order);

                String statusColor = order.getAmount() > 5000 ? "green" : "blue";
                Reporter.log("<tr><td>" + i + "</td><td>" + order.getId() + "</td><td>" + order.getAmount() +
                           "</td><td style='color: " + statusColor + ";'>Success</td><td>" + itemTime + "</td></tr>");
            }

            Reporter.log("</table>");

            // Calculate statistics
            int successCount = createdOrders.size();
            double avgAmount = createdOrders.stream().mapToInt(OrderTestData::getAmount).average().orElse(0);

            Reporter.log("<p><b>Batch Statistics:</b></p>");
            Reporter.log("<ul>");
            Reporter.log("<li>Total Orders Processed: " + successCount + "</li>");
            Reporter.log("<li>Success Rate: 100%</li>");
            Reporter.log("<li>Average Amount: " + String.format("%.2f", avgAmount) + "</li>");
            Reporter.log("<li>Min Amount: " + createdOrders.stream().mapToInt(OrderTestData::getAmount).min().orElse(0) + "</li>");
            Reporter.log("<li>Max Amount: " + createdOrders.stream().mapToInt(OrderTestData::getAmount).max().orElse(0) + "</li>");
            Reporter.log("</ul>");

            Assert.assertEquals(createdOrders.size(), batchSize, "All orders should be created");
            Reporter.log("<p style='color: green;'><b>✓ Batch processing completed successfully</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testOrderBatchProcessingReport", executionTime, true);

            Reporter.log("<p><b>Total Batch Time:</b> " + executionTime + " ms</p>");
            Reporter.log("<p style='color: green;'><b>✓ Test PASSED</b></p>");

            totalTestsExecuted++;
            passedTests++;

        } catch (Exception e) {
            Reporter.log("<p style='color: red;'><b>✗ Test FAILED: " + e.getMessage() + "</b></p>");
            failedTests++;
            throw e;
        }
    }

    /**
     * Test: Order Deletion with Comprehensive Reporting
     * Demonstrates: Operation tracking and verification
     */
    @Test(description = "Test order deletion with comprehensive reporting", groups = "test_reporting")
    public void testOrderDeletionReport() {
        Reporter.log("<h4 style='color: green;'>Test: Order Deletion with Comprehensive Reporting</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Order Deletion Workflow:</b></p>");

            // Create order
            OrderTestData order = orderHelper.createOrder(500, 50, "2026-03-06", 5000);
            Reporter.log("<p>Step 1: Order created - ID: " + order.getId() + "</p>");

            // Verify existence
            OrderTestData beforeDelete = orderHelper.getOrderById(order.getId());
            Assert.assertNotNull(beforeDelete, "Order should exist before deletion");
            Reporter.log("<p>Step 2: Order verified - exists in system</p>");

            // Delete order
            long deleteStart = System.currentTimeMillis();
            orderHelper.deleteOrder(order.getId());
            long deleteTime = System.currentTimeMillis() - deleteStart;
            Reporter.log("<p>Step 3: Order deleted in " + deleteTime + " ms</p>");

            // Verify deletion
            OrderTestData afterDelete = orderHelper.getOrderById(order.getId());
            Assert.assertNull(afterDelete, "Order should be null after deletion");
            Reporter.log("<p>Step 4: Deletion verified - order no longer in system</p>");

            Reporter.log("<p style='color: green;'><b>✓ Deletion workflow completed successfully</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testOrderDeletionReport", executionTime, true);

            Reporter.log("<p><b>Total Deletion Time:</b> " + executionTime + " ms</p>");
            Reporter.log("<p style='color: green;'><b>✓ Test PASSED</b></p>");

            totalTestsExecuted++;
            passedTests++;

        } catch (Exception e) {
            Reporter.log("<p style='color: red;'><b>✗ Test FAILED: " + e.getMessage() + "</b></p>");
            failedTests++;
            throw e;
        }
    }

    /**
     * Test: Order Validation Report
     * Demonstrates: Validation result reporting
     */
    @Test(description = "Test order validation with reporting", groups = "test_reporting")
    public void testOrderValidationReport() {
        Reporter.log("<h4 style='color: green;'>Test: Order Validation Report</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Order Validation Checks:</b></p>");
            Reporter.log("<table border='1' cellpadding='5' cellspacing='0' style='width: 100%;'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>Validation Check</th><th>Result</th><th>Status</th></tr>");

            OrderTestData order = orderHelper.createOrder(501, 55, "2026-03-06", 5500);

            // Validation checks
            boolean validOrder = orderHelper.isValidOrder(order);
            Reporter.log("<tr><td>Valid Order Structure</td><td>" + validOrder + "</td><td style='color: green;'>✓</td></tr>");

            boolean hasPositiveAmount = order.getAmount() > 0;
            Reporter.log("<tr><td>Has Positive Amount</td><td>" + hasPositiveAmount + "</td><td style='color: green;'>✓</td></tr>");

            boolean hasPositiveItemId = order.getItemId() > 0;
            Reporter.log("<tr><td>Has Positive Item ID</td><td>" + hasPositiveItemId + "</td><td style='color: green;'>✓</td></tr>");

            boolean hasOrderDate = order.getOrderDate() != null && !order.getOrderDate().isEmpty();
            Reporter.log("<tr><td>Has Order Date</td><td>" + hasOrderDate + "</td><td style='color: green;'>✓</td></tr>");

            Reporter.log("</table>");

            // Assertions
            Assert.assertTrue(validOrder, "Order should be valid");
            Assert.assertTrue(hasPositiveAmount, "Amount should be positive");
            Assert.assertTrue(hasPositiveItemId, "Item ID should be positive");
            Assert.assertTrue(hasOrderDate, "Order date should exist");

            Reporter.log("<p style='color: green;'><b>✓ All validation checks passed</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testOrderValidationReport", executionTime, true);

            Reporter.log("<p><b>Total Validation Time:</b> " + executionTime + " ms</p>");
            Reporter.log("<p style='color: green;'><b>✓ Test PASSED</b></p>");

            totalTestsExecuted++;
            passedTests++;

        } catch (Exception e) {
            Reporter.log("<p style='color: red;'><b>✗ Test FAILED: " + e.getMessage() + "</b></p>");
            failedTests++;
            throw e;
        }
    }

    /**
     * Test: Multi-step Order Workflow Report
     * Demonstrates: Complex workflow tracking
     */
    @Test(description = "Test multi-step order workflow with detailed reporting", groups = "test_reporting")
    public void testMultiStepOrderWorkflowReport() {
        Reporter.log("<h4 style='color: green;'>Test: Multi-Step Order Workflow Report</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Order Workflow Execution:</b></p>");

            Map<String, Long> stepTimings = new LinkedHashMap<>();

            // Step 1: Create
            long step1Start = System.currentTimeMillis();
            OrderTestData order = orderHelper.createOrder(502, 60, "2026-03-06", 6000);
            stepTimings.put("Create", System.currentTimeMillis() - step1Start);
            Reporter.log("<p>✓ Step 1 (Create): " + stepTimings.get("Create") + " ms - Order ID: " + order.getId() + "</p>");

            // Step 2: Retrieve
            long step2Start = System.currentTimeMillis();
            OrderTestData retrieved = orderHelper.getOrderById(order.getId());
            stepTimings.put("Retrieve", System.currentTimeMillis() - step2Start);
            Reporter.log("<p>✓ Step 2 (Retrieve): " + stepTimings.get("Retrieve") + " ms - Amount: " + retrieved.getAmount() + "</p>");

            // Step 3: Update
            long step3Start = System.currentTimeMillis();
            OrderTestData updated = orderHelper.updateOrder(order.getId(), 65, 6500);
            stepTimings.put("Update", System.currentTimeMillis() - step3Start);
            Reporter.log("<p>✓ Step 3 (Update): " + stepTimings.get("Update") + " ms - New Amount: " + updated.getAmount() + "</p>");

            // Step 4: Validate
            long step4Start = System.currentTimeMillis();
            boolean isValid = orderHelper.isValidOrder(updated);
            stepTimings.put("Validate", System.currentTimeMillis() - step4Start);
            Reporter.log("<p>✓ Step 4 (Validate): " + stepTimings.get("Validate") + " ms - Valid: " + isValid + "</p>");

            // Step 5: Delete
            long step5Start = System.currentTimeMillis();
            orderHelper.deleteOrder(order.getId());
            stepTimings.put("Delete", System.currentTimeMillis() - step5Start);
            Reporter.log("<p>✓ Step 5 (Delete): " + stepTimings.get("Delete") + " ms</p>");

            // Summary
            Reporter.log("<p><b>Workflow Summary:</b></p>");
            Reporter.log("<table border='1' cellpadding='5' cellspacing='0'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>Step</th><th>Time (ms)</th></tr>");

            long totalTime = 0;
            for (Map.Entry<String, Long> entry : stepTimings.entrySet()) {
                Reporter.log("<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");
                totalTime += entry.getValue();
            }

            Reporter.log("<tr style='background-color: #f2f2f2;'><td><b>Total</b></td><td><b>" + totalTime + "</b></td></tr>");
            Reporter.log("</table>");

            Reporter.log("<p style='color: green;'><b>✓ Complete workflow executed successfully</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testMultiStepOrderWorkflowReport", executionTime, true);

            Reporter.log("<p><b>Total Test Time:</b> " + executionTime + " ms</p>");
            Reporter.log("<p style='color: green;'><b>✓ Test PASSED</b></p>");

            totalTestsExecuted++;
            passedTests++;

        } catch (Exception e) {
            Reporter.log("<p style='color: red;'><b>✗ Test FAILED: " + e.getMessage() + "</b></p>");
            failedTests++;
            throw e;
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(ITestContext context) {
        long classExecutionTime = System.currentTimeMillis() - testStartTime;

        Reporter.log("<div style='background-color: #f8f9fa; border-left: 4px solid #6c757d; padding: 10px; margin: 10px 0;'>");
        Reporter.log("<b>Test Class Teardown:</b> " + context.getCurrentXmlTest().getName() + "<br/>");
        Reporter.log("Class Execution Time: " + classExecutionTime + " ms");
        Reporter.log("</div>");

        System.out.println("\n[CLASS TEARDOWN] Order Service Test Reporting tests completed");
        System.out.println("Class execution time: " + classExecutionTime + " ms");

        orderHelper = null;
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown(ITestContext context) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("[SUITE TEARDOWN] Order Service Test Reporting Suite Completed");
        System.out.println("End Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("Total Tests: " + totalTestsExecuted + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println("=".repeat(80));

        // Generate final report
        Reporter.log("<hr style='border: 2px solid #2196F3;'/>");
        Reporter.log("<h3 style='color: blue;'>Order Service Test Reporting Suite - Final Report</h3>");
        Reporter.log("<p><b>Suite End Time:</b> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</p>");
        Reporter.log("<table border='1' cellpadding='10' cellspacing='0' style='width: 50%;'>");
        Reporter.log("<tr style='background-color: #4CAF50; color: white;'><th>Metric</th><th>Value</th></tr>");
        Reporter.log("<tr><td>Total Tests Executed</td><td>" + totalTestsExecuted + "</td></tr>");
        Reporter.log("<tr><td>Passed Tests</td><td style='color: green;'><b>" + passedTests + "</b></td></tr>");
        Reporter.log("<tr><td>Failed Tests</td><td style='color: red;'><b>" + failedTests + "</b></td></tr>");
        Reporter.log("<tr><td>Success Rate</td><td>" + (totalTestsExecuted > 0 ? (passedTests * 100 / totalTestsExecuted) + "%" : "N/A") + "</td></tr>");
        Reporter.log("</table>");

        // Performance summary
        if (!executionMetrics.isEmpty()) {
            Reporter.log("<h4>Performance Metrics Summary</h4>");
            Reporter.log("<table border='1' cellpadding='10' cellspacing='0' style='width: 70%;'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>Test Name</th><th>Execution Time (ms)</th><th>Status</th></tr>");

            long totalExecutionTime = 0;
            for (TestExecutionMetrics metric : executionMetrics) {
                Reporter.log("<tr><td>" + metric.testName + "</td><td>" + metric.executionTime + "</td><td>" + metric.status + "</td></tr>");
                totalExecutionTime += metric.executionTime;
            }

            Reporter.log("<tr style='background-color: #f2f2f2;'><td><b>Total</b></td><td><b>" + totalExecutionTime + "</b></td><td></td></tr>");
            Reporter.log("</table>");
        }

        Reporter.log("<p><b>Report Generated:</b> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</p>");
    }

    /**
     * Helper method to record test execution metrics
     */
    private void recordMetrics(String testName, long executionTime, boolean passed) {
        TestExecutionMetrics metric = new TestExecutionMetrics(testName, executionTime, passed ? "PASS" : "FAIL");
        executionMetrics.add(metric);
    }

    /**
     * Metrics tracking helper class
     */
    private static class TestExecutionMetrics {
        String testName;
        long executionTime;
        String status;

        TestExecutionMetrics(String testName, long executionTime, String status) {
            this.testName = testName;
            this.executionTime = executionTime;
            this.status = status;
        }
    }

    /**
     * Order test data class
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

        public void setItemId(int itemId) { this.itemId = itemId; }
        public void setAmount(int amount) { this.amount = amount; }

        @Override
        public String toString() {
            return "Order{id=" + id + ", itemId=" + itemId + ", date=" + orderDate + ", amount=" + amount + "}";
        }
    }

    /**
     * Helper class for order operations
     */
    public static class OrderReportingHelper {
        private Map<Integer, OrderTestData> orders = new HashMap<>();
        private int orderCounter = 2000;

        public OrderTestData createOrder(int itemId, int quantity, String orderDate, int amount) {
            int orderId = ++orderCounter;
            OrderTestData order = new OrderTestData(orderId, itemId, orderDate, amount);
            orders.put(orderId, order);
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
            }
            return order;
        }

        public void deleteOrder(int orderId) {
            orders.remove(orderId);
        }

        public boolean isValidOrder(OrderTestData order) {
            return order != null && order.getItemId() > 0 && order.getAmount() > 0 && order.getOrderDate() != null;
        }
    }
}



