package com.hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {

        // js import
        model.addAttribute("importJsFileSrcList", new String[]{
                "/js/home",
                "/js/home2"
        });

        return "home";
    }
}
