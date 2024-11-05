package org.example.digital_wishlist.controller;

import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@Controller
public class WishController {

    private final WishService service;


    public WishController(WishService service) {
        this.service = service;
    }

    @GetMapping("/create_user")
    public String showCreateUserForm(Model model) {
        // Add a new User object to the model for form binding
        model.addAttribute("user", new User());
        return "create_user";
    }
    @PostMapping("/create_user")
    public String createUser(@ModelAttribute("user") User user,Model model) throws InterruptedException {
        // Check if the username or email already exists
        if (service.findByUsername(user.getUsername()) || service.findUserByEmail(user.getEmail())) {
            model.addAttribute("error", "Username or email already exists");
            return "create_user";
        }

        // Save the new user
        service.createUser(user);
        model.addAttribute("success", true);
        return "create_user";

    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, Model model) throws InterruptedException {
        User founduser = service.findUser(user.getUsername());

        if(founduser != null
             && founduser.getUsername().equals(user.getUsername())
             && founduser.getPassword().equals(user.getPassword())){

                return "index";
        }
        else {
            model.addAttribute("error","Wrong Password or Username");
            return "login";
        }
    }
    /*
    public createWishlist(){
        // code to createWishlist
    }

    public readUser(){
        // code to readUser
    }

    public readWishlist(){
        // code to readWishlist
    }

    public updateWishlist(){
        // code to updateWishlist
    }

    public deleteUser(){
        // code to deleteUser
    }

    public deleteWishlist(){
        // code to deleteWishlist
    }
     */
}
