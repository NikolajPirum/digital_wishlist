package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // rowMapper til at skabe wishList objekter ud af ResultSets
    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> new Wishlist(
            rs.getInt("id"),
            rs.getString("listName")
    );

    // rowMapper til at skabe present objekter ud af ResultSets (rs)
    private final RowMapper<Present> presentRowMapper = (rs, rowNum) -> new Present(
            rs.getInt("id"),
            rs.getInt("price"),
            rs.getString("name")
    );

    public void createUser(User user1){
        String query = "insert into AppUser (name, email, username, password) values (?, ?, ?, ?)";
        jdbcTemplate.update(query, user1.getName(), user1.getEmail(), user1.getUsername(), user1.getPassword());
    }

    public void deleteUser(int id){
        String query = "delete from Appuser where id = ?";
        jdbcTemplate.update(query, id);
    }

    public int deleteWish(int id){
        String query = "delete from present where id = ?";
        return jdbcTemplate.update(query, id);
    }

    // skal også have link i databasen
    public void addWish(Present present){
        String query = "insert into present (id,name,price,link) values (?, ?, ?, ?)";
        jdbcTemplate.update(query, Present.getId(), Present.getName(), Present.getPrice(), Present.getLink());
    }

    public List<Present> getPresentsByWishListId(int wishListId){
        String query = "select * from present where wishlistId = ?";
        return jdbcTemplate.query(query, presentRowMapper, wishListId);

    }

    public Wishlist getWishlist(int id){
        String query = "select * from wishlist where id = ?";
        return jdbcTemplate.queryForObject(query, wishlistRowMapper, id);
    }

    public List<Wishlist> getAllWishLists(){
        String query = "select * from wishlist";
        return jdbcTemplate.query(query, wishlistRowMapper);
    }

    public void createWishlist( String wishlistName, int userId){
        // code to createWishlist
        String query = "INSERT INTO wishlist(listName, userID) VALUES (?, ?)";
        jdbcTemplate.update(query, wishlistName, userId);
    }
    /*
    public readUser(){
        // code to readUser
    }
*//*
    public List<Present> viewWishlists(int wishlistId){
        // code to readWishlist
        String query = "select * from present where wishlistId = ?";
    }
/*
    public updateWishlist(){
        // code to updateWishlist
    }

    public deleteWishlist(){
        // code to deleteWishlist
    }
    */

    public boolean findByUsername(String username) {
        String query = "SELECT COUNT(*) FROM AppUser WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, username);
        return count != null && count > 0;
    }

    public User findUser(String username) {
        String query = "SELECT * FROM AppUser WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), username);
        } catch (EmptyResultDataAccessException e) {
            return null; // or handle according to your application's needs
        }
    }

    // Using JdbcTemplate to check if email exists
    public boolean findUserByEmail(String email) {
        String query = "SELECT COUNT(*) FROM AppUser WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, email);
        return count != null && count > 0;
    }
}