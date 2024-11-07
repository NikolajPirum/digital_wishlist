package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // rowMapper til at skabe wishList objekter ud af ResultSets
    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> new Wishlist(
            rs.getInt("WishlistID"),
            rs.getString("Wishlistname")
    );

    // rowMapper til at skabe present objekter ud af ResultSets (rs)
    private final RowMapper<Present> presentRowMapper = (rs, rowNum) -> new Present(
            rs.getInt("PresentID"),
            rs.getInt("Price"),
            rs.getString("Presentname"),
            rs.getString("Link")
    );

    public void createUser(User user1){
        String query = "insert into AppUser (name, email, username, password) values (?, ?, ?, ?)";
        jdbcTemplate.update(query, user1.getName(), user1.getEmail(), user1.getUsername(), user1.getPassword());
    }

    public void deleteUser(int id){
        String query = "delete from AppUser where id = ?";
        jdbcTemplate.update(query, id);
    }

    public int deleteWish(int id){
        String query = "delete from Present where PresentID = ?";
        return jdbcTemplate.update(query, id);
    }

    // skal også have link i databasen
    public void addWish(Present present){
        String query = "insert into Present (Presentname,Price,Link, Brand, WishlistID) values (?, ?, ?,?,?)";
        jdbcTemplate.update(query, present.getName(), present.getPrice(), present.getLink(), present.getBrand(), present.getWishListId());
    }

    public List<Wishlist> getAllWishLists(){
        String query = "select * from wishlist";
        return jdbcTemplate.query(query, wishlistRowMapper);
    }

    public void createWishlist(Wishlist wishlist){
        // code to createWishlist
        String query = "INSERT INTO wishlist(Wishlistname) VALUES (?)";
        jdbcTemplate.update(query, wishlist.getListName());
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
    public User findUserById(int id) {
        String query = "SELECT * FROM AppUser WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null; // or handle according to your application's needs
        }
    }
}