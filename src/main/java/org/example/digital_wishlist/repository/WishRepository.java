package org.example.digital_wishlist.repository;
import org.example.digital_wishlist.model.Present;
import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(User user){
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

    public int updateWishlist(Wishlist wishlist){
        String query = "UPDATE Wishlist SET name =?, WHERE wishlist_id =?";
        //returnere int, som er antallet af rækker, der blev påvirket af forespørgslen
        return jdbcTemplate.update(query, wishlist.getListName(), wishlist.getWishlistId());
        // code to updateWishlist
    }

    public deleteWishlist(){
        // code to deleteWishlist
    }

    public User findUsername(String username) {
        String query = "select * from users where username = ?";

    }

    public boolean findByUsername(String username) {
        boolean user_exist = false;
        try (Connection connection = DriverManager.getConnection(PROD_DATABASE_URL, PROD_USERNAME, PROD_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    user_exist = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_exist; // Return null if no user was found
    }
    public boolean findUserByEmail(String email) {
        boolean user_exist = false;
        try (Connection connection = DriverManager.getConnection(PROD_DATABASE_URL, PROD_USERNAME, PROD_PASSWORD)) {
            String sql = "SELECT * FROM users WHERE username = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    user_exist = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user_exist; // Return null if no user was found
    }
}