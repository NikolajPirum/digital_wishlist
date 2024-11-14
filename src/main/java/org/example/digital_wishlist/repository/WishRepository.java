package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.util.List;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> new Wishlist(
            rs.getInt("WishlistId"),
            rs.getString("Wishlistname"),
            rs.getInt("userId")
    );

    // rowMapper til at skabe present objekter ud af ResultSets (rs)
    private final RowMapper<Present> presentRowMapper = (rs,rowNum) -> new Present(
            rs.getInt("PresentID"),
            rs.getInt("Price"),
            rs.getString("Presentname"),
            rs.getString("Link")
    );

    public void createUser(User user){
        String query = "insert into AppUser (Name, Username, Password, Email) values (?, ?, ?, ?)";

        int rowsAffected = jdbcTemplate.update(
                query,
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
        if (rowsAffected != 1) {
            throw new RuntimeException("Failed to create user: " + user);
        }
    }

    public int deleteWish(int id){
        String query = "delete from Present where PresentID = ?";
        return jdbcTemplate.update(query, id);
    }

    // skal også have link i databasen
    public void addWish(Present present){
        String query = "insert into Present (Presentname,Price,Link, Brand, WishlistID) values (?, ?, ?,?,?)";
        jdbcTemplate.update(query, present.getPresentName(), present.getPrice(), present.getLink(), present.getBrand(), present.getWishListId());
    }

    public List<Wishlist> getAllWishLists(){
        String query = "select * from wishlist";
        return jdbcTemplate.query(query, wishlistRowMapper);
    }

    public void createWishlist(Wishlist wishlist){
        // code to createWishlist
        String query = "INSERT INTO wishlist(Wishlistname, UserID) VALUES (?, ?)";
        jdbcTemplate.update(query, wishlist.getListName(), wishlist.getUserID());
    }

    public List<Present> getPresentsByWishlistId(int id){
        String query = "select * from present where WishlistID = ?";
        return jdbcTemplate.query(query, presentRowMapper, id);
    }

    public int updateWishlist(Wishlist wishlist) {
        String query = "UPDATE Wishlist SET Wishlistname = ? WHERE WishlistID = ?";
        //returnere int int er antallet af rækker, der blev påvirket af forespørgslen
        return jdbcTemplate.update(query, wishlist.getListName(), wishlist.getWishlistID());
    }

    public List<Wishlist> getWishlistByUserId(int id){
        String query = "select * from wishlist where UserID = ?";
        return jdbcTemplate.query(query,wishlistRowMapper, id);
    }

    public List<Present> getPresentsByWishListId(int id){
        String query = "select * from present where WishlistID = ?";
        return jdbcTemplate.query(query, presentRowMapper, id);
    }

    public int updatePresent(Present present) {
        String query = "UPDATE Present SET Presentname =?, Price =?, Link =? WHERE PresentID= ?";
        return jdbcTemplate.update(query,
                present.getPresentName(),
                present.getPrice(),
                present.getLink(),
                present.getId()
        );
    }

    public Wishlist findWishlistByUsername(User user, Wishlist wishlist) {
        String query = "select * from Wishlist where UserId = ?";
        return jdbcTemplate.queryForObject(query, wishlistRowMapper, user.getUsername());
    }

    public Wishlist getWishlist(int id){
        String query = "select * from WishList where WishlistID = ?";
        Wishlist wishlist =  jdbcTemplate.queryForObject(query, wishlistRowMapper, id);
        return wishlist;
    }


    public boolean findByUsername(String username) {
        String query = "SELECT COUNT(*) FROM AppUser WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, username);
        return count != null && count > 0;
    }

    public User findUser(String username) {
        String query = "SELECT * FROM AppUser WHERE Username = ?";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getInt("UserID")); // Map UserID column to userId field
            user.setName(rs.getString("Name"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("Password"));
            user.setUsername(rs.getString("Username"));
            return user;
        }, username);
    }



    // Using JdbcTemplate to check if email exists
    public boolean findUserByEmail(String email) {
        String query = "SELECT COUNT(*) FROM AppUser WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, email);
        return count != null && count > 0;
    }

    public boolean reservePresent(int presentId, int userId) {
        try {
            String sql = "INSERT INTO reserve (presentid, userid) VALUES (?, ?)";
            jdbcTemplate.update(sql, presentId, userId);
            return true;
        } catch (DuplicateKeyException e) {
            System.out.println("Reservation already exists for presentId: " + presentId + " and userId: " + userId);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean cancelReservation(int presentId, int userId) {
        String sql = "DELETE FROM reserve WHERE presentid = ? AND userid = ?";
        int rowsAffected = jdbcTemplate.update(sql, presentId, userId);
        return rowsAffected > 0;  // Return true if the reservation was canceled
    }

    public List<Integer> getReservedPresentIds(int wishlistId) {
        String sql = "SELECT PresentID FROM Reserve WHERE PresentID IN (SELECT PresentID FROM Present WHERE WishlistID = ?)";
        List<Integer> reservedIds = jdbcTemplate.queryForList(sql, Integer.class, wishlistId);
        System.out.println("Reserved Present IDs for wishlist " + wishlistId + ": " + reservedIds);
        return reservedIds;
    }

    public Integer getWishlistIdByPresentId(int presentId) {
        String sql = "SELECT WishlistID FROM Present WHERE PresentID = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, presentId);
        } catch (EmptyResultDataAccessException e) {
            // Handle case where presentId does not exist
            System.err.println("Present ID " + presentId + " does not exist.");
            return null;
        }
    }

    public Present getPresentById(int id) {
        String query = "SELECT * FROM Present WHERE PresentID = ?";
        return jdbcTemplate.queryForObject(query, presentRowMapper, id);
    }

    public void deleteReserveByWishlistId(Integer wishlistId) {
        String query = "DELETE FROM Reserve WHERE PresentID IN (SELECT PresentID FROM Present WHERE WishlistID = ?)";
        jdbcTemplate.update(query, wishlistId);
    }

    public void deletePresentByWishlistId(Integer wishlistId) {
        String query = "DELETE FROM Present WHERE WishlistID = ?";
        jdbcTemplate.update(query, wishlistId);
    }

    public void deleteWishlistById(Integer wishlistId) {
        String query = "DELETE FROM Wishlist WHERE WishlistID = ?";
        jdbcTemplate.update(query, wishlistId);
    }
}
