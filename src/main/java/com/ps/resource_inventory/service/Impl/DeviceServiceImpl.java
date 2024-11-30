package com.ps.resource_inventory.service.Impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.resource_inventory.dto.DeviceDTO;
import com.ps.resource_inventory.entities.Device;
import com.ps.resource_inventory.entities.Device.Status;
import com.ps.resource_inventory.exception.DeviceAlreadyExistsException;
import com.ps.resource_inventory.exception.DeviceNotFoundException;
import com.ps.resource_inventory.mapper.DeviceDTOMapper;
import com.ps.resource_inventory.repository.DeviceRepository;
import com.ps.resource_inventory.service.DeviceService;
import com.ps.resource_inventory.util.Response;

import jakarta.transaction.Transactional;

// import lombok.extern.slf4j.Slf4j;

// import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
// @Slf4j 
public class DeviceServiceImpl implements DeviceService {

    // This creates a logger using SLF4J
    private static final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceDTOMapper deviceMapper;

    // Method to check if a device exists by macId
    private boolean isDeviceExist(String macId) {
        try {
            Optional<Device> device = deviceRepository.findById(macId);  // Assuming 'macId' is the primary identifier
            return device.isPresent();
        } catch (Exception e) {
            log.error("Error while checking if device exists: " + e.getMessage(), e);
            return false;
        }
    }
    

    @Override
    public Response createDevice(DeviceDTO entity) {

        try {

            // Validate the deviceDTO (you may want to use a Validator or Bean Validation annotations)
            if (entity == null || entity.getDeviceId() == null || entity.getDeviceId().isEmpty()) {
                throw new IllegalArgumentException("Device DTO or Device ID cannot be null or empty.");
            }
    
            // Check if the device already exists by its deviceId (assuming 'macId' is the unique identifier)
            if (isDeviceExist(entity.getDeviceId())) {
                throw new DeviceAlreadyExistsException("Device with MAC ID " + entity.getDeviceId() + " already exists.");
            }
    
           
            Date now = new Date();
            if (entity.getCreatedOn() == null) {
                entity.setCreatedOn(now);
            }
            if(entity.getStatus()==null)
                entity.setStatus(Status.ACTIVE);
            entity.setLastModifiedOn(now);
            entity.setLastModifiedBy( "system"); // Replace with dynamic logic (e.g., current user)
            Device device = deviceMapper.toEntity(entity);  // Mapping DTO to entity
            deviceRepository.save(device);  // Saving device in the repository
            
            // Return success response with a message
            return Response.success("Device created successfully.");
            
        } catch (DeviceAlreadyExistsException ex) {
            log.error("Device creation failed: " + ex.getMessage(), ex);
            throw ex; // Re-throw the custom exception to be handled globally
        } catch (IllegalArgumentException ex) {
            log.error("Invalid input provided: " + ex.getMessage(), ex);
            throw ex; // Handle specific input validation errors
        } catch (Exception ex) {
            log.error("Unexpected error while creating device: " + ex.getMessage(), ex);
            throw new RuntimeException("Device creation failed due to an unexpected error.");
        }
    }
    
    @Override
    public Response createOrReplaceDevice(String macId, DeviceDTO entity) {
        try {
            if (isDeviceExist(macId)) {
                // Device exists, so update it
                return updateDevice(macId, entity);
            } else {
                // Device does not exist, create it
                return createDevice(entity);
            }
        } catch (Exception ex) {
            log.error("Error while creating or replacing the device: " + ex.getMessage(), ex);
            return Response.failure("Error while creating or replacing the device.");
        }
    }

    @Override
    public Response updateDevice(String macId, DeviceDTO entity) {
        try {
            if (!isDeviceExist(macId)) {
                throw new DeviceNotFoundException("Device with MAC ID " + macId + " not found.");
            }

            Device device = deviceMapper.toEntity(entity);
            device.setDeviceId(macId); // Ensure the MAC ID is correctly set
            deviceRepository.save(device);

            return Response.success("Device updated successfully.");
        } catch (DeviceNotFoundException ex) {
            log.error("Device update failed: " + ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            log.error("Error while updating device: " + ex.getMessage(), ex);
            return Response.failure("Error while updating the device.");
        }
    }

    @Override
    public Response searchDevice(String value) {
        try {
            List<Device> devices = deviceRepository.findByName(value);
            if (devices.isEmpty()) {
                return Response.failure("No devices found for search term: " + value);
            }

            List<DeviceDTO> deviceDTOs = deviceMapper.toDTOList(devices);
            return Response.success("Devices found", deviceDTOs);
        } catch (Exception ex) {
            log.error("Error while searching for devices: " + ex.getMessage(), ex);
            return Response.failure("Error while searching for devices.");
        }
    }

    @Override
    public Response getDevice(String macId) {
        try {
            Optional<Device> deviceOptional = deviceRepository.findById(macId);
            if (deviceOptional.isPresent()) {
                DeviceDTO deviceDTO = deviceMapper.toDTO(deviceOptional.get());
                return Response.success("Device found", deviceDTO);
            } else {
                return Response.failure("Device with MAC ID " + macId + " not found.");
            }
        } catch (Exception ex) {
            log.error("Error while fetching device: " + ex.getMessage(), ex);
            return Response.failure("Error while fetching the device.");
        }
    }

    @Override
    public Response getAllDevices() {
        try {
            List<Device> devices = deviceRepository.findAll();
            if (devices.isEmpty()) {
                return Response.failure("No devices found.");
            }

            // List<DeviceDTO> deviceDTOs = deviceMapper.toDTOList(devices);
            return Response.success("All devices fetched successfully.", devices);
        } catch (Exception ex) {
            log.error("Error while fetching all devices: " + ex.getMessage(), ex);
            return Response.failure("Error while fetching all devices.");
        }
    }

    @Override
    public Response deleteDevice(String macId) {
        try {
            if (!isDeviceExist(macId)) {
                return Response.failure("Device with MAC ID " + macId + " not found.");
            }

            deviceRepository.deleteById(macId);
            return Response.success("Device deleted successfully.");
        } catch (Exception ex) {
            log.error("Error while deleting device: " + ex.getMessage(), ex);
            return Response.failure("Error while deleting the device.");
        }
    }

    @Override
    public Response deleteAllDevices() {
        try {
            deviceRepository.deleteAll();
            return Response.success("All devices deleted successfully.");
        } catch (Exception ex) {
            log.error("Error while deleting all devices: " + ex.getMessage(), ex);
            return Response.failure("Error while deleting all devices.");
        }
    }
}
