package org.example.digital_wishlist.repository;

import org.example.digital_wishlist.model.User;
import org.example.digital_wishlist.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;

@org.springframework.stereotype.Repository
public class wishlistRepository {

    private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public createWishlist(Wishlist wishlist, User user){
        // code to createWishlist
        /*String query = "INSERT INTO wishlist (userid, wishlistid) VALUES (?, ?)";

        try(Connection con = DriverManager.getConnection(url, username, password)){
            try(PreparedStatement statement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){
                statement.setString(1,wishlist.getListName());
                if (wishlist.getUserID() != null){
                    statement.setInt(2,wishlist.getWishlistID());
                } else {
                    statement.setNull(2,Types.INTEGER);
                }
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }*/
        String sql = "INSERT INTO Wishlist(Wishlistname, userID) VALUES(?,?)";
        return jdbcTemplate.update(sql, wishlist.setListName(), user.getID)

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

    public deleteUser(){
        // code to deleteUser
    }

    public deleteWishlist(){
        // code to deleteWishlist
    }

    public Repository() {
    }
}
