package com.example.wishlist.controllers;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    @GetMapping("/fragments")
    public String getFragments(){
        return "fragments";
    }

    @GetMapping("/")
    public String landingPage() {
        return "landingpage";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/registrer")
    public String registrerPage(){
        return "registrer";
    }

    @GetMapping("/lists")
    public String getListOfLists(){
        return "lists";
    }
}
