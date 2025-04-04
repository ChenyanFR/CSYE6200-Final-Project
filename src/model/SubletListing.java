package model;

public class SubletListing {
    private String title;
    private String location;
    private double price;
    private String description;
    private String subletMode;
    private String region;

    public SubletListing(String title, String location, double price, String description, String subletMode) {
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
        this.subletMode = subletMode;
    }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getSubletMode() {return subletMode;}
    public String getRegion() {return region;}

    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setSubletMode(String subletMode) {this.subletMode = subletMode;}
}
