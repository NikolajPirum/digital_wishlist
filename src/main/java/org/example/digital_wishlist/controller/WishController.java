package org.example.digital_wishlist.controller;

import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.example.digital_wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class WishController {

    private final WishService service;
    private final WishService wishService;


    public WishController(WishService service, WishService wishService) {
        this.service = service;
        this.wishService = wishService;
    }

    @GetMapping("/create_user")
    public String showCreateUserForm(Model model) {
        // Add a new User object to the model for form binding
        model.addAttribute("user", new User());
        return "create_user";
    }
    @PostMapping("/create_user")
    public String createUser(@ModelAttribute("user") User user,Model model) {
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
    public void createWishlist(){
        // code to createWishlist
    }

    public void readUser(){
        // code to readUser
    }

    public void readWishlist(){
        // code to readWishlist
    }
    @GetMapping("{name}/edit")
    public String showWishlistUpdateForm(@PathVariable("name") String name, Model model){
        Wishlist wishlist = wishService.getWishlistByName
    }

    @PostMapping("update/presentName")
    public void updatePresent(@ModelAttribute ("updatePresent") Present present, Model model){
        service.updatePresent(present.getName(), present.getId());
        model.addAttribute("changeName", present.setName());

        // code to updateWishlist
    }

    public void deleteUser(){
        // code to deleteUser
    }

    public void deleteWishlist(){
        // code to deleteWishlist
    }
}
