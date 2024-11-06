package org.example.digital_wishlist.service;

import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.example.digital_wishlist.repository.WishRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {

    private final WishRepository repository;
    private final WishRepository wishRepository;

    public WishService(WishRepository repository, WishRepository wishRepository) {
        this.repository = repository;
        this.wishRepository = wishRepository;
    }


    public void createUser(User user){
        repository.createUser(user);
    }

    public void deleteUser(int id){
        repository.deleteUser(id);
    }


    public List<Wishlist> getAllWishLists(){
        return repository.getAllWishLists();
    }

    public Wishlist getWishList(int id){
        return repository.getWishlist(id);
    }

    public List<Present> getPresentsByWishId(int id){
        return repository.getPresentsByWishListId(id);
    }

    public void addWish(Present present){
        repository.addWish(present);
    }

    public int deleteWish(int id){
        return repository.deleteWish(id);
    }
    public boolean findByUsername(String username){
        return repository.findByUsername(username);
    }
    public boolean findUserByEmail(String email){
        return repository.findUserByEmail(email);
    }

    public void createWishlist(Wishlist wishlist){
        wishRepository.createWishlist(wishlist);
    }
    public User findUser(String username){
        return repository.findUser(username);
    }

    public void createWishlist(String wishlistName, int userId){
        repository.createWishlist(wishlistName, userId);
    }
    /*
    public readUser(){
        // code to readUser
    }

    public readWishlist(){
        // code to readWishlist
    }

    public updateWishlist(){
        // code to updateWishlist
    }

    public deleteWishlist(){
        // code to deleteWishlist
    }
}
