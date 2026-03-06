package com.apigateway.apigateway.fixtures;

import java.util.*;

/**
 * Test Data Manager Fixture
 *
 * Manages test data lifecycle - initialization and cleanup
 * Provides predefined test datasets for various scenarios
 */
public class TestDataManager {

    private List<String> initializedResources;
    private Map<String, Object> testDataStore;

    /**
     * Constructor - Initialize test data manager
     */
    public TestDataManager() {
        this.initializedResources = new ArrayList<>();
        this.testDataStore = new HashMap<>();
    }

    /**
     * [SUITE SETUP] Initialize test data
     */
    public void initializeTestData() {
        System.out.println("  → Initializing test data...");

        // Load sample services data
        loadSampleServices();

        // Load sample users
        loadSampleUsers();

        // Load sample orders
        loadSampleOrders();

        // Load sample products
        loadSampleProducts();
    }

    /**
     * Load sample service configurations
     */
    private void loadSampleServices() {
        Map<String, Object> services = new HashMap<>();

        services.put("user-service", new ServiceData("user-service", "http://localhost:8081", true));
        services.put("order-service", new ServiceData("order-service", "http://localhost:8082", true));
        services.put("product-service", new ServiceData("product-service", "http://localhost:8083", true));
        services.put("inventory-service", new ServiceData("inventory-service", "http://localhost:8084", true));

        testDataStore.put("services", services);
        initializedResources.add("sample-services");
        System.out.println("    ✓ Sample services loaded");
    }

    /**
     * Load sample user data
     */
    private void loadSampleUsers() {
        List<Map<String, Object>> users = new ArrayList<>();

        Map<String, Object> user1 = new HashMap<>();
        user1.put("id", 1);
        user1.put("name", "John Doe");
        user1.put("email", "john@example.com");
        user1.put("role", "ADMIN");
        users.add(user1);

        Map<String, Object> user2 = new HashMap<>();
        user2.put("id", 2);
        user2.put("name", "Jane Smith");
        user2.put("email", "jane@example.com");
        user2.put("role", "USER");
        users.add(user2);

        testDataStore.put("users", users);
        initializedResources.add("sample-users");
        System.out.println("    ✓ Sample users loaded");
    }

    /**
     * Load sample order data
     */
    private void loadSampleOrders() {
        List<Map<String, Object>> orders = new ArrayList<>();

        Map<String, Object> order1 = new HashMap<>();
        order1.put("id", "ORD-001");
        order1.put("userId", 1);
        order1.put("status", "PENDING");
        order1.put("totalAmount", 299.99);
        orders.add(order1);

        Map<String, Object> order2 = new HashMap<>();
        order2.put("id", "ORD-002");
        order2.put("userId", 2);
        order2.put("status", "SHIPPED");
        order2.put("totalAmount", 149.99);
        orders.add(order2);

        testDataStore.put("orders", orders);
        initializedResources.add("sample-orders");
        System.out.println("    ✓ Sample orders loaded");
    }

    /**
     * Load sample product data
     */
    private void loadSampleProducts() {
        List<Map<String, Object>> products = new ArrayList<>();

        Map<String, Object> product1 = new HashMap<>();
        product1.put("id", "PROD-001");
        product1.put("name", "Laptop");
        product1.put("price", 999.99);
        product1.put("stock", 50);
        products.add(product1);

        Map<String, Object> product2 = new HashMap<>();
        product2.put("id", "PROD-002");
        product2.put("name", "Mouse");
        product2.put("price", 29.99);
        product2.put("stock", 200);
        products.add(product2);

        testDataStore.put("products", products);
        initializedResources.add("sample-products");
        System.out.println("    ✓ Sample products loaded");
    }

    /**
     * Get test data by key
     */
    public Object getTestData(String key) {
        return testDataStore.get(key);
    }

    /**
     * Get all services data
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getServices() {
        return (Map<String, Object>) testDataStore.get("services");
    }

    /**
     * Get all users data
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getUsers() {
        return (List<Map<String, Object>>) testDataStore.get("users");
    }

    /**
     * Get all orders data
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getOrders() {
        return (List<Map<String, Object>>) testDataStore.get("orders");
    }

    /**
     * Get all products data
     */
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> getProducts() {
        return (List<Map<String, Object>>) testDataStore.get("products");
    }

    /**
     * Clear specific test data
     */
    public void clearTestData(String key) {
        testDataStore.remove(key);
        System.out.println("  → Cleared test data: " + key);
    }

    /**
     * [SUITE TEARDOWN] Cleanup all test data
     */
    public void cleanupTestData() {
        System.out.println("  → Cleaning up test data...");

        for (String resource : initializedResources) {
            System.out.println("    ✓ Cleaning " + resource);
        }

        testDataStore.clear();
        initializedResources.clear();
    }

    /**
     * Inner class: Service Data
     */
    public static class ServiceData {
        public String id;
        public String url;
        public boolean active;

        public ServiceData(String id, String url, boolean active) {
            this.id = id;
            this.url = url;
            this.active = active;
        }
    }

    /**
     * Get summary of loaded test data
     */
    public String getSummary() {
        return String.format("TestDataManager: %d resources initialized", initializedResources.size());
    }
}

