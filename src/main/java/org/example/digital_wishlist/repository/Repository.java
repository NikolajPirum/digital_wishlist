package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.stereotype.Repository
public class Repository {

    private final JdbcTemplate jdbcTemplate;

    public Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int createUser(User user){
        String query = "insert into users (name, email, username, password) values (?, ?, ?, ?)";
        jdbcTemplate.update(query, User.getName(), User.getEmail(), User.getUsername(), User.getPassword());
    }

    public int deleteUser(int id){
        String query = "delete from users where id = ?";
        jdbcTemplate.update(query, id);
    }

    public int deleteWish(int id){
        String query = "delete from present where id = ?";
        jdbcTemplate.update(query, id);
    }

    public int addWish(Present present){
        String query = "insert into present (id,name,price) values (?, ?, ?)";
        jdbcTemplate.update(query, Present.getId(), Present.getName(), Present.getPrice());
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
