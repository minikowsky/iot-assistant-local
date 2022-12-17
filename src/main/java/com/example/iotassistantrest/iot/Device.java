package com.example.iotassistantrest.iot;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
public abstract class Device {
    @NotNull
    private Long id;
    @NotNull
    private String location;
    private final Timestamp timestamp;

    protected Device() {
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }


    public Device id(Long id) {
        this.id = id;
        return this;
    }


    public Device location(String location) {
        this.location = location;
        return this;
    }
}
