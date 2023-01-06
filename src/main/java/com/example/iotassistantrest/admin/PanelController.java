package com.example.iotassistantrest.admin;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class PanelController {

    @GetMapping("/panel")
    public String showPanel() {
        return "admin/panel";
    }

}
