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

    public WishService(WishRepository repository) {
        this.repository = repository;
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
        return repository.getPresentsByWishlistId(id);
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

    public User findUser(String username){
        return repository.findUser(username);
    }

    public User findUserById(int id){

        return repository.findUserById(id);
    }

    public void createWishlist(Wishlist wishlist){
        repository.createWishlist(wishlist);
    }



    public boolean reservePresent(int presentId, int userId) {
        return repository.reservePresent(presentId, userId);
    }

    public boolean cancelReservation(int presentId, int userId) {
        return repository.cancelReservation(presentId, userId);
    }

    public List<Wishlist> getWishlistsByUserId(int userId){
        return repository.getWishlistByUserId(userId);
    }

}
