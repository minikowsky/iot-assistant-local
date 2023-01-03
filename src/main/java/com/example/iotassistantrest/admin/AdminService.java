package com.example.iotassistantrest.admin;

import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.firebase.body.data.Config;
import com.example.iotassistantrest.firebase.body.data.Switch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FirebaseService service;

    Config getConfig() {
        return service.getConfig();
    }

    List<Switch> findAllSwitches() {
        return service.findAllSwitches().values().stream().toList();
    }

    public void addSwitch(Switch data) {
        service.updateSwitch(data);
    }

    public void deleteSwitch(Switch data) {
        service.deleteSwitch(data);
    }

    public void updateConfig(Config config) {
        service.updateConfig(config.getLocalIP(), config.getSsid());
    }
}
