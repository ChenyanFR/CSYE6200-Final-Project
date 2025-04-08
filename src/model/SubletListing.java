package model;

public class SubletListing {
    private String title;
    private String location;
    private double price;
    private String description;
    private String subletMode;
    private String region;
    private String imagePath;

    public SubletListing(String title, String location, double price, String description, String subletMode, String imagePath) {
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
        this.subletMode = subletMode;
        this.imagePath = imagePath;
    }
    
    public SubletListing(String title, String location, double price, String description, String subletMode) {
        this.title = title;
        this.location = location;
        this.price = price;
        this.description = description;
        this.subletMode = subletMode;
        this.imagePath = null;
    }

    public String getTitle() { return title; }
    public String getLocation() { return location; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getSubletMode() {return subletMode;}
    public String getRegion() {return region;}
    public String getImagePath() { return imagePath; }

    public void setTitle(String title) { this.title = title; }
    public void setLocation(String location) { this.location = location; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
    public void setSubletMode(String subletMode) {this.subletMode = subletMode;}
    public void setImagePath(String imagePath) {this.imagePath = imagePath;}
}
