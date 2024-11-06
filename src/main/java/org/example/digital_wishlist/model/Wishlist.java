package org.example.digital_wishlist.model;

import java.util.ArrayList;

public class Wishlist {
    private int WishlistID;
    private String Wishlistname;
    private ArrayList<Present> PresentList = new ArrayList<>();
    private boolean reserv = false;
    private int userId;

    public Wishlist(String listName, ArrayList<Present> PresentList, boolean isReserv) {
        this.listName = listName;
        this.PresentList = PresentList;
        this.reserv = isReserv;
    }

    public Wishlist(int id, String listName){
        this.id = id;
        this.listName = listName;
    }

    public Wishlist(String listName, int userId, ArrayList<Present> Presentlist){
        this.listName = listName;
        this.userId = userId;
        this.PresentList = Presentlist;
    }
    public Wishlist(){

    }
    public int getWishlistID() {return wishlistID;}

    public int getId() {
        return WishlistID;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() { return userId; }

    public void setUserID(int userID) { this.userId = userID; }

}
