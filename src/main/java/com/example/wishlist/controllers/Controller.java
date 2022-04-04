package com.example.wishlist.controllers;

import com.example.wishlist.models.Wishlist;
import com.example.wishlist.models.User;
import com.example.wishlist.repository.DummyWishlistRepo;
import com.example.wishlist.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.stereotype.Controller
public class Controller {
    private final UserService USER_SERVICE = new UserService();

    @GetMapping("/")
    public String landingPage() {
        return "landingpage";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/registrer")
    public String registrerPage(Model model){
        model.addAttribute("user", new User());
        return "registrer";
    }

    @PostMapping("/registrer")
    public String registrerSubmit(@ModelAttribute User user, Model model){
        USER_SERVICE.createUser(user.getName(), user.getEmail(), user.getPassword());
        return "landingpage";
    }

    @GetMapping("/lists")
    public String getListOfLists(Model model){
        model.addAttribute("listOfWishlists", new DummyWishlistRepo().getListOfWishlists());
        return "lists";
    }

    @GetMapping("/wish")
    public String listOfItems() {
        return "listOfItems";
    }
}
