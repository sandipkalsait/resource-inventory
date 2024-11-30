package com.ps.resource_inventory.exception;

import java.sql.SQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ps.resource_inventory.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle DeviceAlreadyExistsException
    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public ResponseEntity<Response> handleDeviceAlreadyExistsException(DeviceAlreadyExistsException ex) {
        logger.error("Device already exists: " + ex.getMessage(), ex);
        Response response = new Response(false, "Device already exists: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // Handle DeviceNotFoundException
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<Response> handleDeviceNotFoundException(DeviceNotFoundException ex) {
        logger.error("Device not found: " + ex.getMessage(), ex);
        Response response = new Response(false, "Device not found: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle ConstraintViolationException (validation errors)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException ex) {
        logger.error("Validation error: " + ex.getMessage(), ex);
        Response response = new Response(false, "Validation error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle DataIntegrityViolationException (SQL constraints)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        logger.error("Data Integrity Error: " + ex.getMessage(), ex);
        Response response = new Response(false, "Data Integrity Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle BadSqlGrammarException (SQL syntax errors)
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<Response> handleBadSqlGrammarException(BadSqlGrammarException ex) {
        logger.error("SQL Grammar Error: " + ex.getMessage(), ex);
        Response response = new Response(false, "SQL Grammar Error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handle SQLException (General database errors)
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Response> handleSQLException(SQLException ex) {
        logger.error("Database error: " + ex.getMessage(), ex);
        Response response = new Response(false, "Database error: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle GenericException (for unexpected errors)
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Response> handleGenericException(GenericException ex) {
        logger.error("An unexpected error occurred: " + ex.getMessage(), ex);
        Response response = new Response(false, "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleUnexpectedException(Exception ex) {
        logger.error("An unexpected error occurred: " + ex.getMessage(), ex);
        Response response = new Response(false, "An unexpected error occurred: " + ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    

}
