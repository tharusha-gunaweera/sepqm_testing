package com.product.product.fixtures;

import java.util.*;

/**
 * Product Test Data Manager
 *
 * Manages test data lifecycle and cleanup
 * Provides utilities for test data initialization and management
 */
public class ProductTestDataManager {

    private List<ProductServiceFixture.ProductTestData> testProducts;
    private Map<String, List<?>> testCollections;
    private long initializationTime;

    public ProductTestDataManager() {
        this.testProducts = new ArrayList<>();
        this.testCollections = new HashMap<>();
    }

    /**
     * Initialize test data for test suite
     */
    public void initializeTestData() {
        System.out.println("  → Initializing test data...");
        this.initializationTime = System.currentTimeMillis();

        // Add predefined test products
        testProducts.add(new ProductServiceFixture.ProductTestData(1, "Laptop", "Gaming Laptop", 1299.99, 50));
        testProducts.add(new ProductServiceFixture.ProductTestData(2, "Mouse", "Wireless Mouse", 29.99, 200));
        testProducts.add(new ProductServiceFixture.ProductTestData(3, "Keyboard", "Mechanical Keyboard", 149.99, 75));
        testProducts.add(new ProductServiceFixture.ProductTestData(4, "Monitor", "4K Monitor", 399.99, 30));
        testProducts.add(new ProductServiceFixture.ProductTestData(5, "Headphones", "Noise-Canceling Headphones", 199.99, 60));

        System.out.println("  → Test data initialized with " + testProducts.size() + " products");
    }

    /**
     * Add test product
     */
    public void addTestProduct(ProductServiceFixture.ProductTestData product) {
        testProducts.add(product);
    }

    /**
     * Get all test products
     */
    public List<ProductServiceFixture.ProductTestData> getTestProducts() {
        return new ArrayList<>(testProducts);
    }

    /**
     * Get test product by index
     */
    public ProductServiceFixture.ProductTestData getTestProduct(int index) {
        if (index >= 0 && index < testProducts.size()) {
            return testProducts.get(index);
        }
        return null;
    }

    /**
     * Store test collection
     */
    public void storeTestCollection(String name, List<?> collection) {
        testCollections.put(name, new ArrayList<>(collection));
    }

    /**
     * Get test collection
     */
    public List<?> getTestCollection(String name) {
        return testCollections.get(name);
    }

    /**
     * Get test data count
     */
    public int getTestDataCount() {
        return testProducts.size();
    }

    /**
     * Get total inventory value from test data
     */
    public double getTotalTestInventoryValue() {
        double total = 0.0;
        for (ProductServiceFixture.ProductTestData product : testProducts) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    /**
     * Clear test data
     */
    public void cleanupTestData() {
        System.out.println("  → Cleaning up test data...");
        testProducts.clear();
        testCollections.clear();
        System.out.println("  → Test data cleaned up");
    }

    /**
     * Get initialization time
     */
    public long getInitializationTime() {
        return System.currentTimeMillis() - initializationTime;
    }

    /**
     * Reset to initial state
     */
    public void reset() {
        System.out.println("  → Resetting test data manager...");
        testProducts.clear();
        testCollections.clear();
        initializeTestData();
    }
}

