package com.product.product.fixtures;

import java.util.*;

/**
 * Product Test Response
 *
 * Manages response data from product operations
 * Tracks status, data, and metadata
 */
public class ProductTestResponse {

    private int statusCode;
    private String message;
    private Object data;
    private Map<String, String> responseHeaders;
    private long responseTime;
    private boolean success;

    public ProductTestResponse() {
        this.responseHeaders = new HashMap<>();
        this.statusCode = 200;
        this.success = true;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        this.success = (statusCode >= 200 && statusCode < 300);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void addHeader(String key, String value) {
        this.responseHeaders.put(key, value);
    }

    public String getHeader(String key) {
        return responseHeaders.get(key);
    }

    public Map<String, String> getHeaders() {
        return new HashMap<>(responseHeaders);
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public boolean isSuccess() {
        return success;
    }

    public void clear() {
        responseHeaders.clear();
        statusCode = 200;
        message = null;
        data = null;
        responseTime = 0;
        success = true;
    }

    @Override
    public String toString() {
        return "ProductTestResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", responseTime=" + responseTime + "ms" +
                '}';
    }
}

