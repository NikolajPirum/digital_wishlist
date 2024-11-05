package org.example.digital_wishlist.model;

public class Present {
    private static int id;
    private static int price;
    private static String name;

    public Present(int id, int price, String name) {
        this.id = id;
        this.price = price;
        this.name = name;
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
