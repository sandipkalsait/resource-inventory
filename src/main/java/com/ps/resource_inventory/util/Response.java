package com.ps.resource_inventory.util;

import java.util.Optional;

public class Response {

    private Optional<Boolean> success = Optional.empty();  // Indicates whether the operation was successful
    private Optional<String> message = Optional.empty();   // A message describing the result (e.g., success or error message)
    private Optional<Object> data = Optional.empty();      // Optional, can hold additional data, like the created device or error details

    // Constructor for success responses
    public Response(Boolean success, String message) {
        this.success = Optional.ofNullable(success);
        this.message = Optional.ofNullable(message);
    }

    // Constructor for responses with data
    public Response(Boolean success, String message, Object data) {
        this.success = Optional.ofNullable(success);
        this.message = Optional.ofNullable(message);
        this.data = Optional.ofNullable(data);
    }

    // Static factory method for success response without message or data
    public static Response success() {
        return new Response(true, null, null);
    }

    // Static factory method for success response with message
    public static Response success(String message) {
        return new Response(true, message, null);
    }

    // Static factory method for success response with message and data
    public static Response success(String message, Object data) {
        return new Response(true, message, data);
    }

    // Static factory method for failure response with message
    public static Response failure(String message) {
        return new Response(false, message, null);
    }

    // Getters for Optional fields, which can be empty
    public Optional<Boolean> getSuccess() {
        return success;
    }

    public Optional<String> getMessage() {
        return message;
    }

    public Optional<Object> getData() {
        return data;
    }

    // Utility methods to check the presence of values
    public boolean isSuccessPresent() {
        return success.isPresent();
    }

    public boolean isMessagePresent() {
        return message.isPresent();
    }

    public boolean isDataPresent() {
        return data.isPresent();
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success.orElse(null) + // Unwrap Optional or show null
                ", message='" + message.orElse("N/A") + '\'' + // Default to "N/A" if empty
                ", data=" + (data.orElse("N/A")) + // Default to "N/A" if empty
                '}';
    }
}
