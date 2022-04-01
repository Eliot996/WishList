package com.example.wishlist.controllers;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/")
    public String landingPage() {
        return "landingpage";
    }

    @GetMapping("/fragments")
    public String getFragments(){
        return "fragments";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/registrer")
    public String registrerPage(){
        return "registrer";
    }
}
