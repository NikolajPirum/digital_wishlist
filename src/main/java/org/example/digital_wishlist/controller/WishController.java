package org.example.digital_wishlist.controller;

import jakarta.servlet.http.HttpSession;
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

    }@GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, Model model) throws InterruptedException {
        User foundUser = service.findUser(user.getUsername());

        // Check for password match
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            // Store user ID in session
            session.setAttribute("userId", foundUser.getId());

            // Redirect to the user's wishlist page or homepage after login
            return "redirect:/overview"; // Adjust to the appropriate page
        } else {
            // Add an error message and reload the login page if credentials are incorrect
            model.addAttribute("error", "Wrong Password or Username");
            return "login";
        }
    }


    @GetMapping("/wishlist/{wishlistId}")
    public String showWishlist(@PathVariable int wishlistId, Model model) {
        List<Present> presents = service.getPresentsForWishlist(wishlistId);
        model.addAttribute("presents", presents);
        return "wishlist";
    }

    @PostMapping("/reserve")
    public String reservePresent(@RequestParam("presentId") int presentId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        if (service.reservePresent(presentId, userId)) {
            model.addAttribute("message", "Reserved successfully!");
        } else {
            model.addAttribute("message", "Already reserved.");
        }
        return "redirect:/wishlist/" + presentId;
    }

    @PostMapping("/cancel-reservation")
    public String cancelReservation(@RequestParam("presentId") int presentId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        boolean success = service.cancelReservation(presentId, userId);
        if (success) {
            model.addAttribute("message", "Reservation canceled successfully.");
        } else {
            model.addAttribute("message", "Failed to cancel reservation.");
        }
        return "redirect:/wishlist/" + presentId; // Redirect to the wishlist after cancellation
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
