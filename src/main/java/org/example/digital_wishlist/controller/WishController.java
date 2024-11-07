package org.example.digital_wishlist.controller;

import jakarta.servlet.http.HttpSession;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.example.digital_wishlist.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Controller("/")
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

    @GetMapping("/{id}")
    public String getWishlist(@PathVariable int id, Model model) {
        Wishlist wishlist = service.getWishList(id);
        List<Present> presents = service.getPresentsByWishId(id);

        if (wishlist == null) {
            return "redirect:/wishListSite";
        }

        // Get the list of reserved present IDs for this wishlist
        List<Integer> reservedPresentIds = service.getReservedPresentIds(id);

        // Map each Present to a Boolean indicating its reservation status
        Map<Present, Boolean> presentWithStatus = new LinkedHashMap<>();
        for (Present present : presents) {
            boolean isReserved = reservedPresentIds.contains(present.getId());
            presentWithStatus.put(present, isReserved);
        }

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("presentWithStatus", presentWithStatus); // Pass the map to the view
        return "wishList";
    }




    // form for adding a new wish
    @GetMapping("create_wish")
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

        return "redirect:/overview";
    }

    @PostMapping("/{id}/delete")
    public String deleteWish(@PathVariable int id){
        int deletedRows = service.deleteWish(id);

        if(deletedRows > 0){ // hvis en row er slettet, så vil deltedrows være > 0
            return "redirect:/overview";
        }else{
            return "wishList";
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

    }@GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, Model model) {
        User foundUser = service.findUser(user.getUsername());

        if (foundUser != null) {
            System.out.println("Found user: " + foundUser.getUsername() + " with ID: " + foundUser.getId());
        } else {
            System.out.println("No user found with username: " + user.getUsername());
        }

        // Check for password match
        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("userId", foundUser.getId());
            return "redirect:/overview";
        } else {
            model.addAttribute("error", "Wrong Password or Username");
            return "login";
        }
    }
    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnFavicon() {
        // You can leave this method empty, or return an actual favicon if desired
    }





    @PostMapping("/reserve")
    public String reservePresent(@RequestParam("presentId") int wishlistId, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = service.findUserById(userId);
        boolean isReserved = service.reservePresent(wishlistId, userId);

        // Add a flash attribute to pass the message after redirect
        redirectAttributes.addFlashAttribute("message", isReserved);

        // Redirect to the wishlist page for this wishId
        return "redirect:/" + wishlistId; // redirects to the specific wishlist page, handled by getWishlist
    }

    @PostMapping("/cancel-reservation")
    public String cancelReservation(@RequestParam("presentId") int wishlistId, HttpSession session, Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        boolean success = service.cancelReservation(wishlistId, userId);
        if (success) {
            model.addAttribute("message", "Reservation canceled successfully.");
        } else {
            model.addAttribute("message", "Failed to cancel reservation.");
        }
        return "redirect:/" + wishlistId; // Redirect to the wishlist after cancellation
    }

    /*@GetMapping("/create_wishlist")
    public String createWishList(Model model, HttpSession session) {
        model.addAttribute("wishlist", new Wishlist(rs.getInt("WishlistID"),rs.getString("Wishlistname")));
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
    }

     */
}
