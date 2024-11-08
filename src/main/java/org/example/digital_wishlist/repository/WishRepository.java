package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;



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

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> new Wishlist(
            rs.getInt("WishlistID"),
            rs.getString("Wishlistname")
    );

    // rowMapper til at skabe present objekter ud af ResultSets (rs)
    private final RowMapper<Present> presentRowMapper = (rs,rowNum) -> new Present(
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

    public List<Present> getPresentsByWishListId(int id){
        String query = "select * from present where WishlistID = ?";
        return jdbcTemplate.query(query, presentRowMapper, id);
    }

    public Wishlist getWishlist(int id){
        String query = "select * from wishlist where WishlistID = ?";
        return jdbcTemplate.queryForObject(query, wishlistRowMapper, id);
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
            // Check that presentId and userId are valid
            String checkPresentSql = "SELECT COUNT(*) FROM Present WHERE presentid = ?";
            String checkUserSql = "SELECT COUNT(*) FROM AppUser WHERE userId = ?";

            Integer presentCount = jdbcTemplate.queryForObject(checkPresentSql, Integer.class, presentId);
            Integer userCount = jdbcTemplate.queryForObject(checkUserSql, Integer.class, userId);

            if (presentCount == null || presentCount == 0) {
                throw new IllegalArgumentException("Invalid present ID: " + presentId);
            }

            if (userCount == null || userCount == 0) {
                throw new IllegalArgumentException("Invalid user ID: " + userId);
            }

            // Insert into reserve table if both IDs are valid
            String sql = "INSERT INTO reserve (presentid, userid) VALUES (?, ?)";
            int rowsAffected = jdbcTemplate.update(sql, presentId, userId);
            return rowsAffected > 0;

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

    public User findUserById(int id) {
        String query = "SELECT * FROM AppUser WHERE UserID = ?";
        try {
            return jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(User.class), id);
        } catch (EmptyResultDataAccessException e) {
            System.out.println("User not found for ID: " + id); // Debugging line
            return null;
        }
    }

    public List<Present> getPresents(int wishlistId) {
        String sql = "SELECT * FROM Present WHERE wishlistId = ?";
        return jdbcTemplate.query(sql, new Object[]{wishlistId}, (rs, rowNum) -> {
            Present present = new Present();
            present.setId(rs.getInt("id"));
            present.setName(rs.getString("name"));
            present.setLink(rs.getString("link"));
            present.setPrice(rs.getInt("price"));
            present.setBrand(rs.getString("brand"));
            return present;
        });
    }

    public List<Integer> getReservedPresentIds(int wishlistId) {
        String sql = "SELECT reserveID FROM Reserve WHERE reserveid IN (SELECT presentid FROM Present WHERE wishlistId = ?)";
        List<Integer> reservedIds = jdbcTemplate.queryForList(sql, new Object[]{wishlistId}, Integer.class);
        System.out.println("Reserved Present IDs for wishlist " + wishlistId + ": " + reservedIds); // Debugging output
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
}
