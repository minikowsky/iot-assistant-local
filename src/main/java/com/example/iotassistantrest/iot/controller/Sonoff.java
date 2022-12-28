package com.example.iotassistantrest.iot.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "SONOFF")
public class Sonoff {
    @Id
    @Column(name = "DEVICE_ID")
    private String deviceid;
    @Column(name = "LOCAL_ADDR")
    private String deviceLocalAddr;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getDeviceLocalAddr() {
        return deviceLocalAddr;
    }

    public void setDeviceLocalAddr(String deviceLocalAddr) {
        this.deviceLocalAddr = deviceLocalAddr;
    }
}
