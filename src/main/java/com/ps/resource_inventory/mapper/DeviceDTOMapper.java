package com.ps.resource_inventory.mapper;

import java.util.List;

import org.mapstruct.Mapper;
// import org.mapstruct.factory.Mappers;
import com.ps.resource_inventory.dto.DeviceDTO;
import com.ps.resource_inventory.entities.Device;

// @Mapper
@Mapper(componentModel = "spring")  // Ensure MapStruct generates a Spring bean
public interface DeviceDTOMapper {

    // DeviceDTOMapper INSTANCE = Mappers.getMapper(DeviceDTOMapper.class);

    DeviceDTO toDTO(Device device);

    Device toEntity(DeviceDTO deviceDTO);

    List<DeviceDTO> toDTOList(List<Device> devices);
}


// public class DeviceDTOMapper {
//      // Convert Device entity to DeviceDTO
//     public static DeviceDTO toDTO(Device device) {
//         if (device == null) {
//             return null;
//         }
//         DeviceDTO deviceDTO = new DeviceDTO();
//         deviceDTO.setDeviceId(device.getDeviceId());
//         deviceDTO.setDeviceType(device.getDeviceType());
//         deviceDTO.setStatus(device.getStatus().name());
//         deviceDTO.setName(device.getName());
//         deviceDTO.setManufacturer(device.getManufacturer());
//         deviceDTO.setCreatedOn(device.getCreatedOn());
//         deviceDTO.setLastModifiedOn(device.getLastModifiedOn());
//         deviceDTO.setLastModifiedBy(device.getLastModifiedBy());
//         return deviceDTO;
//     }

//     // Convert DeviceDTO to Device entity
//     public static Device toEntity(DeviceDTO deviceDTO) {
//         if (deviceDTO == null) {
//             return null;
//         }
//         Device device = new Device();
//         device.setDeviceId(deviceDTO.getDeviceId());
//         device.setDeviceType(deviceDTO.getDeviceType());
//         device.setStatus(Device.Status.valueOf(deviceDTO.getStatus()));
//         device.setName(deviceDTO.getName());
//         device.setManufacturer(deviceDTO.getManufacturer());
//         device.setCreatedOn(deviceDTO.getCreatedOn());
//         device.setLastModifiedOn(deviceDTO.getLastModifiedOn());
//         device.setLastModifiedBy(deviceDTO.getLastModifiedBy());
//         return device;
//     }
// }
