package com.product.product.fixtures;

import org.testng.annotations.*;
import org.testng.Assert;
import java.util.*;

/**
 * TestNG Fixtures and Setup/Teardown Mechanisms Test Suite for Product Service
 *
 * This test class demonstrates comprehensive fixture management including:
 * - Suite-level setup/teardown
 * - Class-level setup/teardown
 * - Method-level setup/teardown
 * - Product lifecycle management
 * - Inventory management and state tracking
 * - Resource initialization and cleanup
 */
public class ProductServiceFixturesTest {

    private ProductServiceFixture productFixture;
    private ProductTestContext testContext;
    private ProductTestResponse testResponse;
    private ProductTestDataManager testDataManager;

    @BeforeSuite(alwaysRun = true)
    public void initializeSuite() {
        System.out.println("\n========== [SUITE SETUP] Initializing Product Service test suite ==========");
        productFixture = new ProductServiceFixture();
        testDataManager = new ProductTestDataManager();

        // Initialize product service infrastructure
        productFixture.startProductServiceInfrastructure();
        testDataManager.initializeTestData();

        System.out.println("[SUITE SETUP] Product service infrastructure started");
        System.out.println("[SUITE SETUP] Test data initialized");
    }

    @BeforeClass(alwaysRun = true)
    public void initializeClass() {
        System.out.println("\n[CLASS SETUP] Setting up product service fixtures...");

        // Setup class-level resources
        productFixture.setupTestDatabase();
        productFixture.initializeProductCatalog();
        productFixture.setupInventoryManagement();

        System.out.println("[CLASS SETUP] Test database ready");
        System.out.println("[CLASS SETUP] Product catalog initialized");
        System.out.println("[CLASS SETUP] Inventory management setup");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupTestMethod() {
        System.out.println("\n[METHOD SETUP] Preparing test context...");

        // Create fresh context for each test
        testContext = new ProductTestContext();
        testResponse = new ProductTestResponse();

        // Reset fixture state
        productFixture.clearAllData();

        System.out.println("[METHOD SETUP] Test context created");
        System.out.println("[METHOD SETUP] Test response initialized");
        System.out.println("[METHOD SETUP] Fixture state reset");
    }

    /**
     * Test: Create Single Product
     * Demonstrates fixture usage for product creation
     */
    @Test(description = "Test creating a single product", groups = "fixtures")
    public void testCreateSingleProduct() {
        System.out.println("\n[TEST] Executing: testCreateSingleProduct");

        testContext.setOperation("CREATE");
        String productName = "Wireless Keyboard";
        String description = "High-quality wireless keyboard";
        double price = 79.99;
        int quantity = 50;

        ProductServiceFixture.ProductTestData product = productFixture.createProduct(
            productName, description, price, quantity
        );

        Assert.assertNotNull(product, "Product should be created");
        Assert.assertEquals(product.getName(), productName, "Product name should match");
        Assert.assertEquals(product.getPrice(), price, "Product price should match");
        Assert.assertEquals(product.getQuantity(), quantity, "Product quantity should match");
        Assert.assertEquals(productFixture.getProductCount(), 1, "Product count should be 1");

        System.out.println("[TEST] Single product created successfully: " + product.getProductId());
    }

    /**
     * Test: Create Multiple Products
     * Tests fixture management with multiple product creations
     */
    @Test(description = "Test creating multiple products", groups = "fixtures")
    public void testCreateMultipleProducts() {
        System.out.println("\n[TEST] Executing: testCreateMultipleProducts");

        testContext.setOperation("CREATE_BULK");
        String[] productNames = {"Laptop", "Monitor", "Mouse"};
        double[] prices = {1299.99, 399.99, 29.99};
        int[] quantities = {20, 15, 100};

        for (int i = 0; i < productNames.length; i++) {
            productFixture.createProduct(
                productNames[i],
                "Product description",
                prices[i],
                quantities[i]
            );
        }

        Assert.assertEquals(productFixture.getProductCount(), 3, "All products should be created");

        for (String name : productNames) {
            List<ProductServiceFixture.ProductTestData> results = productFixture.searchProductsByName(name);
            Assert.assertTrue(results.size() > 0, "Product " + name + " should exist");
        }

        System.out.println("[TEST] All products created successfully");
    }

    /**
     * Test: Get Product by ID
     * Demonstrates context fixture management
     */
    @Test(description = "Test retrieving product by ID", groups = "fixtures")
    public void testGetProductById() {
        System.out.println("\n[TEST] Executing: testGetProductById");

        testContext.setOperation("READ");
        ProductServiceFixture.ProductTestData createdProduct = productFixture.createProduct(
            "Gaming Laptop", "High-performance laptop", 1899.99, 10
        );

        int productId = createdProduct.getProductId();
        testContext.setProductId(productId);

        ProductServiceFixture.ProductTestData retrievedProduct = productFixture.getProductById(productId);

        Assert.assertNotNull(retrievedProduct, "Product should be retrieved");
        Assert.assertEquals(retrievedProduct.getProductId(), productId, "Product ID should match");
        Assert.assertEquals(retrievedProduct.getName(), "Gaming Laptop", "Product name should match");
        Assert.assertTrue(productFixture.getProductAccessCount(productId) > 0, "Access count should be incremented");

        System.out.println("[TEST] Product retrieved successfully by ID: " + productId);
    }

    /**
     * Test: Update Product Information
     * Tests product update fixture
     */
    @Test(description = "Test updating product information", groups = "fixtures")
    public void testUpdateProductInformation() {
        System.out.println("\n[TEST] Executing: testUpdateProductInformation");

        testContext.setOperation("UPDATE");
        ProductServiceFixture.ProductTestData product = productFixture.createProduct(
            "Original Name", "Original Description", 100.00, 50
        );

        int productId = product.getProductId();
        testContext.setProductId(productId);

        productFixture.updateProduct(productId, "Updated Name", "Updated Description", 150.00);

        ProductServiceFixture.ProductTestData updatedProduct = productFixture.getProductById(productId);

        Assert.assertEquals(updatedProduct.getName(), "Updated Name", "Product name should be updated");
        Assert.assertEquals(updatedProduct.getPrice(), 150.00, "Product price should be updated");

        System.out.println("[TEST] Product information updated successfully");
    }

    /**
     * Test: Delete Product
     * Tests product deletion fixture
     */
    @Test(description = "Test deleting a product", groups = "fixtures")
    public void testDeleteProduct() {
        System.out.println("\n[TEST] Executing: testDeleteProduct");

        testContext.setOperation("DELETE");
        ProductServiceFixture.ProductTestData product = productFixture.createProduct(
            "To Be Deleted", "Temporary product", 50.00, 10
        );

        int productId = product.getProductId();
        testContext.setProductId(productId);

        Assert.assertTrue(productFixture.productExists(productId), "Product should exist before deletion");

        productFixture.deleteProduct(productId);

        Assert.assertFalse(productFixture.productExists(productId), "Product should not exist after deletion");
        Assert.assertEquals(productFixture.getDeletedProductsCount(), 1, "Deleted products count should be 1");

        System.out.println("[TEST] Product deleted successfully");
    }

    /**
     * Test: Update Product Stock
     * Tests inventory management fixture
     */
    @Test(description = "Test updating product stock", groups = "fixtures")
    public void testUpdateProductStock() {
        System.out.println("\n[TEST] Executing: testUpdateProductStock");

        testContext.setOperation("UPDATE_STOCK");
        ProductServiceFixture.ProductTestData product = productFixture.createProduct(
            "Stock Test Product", "Product for stock testing", 99.99, 100
        );

        int productId = product.getProductId();
        testContext.addParameter("quantity", 50);

        productFixture.updateProductStock(productId, 50);

        ProductServiceFixture.ProductTestData updatedProduct = productFixture.getProductById(productId);

        Assert.assertEquals(updatedProduct.getQuantity(), 50, "Product quantity should be updated");

        System.out.println("[TEST] Product stock updated successfully: 100 -> 50");
    }

    /**
     * Test: Search Products by Name
     * Tests product search fixture
     */
    @Test(description = "Test searching products by name", groups = "fixtures")
    public void testSearchProductsByName() {
        System.out.println("\n[TEST] Executing: testSearchProductsByName");

        testContext.setOperation("SEARCH");
        testContext.addParameter("searchTerm", "Pro");

        productFixture.createProduct("Professional Camera", "High-end camera", 1299.99, 5);
        productFixture.createProduct("Professional Monitor", "4K professional monitor", 799.99, 10);
        productFixture.createProduct("Consumer Headphones", "Regular headphones", 79.99, 50);

        List<ProductServiceFixture.ProductTestData> results = productFixture.searchProductsByName("Pro");

        Assert.assertEquals(results.size(), 2, "Should find 2 products with 'Pro' in name");

        for (ProductServiceFixture.ProductTestData product : results) {
            Assert.assertTrue(product.getName().contains("Pro"), "Product name should contain search term");
        }

        System.out.println("[TEST] Product search completed: found " + results.size() + " products");
    }

    /**
     * Test: Get Products by Price Range
     * Tests price filtering fixture
     */
    @Test(description = "Test filtering products by price range", groups = "fixtures")
    public void testGetProductsByPriceRange() {
        System.out.println("\n[TEST] Executing: testGetProductsByPriceRange");

        testContext.setOperation("FILTER_PRICE");
        testContext.addParameter("minPrice", 100.0);
        testContext.addParameter("maxPrice", 500.0);

        productFixture.createProduct("Budget Keyboard", "Budget keyboard", 29.99, 100);
        productFixture.createProduct("Premium Keyboard", "Premium keyboard", 199.99, 50);
        productFixture.createProduct("Luxury Monitor", "Luxury monitor", 1999.99, 5);

        List<ProductServiceFixture.ProductTestData> results =
            productFixture.getProductsByPriceRange(100.0, 500.0);

        Assert.assertEquals(results.size(), 1, "Should find 1 product in price range");
        Assert.assertEquals(results.get(0).getName(), "Premium Keyboard", "Should find the correct product");

        System.out.println("[TEST] Price range filtering completed");
    }

    /**
     * Test: Get Low Stock Products
     * Tests inventory threshold fixture
     */
    @Test(description = "Test finding low stock products", groups = "fixtures")
    public void testGetLowStockProducts() {
        System.out.println("\n[TEST] Executing: testGetLowStockProducts");

        testContext.setOperation("LOW_STOCK_CHECK");
        testContext.addParameter("threshold", 20);

        productFixture.createProduct("High Stock Item", "Plenty in stock", 50.00, 500);
        productFixture.createProduct("Medium Stock Item", "Medium stock", 75.00, 50);
        productFixture.createProduct("Low Stock Item 1", "Low stock", 100.00, 5);
        productFixture.createProduct("Low Stock Item 2", "Low stock", 120.00, 10);

        List<ProductServiceFixture.ProductTestData> lowStockProducts =
            productFixture.getLowStockProducts(20);

        Assert.assertEquals(lowStockProducts.size(), 2, "Should find 2 low stock products");

        for (ProductServiceFixture.ProductTestData product : lowStockProducts) {
            Assert.assertTrue(product.getQuantity() < 20, "Product should have quantity < 20");
        }

        System.out.println("[TEST] Low stock check completed: found " + lowStockProducts.size() + " products");
    }

    /**
     * Test: Calculate Total Inventory Value
     * Tests inventory valuation fixture
     */
    @Test(description = "Test calculating total inventory value", groups = "fixtures")
    public void testCalculateTotalInventoryValue() {
        System.out.println("\n[TEST] Executing: testCalculateTotalInventoryValue");

        testContext.setOperation("INVENTORY_VALUE");

        productFixture.createProduct("Product 1", "Desc 1", 100.00, 10);  // 1000
        productFixture.createProduct("Product 2", "Desc 2", 50.00, 20);   // 1000
        productFixture.createProduct("Product 3", "Desc 3", 25.00, 40);   // 1000

        double totalValue = productFixture.calculateTotalInventoryValue();

        Assert.assertEquals(totalValue, 3000.00, 0.01, "Total inventory value should be 3000");

        testResponse.setData(totalValue);
        testResponse.setMessage("Inventory value calculated");

        System.out.println("[TEST] Total inventory value: $" + totalValue);
    }

    /**
     * Test: Bulk Product Operations
     * Tests bulk operation fixtures
     */
    @Test(description = "Test bulk product creation", groups = "fixtures")
    public void testBulkProductOperations() {
        System.out.println("\n[TEST] Executing: testBulkProductOperations");

        testContext.setOperation("BULK_CREATE");
        testContext.addParameter("count", 10);

        List<ProductServiceFixture.ProductTestData> createdProducts = productFixture.createBulkProducts(10);

        Assert.assertEquals(createdProducts.size(), 10, "Should create 10 products");
        Assert.assertEquals(productFixture.getProductCount(), 10, "Product count should be 10");

        testResponse.setData(createdProducts);
        testResponse.setMessage("Bulk products created");

        System.out.println("[TEST] Bulk product operation completed: " + createdProducts.size() + " products");
    }

    /**
     * Test: Get All Products
     * Tests complete inventory fixture
     */
    @Test(description = "Test retrieving all products", groups = "fixtures")
    public void testGetAllProducts() {
        System.out.println("\n[TEST] Executing: testGetAllProducts");

        testContext.setOperation("GET_ALL");

        productFixture.createProduct("Product A", "Description A", 100.00, 50);
        productFixture.createProduct("Product B", "Description B", 200.00, 30);
        productFixture.createProduct("Product C", "Description C", 150.00, 40);

        Collection<ProductServiceFixture.ProductTestData> allProducts = productFixture.getAllProducts();

        Assert.assertEquals(allProducts.size(), 3, "Should retrieve all 3 products");
        Assert.assertNotNull(allProducts, "Product collection should not be null");

        testResponse.setData(new ArrayList<>(allProducts));

        System.out.println("[TEST] Retrieved all products: " + allProducts.size() + " products");
    }

    @AfterMethod(alwaysRun = true)
    public void cleanupTestMethod() {
        System.out.println("\n[METHOD TEARDOWN] Cleaning up test context...");

        // Clean up test contexts
        if (testContext != null) {
            testContext.clear();
            testContext = null;
        }

        if (testResponse != null) {
            testResponse.clear();
            testResponse = null;
        }

        // Reset fixture state
        productFixture.clearAllData();

        System.out.println("[METHOD TEARDOWN] Test context cleared");
        System.out.println("[METHOD TEARDOWN] Test response cleared");
        System.out.println("[METHOD TEARDOWN] Fixture state cleaned");
    }

    @AfterClass(alwaysRun = true)
    public void cleanupClass() {
        System.out.println("\n[CLASS TEARDOWN] Tearing down product service fixtures...");

        productFixture.shutdownDatabase();
        productFixture.resetProductCatalog();
        productFixture.closeInventoryManagement();

        System.out.println("[CLASS TEARDOWN] Database shutdown");
        System.out.println("[CLASS TEARDOWN] Product catalog reset");
        System.out.println("[CLASS TEARDOWN] Inventory management closed");
    }

    @AfterSuite(alwaysRun = true)
    public void teardownSuite() {
        System.out.println("\n========== [SUITE TEARDOWN] Shutting down Product Service test suite ==========");

        productFixture.stopProductServiceInfrastructure();
        testDataManager.cleanupTestData();

        productFixture = null;
        testDataManager = null;

        System.out.println("[SUITE TEARDOWN] Product service infrastructure stopped");
        System.out.println("[SUITE TEARDOWN] Test data cleaned up");
        System.out.println("[SUITE TEARDOWN] Test suite completed\n");
    }
}

