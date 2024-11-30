package com.ps.resource_inventory.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);

    @Value("${rate.limit.max-requests-per-minute:100}")
    private int maxRequestsPerMinute;

    @Value("${rate.limit.reset-time-interval:60}")
    private int resetTimeInterval; // default to 60 seconds if not specified

    private final ConcurrentHashMap<String, RequestInfo> requestCountsPerIpAddress = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String clientIpAddress = request.getRemoteAddr();
        logger.info("Processing request from IP: {} at {}", clientIpAddress, getCurrentTimeFormatted());

        // Get or create the request info for the client IP address
        RequestInfo requestInfo = requestCountsPerIpAddress.computeIfAbsent(clientIpAddress, ip -> {
            logger.debug("Initializing request info for IP: {}", ip);
            return new RequestInfo();
        });

        // Check if it's a new time period based on reset interval (in milliseconds)
        long currentTime = System.currentTimeMillis();
        if (currentTime - requestInfo.timestamp >= resetTimeInterval * 1000) {
            logger.debug("Resetting request count for IP: {} at {}", clientIpAddress, getCurrentTimeFormatted());
            requestInfo.resetRequestCount(currentTime);
        }

        // Increment the request count
        int requests = requestInfo.requestCount.incrementAndGet();
        logger.debug("Request count for IP {}: {} at {}", clientIpAddress, requests, getCurrentTimeFormatted());

        // Check if the request limit has been exceeded
        if (requests > maxRequestsPerMinute) {
            logger.warn("Rate limit exceeded for IP: {} at {}. Requests: {}, Limit: {}", clientIpAddress,
                    getCurrentTimeFormatted(), requests, maxRequestsPerMinute);
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            response.getWriter().write("Too many requests. Please try again later.");
            return;
        }

        logger.info("Allowing request from IP: {} at {}", clientIpAddress, getCurrentTimeFormatted());

        // Allow the request to proceed
        chain.doFilter(request, response);
    }

    // Function to format the current time as hh:mm:ss AM/PM
    private String getCurrentTimeFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    // Helper class to store request count and timestamp
    private static class RequestInfo {
        AtomicInteger requestCount = new AtomicInteger(0); // Tracks the number of requests
        long timestamp = System.currentTimeMillis(); // Stores the timestamp as a long (milliseconds)

        // Reset the request count and update the timestamp
        void resetRequestCount(long currentTime) {
            logger.debug("Resetting request count. Previous count: {}, New timestamp: {}", requestCount.get(),
                    currentTime);
            requestCount.set(0); // Reset the count
            timestamp = currentTime; // Update the timestamp to the current time
        }
    }

    @PostConstruct
    public void logConfigValues() {
        logger.info("Max Requests Per Minute: {}", maxRequestsPerMinute);
        logger.info("Reset Time Interval: {}", resetTimeInterval);
    }
}
