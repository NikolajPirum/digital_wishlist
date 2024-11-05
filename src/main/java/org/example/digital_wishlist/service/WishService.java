package org.example.digital_wishlist.service;

import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.repository.WishRepository;
import org.springframework.stereotype.Service;

@Service
public class WishService {

    private final WishRepository repository;

    public WishService(WishRepository repository) {
        this.repository = repository;
    }


    public void createUser(User user){
        repository.createUser(user);
    }

    public int deleteUser(int id){
        return repository.deleteUser(id);
    }

    public int addWish(Present present){
        return repository.addWish(present);
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

    public deleteWishlist(){
        // code to deleteWishlist
    }
}
