package com.ps.resource_inventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ps.resource_inventory.entities.Device;

public interface DeviceRepository extends JpaRepository<Device, String> {
     // Find device by MAC ID
     Optional<Device> findById(String macId);
     // Find all devices (already provided by JpaRepository, but can be customized)
     List<Device> findAll();
     // Find devices by name
     List<Device> findByName(String name);
}
