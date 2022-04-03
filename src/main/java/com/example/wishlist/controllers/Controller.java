package com.example.wishlist.controllers;

import com.example.wishlist.repository.DummyWishlistRepo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String landing() {
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
    public String getListOfLists(Model model){
        model.addAttribute("listOfWishlists", new DummyWishlistRepo().getListOfWishlists());
        return "lists";
    }
}
