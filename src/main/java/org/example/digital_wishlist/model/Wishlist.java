package org.example.digital_wishlist.model;

import java.util.ArrayList;

public class Wishlist {
    private Integer wishlistID;
    private String listName;
    private ArrayList<Present> PresentList = new ArrayList<>();
    private boolean reserv = false;
    private int userId;
    private String userName;


    public Wishlist(String listName, ArrayList<Present> PresentList, boolean isReserv) {
        this.listName = listName;
        this.PresentList = PresentList;
        this.reserv = isReserv;
    }
    public Wishlist(int wishlistID, String listName, int userId){
        this.wishlistID = wishlistID;
        this.listName = listName;
        this.userId = userId;
    }

    public Wishlist(int WishlistID,String listName, ArrayList<Present> PresentList, boolean isReserv, int userId) {
        this.wishlistID = WishlistID;
        this.listName = listName;
        this.PresentList = PresentList;
        this.reserv = isReserv;
        this.userId = userId;

    }
    public Wishlist(int wishlistID, String listName){
        this.wishlistID = wishlistID;
        this.listName = listName;
    }

    public Wishlist(String listName, int userId, ArrayList<Present> Presentlist){
        this.listName = listName;
        this.userId = userId;
        this.PresentList = Presentlist;
    }
    public Wishlist(){

    }
    public Wishlist(int wishlistId, String listName, String userName){
        this.listName = listName;
        this.wishlistID = wishlistId;
        this.userName = userName;
    }
    public int getWishlistID() {return wishlistID;}

    public int getId() {
        return wishlistID;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public ArrayList<Present> getPresentList() {
        return PresentList;
    }

    public void setPresentList(ArrayList<Present> presentList) {
        PresentList = presentList;
    }

    public boolean isReserv() {
        return reserv;
    }

    public void setReserv(boolean reserv) {
        this.reserv = reserv;
    }

    public int getUserID() { return userId; }

    public void setUserID(int userID) { this.userId = userID; }

    public String getUserName() {return userName;}

    public void setUserName(String userName) {
        this.userName = userName;
    }

}