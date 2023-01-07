package com.example.iotassistantrest.admin;

import com.example.iotassistantrest.firebase.FirebaseService;
import com.example.iotassistantrest.firebase.body.data.Config;
import com.example.iotassistantrest.firebase.body.data.Switch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FirebaseService service;

    Config getConfig() {
        return service.getConfig();
    }

    List<Switch> findAllSwitches() {
        Map<String, Switch> map = service.findAllSwitches();
        return map == null ? null : map.values().stream().toList();
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
