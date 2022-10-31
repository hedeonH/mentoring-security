package com.mentoringsecurity.controller;

import com.mentoringsecurity.service.LoginAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InfoController {

    @Autowired
    LoginAttemptService loginAttemptService;

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

    @GetMapping("/blocked")
    public List<String> getBlockedUsers(){
        return loginAttemptService.getBlockedUsers();
    }
}
