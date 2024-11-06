package org.example.digital_wishlist.controller;

import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

            // Check for password match
            if (founduser != null && founduser.getPassword().equals(user.getPassword())) {
                return "index";
            } else {
                model.addAttribute("error", "Wrong Password or Username");
                return "lo2gin";
            }

    }

    @GetMapping("/wishlist/{userId}")
    public String getWishlist(@PathVariable int userId, Model model) {
        // Fetch all presents for the given user (or all presents if no user-specific filtering is needed)
        List<Present> presents = service.getAllPresents();

        // Add the presents list to the model
        model.addAttribute("presents", presents);
        model.addAttribute("userId", userId); // Pass the userId for later use in forms (if needed)

        return "wishlist"; // Return the Thymeleaf template name
    }
    @PostMapping("/reserve")
    public String reserve(@RequestParam int presentId, @RequestParam int userId, Model model) {
        boolean success = service.reservePresent(presentId, userId);

        if (success) {
            model.addAttribute("message", "Present reserved successfully!");
        } else {
            model.addAttribute("error", "This present is already reserved.");
        }

        return "wishlist"; // Redirect back to the wishlist page
    }

    // Cancel a reservation
    @PostMapping("/cancel-reservation")
    public String cancelReservation(@RequestParam int presentId, @RequestParam int userId, Model model) {
        boolean success = service.cancelReservation(presentId, userId);

        if (success) {
            model.addAttribute("message", "Reservation canceled successfully.");
        } else {
            model.addAttribute("error", "Failed to cancel reservation.");
        }

        return "wishlist"; // Redirect back to the wishlist page
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
