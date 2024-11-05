package org.example.digital_wishlist.service;

import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.repository.Repository;

@org.springframework.stereotype.Service
public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public int createUser(User user){
        return repository.createUser(user);
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
