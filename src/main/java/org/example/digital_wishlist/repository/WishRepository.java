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
    // Reserve a present if it's not already reserved by another user
    public boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM AppUser WHERE Username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    // Authenticate a user
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM AppUser WHERE Username = ? AND Password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (EmptyResultDataAccessException e) {
            return null; // No user found with this username/password
        }
    }


    // Get presents in a wishlist
    public List<Present> getPresentsForWishlist(int wishlistId) {
        String query = "SELECT p.PresentID, p.Presentname, p.Brand, p.Price, p.Link, " +
                "CASE WHEN r.PresentID IS NULL THEN 'Available' ELSE 'Reserved' END AS Status " +
                "FROM Present p " +
                "LEFT JOIN Reserve r ON p.PresentID = r.PresentID " +
                "WHERE p.WishlistID = ?";
        return jdbcTemplate.query(query, new Object[]{wishlistId}, (rs, rowNum) -> {
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




    // Reserve a present
    public boolean reservePresent(int presentId, int userId) {
        String checkSql = "SELECT COUNT(*) FROM Reserve WHERE PresentID = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, presentId);

        if (count > 0) {
            return false; // Already reserved
        }

        String reserveSql = "INSERT INTO Reserve (PresentID, UserID) VALUES (?, ?)";
        jdbcTemplate.update(reserveSql, presentId, userId);
        return true;
    }

    // Cancel a reservation
    public boolean cancelReservation(int presentId, int userId) {
        String query = "DELETE FROM Reserve WHERE PresentID = ? AND UserID = ?";
        int rowsAffected = jdbcTemplate.update(query, presentId, userId);
        return rowsAffected > 0; // Returns true if a reservation was canceled
    }

}
