package com.example.iotassistantrest.limit.lastpush;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "LASTPUSH")
@Data
public class LastPush {
    @Id
    @Column(name = "PUSH_TYPE")
    private String pushType;

    @Column(name = "TIMESTAMP")
    private Timestamp timestamp;

    public LastPush pushType(String pushType) {
        this.pushType = pushType;
        return this;
    }

    public LastPush timestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
