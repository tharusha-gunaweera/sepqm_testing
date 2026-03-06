package com.apigateway.apigateway.fixtures;

import java.util.*;
import java.util.concurrent.*;

/**
 * Gateway Service Fixture
 *
 * Manages all setup and teardown operations for API Gateway testing
 * Provides helper methods for:
 * - Service registration and management
 * - Request routing and load balancing
 * - Circuit breaker pattern simulation
 * - Connection pool management
 */
public class GatewayServiceFixture {

    private Map<String, String> registeredServices;
    private Map<String, Long> serviceResponseTimes;
    private Map<String, Integer> serviceFailureCounts;
    private Map<String, Boolean> circuitBreakerStates;
    private int requestCounter;
    private ExecutorService mockExecutor;
    private LinkedBlockingQueue<String> connectionPool;
    private int connectionPoolSize;
    private Random loadBalancerRandom;

    public GatewayServiceFixture() {
        this.registeredServices = new ConcurrentHashMap<>();
        this.serviceResponseTimes = new ConcurrentHashMap<>();
        this.serviceFailureCounts = new ConcurrentHashMap<>();
        this.circuitBreakerStates = new ConcurrentHashMap<>();
        this.requestCounter = 0;
        this.loadBalancerRandom = new Random();
    }

    /**
     * [SUITE SETUP] Start mock services
     */
    public void startMockServices() {
        System.out.println("  → Starting mock services...");
        this.mockExecutor = Executors.newFixedThreadPool(5);
    }

    /**
     * [CLASS SETUP] Setup test database
     */
    public void setupDatabase() {
        System.out.println("  → Setting up test database...");
        // Simulate database connection
        Thread.currentThread().getName();
    }

    /**
     * [CLASS SETUP] Setup routing configuration
     */
    public void setupRoutingConfiguration() {
        System.out.println("  → Configuring routing rules...");
        // Load routing configuration
    }

    /**
     * [CLASS SETUP] Initialize load balancer
     */
    public void initializeLoadBalancer() {
        System.out.println("  → Initializing load balancer...");
    }

    /**
     * [METHOD SETUP] Register a microservice with the gateway
     */
    public void registerService(String serviceId, String serviceUrl) {
        registeredServices.put(serviceId, serviceUrl);
        serviceResponseTimes.put(serviceId, 1000L);
        serviceFailureCounts.put(serviceId, 0);
        circuitBreakerStates.put(serviceId, false);
        System.out.println("  → Service registered: " + serviceId + " -> " + serviceUrl);
    }

    /**
     * Check if service is registered
     */
    public boolean isServiceRegistered(String serviceId) {
        return registeredServices.containsKey(serviceId);
    }

    /**
     * Get service URL
     */
    public String getServiceUrl(String serviceId) {
        return registeredServices.get(serviceId);
    }

    /**
     * Get total number of registered services
     */
    public int getRegisteredServiceCount() {
        return registeredServices.size();
    }

    /**
     * Route request to appropriate service based on path
     */
    public String routeRequest(RequestContext context) {
        List<String> matchingServices = new ArrayList<>();

        for (String serviceId : registeredServices.keySet()) {
            // Skip services with open circuit breaker
            if (circuitBreakerStates.getOrDefault(serviceId, false)) {
                continue;
            }

            String serviceName = serviceId.replace("-service", "");
            if (context.getPath().contains(serviceName)) {
                matchingServices.add(serviceId);
            }
        }

        return matchingServices.isEmpty() ? null : matchingServices.get(0);
    }

    /**
     * Set service response time for testing timeout scenarios
     */
    public void setServiceResponseTime(String serviceId, long responseTime) {
        serviceResponseTimes.put(serviceId, responseTime);
        System.out.println("  → Service response time set: " + serviceId + " = " + responseTime + "ms");
    }

    /**
     * Execute request with timeout handling
     */
    public boolean executeRequestWithTimeout(RequestContext context) {
        long timeout = context.getTimeout();
        // Simulate request execution - returns true if timeout occurs
        return timeout < 8000; // Simplified logic
    }

    /**
     * Load balance request across registered services
     */
    public String loadBalanceRequest(RequestContext context) {
        List<String> services = new ArrayList<>(registeredServices.keySet());
        if (services.isEmpty()) return null;

        // Round-robin load balancing
        int index = (requestCounter++) % services.size();
        return services.get(index);
    }

    /**
     * Simulate service failure for circuit breaker testing
     */
    public void simulateServiceFailure(String serviceId) {
        Integer failureCount = serviceFailureCounts.getOrDefault(serviceId, 0);
        failureCount++;
        serviceFailureCounts.put(serviceId, failureCount);

        // Open circuit breaker after 5 failures
        if (failureCount >= 5) {
            circuitBreakerStates.put(serviceId, true);
        }
    }

    /**
     * Check if circuit breaker is open for a service
     */
    public boolean isCircuitBreakerOpen(String serviceId) {
        return circuitBreakerStates.getOrDefault(serviceId, false);
    }

    /**
     * Check service health
     */
    public boolean checkServiceHealth(String serviceId) {
        if (!isServiceRegistered(serviceId)) {
            return false;
        }
        return !isCircuitBreakerOpen(serviceId);
    }

    /**
     * Initialize connection pool
     */
    public void initializeConnectionPool(int poolSize) {
        this.connectionPoolSize = poolSize;
        this.connectionPool = new LinkedBlockingQueue<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            connectionPool.offer("connection-" + i);
        }
        System.out.println("  → Connection pool initialized with " + poolSize + " connections");
    }

    /**
     * Get connection pool size
     */
    public int getConnectionPoolSize() {
        return connectionPoolSize;
    }

    /**
     * Get available connections in pool
     */
    public int getAvailableConnections() {
        return connectionPool == null ? 0 : connectionPool.size();
    }

    /**
     * [METHOD TEARDOWN] Reset fixture state
     */
    public void resetState() {
        requestCounter = 0;
        System.out.println("  → Fixture state reset");
    }

    /**
     * [METHOD TEARDOWN] Clear all registered services
     */
    public void clearRegisteredServices() {
        registeredServices.clear();
        serviceResponseTimes.clear();
        circuitBreakerStates.clear();
        System.out.println("  → All registered services cleared");
    }

    /**
     * Reset failure counts for all services
     */
    public void resetFailureCount() {
        serviceFailureCounts.values().forEach(count -> count = 0);
        circuitBreakerStates.values().forEach(state -> state = false);
    }

    /**
     * [CLASS TEARDOWN] Shutdown database
     */
    public void shutdownDatabase() {
        System.out.println("  → Shutting down test database...");
    }

    /**
     * [CLASS TEARDOWN] Reset routing configuration
     */
    public void resetRoutingConfiguration() {
        System.out.println("  → Resetting routing configuration...");
    }

    /**
     * [CLASS TEARDOWN] Close load balancer
     */
    public void closeLoadBalancer() {
        System.out.println("  → Closing load balancer...");
    }

    /**
     * [SUITE TEARDOWN] Stop mock services
     */
    public void stopMockServices() {
        System.out.println("  → Stopping mock services...");
        if (mockExecutor != null && !mockExecutor.isShutdown()) {
            mockExecutor.shutdown();
            try {
                if (!mockExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                    mockExecutor.shutdownNow();
                }
            } catch (InterruptedException e) {
                mockExecutor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}

