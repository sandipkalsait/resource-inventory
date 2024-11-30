package com.ps.resource_inventory.entities;

import java.util.Date;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "device_master")
public class Device {
    
 
    @Id
    @Column(name = "deviceId", unique = true, nullable = false, updatable = false)
    private String deviceId;
    
    private String deviceType;
    
    @Nonnull
    @Enumerated(EnumType.STRING)
    private Status status;
    
    @Nonnull
    @Column(name = "name")
    private String name;
    
    @Nonnull
    @Column(name = "createdOn")
    private Date createdOn; // Automatically set by @PrePersist
    
    @Column(name ="manufacturer")
    private String manufacturer;

    private Date lastModifiedOn;
    private String lastModifiedBy;

    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = Status.ACTIVE; // Default value
        }
        Date now = new Date();
        if (this.createdOn == null) {
            this.createdOn = now;
        }
        this.lastModifiedOn = now;
        this.lastModifiedBy = "system"; // Replace with dynamic logic (e.g., current user)
        
        // Log to verify if this method is executed
        System.out.println("prePersist executed, createdOn set to: " + this.createdOn);
    
    }

    @PreUpdate
    public void preUpdate() {
        this.lastModifiedOn = new Date();
        this.lastModifiedBy = "system"; // Replace with dynamic logic
    }

    public enum Status {
        ACTIVE,
        INACTIVE
    }


}
