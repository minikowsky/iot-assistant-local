package com.example.iotassistantrest.iot.detector.alarm;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "ALARM")
public class Alarm {
    @Id
    @Column(name="ID")
    private Integer id;

    @Column(name = "MODE")
    private String mode;
}
