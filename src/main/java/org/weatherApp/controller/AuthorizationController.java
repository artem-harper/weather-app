package org.weatherApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller()
public class AuthorizationController {

    @GetMapping()
    private String signIn(){
        return "sign/sign-in";
    }

    @GetMapping("/sign-up")
    private String signUp(){
        return "sign/sign-up";
    }
}
