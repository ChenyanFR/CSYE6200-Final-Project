package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SubletStorage {
    private static final ObservableList<SubletListing> listings = FXCollections.observableArrayList();

    static {
        listings.add(new SubletListing("Near Northeastern University", "Boston", 900.0, "Close to campus"));
        listings.add(new SubletListing("Waterfront park 1 private room", "Cambridge", 1200.0, "Furnished and quiet"));
    }

    public static ObservableList<SubletListing> getListings() {
        return listings;
    }

    public static void addListing(SubletListing listing) {
        listings.add(listing);
    }
}
