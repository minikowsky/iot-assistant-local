package com.example.iotassistantrest.admin;

import com.example.iotassistantrest.firebase.body.data.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ConfigController {
    private final AdminService service;

    @GetMapping("/config")
    public String showServerConfig(Model model) {
        model.addAttribute("config", service.getConfig());
        return "admin/config";
    }

    @PostMapping("/updateConfig")
    public String updateServerConfig(@Valid Config config, BindingResult result, Model model) {

        service.updateConfig(config);
        return "redirect:/admin/config";

    }

}
