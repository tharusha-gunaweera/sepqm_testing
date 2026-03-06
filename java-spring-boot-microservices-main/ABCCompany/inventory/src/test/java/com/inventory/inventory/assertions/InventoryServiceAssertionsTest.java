package com.inventory.inventory.assertions;

import org.testng.annotations.*;
import org.testng.Assert;
import java.util.*;

/**
 * TestNG Assertions Test Suite for Inventory Service
 *
 * This test class demonstrates comprehensive assertions including:
 * - Basic assertions (assertEquals, assertNotNull, assertTrue, assertFalse)
 * - Collection assertions
 * - Boundary value assertions
 * - Range assertions
 * - Status assertions
 * - Comparison assertions
 */
public class InventoryServiceAssertionsTest {

    private InventoryTestHelper inventoryHelper;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        System.out.println("\n[CLASS SETUP] Initializing Inventory Service Assertions tests...");
        inventoryHelper = new InventoryTestHelper();
        System.out.println("[CLASS SETUP] Inventory test helper initialized");
    }

    /**
     * Test: Basic Assert Equals for Item Creation
     * Demonstrates: assertEquals assertion
     */
    @Test(description = "Test inventory item creation with assertEquals", groups = "assertions")
    public void testItemCreationAssertEquals() {
        System.out.println("\n[TEST] Executing: testItemCreationAssertEquals");

        InventoryTestData item = inventoryHelper.createItem(201, 50, 100);

        // Assert equals
        Assert.assertEquals(item.getItemId(), 201, "Item ID should be 201");
        Assert.assertEquals(item.getProductId(), 50, "Product ID should be 50");
        Assert.assertEquals(item.getQuantity(), 100, "Quantity should be 100");

        System.out.println("[TEST] Item created with correct values verified");
    }

    /**
     * Test: Assert Not Null for Item Retrieval
     * Demonstrates: assertNotNull assertion
     */
    @Test(description = "Test item retrieval is not null", groups = "assertions")
    public void testItemRetrievalAssertNotNull() {
        System.out.println("\n[TEST] Executing: testItemRetrievalAssertNotNull");

        InventoryTestData createdItem = inventoryHelper.createItem(202, 51, 150);
        InventoryTestData retrievedItem = inventoryHelper.getItemById(createdItem.getId());

        // Assert not null
        Assert.assertNotNull(retrievedItem, "Retrieved item should not be null");
        Assert.assertNotNull(retrievedItem.getItemId(), "Item ID should not be null");
        Assert.assertNotNull(retrievedItem.getProductId(), "Product ID should not be null");

        System.out.println("[TEST] Item retrieval verified - object not null");
    }

    /**
     * Test: Assert True for Inventory Validation
     * Demonstrates: assertTrue assertion
     */
    @Test(description = "Test inventory validation with assertTrue", groups = "assertions")
    public void testInventoryValidationAssertTrue() {
        System.out.println("\n[TEST] Executing: testInventoryValidationAssertTrue");

        InventoryTestData item = inventoryHelper.createItem(203, 52, 200);

        // Assert true
        Assert.assertTrue(item.getItemId() > 0, "Item ID should be positive");
        Assert.assertTrue(item.getProductId() > 0, "Product ID should be positive");
        Assert.assertTrue(item.getQuantity() > 0, "Quantity should be positive");
        Assert.assertTrue(inventoryHelper.isValidInventory(item), "Item should be valid");

        System.out.println("[TEST] Inventory validation verified - conditions true");
    }

    /**
     * Test: Assert False for Invalid Inventory
     * Demonstrates: assertFalse assertion
     */
    @Test(description = "Test invalid inventory with assertFalse", groups = "assertions")
    public void testInvalidInventoryAssertFalse() {
        System.out.println("\n[TEST] Executing: testInvalidInventoryAssertFalse");

        InventoryTestData item = inventoryHelper.createItem(204, 53, 0);

        // Assert false
        Assert.assertFalse(item.getQuantity() < 0, "Quantity should not be negative");
        Assert.assertTrue(inventoryHelper.isOutOfStock(item), "Item with 0 quantity is out of stock");
        Assert.assertTrue(inventoryHelper.isLowStock(item, 10), "Item with 0 quantity is below low stock threshold");

        System.out.println("[TEST] Invalid inventory verified - conditions false");
    }

    /**
     * Test: Quantity Range Assertions
     * Demonstrates: range and boundary assertions
     */
    @Test(description = "Test inventory quantity range assertions", groups = "assertions")
    public void testQuantityRangeAssertions() {
        System.out.println("\n[TEST] Executing: testQuantityRangeAssertions");

        InventoryTestData item = inventoryHelper.createItem(205, 54, 500);

        // Range assertions
        Assert.assertTrue(item.getQuantity() >= 0, "Quantity should be >= 0");
        Assert.assertTrue(item.getQuantity() <= 1000, "Quantity should be <= 1000");
        Assert.assertTrue(item.getQuantity() > 100, "Quantity should be > 100");
        Assert.assertTrue(item.getQuantity() < 1000, "Quantity should be < 1000");
        Assert.assertEquals(item.getQuantity(), 500, "Quantity should equal 500");

        System.out.println("[TEST] Quantity range assertions verified");
    }

    /**
     * Test: Stock Level Assertions
     * Demonstrates: boolean and threshold assertions
     */
    @Test(description = "Test stock level assertions", groups = "assertions")
    public void testStockLevelAssertions() {
        System.out.println("\n[TEST] Executing: testStockLevelAssertions");

        InventoryTestData highStock = inventoryHelper.createItem(206, 55, 800);
        InventoryTestData lowStock = inventoryHelper.createItem(207, 56, 20);
        InventoryTestData outOfStock = inventoryHelper.createItem(208, 57, 0);

        // High stock assertions
        Assert.assertTrue(inventoryHelper.isInStock(highStock), "High stock item should be in stock");
        Assert.assertFalse(inventoryHelper.isOutOfStock(highStock), "High stock item should not be out of stock");
        Assert.assertFalse(inventoryHelper.isLowStock(highStock, 100), "High stock should not be low");

        // Low stock assertions
        Assert.assertTrue(inventoryHelper.isInStock(lowStock), "Low stock item should be in stock");
        Assert.assertTrue(inventoryHelper.isLowStock(lowStock, 100), "Low stock should be flagged as low");
        Assert.assertFalse(inventoryHelper.isOutOfStock(lowStock), "Low stock item should not be out of stock");

        // Out of stock assertions
        Assert.assertFalse(inventoryHelper.isInStock(outOfStock), "Out of stock item should not be in stock");
        Assert.assertTrue(inventoryHelper.isOutOfStock(outOfStock), "Out of stock item should be out of stock");
        Assert.assertTrue(inventoryHelper.isLowStock(outOfStock, 50), "Out of stock should be flagged as low");

        System.out.println("[TEST] Stock level assertions verified");
    }

    /**
     * Test: Inventory Collection Assertions
     * Demonstrates: collection assertions
     */
    @Test(description = "Test inventory collection assertions", groups = "assertions")
    public void testInventoryCollectionAssertions() {
        System.out.println("\n[TEST] Executing: testInventoryCollectionAssertions");

        // Create multiple items
        List<InventoryTestData> items = new ArrayList<>();
        items.add(inventoryHelper.createItem(209, 58, 100));
        items.add(inventoryHelper.createItem(210, 59, 200));
        items.add(inventoryHelper.createItem(211, 60, 300));

        // Collection assertions
        Assert.assertNotNull(items, "Items list should not be null");
        Assert.assertEquals(items.size(), 3, "Items list should contain 3 items");
        Assert.assertTrue(items.size() > 0, "Items list should not be empty");

        // Individual item assertions
        for (InventoryTestData item : items) {
            Assert.assertNotNull(item, "Item should not be null");
            Assert.assertTrue(item.getQuantity() > 0, "Item quantity should be positive");
        }

        System.out.println("[TEST] Inventory collection assertions verified");
    }

    /**
     * Test: Item ID Assertions
     * Demonstrates: ID verification assertions
     */
    @Test(description = "Test inventory item ID assertions", groups = "assertions")
    public void testItemIdAssertions() {
        System.out.println("\n[TEST] Executing: testItemIdAssertions");

        InventoryTestData item = inventoryHelper.createItem(212, 61, 400);

        // ID assertions
        Assert.assertNotNull(item.getId(), "Item ID should not be null");
        Assert.assertTrue(item.getItemId() == 212, "Item ID should be 212");
        Assert.assertNotEquals(item.getId(), 0, "Item ID should not be 0");
        Assert.assertTrue(item.getId() > 0, "Item ID should be positive");

        System.out.println("[TEST] Item ID assertions verified");
    }

    /**
     * Test: Product ID Assertions
     * Demonstrates: relationship assertions
     */
    @Test(description = "Test inventory product ID assertions", groups = "assertions")
    public void testProductIdAssertions() {
        System.out.println("\n[TEST] Executing: testProductIdAssertions");

        InventoryTestData item = inventoryHelper.createItem(213, 62, 500);

        // Product ID assertions
        Assert.assertEquals(item.getProductId(), 62, "Product ID should be 62");
        Assert.assertTrue(item.getProductId() > 0, "Product ID should be positive");
        Assert.assertNotEquals(item.getProductId(), 0, "Product ID should not be 0");
        Assert.assertNotNull(item.getProductId(), "Product ID should not be null");

        System.out.println("[TEST] Product ID assertions verified");
    }

    /**
     * Test: Item Update Assertions
     * Demonstrates: multiple assertions on update
     */
    @Test(description = "Test inventory item update assertions", groups = "assertions")
    public void testItemUpdateAssertions() {
        System.out.println("\n[TEST] Executing: testItemUpdateAssertions");

        // Create item
        InventoryTestData originalItem = inventoryHelper.createItem(214, 63, 300);
        int originalQuantity = originalItem.getQuantity();

        // Update item
        InventoryTestData updatedItem = inventoryHelper.updateItem(originalItem.getId(), 100);

        // Update assertions
        Assert.assertNotNull(updatedItem, "Updated item should not be null");
        Assert.assertEquals(updatedItem.getId(), originalItem.getId(), "Item ID should remain same");
        Assert.assertEquals(updatedItem.getQuantity(), 100, "Quantity should be updated to 100");
        Assert.assertNotEquals(updatedItem.getQuantity(), originalQuantity, "Quantity should be different");
        Assert.assertTrue(updatedItem.getQuantity() < originalQuantity, "New quantity should be less");

        System.out.println("[TEST] Item update assertions verified");
    }

    /**
     * Test: Inventory Deletion Assertions
     * Demonstrates: post-deletion assertions
     */
    @Test(description = "Test inventory item deletion assertions", groups = "assertions")
    public void testItemDeletionAssertions() {
        System.out.println("\n[TEST] Executing: testItemDeletionAssertions");

        // Create item
        InventoryTestData item = inventoryHelper.createItem(215, 64, 600);
        int itemId = item.getId();

        // Verify exists
        Assert.assertNotNull(inventoryHelper.getItemById(itemId), "Item should exist before deletion");

        // Delete item
        inventoryHelper.deleteItem(itemId);

        // Verify deleted
        Assert.assertNull(inventoryHelper.getItemById(itemId), "Item should be null after deletion");
        Assert.assertFalse(inventoryHelper.itemExists(itemId), "Item should not exist after deletion");

        System.out.println("[TEST] Item deletion assertions verified");
    }

    /**
     * Test: Inventory Comparison Assertions
     * Demonstrates: comparison operators
     */
    @Test(description = "Test inventory comparison assertions", groups = "assertions")
    public void testInventoryComparisonAssertions() {
        System.out.println("\n[TEST] Executing: testInventoryComparisonAssertions");

        InventoryTestData item1 = inventoryHelper.createItem(216, 65, 700);
        InventoryTestData item2 = inventoryHelper.createItem(217, 66, 800);

        // Comparison assertions
        Assert.assertNotEquals(item1.getItemId(), item2.getItemId(), "Item IDs should be different");
        Assert.assertNotEquals(item1.getQuantity(), item2.getQuantity(), "Quantities should be different");
        Assert.assertTrue(item2.getQuantity() > item1.getQuantity(), "Item 2 quantity should be greater");
        Assert.assertNotEquals(item1.getProductId(), item2.getProductId(), "Product IDs should be different");

        System.out.println("[TEST] Inventory comparison assertions verified");
    }

    /**
     * Test: Stock Status Assertions
     * Demonstrates: status string assertions
     */
    @Test(description = "Test inventory stock status assertions", groups = "assertions")
    public void testStockStatusAssertions() {
        System.out.println("\n[TEST] Executing: testStockStatusAssertions");

        InventoryTestData item = inventoryHelper.createItem(218, 67, 250);

        // Status assertions
        String status = inventoryHelper.getStockStatus(item.getId());
        Assert.assertNotNull(status, "Stock status should not be null");
        Assert.assertEquals(status, "IN_STOCK", "Status should be IN_STOCK");
        Assert.assertNotEquals(status, "OUT_OF_STOCK", "Status should not be OUT_OF_STOCK");
        Assert.assertTrue(status.equals("IN_STOCK"), "Status should match exactly");

        System.out.println("[TEST] Stock status assertions verified");
    }

    /**
     * Test: Comprehensive Inventory Data Assertions
     * Demonstrates: multiple data point assertions
     */
    @Test(description = "Test comprehensive inventory data assertions", groups = "assertions")
    public void testComprehensiveInventoryDataAssertions() {
        System.out.println("\n[TEST] Executing: testComprehensiveInventoryDataAssertions");

        InventoryTestData item = inventoryHelper.createItem(219, 68, 350);

        // Comprehensive data verification
        Assert.assertNotNull(item, "Item object should not be null");
        Assert.assertTrue(item.getId() > 0, "Item ID should be positive");
        Assert.assertEquals(item.getItemId(), 219, "Item ID should be 219");
        Assert.assertEquals(item.getProductId(), 68, "Product ID should be 68");
        Assert.assertEquals(item.getQuantity(), 350, "Quantity should be 350");
        Assert.assertTrue(item.getQuantity() > 0, "Quantity should be positive");
        Assert.assertTrue(item.getProductId() > 0, "Product ID should be positive");
        Assert.assertTrue(inventoryHelper.isValidInventory(item), "Item should be valid");

        System.out.println("[TEST] Comprehensive inventory data assertions verified");
    }

    /**
     * Test: Bulk Quantity Adjustment Assertions
     * Demonstrates: bulk operation assertions
     */
    @Test(description = "Test bulk quantity adjustment assertions", groups = "assertions")
    public void testBulkQuantityAdjustmentAssertions() {
        System.out.println("\n[TEST] Executing: testBulkQuantityAdjustmentAssertions");

        // Create items
        InventoryTestData item1 = inventoryHelper.createItem(220, 69, 100);
        InventoryTestData item2 = inventoryHelper.createItem(221, 70, 100);
        InventoryTestData item3 = inventoryHelper.createItem(222, 71, 100);

        // Adjust quantities
        inventoryHelper.updateItem(item1.getId(), 80);
        inventoryHelper.updateItem(item2.getId(), 90);
        inventoryHelper.updateItem(item3.getId(), 70);

        // Verify adjustments
        Assert.assertEquals(inventoryHelper.getItemById(item1.getId()).getQuantity(), 80);
        Assert.assertEquals(inventoryHelper.getItemById(item2.getId()).getQuantity(), 90);
        Assert.assertEquals(inventoryHelper.getItemById(item3.getId()).getQuantity(), 70);

        System.out.println("[TEST] Bulk quantity adjustment assertions verified");
    }

    /**
     * Test: Edge Case Assertions
     * Demonstrates: boundary value testing
     */
    @Test(description = "Test edge case assertions", groups = "assertions")
    public void testEdgeCaseAssertions() {
        System.out.println("\n[TEST] Executing: testEdgeCaseAssertions");

        // Minimum quantity
        InventoryTestData minItem = inventoryHelper.createItem(223, 72, 1);
        Assert.assertTrue(minItem.getQuantity() > 0, "Minimum quantity should be positive");
        Assert.assertEquals(minItem.getQuantity(), 1, "Minimum quantity should be 1");

        // Maximum quantity
        InventoryTestData maxItem = inventoryHelper.createItem(224, 73, 10000);
        Assert.assertTrue(maxItem.getQuantity() <= 10000, "Maximum quantity should not exceed limit");
        Assert.assertEquals(maxItem.getQuantity(), 10000, "Maximum quantity should be 10000");

        // Zero quantity
        InventoryTestData zeroItem = inventoryHelper.createItem(225, 74, 0);
        Assert.assertEquals(zeroItem.getQuantity(), 0, "Zero quantity should be 0");
        Assert.assertTrue(inventoryHelper.isOutOfStock(zeroItem), "Zero quantity should be out of stock");

        System.out.println("[TEST] Edge case assertions verified");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        System.out.println("\n[CLASS TEARDOWN] Cleaning up Inventory Service Assertions tests...");
        inventoryHelper = null;
        System.out.println("[CLASS TEARDOWN] Inventory Service Assertions tests completed");
    }

    /**
     * Helper class for Inventory testing
     */
    public static class InventoryTestData {
        private int id;
        private int itemId;
        private int productId;
        private int quantity;

        public InventoryTestData(int id, int itemId, int productId, int quantity) {
            this.id = id;
            this.itemId = itemId;
            this.productId = productId;
            this.quantity = quantity;
        }

        public int getId() { return id; }
        public int getItemId() { return itemId; }
        public int getProductId() { return productId; }
        public int getQuantity() { return quantity; }

        public void setQuantity(int quantity) { this.quantity = quantity; }

        @Override
        public String toString() {
            return "Inventory{id=" + id + ", itemId=" + itemId + ", productId=" + productId + ", qty=" + quantity + "}";
        }
    }

    /**
     * Helper class for Inventory operations
     */
    public static class InventoryTestHelper {
        private Map<Integer, InventoryTestData> inventory = new HashMap<>();
        private int itemCounter = 2000;

        public InventoryTestData createItem(int itemId, int productId, int quantity) {
            int id = ++itemCounter;
            InventoryTestData item = new InventoryTestData(id, itemId, productId, quantity);
            inventory.put(id, item);
            System.out.println("  → Item created: " + itemId + " (Product: " + productId + ", Qty: " + quantity + ")");
            return item;
        }

        public InventoryTestData getItemById(int id) {
            return inventory.get(id);
        }

        public InventoryTestData updateItem(int id, int newQuantity) {
            InventoryTestData item = inventory.get(id);
            if (item != null) {
                item.setQuantity(newQuantity);
                System.out.println("  → Item updated: " + id + " (New Qty: " + newQuantity + ")");
            }
            return item;
        }

        public void deleteItem(int id) {
            inventory.remove(id);
            System.out.println("  → Item deleted: " + id);
        }

        public boolean itemExists(int id) {
            return inventory.containsKey(id);
        }

        public boolean isValidInventory(InventoryTestData item) {
            return item != null && item.getItemId() > 0 && item.getProductId() > 0;
        }

        public boolean isInStock(InventoryTestData item) {
            return item != null && item.getQuantity() > 0;
        }

        public boolean isOutOfStock(InventoryTestData item) {
            return item != null && item.getQuantity() == 0;
        }

        public boolean isLowStock(InventoryTestData item, int threshold) {
            return item != null && item.getQuantity() < threshold;
        }

        public String getStockStatus(int id) {
            InventoryTestData item = inventory.get(id);
            if (item == null) return "NOT_FOUND";
            if (item.getQuantity() == 0) return "OUT_OF_STOCK";
            if (item.getQuantity() < 50) return "LOW_STOCK";
            return "IN_STOCK";
        }
    }
}

