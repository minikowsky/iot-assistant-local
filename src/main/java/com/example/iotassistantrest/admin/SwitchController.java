package com.example.iotassistantrest.admin;

import com.example.iotassistantrest.firebase.body.data.Switch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class SwitchController {
    private final AdminService service;

    @GetMapping("/switch")
    public String findSwitches(Model model) {
        model.addAttribute("switches", service.findAllSwitches());
        return "admin/switch";
    }

    @GetMapping("/addSwitch")
    public String showAddSwitch(Switch data) {
        return "admin/switch-add";
    }

    @PostMapping("/addSwitch")
    public String addSwitch(@Valid Switch data, BindingResult result, Model model ) {
        if(result.hasErrors()) {
            return "admin/switch-add";
        }

        service.addSwitch(data);
        return "redirect:/switch";
    }

    @GetMapping("/deleteSwitch/{id}")
    public String deleteSwitch(@PathVariable("id") String id, Model model) {
        Optional<Switch> s = service.findAllSwitches().stream().filter(f -> id.equals(f.getId())).findFirst();
        s.ifPresent(service::deleteSwitch);
        return "redirect:/switch";
    }
}
