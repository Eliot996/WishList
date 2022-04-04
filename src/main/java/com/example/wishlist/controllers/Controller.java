package com.example.wishlist.controllers;

import com.example.wishlist.models.User;
import com.example.wishlist.repository.DummyWishlistRepo;
import com.example.wishlist.services.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class Controller {
    private final UserService USER_SERVICE = new UserService();

    @GetMapping("/")
    public String landingPage() {
        return "landingpage";
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model){
        if (session.getAttribute("user_token") == null){
            model.addAttribute("user", new User());
            return "login";
        }
        return "redirect:/lists";
    }

    @PostMapping("/login")
    public String loginPage(HttpSession session, @ModelAttribute User user, Model model){
        String token = USER_SERVICE.login(user.getEmail(), user.getPassword());

        if (token != null) {
            session.setAttribute("user_token", token);
        }

        return "registrer";
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
