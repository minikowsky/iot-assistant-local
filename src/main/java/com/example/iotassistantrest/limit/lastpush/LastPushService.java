package com.example.iotassistantrest.limit.lastpush;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LastPushService {
    private final LastPushRepository repository;

    public boolean shouldPush(String pushType, long time) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Optional<LastPush> lastPush = repository.findById(pushType);
        if(lastPush.isEmpty()) {
            LastPush newLastPush = new LastPush().pushType(pushType)
                    .timestamp(new Timestamp(0));
            lastPush = Optional.of(repository.save(newLastPush));
        }
        if(now.getTime() > lastPush.get().getTimestamp().getTime() + time * 60_000L) {
            lastPush.get().setTimestamp(now);
            repository.save(lastPush.get());
            return true;
        }
        return false;
    }
}
