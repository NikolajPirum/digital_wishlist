package org.example.digital_wishlist.model;

import java.util.ArrayList;

public class User {

    private static String name;
    private static String email;
    private static String password;
    private static String username;
    private ArrayList<Present> reserveList = new ArrayList<>();

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

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getUsername() {
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

    public void setId(int id) {
    }
}
