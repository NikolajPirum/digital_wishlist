package org.example.digital_wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.example.digital_wishlist.service.WishService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Controller
public class WishController {

    private final WishService service;


    public WishController(WishService service) {
        this.service = service;
    }
/*
    @GetMapping("/favicon.ico")
    public void favicon() {
        // Do nothing or log the request if needed
    }
*/
    @GetMapping("/overview")
    public String overview(Model model) {
        List<Wishlist> wishlists = service.getAllWishLists();

        model.addAttribute("wishlists", wishlists);

        return "wishListSite";
    }
/*
    @GetMapping("/{id}")
    public String getWishlist(@PathVariable int id, Model model) {
        Wishlist wishlist = service.getWishList(id);
        List<Present> presents = service.getPresentsByWishId(id);

        if(wishlist == null) {
            return "redirect:/wishListSite";
        }

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("presents", presents);
        return "wishList";
    }
*/
    // form for adding a new wish
    @GetMapping("/create_wish")
    public String showAddWishForm(Model model){
        List<Wishlist> wishLists = service.getAllWishLists();

        Present present = new Present();

        model.addAttribute("wishlists", wishLists);
        model.addAttribute("present", present);

        return "create_wish";
    }

    // adding wish to wishlist
    @PostMapping("/create_wish")
    public String addWish(@ModelAttribute Present present){
        service.addWish(present);

        return "wishListSite";
    }

    @PostMapping("/{id}/delete")
    public String deleteWish(@PathVariable int id){
        int deletedRows = service.deleteWish(id);

        if(deletedRows > 0){ // hvis en row er slettet, så vil deltedrows være > 0
            return "wishListSite";
        }else{
            return "redirect:/wisList";
        }

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

        if (founduser != null) {
            // Check for password match
            if (founduser.getPassword() != null && founduser.getPassword().equals(user.getPassword())) {
                return "index";
            } else {
                model.addAttribute("error", "Wrong Password or Username");
                return "login";
            }
        } else {
            model.addAttribute("error", "Wrong Password or Username");
            return "login";
        }
    }
    @GetMapping("/create_wishlist")
    public String createWishList(Model model, HttpSession session) {
        model.addAttribute("wishlist", new Wishlist());
        return "create_wishlist";
    }

    @PostMapping("/create_wishlist")
    public String createWishlist(@ModelAttribute Wishlist wishlist, Model model) {
        service.createWishlist(wishlist);
        model.addAttribute("success", true);
        return "redirect:/overview";
    }
    /*
    @PostMapping("/create_wishlist")
    public String createWishlist(@RequestParam String wishlistName,@RequestParam int userId, Principal principal, Model model) {
        String username = principal.getName();
        User user = service.findUser(username);


        service.createWishlist(wishlistName, userId);

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
