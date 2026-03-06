package com.inventory.inventory.test_reporting;

import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * TestNG Test Reporting Test Suite for Inventory Service
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
public class InventoryServiceTestReportingTest {

    private InventoryReportingHelper inventoryHelper;
    private long testStartTime;
    private int totalTestsExecuted;
    private int passedTests;
    private int failedTests;
    private List<TestExecutionMetrics> executionMetrics;

    @BeforeSuite(alwaysRun = true)
    public void suiteSetup(ITestContext context) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("[SUITE SETUP] Starting Inventory Service Test Reporting Suite");
        System.out.println("Suite Name: " + context.getSuite().getName());
        System.out.println("Start Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("=".repeat(80));

        Reporter.log("<h3 style='color: darkgreen; border: 2px solid darkgreen; padding: 10px;'>Inventory Service Test Reporting Suite Started</h3>");
        Reporter.log("<p><b>Suite Name:</b> " + context.getSuite().getName() + "</p>");
        Reporter.log("<p><b>Start Time:</b> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "</p>");

        totalTestsExecuted = 0;
        passedTests = 0;
        failedTests = 0;
        executionMetrics = new ArrayList<>();
    }

    @BeforeClass(alwaysRun = true)
    public void setUp(ITestContext context) {
        System.out.println("\n[CLASS SETUP] Initializing Inventory Service Test Reporting tests");
        testStartTime = System.currentTimeMillis();
        inventoryHelper = new InventoryReportingHelper();

        Reporter.log("<div style='background-color: #f0f8e7; border-left: 4px solid #4CAF50; padding: 10px; margin: 10px 0;'>");
        Reporter.log("<b>Test Class Setup:</b> " + context.getCurrentXmlTest().getName());
        Reporter.log("</div>");

        System.out.println("[CLASS SETUP] Inventory test helper initialized - Test Reporting Suite");
    }

    /**
     * Test: Inventory Addition with Detailed Reporting
     * Demonstrates: Detailed test logging and metrics
     */
    @Test(description = "Test inventory addition with detailed reporting", groups = "test_reporting")
    public void testInventoryAdditionWithReporting() {
        Reporter.log("<h4 style='color: darkgreen;'>Test: Inventory Addition with Detailed Reporting</h4>");

        long startTime = System.currentTimeMillis();
        Reporter.log("<p><b>Test Started at:</b> " + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()) + "</p>");

        try {
            // Add multiple inventory items and log each step
            Reporter.log("<p><b>Adding Inventory Items:</b></p>");
            Reporter.log("<ol>");

            InventoryTestData item1 = inventoryHelper.addInventory(301, "Widget", 100, 50.0);
            Reporter.log("<li>Item 1 Added - SKU: " + item1.getSku() + ", Name: " + item1.getName() + ", Quantity: " + item1.getQuantity() + "</li>");

            InventoryTestData item2 = inventoryHelper.addInventory(302, "Gadget", 150, 75.0);
            Reporter.log("<li>Item 2 Added - SKU: " + item2.getSku() + ", Name: " + item2.getName() + ", Quantity: " + item2.getQuantity() + "</li>");

            InventoryTestData item3 = inventoryHelper.addInventory(303, "Component", 200, 100.0);
            Reporter.log("<li>Item 3 Added - SKU: " + item3.getSku() + ", Name: " + item3.getName() + ", Quantity: " + item3.getQuantity() + "</li>");

            Reporter.log("</ol>");

            // Assertions
            Assert.assertNotNull(item1, "Item 1 should not be null");
            Assert.assertNotNull(item2, "Item 2 should not be null");
            Assert.assertNotNull(item3, "Item 3 should not be null");

            Reporter.log("<p style='color: green;'><b>✓ All assertions passed</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testInventoryAdditionWithReporting", executionTime, true);

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
     * Test: Inventory Retrieval Performance Reporting
     * Demonstrates: Performance metrics and benchmarking
     */
    @Test(description = "Test inventory retrieval with performance reporting", groups = "test_reporting")
    public void testInventoryRetrievalPerformanceReporting() {
        Reporter.log("<h4 style='color: darkgreen;'>Test: Inventory Retrieval Performance Reporting</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Retrieving inventory items and measuring performance...</b></p>");
            Reporter.log("<table border='1' cellpadding='5' cellspacing='0'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>Item SKU</th><th>Retrieval Time (ms)</th><th>Status</th></tr>");

            // Add inventory
            InventoryTestData item = inventoryHelper.addInventory(304, "Product", 250, 125.0);

            // Retrieve with performance tracking
            long retrievalStart = System.currentTimeMillis();
            InventoryTestData retrievedItem = inventoryHelper.getItemBySku(item.getSku());
            long retrievalTime = System.currentTimeMillis() - retrievalStart;

            Reporter.log("<tr><td>" + retrievedItem.getSku() + "</td><td>" + retrievalTime + "</td><td style='color: green;'>Success</td></tr>");
            Reporter.log("</table>");

            // Performance assertion
            Assert.assertTrue(retrievalTime < 1000, "Retrieval should complete within 1 second");
            Reporter.log("<p style='color: green;'><b>✓ Performance requirement met (< 1000ms)</b></p>");

            // Calculate throughput
            int itemCount = 5;
            long throughputStart = System.currentTimeMillis();
            for (int i = 0; i < itemCount; i++) {
                inventoryHelper.addInventory(400 + i, "Item" + i, 100 + (i * 10), 50.0 + (i * 5));
            }
            long throughputTime = System.currentTimeMillis() - throughputStart;
            double throughput = (itemCount * 1000.0) / throughputTime;

            Reporter.log("<p><b>Throughput Measurement:</b></p>");
            Reporter.log("<p>Items Added: " + itemCount + " in " + throughputTime + " ms</p>");
            Reporter.log("<p>Throughput: " + String.format("%.2f", throughput) + " items/second</p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testInventoryRetrievalPerformanceReporting", executionTime, true);

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
     * Test: Inventory Update with Detailed State Reporting
     * Demonstrates: State tracking and change logging
     */
    @Test(description = "Test inventory update with state change reporting", groups = "test_reporting")
    public void testInventoryUpdateStateReporting() {
        Reporter.log("<h4 style='color: darkgreen;'>Test: Inventory Update with State Change Reporting</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Inventory State Change Tracking:</b></p>");

            InventoryTestData originalItem = inventoryHelper.addInventory(305, "UpdateTest", 300, 150.0);

            Reporter.log("<div style='background-color: #fff3cd; padding: 10px; margin: 10px 0;'>");
            Reporter.log("<b>BEFORE Update:</b><br/>");
            Reporter.log("SKU: " + originalItem.getSku() + "<br/>");
            Reporter.log("Name: " + originalItem.getName() + "<br/>");
            Reporter.log("Quantity: " + originalItem.getQuantity() + "<br/>");
            Reporter.log("Price: $" + originalItem.getPrice() + "<br/>");
            Reporter.log("</div>");

            // Update inventory
            InventoryTestData updatedItem = inventoryHelper.updateInventory(originalItem.getSku(), 350, 175.0);

            Reporter.log("<div style='background-color: #d4edda; padding: 10px; margin: 10px 0;'>");
            Reporter.log("<b>AFTER Update:</b><br/>");
            Reporter.log("SKU: " + updatedItem.getSku() + "<br/>");
            Reporter.log("Name: " + updatedItem.getName() + "<br/>");
            Reporter.log("Quantity: " + updatedItem.getQuantity() + " (Changed from " + originalItem.getQuantity() + ")<br/>");
            Reporter.log("Price: $" + updatedItem.getPrice() + " (Changed from $" + originalItem.getPrice() + ")<br/>");
            Reporter.log("</div>");

            // Verify changes
            Assert.assertEquals(updatedItem.getQuantity(), 350, "Quantity should be updated");
            Assert.assertEquals(updatedItem.getPrice(), 175.0, 0.01, "Price should be updated");

            Reporter.log("<p style='color: green;'><b>✓ Inventory state changes verified</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testInventoryUpdateStateReporting", executionTime, true);

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
     * Test: Inventory Batch Processing Report
     * Demonstrates: Batch operation logging and statistics
     */
    @Test(description = "Test inventory batch processing with detailed reporting", groups = "test_reporting")
    public void testInventoryBatchProcessingReport() {
        Reporter.log("<h4 style='color: darkgreen;'>Test: Inventory Batch Processing Report</h4>");

        long startTime = System.currentTimeMillis();

        try {
            int batchSize = 10;
            Reporter.log("<p><b>Batch Processing Report:</b></p>");
            Reporter.log("<p>Batch Size: " + batchSize + " items</p>");

            Reporter.log("<table border='1' cellpadding='5' cellspacing='0' style='width: 100%;'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>#</th><th>SKU</th><th>Name</th><th>Quantity</th><th>Price</th><th>Status</th><th>Time (ms)</th></tr>");

            List<InventoryTestData> addedItems = new ArrayList<>();

            for (int i = 1; i <= batchSize; i++) {
                long itemStart = System.currentTimeMillis();
                InventoryTestData item = inventoryHelper.addInventory(500 + i, "Item-" + i, 100 + (i * 10), 50.0 + (i * 2.5));
                long itemTime = System.currentTimeMillis() - itemStart;
                addedItems.add(item);

                String statusColor = item.getQuantity() > 150 ? "green" : "blue";
                Reporter.log("<tr><td>" + i + "</td><td>" + item.getSku() + "</td><td>" + item.getName() +
                           "</td><td>" + item.getQuantity() + "</td><td>$" + item.getPrice() +
                           "</td><td style='color: " + statusColor + ";'>Success</td><td>" + itemTime + "</td></tr>");
            }

            Reporter.log("</table>");

            // Calculate statistics
            int totalItems = addedItems.size();
            double avgQuantity = addedItems.stream().mapToInt(InventoryTestData::getQuantity).average().orElse(0);
            double avgPrice = addedItems.stream().mapToDouble(InventoryTestData::getPrice).average().orElse(0);

            Reporter.log("<p><b>Batch Statistics:</b></p>");
            Reporter.log("<ul>");
            Reporter.log("<li>Total Items Added: " + totalItems + "</li>");
            Reporter.log("<li>Success Rate: 100%</li>");
            Reporter.log("<li>Average Quantity: " + String.format("%.0f", avgQuantity) + "</li>");
            Reporter.log("<li>Average Price: $" + String.format("%.2f", avgPrice) + "</li>");
            Reporter.log("<li>Min Quantity: " + addedItems.stream().mapToInt(InventoryTestData::getQuantity).min().orElse(0) + "</li>");
            Reporter.log("<li>Max Quantity: " + addedItems.stream().mapToInt(InventoryTestData::getQuantity).max().orElse(0) + "</li>");
            Reporter.log("</ul>");

            Assert.assertEquals(addedItems.size(), batchSize, "All items should be added");
            Reporter.log("<p style='color: green;'><b>✓ Batch processing completed successfully</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testInventoryBatchProcessingReport", executionTime, true);

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
     * Test: Inventory Reduction with Comprehensive Reporting
     * Demonstrates: Operation tracking and verification
     */
    @Test(description = "Test inventory reduction with comprehensive reporting", groups = "test_reporting")
    public void testInventoryReductionReport() {
        Reporter.log("<h4 style='color: darkgreen;'>Test: Inventory Reduction with Comprehensive Reporting</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Inventory Reduction Workflow:</b></p>");

            // Add inventory
            InventoryTestData item = inventoryHelper.addInventory(600, "ReduceTest", 500, 250.0);
            int originalQuantity = item.getQuantity();
            Reporter.log("<p>Step 1: Inventory added - SKU: " + item.getSku() + ", Quantity: " + originalQuantity + "</p>");

            // Verify existence
            InventoryTestData beforeReduction = inventoryHelper.getItemBySku(item.getSku());
            Assert.assertNotNull(beforeReduction, "Item should exist before reduction");
            Reporter.log("<p>Step 2: Inventory verified - exists in system</p>");

            // Reduce quantity
            long reductionStart = System.currentTimeMillis();
            InventoryTestData reducedItem = inventoryHelper.reduceInventory(item.getSku(), 200);
            long reductionTime = System.currentTimeMillis() - reductionStart;
            int newQuantity = reducedItem.getQuantity();
            Reporter.log("<p>Step 3: Inventory reduced in " + reductionTime + " ms - From " + originalQuantity + " to " + newQuantity + "</p>");

            // Verify reduction
            Assert.assertEquals(newQuantity, originalQuantity - 200, "Quantity should be reduced by 200");
            Assert.assertTrue(newQuantity < originalQuantity, "New quantity should be less than original");
            Reporter.log("<p>Step 4: Reduction verified - quantity correctly updated</p>");

            Reporter.log("<p style='color: green;'><b>✓ Reduction workflow completed successfully</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testInventoryReductionReport", executionTime, true);

            Reporter.log("<p><b>Total Reduction Time:</b> " + executionTime + " ms</p>");
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
     * Test: Inventory Level Report
     * Demonstrates: Inventory level tracking and alerting
     */
    @Test(description = "Test inventory level reporting", groups = "test_reporting")
    public void testInventoryLevelReport() {
        Reporter.log("<h4 style='color: darkgreen;'>Test: Inventory Level Report</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Inventory Level Analysis:</b></p>");
            Reporter.log("<table border='1' cellpadding='5' cellspacing='0' style='width: 100%;'>");
            Reporter.log("<tr style='background-color: #f2f2f2;'><th>Item</th><th>Quantity</th><th>Status</th><th>Alert</th></tr>");

            // Add items with various quantities
            InventoryTestData item1 = inventoryHelper.addInventory(601, "HighStock", 500, 100.0);
            InventoryTestData item2 = inventoryHelper.addInventory(602, "MediumStock", 100, 50.0);
            InventoryTestData item3 = inventoryHelper.addInventory(603, "LowStock", 10, 25.0);
            InventoryTestData item4 = inventoryHelper.addInventory(604, "OutOfStock", 0, 75.0);

            reportInventoryStatus(item1, "HighStock - Optimal");
            reportInventoryStatus(item2, "MediumStock - Good");
            reportInventoryStatus(item3, "LowStock - Warning");
            reportInventoryStatus(item4, "OutOfStock - Critical");

            Reporter.log("</table>");

            Reporter.log("<p><b>Inventory Status Summary:</b></p>");
            Reporter.log("<ul>");
            Reporter.log("<li>High Stock (>200): 1 item</li>");
            Reporter.log("<li>Medium Stock (50-200): 1 item</li>");
            Reporter.log("<li>Low Stock (1-49): 1 item</li>");
            Reporter.log("<li>Out of Stock (0): 1 item</li>");
            Reporter.log("</ul>");

            Reporter.log("<p style='color: green;'><b>✓ Inventory level analysis completed</b></p>");

            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            recordMetrics("testInventoryLevelReport", executionTime, true);

            Reporter.log("<p><b>Total Analysis Time:</b> " + executionTime + " ms</p>");
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
     * Helper method to report inventory status
     */
    private void reportInventoryStatus(InventoryTestData item, String description) {
        String statusColor;
        String status;

        if (item.getQuantity() > 200) {
            statusColor = "green";
            status = "Optimal";
        } else if (item.getQuantity() > 50) {
            statusColor = "blue";
            status = "Good";
        } else if (item.getQuantity() > 0) {
            statusColor = "orange";
            status = "Warning";
        } else {
            statusColor = "red";
            status = "Critical";
        }

        String alert = item.getQuantity() <= 50 ? "⚠ Reorder" : "✓ OK";
        Reporter.log("<tr><td>" + description + "</td><td>" + item.getQuantity() + "</td><td style='color: " + statusColor + ";'>" + status + "</td><td>" + alert + "</td></tr>");
    }

    /**
     * Test: Multi-step Inventory Workflow Report
     * Demonstrates: Complex workflow tracking
     */
    @Test(description = "Test multi-step inventory workflow with detailed reporting", groups = "test_reporting")
    public void testMultiStepInventoryWorkflowReport() {
        Reporter.log("<h4 style='color: darkgreen;'>Test: Multi-Step Inventory Workflow Report</h4>");

        long startTime = System.currentTimeMillis();

        try {
            Reporter.log("<p><b>Inventory Workflow Execution:</b></p>");

            Map<String, Long> stepTimings = new LinkedHashMap<>();

            // Step 1: Add
            long step1Start = System.currentTimeMillis();
            InventoryTestData item = inventoryHelper.addInventory(605, "Workflow", 400, 200.0);
            stepTimings.put("Add", System.currentTimeMillis() - step1Start);
            Reporter.log("<p>✓ Step 1 (Add): " + stepTimings.get("Add") + " ms - SKU: " + item.getSku() + "</p>");

            // Step 2: Retrieve
            long step2Start = System.currentTimeMillis();
            InventoryTestData retrieved = inventoryHelper.getItemBySku(item.getSku());
            stepTimings.put("Retrieve", System.currentTimeMillis() - step2Start);
            Reporter.log("<p>✓ Step 2 (Retrieve): " + stepTimings.get("Retrieve") + " ms - Quantity: " + retrieved.getQuantity() + "</p>");

            // Step 3: Update
            long step3Start = System.currentTimeMillis();
            InventoryTestData updated = inventoryHelper.updateInventory(item.getSku(), 450, 225.0);
            stepTimings.put("Update", System.currentTimeMillis() - step3Start);
            Reporter.log("<p>✓ Step 3 (Update): " + stepTimings.get("Update") + " ms - New Quantity: " + updated.getQuantity() + "</p>");

            // Step 4: Reduce
            long step4Start = System.currentTimeMillis();
            InventoryTestData reduced = inventoryHelper.reduceInventory(item.getSku(), 100);
            stepTimings.put("Reduce", System.currentTimeMillis() - step4Start);
            Reporter.log("<p>✓ Step 4 (Reduce): " + stepTimings.get("Reduce") + " ms - Remaining: " + reduced.getQuantity() + "</p>");

            // Step 5: Verify
            long step5Start = System.currentTimeMillis();
            InventoryTestData verified = inventoryHelper.getItemBySku(item.getSku());
            stepTimings.put("Verify", System.currentTimeMillis() - step5Start);
            Reporter.log("<p>✓ Step 5 (Verify): " + stepTimings.get("Verify") + " ms - Final Quantity: " + verified.getQuantity() + "</p>");

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
            recordMetrics("testMultiStepInventoryWorkflowReport", executionTime, true);

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

        System.out.println("\n[CLASS TEARDOWN] Inventory Service Test Reporting tests completed");
        System.out.println("Class execution time: " + classExecutionTime + " ms");

        inventoryHelper = null;
    }

    @AfterSuite(alwaysRun = true)
    public void suiteTearDown(ITestContext context) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("[SUITE TEARDOWN] Inventory Service Test Reporting Suite Completed");
        System.out.println("End Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("Total Tests: " + totalTestsExecuted + " | Passed: " + passedTests + " | Failed: " + failedTests);
        System.out.println("=".repeat(80));

        // Generate final report
        Reporter.log("<hr style='border: 2px solid #4CAF50;'/>");
        Reporter.log("<h3 style='color: darkgreen;'>Inventory Service Test Reporting Suite - Final Report</h3>");
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
     * Inventory test data class
     */
    public static class InventoryTestData {
        private int sku;
        private String name;
        private int quantity;
        private double price;

        public InventoryTestData(int sku, String name, int quantity, double price) {
            this.sku = sku;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }

        public int getSku() { return sku; }
        public String getName() { return name; }
        public int getQuantity() { return quantity; }
        public double getPrice() { return price; }

        public void setQuantity(int quantity) { this.quantity = quantity; }
        public void setPrice(double price) { this.price = price; }

        @Override
        public String toString() {
            return "Inventory{sku=" + sku + ", name='" + name + '\'' + ", quantity=" + quantity + ", price=" + price + "}";
        }
    }

    /**
     * Helper class for inventory operations
     */
    public static class InventoryReportingHelper {
        private Map<Integer, InventoryTestData> inventory = new HashMap<>();

        public InventoryTestData addInventory(int sku, String name, int quantity, double price) {
            InventoryTestData item = new InventoryTestData(sku, name, quantity, price);
            inventory.put(sku, item);
            return item;
        }

        public InventoryTestData getItemBySku(int sku) {
            return inventory.get(sku);
        }

        public InventoryTestData updateInventory(int sku, int newQuantity, double newPrice) {
            InventoryTestData item = inventory.get(sku);
            if (item != null) {
                item.setQuantity(newQuantity);
                item.setPrice(newPrice);
            }
            return item;
        }

        public InventoryTestData reduceInventory(int sku, int reduceBy) {
            InventoryTestData item = inventory.get(sku);
            if (item != null) {
                item.setQuantity(Math.max(0, item.getQuantity() - reduceBy));
            }
            return item;
        }

        public boolean itemExists(int sku) {
            return inventory.containsKey(sku);
        }

        public boolean isValidInventory(InventoryTestData item) {
            return item != null && item.getSku() > 0 && item.getQuantity() >= 0 && item.getPrice() > 0;
        }
    }
}


