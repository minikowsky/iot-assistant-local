package com.example.iotassistantrest.limit.lastpush;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LastPushService {
    private static final Logger log = LoggerFactory.getLogger(LastPushService.class);
    private final LastPushRepository repository;

    public boolean shouldPush(String pushType, long time) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Optional<LastPush> lastPush = repository.findById(pushType);
        if(lastPush.isEmpty()) {
            LastPush newLastPush = new LastPush().pushType(pushType)
                    .timestamp(new Timestamp(0));
            lastPush = Optional.of(repository.save(newLastPush));
        }
        Timestamp lastPushTimestamp = lastPush.get().getTimestamp();
        log.info("Last push: " + lastPushTimestamp.toLocalDateTime());
        log.info("Now: " + now.toLocalDateTime());
        log.info(time + " minutes between messages for this type of detection");
        if(now.getTime() >  lastPushTimestamp.getTime() + time * 60_000L) {
            log.info("Should push");
            lastPush.get().setTimestamp(now);
            repository.save(lastPush.get());
            return true;
        }
        log.info("the last message was sent quite recently");
        return false;
    }
}
