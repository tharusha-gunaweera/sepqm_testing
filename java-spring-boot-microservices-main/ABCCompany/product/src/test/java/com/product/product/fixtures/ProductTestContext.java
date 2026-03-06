package com.product.product.fixtures;

import java.util.*;

/**
 * Product Test Context
 *
 * Manages request context for product operations
 * Tracks request metadata, headers, and parameters
 */
public class ProductTestContext {

    private String operation;
    private int productId;
    private Map<String, String> headers;
    private Map<String, Object> parameters;
    private long startTime;
    private long endTime;
    private boolean isComplete;

    public ProductTestContext() {
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
        this.startTime = System.currentTimeMillis();
        this.isComplete = false;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public Map<String, String> getHeaders() {
        return new HashMap<>(headers);
    }

    public void addParameter(String key, Object value) {
        this.parameters.put(key, value);
    }

    public Object getParameter(String key) {
        return parameters.get(key);
    }

    public Map<String, Object> getParameters() {
        return new HashMap<>(parameters);
    }

    public long getExecutionTime() {
        if (isComplete) {
            return endTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    public void complete() {
        this.endTime = System.currentTimeMillis();
        this.isComplete = true;
    }

    public void clear() {
        headers.clear();
        parameters.clear();
        operation = null;
        productId = 0;
        isComplete = false;
    }

    @Override
    public String toString() {
        return "ProductTestContext{" +
                "operation='" + operation + '\'' +
                ", productId=" + productId +
                ", headers=" + headers +
                ", executionTime=" + getExecutionTime() + "ms" +
                '}';
    }
}

