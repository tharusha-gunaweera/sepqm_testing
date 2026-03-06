package com.product.product.fixtures;

import java.util.*;
import java.util.concurrent.*;

/**
 * Product Service Fixture
 *
 * Manages all setup and teardown operations for Product Service testing
 * Provides helper methods for:
 * - Product CRUD operations
 * - Database initialization and cleanup
 * - Product inventory management
 * - Stock and pricing operations
 */
public class ProductServiceFixture {

    private Map<Integer, ProductTestData> productInventory;
    private Map<Integer, Long> productCreationTimes;
    private Map<Integer, Integer> productAccessCounts;
    private Set<Integer> deletedProductIds;
    private int productCounter;
    private ExecutorService executorService;
    private LinkedBlockingQueue<Integer> productQueue;
    private Random random;

    public ProductServiceFixture() {
        this.productInventory = new ConcurrentHashMap<>();
        this.productCreationTimes = new ConcurrentHashMap<>();
        this.productAccessCounts = new ConcurrentHashMap<>();
        this.deletedProductIds = ConcurrentHashMap.newKeySet();
        this.productCounter = 1000;
        this.random = new Random();
    }

    /**
     * [SUITE SETUP] Start product service infrastructure
     */
    public void startProductServiceInfrastructure() {
        System.out.println("  → Starting product service infrastructure...");
        this.executorService = Executors.newFixedThreadPool(3);
        this.productQueue = new LinkedBlockingQueue<>();
    }

    /**
     * [CLASS SETUP] Setup test database for products
     */
    public void setupTestDatabase() {
        System.out.println("  → Setting up product test database...");
        // Initialize in-memory database
        productInventory.clear();
    }

    /**
     * [CLASS SETUP] Initialize product catalog
     */
    public void initializeProductCatalog() {
        System.out.println("  → Initializing product catalog...");
        // Load product categories and configurations
    }

    /**
     * [CLASS SETUP] Setup inventory management system
     */
    public void setupInventoryManagement() {
        System.out.println("  → Setting up inventory management...");
        // Initialize inventory tracking
    }

    /**
     * Create a test product
     */
    public ProductTestData createProduct(String name, String description, double price, int quantity) {
        int productId = ++productCounter;
        ProductTestData product = new ProductTestData(productId, name, description, price, quantity);
        productInventory.put(productId, product);
        productCreationTimes.put(productId, System.currentTimeMillis());
        productAccessCounts.put(productId, 0);
        System.out.println("  → Product created: " + productId + " - " + name);
        return product;
    }

    /**
     * Get product by ID
     */
    public ProductTestData getProductById(int productId) {
        if (productInventory.containsKey(productId)) {
            productAccessCounts.put(productId, productAccessCounts.getOrDefault(productId, 0) + 1);
            return productInventory.get(productId);
        }
        return null;
    }

    /**
     * Update product details
     */
    public void updateProduct(int productId, String name, String description, double price) {
        if (productInventory.containsKey(productId)) {
            ProductTestData product = productInventory.get(productId);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            System.out.println("  → Product updated: " + productId);
        }
    }

    /**
     * Delete product
     */
    public void deleteProduct(int productId) {
        if (productInventory.containsKey(productId)) {
            productInventory.remove(productId);
            deletedProductIds.add(productId);
            System.out.println("  → Product deleted: " + productId);
        }
    }

    /**
     * Update product stock
     */
    public void updateProductStock(int productId, int quantity) {
        if (productInventory.containsKey(productId)) {
            ProductTestData product = productInventory.get(productId);
            product.setQuantity(quantity);
            System.out.println("  → Product stock updated: " + productId + " - " + quantity);
        }
    }

    /**
     * Get all products
     */
    public Collection<ProductTestData> getAllProducts() {
        return productInventory.values();
    }

    /**
     * Get product count
     */
    public int getProductCount() {
        return productInventory.size();
    }

    /**
     * Check if product exists
     */
    public boolean productExists(int productId) {
        return productInventory.containsKey(productId);
    }

    /**
     * Get product creation time
     */
    public long getProductCreationTime(int productId) {
        return productCreationTimes.getOrDefault(productId, -1L);
    }

    /**
     * Get product access count
     */
    public int getProductAccessCount(int productId) {
        return productAccessCounts.getOrDefault(productId, 0);
    }

    /**
     * Search products by name
     */
    public List<ProductTestData> searchProductsByName(String searchTerm) {
        List<ProductTestData> results = new ArrayList<>();
        for (ProductTestData product : productInventory.values()) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                results.add(product);
            }
        }
        return results;
    }

    /**
     * Get products by price range
     */
    public List<ProductTestData> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<ProductTestData> results = new ArrayList<>();
        for (ProductTestData product : productInventory.values()) {
            if (product.getPrice() >= minPrice && product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }
        return results;
    }

    /**
     * Get low stock products
     */
    public List<ProductTestData> getLowStockProducts(int threshold) {
        List<ProductTestData> results = new ArrayList<>();
        for (ProductTestData product : productInventory.values()) {
            if (product.getQuantity() < threshold) {
                results.add(product);
            }
        }
        return results;
    }

    /**
     * Calculate total inventory value
     */
    public double calculateTotalInventoryValue() {
        double total = 0.0;
        for (ProductTestData product : productInventory.values()) {
            total += product.getPrice() * product.getQuantity();
        }
        return total;
    }

    /**
     * Simulate bulk product creation
     */
    public List<ProductTestData> createBulkProducts(int count) {
        List<ProductTestData> createdProducts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            ProductTestData product = createProduct(
                "Bulk Product " + (i + 1),
                "Bulk created product",
                random.nextDouble() * 1000,
                random.nextInt(100) + 10
            );
            createdProducts.add(product);
        }
        System.out.println("  → Bulk creation completed: " + count + " products");
        return createdProducts;
    }

    /**
     * Get deleted products count
     */
    public int getDeletedProductsCount() {
        return deletedProductIds.size();
    }

    /**
     * Clear all data
     */
    public void clearAllData() {
        System.out.println("  → Clearing all product data...");
        productInventory.clear();
        productCreationTimes.clear();
        productAccessCounts.clear();
        deletedProductIds.clear();
    }

    /**
     * [CLASS TEARDOWN] Shutdown database
     */
    public void shutdownDatabase() {
        System.out.println("  → Shutting down product test database...");
        productInventory.clear();
    }

    /**
     * [CLASS TEARDOWN] Close inventory management
     */
    public void closeInventoryManagement() {
        System.out.println("  → Closing inventory management system...");
    }

    /**
     * [CLASS TEARDOWN] Reset product catalog
     */
    public void resetProductCatalog() {
        System.out.println("  → Resetting product catalog...");
    }

    /**
     * [SUITE TEARDOWN] Stop product service infrastructure
     */
    public void stopProductServiceInfrastructure() {
        System.out.println("  → Stopping product service infrastructure...");
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
        if (productQueue != null) {
            productQueue.clear();
        }
    }

    /**
     * Helper class for test product data
     */
    public static class ProductTestData {
        private int productId;
        private String name;
        private String description;
        private double price;
        private int quantity;

        public ProductTestData(int productId, String name, String description, double price, int quantity) {
            this.productId = productId;
            this.name = name;
            this.description = description;
            this.price = price;
            this.quantity = quantity;
        }

        // Getters and Setters
        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return "ProductTestData{" +
                    "productId=" + productId +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}

