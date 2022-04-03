package com.example.wishlist.controllers;

import com.example.wishlist.repository.DummyWishlistRepo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {

    private final DummyWishlistRepo DUMMY_WISHLIST_REPO = new DummyWishlistRepo();

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
    public String getListOfLists(Model model){
        model.addAttribute("listOfWishlists", DUMMY_WISHLIST_REPO.getListOfWishlists());
        return "lists";
    }

}
