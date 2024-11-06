package org.example.digital_wishlist.model;

public class Present {
    private String name;
    private int id;
    private int price;
    private String link;
    private String brand;
    private String presentname;
    private Integer wishListId;

    public Present(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.presentname = name;
    }

    public Present(){

    }

    public String getPresentname() {
        return presentname;
    }

    public String getBrand() {
        return brand;
    }

    public void setPresentname(String presentname) {
        presentname = presentname;
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

    public void setWishListId(Integer wishListId) {
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

    public String getName() {
        return presentname;
    }

    public void setName(String name) {
        this.presentname = name;
    }

}
