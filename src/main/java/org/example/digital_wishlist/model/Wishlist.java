package org.example.digital_wishlist.model;

import java.util.ArrayList;

public class Wishlist {
    private int wishlistID;
    private String listName;
    private ArrayList<Present> PresentList = new ArrayList<>();
    private boolean reserv = false;
    private int userID;

    public Wishlist(String listName, ArrayList<Present> PresentList, boolean isReserv) {
        this.listName = listName;
        this.PresentList = PresentList;
        this.reserv = isReserv;
    }

    public Wishlist(){

    }
    public int getWishlistID() {return wishlistID;}

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

    public int getUserID() { return userID; }

    public void setUserID(int userID) { this.userID = userID; }
}
