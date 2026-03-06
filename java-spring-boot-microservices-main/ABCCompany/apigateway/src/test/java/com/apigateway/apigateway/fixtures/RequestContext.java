package com.apigateway.apigateway.fixtures;

import java.util.*;

/**
 * Request Context Fixture
 *
 * Encapsulates HTTP request details for testing
 * Manages request parameters, headers, and metadata
 */
public class RequestContext {

    private String path;
    private String method;
    private long timeout;
    private Map<String, String> headers;
    private String body;
    private Map<String, String> queryParams;
    private String clientIp;
    private long timestamp;

    /**
     * Constructor - Initialize request context with defaults
     */
    public RequestContext() {
        this.headers = new HashMap<>();
        this.queryParams = new HashMap<>();
        this.timeout = 5000; // Default 5 seconds
        this.method = "GET";
        this.timestamp = System.currentTimeMillis();
    }

    // ===== Path Methods =====

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // ===== Method Methods =====

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    // ===== Timeout Methods =====

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    // ===== Header Methods =====

    public Map<String, String> getHeaders() {
        return new HashMap<>(headers);
    }

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public String getHeader(String key) {
        return this.headers.get(key);
    }

    public void removeHeader(String key) {
        this.headers.remove(key);
    }

    public boolean hasHeader(String key) {
        return this.headers.containsKey(key);
    }

    // ===== Body Methods =====

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    // ===== Query Parameter Methods =====

    public Map<String, String> getQueryParams() {
        return new HashMap<>(queryParams);
    }

    public void addQueryParam(String key, String value) {
        this.queryParams.put(key, value);
    }

    public String getQueryParam(String key) {
        return this.queryParams.get(key);
    }

    public void removeQueryParam(String key) {
        this.queryParams.remove(key);
    }

    // ===== Client IP Methods =====

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    // ===== Timestamp Methods =====

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    // ===== Utility Methods =====

    /**
     * Clear all request context data
     */
    public void clear() {
        this.path = null;
        this.method = "GET";
        this.body = null;
        this.clientIp = null;
        this.headers.clear();
        this.queryParams.clear();
        this.timeout = 5000;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Get request summary for logging
     */
    public String getSummary() {
        return String.format("%s %s (timeout: %dms, headers: %d, params: %d)",
            method, path, timeout, headers.size(), queryParams.size());
    }

    /**
     * Clone request context
     */
    @Override
    public RequestContext clone() {
        RequestContext clone = new RequestContext();
        clone.path = this.path;
        clone.method = this.method;
        clone.timeout = this.timeout;
        clone.headers.putAll(this.headers);
        clone.queryParams.putAll(this.queryParams);
        clone.body = this.body;
        clone.clientIp = this.clientIp;
        clone.timestamp = this.timestamp;
        return clone;
    }

    @Override
    public String toString() {
        return "RequestContext{" +
                "path='" + path + '\'' +
                ", method='" + method + '\'' +
                ", timeout=" + timeout +
                ", headers=" + headers +
                ", clientIp='" + clientIp + '\'' +
                '}';
    }
}

