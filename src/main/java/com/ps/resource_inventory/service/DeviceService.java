package com.ps.resource_inventory.service;

import com.ps.resource_inventory.dto.DeviceDTO;
import com.ps.resource_inventory.util.Response;

public interface DeviceService {
    
    // create or update device details
    Response createDevice(DeviceDTO entity);
    Response createOrReplaceDevice(String macId,DeviceDTO entity);
    Response updateDevice(String macId,DeviceDTO entity);
    
    // get device details
    Response searchDevice(String value); 
    Response getDevice(String macId);
    Response getAllDevices();

    //delete device 
    Response deleteDevice(String macId);
    Response deleteAllDevices();

}
