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
        // check session and redirect if necessary
        int userID = checkTokenAndGetID(session);
        if (userID != -1){
            return "redirect:/lists";
        }

        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // check session and break the token if it exists
        int userID = checkTokenAndGetID(session);
        if (userID != -1 ) {
            USER_SERVICE.breakToken(userID);
        }

        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String loginPage(HttpSession session, @ModelAttribute User user, Model model){
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID != -1 ) {
            return "redirect:/lists";
        }

        // login and set token in session
        String token = USER_SERVICE.login(user.getEmail(), user.getPassword());
        session.setAttribute("user_token", token);

        return "redirect:/lists";
    }

    @GetMapping("/registrer")
    public String registrerPage(HttpSession session, Model model){
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID != -1 ) {
            return "redirect:/lists";
        }

        // return the page
        model.addAttribute("user", new User());
        return "registrer";
    }

    @PostMapping("/registrer")
    public String registrerSubmit(HttpSession session, @ModelAttribute User user, Model model){
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID != -1 ) {
            return "redirect:/lists";
        }

        // create user
        // TODO: 04/04/2022 log user in when created
        USER_SERVICE.createUser(user.getName(), user.getEmail(), user.getPassword());

        return "landingpage";
    }

    @GetMapping("/lists")
    public String getListOfLists(HttpSession session, Model model){
        // check session and redirect if the session is invalid
        int userID = checkTokenAndGetID(session);
        if (userID == -1){
            return "redirect:/login";
        }

        model.addAttribute("listOfWishlists", new DummyWishlistRepo().getListOfWishlists());
        return "lists";
    }

    @GetMapping("/wish")
    public String listOfItems(HttpSession session) {
        // check session and redirect if the session is invalid
        int userID = checkTokenAndGetID(session);
        if (userID == -1){
            return "redirect:/login";
        }

        return "listOfItems";
    }

    private int checkTokenAndGetID(HttpSession session) {
        String token = (String) session.getAttribute("user_token");
        // check if there is a token
        if (token == null) {
            return -1;
        }

        return USER_SERVICE.getUserID(token);
    }
}
