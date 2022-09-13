package com.mentoringsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @GetMapping("/info")
    public String getInfo(){
        return String.valueOf(System.nanoTime());
    }

    @GetMapping("/about")
    public String getAbout(){
        return "About what?";
    }

    @GetMapping("/admin")
    public String getAdmin(){
        return "Admin info";
    }
}
