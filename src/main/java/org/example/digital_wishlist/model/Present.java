package org.example.digital_wishlist.model;

public class Present {
    private int id;
    private int price;
    private String name;
    private String brand;
    private String link;
    private String status;

    public Present(int id, int price, String name,  String brand, String link, String status) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.brand = brand;
        this.link = link;
        this.status = status;
    }

    public Present(){

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
