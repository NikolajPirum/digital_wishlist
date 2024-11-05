package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user1){
        String query = "insert into User (name, email, username, password) values (?, ?, ?, ?)";
        jdbcTemplate.update(query, user1.getName(), user1.getEmail(), user1.getUsername(), user1.getPassword());
    }

    public void deleteUser(int id){
        String query = "delete from user where id = ?";
        jdbcTemplate.update(query, id);
    }

    public void deleteWish(int id){
        String query = "delete from present where id = ?";
        jdbcTemplate.update(query, id);
    }

    public void addWish(Present present){
        String query = "insert into present (id,name,price) values (?, ?, ?)";
        jdbcTemplate.update(query, present.getId(), present.getName(), present.getPrice());
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

    public deleteWishlist(){
        // code to deleteWishlist
    }
    */

    public boolean findByUsername(String username) {
        String query = "SELECT COUNT(*) FROM User WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, username);
        return count != null && count > 0;
    }

    public User findUser(String username) {
        String query = "SELECT * FROM User WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null; // or handle according to your application's needs
        }
    }

    // Using JdbcTemplate to check if email exists
    public boolean findUserByEmail(String email) {
        String query = "SELECT COUNT(*) FROM User WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, email);
        return count != null && count > 0;
    }
}