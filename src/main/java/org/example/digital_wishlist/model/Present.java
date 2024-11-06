package org.example.digital_wishlist.model;

public class Present {
    private static int id;
    private static int price;
    private static String link;
    private static String name;
    private Integer wishListId;

    public Present(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public Present(){

    }

    public static String getLink() {
        return link;
    }

    public static void setLink(String link) {
        Present.link = link;
    }

    public Integer getWishListId() {
        return wishListId;
    }

    public void setWishListId(Integer wishListId) {
        this.wishListId = wishListId;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
