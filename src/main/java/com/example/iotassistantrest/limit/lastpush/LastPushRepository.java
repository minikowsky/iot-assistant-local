package com.example.iotassistantrest.limit.lastpush;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface LastPushRepository extends CrudRepository<LastPush, String> {
}
