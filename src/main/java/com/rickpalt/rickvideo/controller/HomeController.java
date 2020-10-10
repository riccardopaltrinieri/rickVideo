package com.rickpalt.rickvideo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping({"/", "/Home", "/home"})
    public String home(Model model) {
        return "Home";
    }

}
