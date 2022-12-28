package com.example.iotassistantrest.alarm;

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

    public Alarm id(Integer id) {
        this.id = id;
        return this;
    }

    public Alarm mode(String mode) {
        this.mode = mode;
        return this;
    }
}
