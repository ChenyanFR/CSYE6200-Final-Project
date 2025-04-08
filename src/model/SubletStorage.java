package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubletStorage {
	
	
    private static final ObservableList<SubletListing> listings = FXCollections.observableArrayList();

    static {
        listings.add(new SubletListing("Near Northeastern University", "Boston", 900.0, "Close to campus", "short", "@../resources/images/sample.webp"));
        listings.add(new SubletListing("Waterfront park 1 private room", "Cambridge", 1200.0, "Furnished and quiet","short"));
        listings.add(new SubletListing("Luxury Flat #1", "Cambridge", 1998.77, "Close to campus","short"));
        listings.add(new SubletListing("Sunny Room #2", "Allston", 1201.78, "Newly renovated","short"));
        listings.add(new SubletListing("Downtown Apartment #3", "Brookline", 2203.38, "Utilities included", "long"));
        listings.add(new SubletListing("Shared Room #4", "Cambridge", 2443.92, "Newly renovated","short"));
        listings.add(new SubletListing("Private Suite #5", "Allston", 1725.98, "Close to campus", "long"));
        listings.add(new SubletListing("Luxury Flat #6", "Brookline", 1981.16, "Utilities included","short"));
        listings.add(new SubletListing("Luxury Flat #7", "Fenway", 1766.83, "Close to campus","short"));
        listings.add(new SubletListing("Private Suite #8", "Brookline", 1657.47, "Furnished and quiet","short"));
        listings.add(new SubletListing("Downtown Apartment #9", "Allston", 1649.07, "Pet friendly", "long"));
        listings.add(new SubletListing("Shared Room #10", "Cambridge", 2258.91, "Furnished and quiet","short"));
        listings.add(new SubletListing("Private Suite #11", "Allston", 1012.26, "Close to campus","short"));
        listings.add(new SubletListing("Downtown Apartment #12", "Allston", 2214.74, "Newly renovated","short"));
        listings.add(new SubletListing("Downtown Apartment #13", "Fenway", 1466.15, "Newly renovated", "long"));
        listings.add(new SubletListing("Modern Loft #14", "Allston", 1309.29, "Utilities included","short"));
        listings.add(new SubletListing("Shared Room #15", "Cambridge", 2482.2, "Furnished and quiet","short"));
        listings.add(new SubletListing("Modern Loft #16", "Somerville", 2276.64, "Newly renovated", "long"));
        listings.add(new SubletListing("Spacious Condo #17", "Cambridge", 2207.98, "Pet friendly", "long"));
        listings.add(new SubletListing("Sunny Room #18", "Allston", 1963.3, "Utilities included", "long"));
        listings.add(new SubletListing("Shared Room #19", "Brookline", 1096.87, "Utilities included", "long"));
        listings.add(new SubletListing("Modern Loft #20", "Fenway", 2317.82, "Newly renovated", "long"));
        listings.add(new SubletListing("Shared Room #21", "Allston", 1786.67, "Utilities included", "long"));
        listings.add(new SubletListing("Luxury Flat #22", "Fenway", 1674.33, "Newly renovated", "long"));
        listings.add(new SubletListing("Luxury Flat #23", "Somerville", 1383.12, "Pet friendly", "long"));
        listings.add(new SubletListing("Sunny Room #24", "Brookline", 1297.28, "Pet friendly", "long"));
        listings.add(new SubletListing("Sunny Room #25", "Fenway", 835.65, "Pet friendly", "long"));
        listings.add(new SubletListing("Private Suite #26", "Allston", 1662.52, "Utilities included","short"));
        listings.add(new SubletListing("Stylish Unit #27", "Brookline", 1874.07, "Utilities included","short"));
        listings.add(new SubletListing("Private Suite #28", "Fenway", 1411.4, "Newly renovated","short"));
        listings.add(new SubletListing("Modern Loft #29", "Cambridge", 2054.74, "Close to campus","short"));
        listings.add(new SubletListing("Downtown Apartment #30", "Brookline", 1657.75, "Utilities included","short"));
        listings.add(new SubletListing("Modern Loft #31", "Fenway", 1835.76, "Utilities included", "long"));
        listings.add(new SubletListing("Luxury Flat #32", "Fenway", 1167.58, "Furnished and quiet", "long"));
        listings.add(new SubletListing("Modern Loft #33", "Brookline", 845.36, "Furnished and quiet", "long"));
        listings.add(new SubletListing("Quiet Basement #34", "Fenway", 1967.92, "Utilities included", "long"));
        listings.add(new SubletListing("Downtown Apartment #35", "Cambridge", 1895.31, "Pet friendly","short"));
        listings.add(new SubletListing("Modern Loft #36", "Somerville", 1897.29, "Close to campus", "long"));
        listings.add(new SubletListing("Modern Loft #37", "Cambridge", 1676.84, "Newly renovated", "long"));
        listings.add(new SubletListing("Quiet Basement #38", "Brookline", 1714.59, "Close to campus", "long"));
        listings.add(new SubletListing("Shared Room #39", "Fenway", 2277.81, "Newly renovated", "long"));
        listings.add(new SubletListing("Stylish Unit #40", "Fenway", 1440.9, "Pet friendly", "long"));
        listings.add(new SubletListing("Spacious Condo #41", "Fenway", 1340.5, "Pet friendly","short"));
        listings.add(new SubletListing("Downtown Apartment #42", "Somerville", 2072.13, "Furnished and quiet","short"));
        listings.add(new SubletListing("Shared Room #43", "Somerville", 1605.99, "Close to campus","short"));
        listings.add(new SubletListing("Spacious Condo #44", "Cambridge", 2074.18, "Newly renovated","short"));
        listings.add(new SubletListing("Stylish Unit #45", "Cambridge", 1611.25, "Close to campus","short"));
        listings.add(new SubletListing("Stylish Unit #46", "Fenway", 1682.5, "Pet friendly","short"));
        listings.add(new SubletListing("Stylish Unit #47", "Allston", 1992.06, "Newly renovated","short"));
        listings.add(new SubletListing("Spacious Condo #48", "Somerville", 2113.67, "Newly renovated","short"));
        listings.add(new SubletListing("Spacious Condo #49", "Brookline", 1382.62, "Close to campus","short"));
        listings.add(new SubletListing("Stylish Unit #50", "Brookline", 1767.7, "Pet friendly","short"));
    }


    public static ObservableList<SubletListing> getListings() {
        return listings;
    }

    public static void addListing(SubletListing listing) {
        listings.add(listing);
    }
}

