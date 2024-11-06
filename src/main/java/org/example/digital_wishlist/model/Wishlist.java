package org.example.digital_wishlist.model;

import java.util.ArrayList;

public class Wishlist {
    private int wishlistID;
    private String wishlistName;
    private ArrayList<Present> PresentList = new ArrayList<>();
    private boolean reserv = false;
    private int wishlistId;
    private String userName;


    public Wishlist(String listName, ArrayList<Present> PresentList, boolean isReserv) {
        this.wishlistName = listName;
        this.PresentList = PresentList;
        this.reserv = isReserv;
    }

    public Wishlist(int id, String listName){
        this.wishlistID = id;
        this.wishlistName = listName;
    }

    public Wishlist(){

    }
    public Wishlist(int wishlistId, String wishlistName, String userName){
        this.wishlistName = wishlistName;
        this.wishlistId = wishlistId;
        this.userName = userName;
    }

    public int getId() {
        return wishlistID;
    }

    public String getListName() {
        return wishlistName;
    }

    public void setListName(String listName) {
        this.wishlistName = listName;
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
}
