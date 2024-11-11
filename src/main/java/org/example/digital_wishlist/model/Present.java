package org.example.digital_wishlist.model;

public class Present {
    private int id;
    private String presentName;
    private int price;
    private String link;
    private String brand;
    private int wishListId;

    public Present(int id, int price, String name, String link) {
        this.id = id;
        this.price = price;
        this.presentName = name;
        this.link = link;
    }

    public Present(){

    }
    public Present(String name, int price, String link){
        this.presentName = name;
        this.price = price;
        this.link = link;
    }

    public String getPresentName() {
        return presentName;
    }
    public void setPresentName(String presentName) {
        this.presentName = presentName;
    }
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
