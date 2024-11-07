package org.example.digital_wishlist.model;

import java.util.ArrayList;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private String username;
    private ArrayList<Present> reserveList = new ArrayList<>();
    private String wishlistID;

    public User(String name, String email, String password, String username) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
    }
    public User(String name, String email, String password, String username, ArrayList<Present> reserveList) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.username = username;
        this.reserveList = reserveList;
    }
    public User(){

    }
    public int getId() {return id;}

    public void setId(int id) {id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Present> getReserveList() {
        return reserveList;
    }

    public void setReserveList(ArrayList<Present> reserveList) {
        this.reserveList = reserveList;
    }

    public String getWishlistID() { return wishlistID; }

    public void setWishlistID(String wishlistID) { this.wishlistID = wishlistID; }
}
