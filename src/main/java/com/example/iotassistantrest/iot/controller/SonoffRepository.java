package com.example.iotassistantrest.iot.controller;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SonoffRepository extends CrudRepository<Sonoff, String> {
}
