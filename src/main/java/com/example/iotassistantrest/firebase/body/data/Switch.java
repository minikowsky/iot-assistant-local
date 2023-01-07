package com.example.iotassistantrest.firebase.body.data;

import lombok.Data;

@Data
public class Switch {
    private String id;
    private String name;
    private String location;
    private String localIP;
    private String mode;
}
