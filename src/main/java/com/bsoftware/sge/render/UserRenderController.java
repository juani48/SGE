package com.bsoftware.sge.render;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserRenderController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
}
