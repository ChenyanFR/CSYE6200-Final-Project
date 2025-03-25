//package CSYE6200-Final-Project.model;

package model;

public class SubletListing {
    private String title;
    private String location;
    private double price;
    private String description;

    public SubletListing(String title, String location, double price, String description) {
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }

    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
}
