package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user1){
        String query = "insert into AppUser (name, email, username, password) values (?, ?, ?, ?)";
        jdbcTemplate.update(query, user1.getName(), user1.getEmail(), user1.getUsername(), user1.getPassword());
    }

    public void deleteUser(int id){
        String query = "delete from Appuser where id = ?";
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
    public boolean reservePresent(int presentId, int userId) {
        String checkAvailabilityQuery = "SELECT COUNT(*) FROM Reserve WHERE PresentID = ? AND UserID != ?";
        String reservePresentQuery = "INSERT INTO Reserve (PresentID, UserID) VALUES (?, ?)";

        // Check if the present is already reserved
        int count = jdbcTemplate.queryForObject(checkAvailabilityQuery, Integer.class, presentId, userId);

        if (count > 0) {
            // The present is already reserved by another user, cannot reserve it
            return false;
        }

        // Otherwise, proceed to reserve the present
        jdbcTemplate.update(reservePresentQuery, presentId, userId);
        return true;
    }
    public List<Present> getPresentsForWishlist(int wishlistId) {
        String query = "SELECT p.PresentID, p.Presentname, p.Brand, p.Price, p.Link, " +
                "       CASE WHEN r.PresentID IS NOT NULL THEN 'Reserved' ELSE 'Available' END AS Status " +
                "FROM Present p " +
                "LEFT JOIN Reserve r ON p.PresentID = r.PresentID " +
                "WHERE p.WishlistID = ?";

        return jdbcTemplate.query(query, new Object[] { wishlistId }, (rs, rowNum) -> {
            Present present = new Present();
            present.setId(rs.getInt("PresentID"));
            present.setName(rs.getString("Presentname"));
            present.setBrand(rs.getString("Brand"));
            present.setPrice(rs.getInt("Price"));
            present.setLink(rs.getString("Link"));
            present.setStatus(rs.getString("Status"));
            return present;
        });
    }

    // Method to cancel a reservation
    public boolean cancelReservation(int presentId, int userId) {
        String query = "DELETE FROM Reserve WHERE PresentID = ? AND UserID = ?";
        int rowsAffected = jdbcTemplate.update(query, presentId, userId);
        return rowsAffected > 0; // Returns true if the reservation was successfully canceled
    }

    public List<Present> getAllPresents() {
        String query = "SELECT p.PresentID, p.Presentname, p.Brand, p.Price, p.Link, " +
                "       CASE WHEN r.PresentID IS NOT NULL THEN 'Reserved' ELSE 'Available' END AS Status " +
                "FROM Present p " +
                "LEFT JOIN Reserve r ON p.PresentID = r.PresentID";

        // Fetch data from the database and return it as a list of Present objects
        try {
            return jdbcTemplate.query(query,(rs,rowNum) -> {
                Present present = new Present();
                present.setId(rs.getInt("PresentID"));
                present.setName(rs.getString("Presentname"));
                present.setBrand(rs.getString("Brand"));
                present.setPrice(rs.getInt("Price"));
                present.setLink(rs.getString("Link"));
                present.setStatus(rs.getString("Status"));
                return present;
            });
        }
        catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}