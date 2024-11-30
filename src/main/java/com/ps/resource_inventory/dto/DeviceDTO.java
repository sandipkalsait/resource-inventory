package com.ps.resource_inventory.dto;

import java.util.Date;

import com.ps.resource_inventory.entities.Device.Status;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {

    @NotBlank
    @Pattern(regexp = "^([0-9A-Fa-f]{2}:){5}([0-9A-Fa-f]{2})$", message = "Invalid MAC Address format")
    private String deviceId;

    @NotBlank(message = "Device type is required")
    private String deviceType;

    @NotNull(message = "Status is required")
    private Status status;

    @NotBlank(message = "Device name is required")
    private String name;

    private String manufacturer;

    private Date createdOn;

    private Date lastModifiedOn;

    private String lastModifiedBy;

    @Override
    public String toString() {
        return "Device {" +
                "macId='" + deviceId + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", manufacturer='" + (manufacturer != null ? manufacturer : "N/A") + '\'' +
                ", createdOn=" + (createdOn != null ? createdOn : "N/A") +
                ", lastModifiedOn=" + (lastModifiedOn != null ? lastModifiedOn : "N/A") +
                ", lastModifiedBy='" + (lastModifiedBy != null ? lastModifiedBy : "N/A") + '\'' +
                '}';
    }
}
