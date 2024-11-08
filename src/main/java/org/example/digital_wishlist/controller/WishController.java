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

        model.addAttribute("wishlist", wishlist);
        model.addAttribute("presentWithStatus", presentWithStatus); // Pass map to the view
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
    public String reservePresent(@RequestParam("presentId") int presentId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";  // Redirect to login if user is not authenticated
        }

        // Attempt to reserve the present
        boolean isReserved = service.reservePresent(presentId, userId);

        // Get the wishlist ID for this present
        Integer wishlistId = service.getWishlistIdByPresentId(presentId);
        if (wishlistId == null) {
            return "redirect:/overview";  // Redirect to an error page if wishlistId is not found
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
