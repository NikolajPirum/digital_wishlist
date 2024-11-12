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

import javax.swing.text.html.StyleSheet;
import java.nio.file.Files;
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
    public String overview(Model model,HttpSession session) {
        List<Wishlist> wishlists = service.getAllWishLists();
        Integer currentUserId = (Integer) session.getAttribute("userId");
        if (currentUserId == null) {
            // Redirect or handle if the user is not logged in
            return "redirect:/login";
        }
        model.addAttribute("wishlists", wishlists);

        return "wishListSite";
    }

    @GetMapping("/overview/noaccess")
    public String overviewNoAccess(Model model) {
        List<Wishlist> wishlists = service.getAllWishLists();

        model.addAttribute("wishlists", wishlists);

        return "wishListSiteNoAccess";
    }

    // lave en anden metode der viser en begrænset html fil


    // problemer med status i present (reserve)
    @GetMapping("/{id}")
    public String getWishlist(@PathVariable int id, Model model, HttpSession session) {
        // Fetch wishlist and presents for the specified wishlist ID
        Wishlist wishlist = service.getWishList(id);
        List<Present> presents = service.getPresentsByWishId(id);

        if (wishlist == null) {
            return "redirect:/wishListSite";  // Redirect if wishlist is not found
        }

        // Retrieve reserved present IDs for this wishlist
        List<Integer> reservedPresentIds = service.getReservedPresentIds(id);
        System.out.println("Reserved Present IDs for wishlist " + id + ": " + reservedPresentIds); // Debugging output

        // Map each Present to its reservation status
        Map<Present, Boolean> presentWithStatus = new LinkedHashMap<>();
        for (Present present : presents) {
            boolean isReserved = reservedPresentIds.contains(present.getId());
            presentWithStatus.put(present, isReserved); // Store Present with its reservation status
        }
        Integer currentUserId = (Integer) session.getAttribute("userId");
        if (currentUserId == null) {
            // Redirect or handle if the user is not logged in
            return "redirect:/login";
        }
        model.addAttribute("currentUserId", currentUserId);
        model.addAttribute("wishlistOwnerId",id);
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("presentWithStatus", presentWithStatus); // Pass map to the view
        return "wishList";

    }





    // form for adding a new wish
    @GetMapping("create_wish")
    public String showAddWishForm(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<Wishlist> wishLists = service.getWishlistByUserId(userId);

        Present present = new Present();

        model.addAttribute("wishlists", wishLists);
        model.addAttribute("present", present);

        return "create_wish";
    }

    // adding wish to wishlist
    @PostMapping("/create_wish")
    public String addWish(@ModelAttribute Present present){
        service.addWish(present);

        return "redirect:/overview/noaccess";
    }

    @PostMapping("/{id}/delete")
    public String deleteWish(@PathVariable int id){
        int deletedRows = service.deleteWish(id);

        if(deletedRows > 0){ // hvis en row er slettet, så vil deltedrows være > 0
            return "redirect:/overview/noaccess";
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
    }

    @GetMapping("/{userID}/wishlist")
    public String showWishlistByUserId(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        List<Wishlist> wishlists = service.getWishlistByUserId(userId);

        model.addAttribute("wishlists", wishlists);
        model.addAttribute("userID", userId);
        return "personalWishListSite";
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session, Model model) {
        try {
            User foundUser = service.findUser(user.getUsername());

            if (foundUser == null) {
                model.addAttribute("error", "User not found.");
                return "login";
            }

            if (!foundUser.getPassword().equals(user.getPassword())) {
                model.addAttribute("error", "Wrong password.");
                return "login";
            }

            // Store user ID in session and redirect to overview page
            session.setAttribute("userId", foundUser.getId());
            return "redirect:/" + foundUser.getId() + "/wishlist";

        } catch (Exception e) {
            e.printStackTrace(); // Log the stack trace for debugging
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnFavicon() {
        // You can leave this method empty, or return an actual favicon if desired
    }





    @PostMapping("/reserve")
    public String reservePresent(@RequestParam("presentId") int presentId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";  // Redirect to login if user is not authenticated
        }

        // reservePresent, adds the present to reserve list and returns a boolean.
        boolean isReserved = service.reservePresent(presentId, userId);

        // Get the wishlist ID for this present
        Integer wishlistId = service.getWishlistIdByPresentId(presentId);
        if (wishlistId == null) {
            return "redirect:/overview/noaccess";  // Redirect to an error page if wishlistId is not found
        }

        // Redirect to the wishlist page, invoking getWishlist to refresh the status
        return "redirect:/" + wishlistId;
    }

    @PostMapping("/cancel-reservation")
    public String cancelReservation(@RequestParam("presentId") int presentId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";  // Redirect to login if user is not authenticated
        }

        // Attempt to cancel the reservation
        boolean isCanceled = service.cancelReservation(presentId, userId);

        // Get the wishlist ID for this present
        Integer wishlistId = service.getWishlistIdByPresentId(presentId);
        if (wishlistId == null) {
            return "redirect:/overview";  // Redirect to an error page if wishlistId is not found
        }

        // Redirect to the wishlist page, invoking getWishlist to refresh the status
        return "redirect:/" + wishlistId;
    }
    @GetMapping("/create_wishlist")
    public String createWishList(Model model) {
        model.addAttribute("wishlist", new Wishlist());
        return "create_wishlist";
    }

    @PostMapping("/create_wishlist")
    public String createWishlist(@ModelAttribute Wishlist wishlist, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            wishlist.setUserID(userId);
            service.createWishlist(wishlist, userId);
            return "redirect:/overview/noaccess";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/editWishlist/{id}")
    public String showWishlistUpdateForm(@PathVariable("id") int id, Model model){
        Wishlist wishlist = service.getWishList(id);
        List<Present> presents = wishlist.getPresentList();
        model.addAttribute("wishlist", wishlist);
        model.addAttribute("presents", presents);
        return"updateWishlist";
    }
    @PostMapping("/update/wishlist")
    public String updateWishlist(@ModelAttribute("updateWishlist") Wishlist wishlist, Model model){
        service.updateWishlist(wishlist);
        model.addAttribute("updatedWishlistName", wishlist.getListName());
        return "redirect:/overview";
    }

    @GetMapping("/editPresent/{id}")
    public String showPresentUpdateForm(@PathVariable("id") int id, Model model) {
        Present present = service.getPresentById(id);
        model.addAttribute("present", present);
        return "update_present";
    }

    @PostMapping("/update/present")
    public String updatePresent(@ModelAttribute ("present") Present present, Model model){
      service.updatePresent(present);
        model.addAttribute("present", present);
        return "redirect:/overview";
    }

    public void deleteUser(){
        // code to deleteUser
    }

    public void deleteWishlist(){
        // code to deleteWishlist
    }
}
