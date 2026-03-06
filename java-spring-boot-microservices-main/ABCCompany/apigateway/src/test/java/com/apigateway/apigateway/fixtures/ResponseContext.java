package com.apigateway.apigateway.fixtures;

import java.util.*;

/**
 * Response Context Fixture
 *
 * Encapsulates HTTP response details for testing
 * Manages response status, headers, body, and performance metrics
 */
public class ResponseContext {

    private int statusCode;
    private String body;
    private Map<String, String> headers;
    private long responseTime; // in milliseconds
    private Map<String, String> cookies;
    private String contentType;
    private long contentLength;
    private long timestamp;

    /**
     * Constructor - Initialize response context
     */
    public ResponseContext() {
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
        this.timestamp = System.currentTimeMillis();
    }

    // ===== Status Code Methods =====

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }

    public boolean isClientError() {
        return statusCode >= 400 && statusCode < 500;
    }

    public boolean isServerError() {
        return statusCode >= 500;
    }

    // ===== Body Methods =====

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        this.contentLength = body != null ? body.length() : 0;
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

    // ===== Response Time Methods =====

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public boolean isSlowResponse() {
        return responseTime > 1000; // Threshold: 1 second
    }

    // ===== Cookie Methods =====

    public Map<String, String> getCookies() {
        return new HashMap<>(cookies);
    }

    public void addCookie(String name, String value) {
        this.cookies.put(name, value);
    }

    public String getCookie(String name) {
        return this.cookies.get(name);
    }

    public void removeCookie(String name) {
        this.cookies.remove(name);
    }

    // ===== Content Type Methods =====

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    // ===== Content Length Methods =====

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
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
     * Clear all response context data
     */
    public void clear() {
        this.statusCode = 0;
        this.body = null;
        this.headers.clear();
        this.cookies.clear();
        this.responseTime = 0;
        this.contentType = null;
        this.contentLength = 0;
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Get response summary for logging
     */
    public String getSummary() {
        return String.format("Status: %d, ResponseTime: %dms, ContentLength: %d, Headers: %d",
            statusCode, responseTime, contentLength, headers.size());
    }

    /**
     * Check if response is valid JSON
     */
    public boolean isJsonResponse() {
        String ct = getHeader("Content-Type");
        return ct != null && ct.contains("application/json");
    }

    /**
     * Check if response is valid XML
     */
    public boolean isXmlResponse() {
        String ct = getHeader("Content-Type");
        return ct != null && ct.contains("application/xml");
    }

    /**
     * Clone response context
     */
    @Override
    public ResponseContext clone() {
        ResponseContext clone = new ResponseContext();
        clone.statusCode = this.statusCode;
        clone.body = this.body;
        clone.headers.putAll(this.headers);
        clone.cookies.putAll(this.cookies);
        clone.responseTime = this.responseTime;
        clone.contentType = this.contentType;
        clone.contentLength = this.contentLength;
        clone.timestamp = this.timestamp;
        return clone;
    }

    @Override
    public String toString() {
        return "ResponseContext{" +
                "statusCode=" + statusCode +
                ", responseTime=" + responseTime +
                "ms, contentLength=" + contentLength +
                ", headers=" + headers.size() +
                ", cookies=" + cookies.size() +
                '}';
    }
}

