package com.ps.resource_inventory.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ps.resource_inventory.dto.DeviceDTO;
import com.ps.resource_inventory.service.DeviceService;
import com.ps.resource_inventory.util.Response;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    // Create a new device
    @PostMapping("device")
    public ResponseEntity<String> createDevice(@RequestBody DeviceDTO entity) {
        try {
            // Call the service to create the device
            Response response = deviceService.createDevice(entity);

            // Check the response and return appropriate status
            if (response.isSuccessPresent() && response.getSuccess().get()) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Device Created Successfully");
            } else {
                // In case the device creation failed but no exception was thrown
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Device creation failed");
            }
        } catch (Exception e) {
            // Any unexpected exception that was not handled by the GlobalExceptionHandler
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the device: " + e.getMessage());
        }
    }

    // Create or replace a device
    @PutMapping("device/{id}")
    public ResponseEntity<String> createOrReplaceDevice(@PathVariable String id, @RequestBody DeviceDTO entity) {
        try {
            Response response = deviceService.createOrReplaceDevice(id, entity);

            if (response.isSuccessPresent() && response.getSuccess().get()) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Device created or replaced successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create or replace device");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating or replacing the device: " + e.getMessage());
        }
    }

    // Update device details
    @PatchMapping("device/{id}")
    public ResponseEntity<String> updateDevice(@PathVariable String id, @RequestBody DeviceDTO entity) {
        try {
            Response response = deviceService.updateDevice(id, entity);

            if (response.isSuccessPresent() && response.getSuccess().get()) {
                return ResponseEntity.status(HttpStatus.OK).body("Device updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Device update failed");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while updating the device: " + e.getMessage());
        }
    }

    // Get device details
    @GetMapping("device/{id}")
    public ResponseEntity<?> getDevice(@PathVariable("id") String deviceId) {
        try {
            Response response = deviceService.getDevice(deviceId);
            if (response.isSuccessPresent() && response.getSuccess().get()) {
                return ResponseEntity.status(HttpStatus.OK).body(response.getData().get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the device: " + e.getMessage());
        }
    }

    @GetMapping("list")
    public ResponseEntity<Object> getAllDevices() {
        try {
            Response response = deviceService.getAllDevices();
            
            // Check if the response indicates success and contains data
            if (response.isSuccessPresent() && response.getSuccess().orElse(false)) {
                return ResponseEntity.status(HttpStatus.OK).body(response.getData().orElse("No data available"));
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No devices found");
            }
        } catch (Exception e) {
            // Handle exceptions and return an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An error occurred while fetching all devices", 
                                 "details", e.getMessage()));
        }
    }
    

    // Search for a device by value
    @GetMapping("device")
    public ResponseEntity<Object> searchDevice(@RequestParam String value) {
        try {
            Response response = deviceService.searchDevice(value);
            if (response.isSuccessPresent() && response.getSuccess().get()) {
                return ResponseEntity.status(HttpStatus.OK).body("Devices found: " + response.getData().get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No devices found for search term: " + value);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while searching for the device: " + e.getMessage());
        }
    }

    // Delete a device
    @DeleteMapping("device/{id}")
    public ResponseEntity<String> deleteDevice(@PathVariable String id) {
        try {
            Response response = deviceService.deleteDevice(id);
            if (response.isSuccessPresent() && response.getSuccess().get()) {
                return ResponseEntity.status(HttpStatus.OK).body("Device deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Device not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the device: " + e.getMessage());
        }
    }
}
