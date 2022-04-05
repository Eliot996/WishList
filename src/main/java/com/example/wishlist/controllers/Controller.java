package com.example.wishlist.controllers;

import com.example.wishlist.models.User;
import com.example.wishlist.models.Wishlist;
import com.example.wishlist.services.UserService;
import com.example.wishlist.services.WishService;
import com.example.wishlist.services.WishlistService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
public class Controller {
    private final UserService USER_SERVICE = new UserService();
    private final WishlistService WISHLIST_SERVICE = new WishlistService();
    private final WishService WISH_SERVICE = new WishService();

    // *******************
    // *
    // *  landing page
    // *
    // *******************

    @GetMapping("/")
    public String landingPage() {
        return "landingpage";
    }

    // **********************
    // *
    // *  login management
    // *
    // **********************

    @GetMapping("/login")
    public String loginPage(HttpSession session, Model model){
        // check session and redirect if necessary
        int userID = checkTokenAndGetID(session);
        if (userID != -1){
            return "redirect:/wishlists";
        }

        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginPage(HttpSession session, @ModelAttribute User user, Model model){
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID != -1 ) {
            return "redirect:/wishlists";
        }

        // login and set token in session
        String token = USER_SERVICE.login(user.getEmail(), user.getPassword());
        session.setAttribute("user_token", token);

        return "redirect:/wishlists";
    }

    // *******************
    // *
    // *  logout
    // *
    // *******************

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

    // *******************
    // *
    // *  registre user
    // *
    // *******************

    @GetMapping("/registrer")
    public String registrerPage(HttpSession session, Model model){
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID != -1 ) {
            return "redirect:/wishlists";
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
            return "redirect:/wishlists";
        }

        // create user
        User createdUser = USER_SERVICE.createUser(user.getName(), user.getEmail(), user.getPassword());
        // log the new user in
        session.setAttribute("user_token", USER_SERVICE.login(createdUser.getEmail(), user.getPassword()));
        return "redirect:/wishlists";
    }

    // *******************
    // *
    // *  Edit user
    // *
    // *******************

    @GetMapping("/edit")
    public String editPage(HttpSession session, Model model){
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID == -1 ) {
            return "redirect:/register";
        }

        // return the page
        model.addAttribute("user", USER_SERVICE.getUser(userID));
        return "edit_user";
    }

    @PostMapping("/edit")
    public String updateUserInfo(HttpSession session, @ModelAttribute User user, Model model) {
        int userID = checkTokenAndGetID(session);
        if (userID == -1 ) {
            return "redirect:/register";
        }

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(user.getPassword() == null);

        USER_SERVICE.updateUser(user, userID);
        return "edit_user";
    }

    // *******************
    // *
    // *  create wishlist
    // *
    // *******************

    @GetMapping("/create-wishlist")
    public String createWishlistPage(HttpSession session, Model model) {
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID == -1 ) {
            return "redirect:/login";
        }

        model.addAttribute("wishlist", new Wishlist());
        return "create_wishlist";
    }

    @PostMapping("/create-wishlist")
    public String createWishlistPage(@ModelAttribute Wishlist wishlist,HttpSession session, Model model) {
        // check session and redirect if the session is invalid
        int userID = checkTokenAndGetID(session);
        if (userID == -1 ) {
            return "redirect:/register";
        }

        int wishlistID = WISHLIST_SERVICE.createWishlist(userID, wishlist);

        return "redirect:/wishlists/" + wishlistID;
    }

    // *******************
    // *
    // *  edit wishlist
    // *
    // *******************

    @GetMapping("/wishlists/{wishlistID}/edit")
    public String editWishlistPage(HttpSession session, Model model, @PathVariable() int wishlistID) {
        // check session and redirect if the session is valid
        int userID = checkTokenAndGetID(session);
        if (userID == -1 ) {
            return "redirect:/register";
        }

        Wishlist wishlist = WISHLIST_SERVICE.getWishlistInfo(wishlistID);

        if (userID == wishlist.getUserID()) {
            model.addAttribute("wishlist", wishlist);
            return "edit_wishlist";
        } else {
            return "redirect:/wishlists";
        }
    }

    @PostMapping("/wishlists/{wishlistID}/edit")
    public String updateWishlist(@ModelAttribute Wishlist wishlist,HttpSession session, Model model, @PathVariable() int wishlistID) {
        // check session and redirect if the session is invalid
        int userID = checkTokenAndGetID(session);
        if (userID == -1 ) {
            return "redirect:/login";
        }

        Wishlist oldWishlist = WISHLIST_SERVICE.getWishlistInfo(wishlistID);
        if (oldWishlist.getUserID() == userID) {
            WISHLIST_SERVICE.update(oldWishlist, wishlist);
        }

        return "redirect:/wishlists/" + wishlistID;
    }

    // *******************
    // *
    // *  view wishlists
    // *
    // *******************

    @GetMapping("/wishlists")
    public String getListOfLists(HttpSession session, Model model){
        // check session and redirect if the session is invalid
        int userID = checkTokenAndGetID(session);
        if (userID == -1){
            return "redirect:/login";
        }

        model.addAttribute("listOfWishlists", WISHLIST_SERVICE.getAllWishlistsFromUser(userID));
        return "lists";
    }

    // *******************
    // *
    // *  view a wishlist
    // *
    // *******************

    @GetMapping("/wishlists/{wishlistID}")
    public String listOfItems(HttpSession session, Model model, @PathVariable() int wishlistID) {
        // check session and redirect if the session is invalid
        int userID = checkTokenAndGetID(session);
        if (userID == -1){
            return "redirect:/login";
        }

        Wishlist wishlist = WISHLIST_SERVICE.getWishlistInfo(wishlistID);
        if (wishlist.getUserID() == userID) {
            WISH_SERVICE.populateWishlist(wishlist);

            model.addAttribute("wishlist", wishlist);
        }
        return "listOfItems";
    }

    // *******************
    // *
    // *  \/\/\/ helper functions \/\/\/
    // *
    // *******************

    private int checkTokenAndGetID(HttpSession session) {
        String token = (String) session.getAttribute("user_token");
        // check if there is a token
        if (token == null) {
            return -1;
        }

        return USER_SERVICE.getUserID(token);
    }
}
