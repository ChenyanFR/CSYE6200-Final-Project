package model;

import java.util.HashMap;
import java.util.Map;

public class SubletClickTracker {
    private static final Map<SubletListing, Integer> clickCounts = new HashMap<>();

    public static void registerClick(SubletListing listing) {
        clickCounts.put(listing, clickCounts.getOrDefault(listing, 0) + 1);
    }

    public static Map<SubletListing, Integer> getClickCounts() {
        return clickCounts;
    }
    static {
        SubletListing cambridgeRoom = new SubletListing("Sunny Room in Cambridge", "Cambridge", 1200.0, "Close to Harvard", "short");
        SubletListing allstonFlat = new SubletListing("Modern Flat in Allston", "Allston", 1500.0, "Furnished + All Utilities", "long");

        SubletClickTracker.registerClick(cambridgeRoom);
        SubletClickTracker.registerClick(cambridgeRoom);
        SubletClickTracker.registerClick(cambridgeRoom);
        SubletClickTracker.registerClick(cambridgeRoom);
        SubletClickTracker.registerClick(cambridgeRoom); // 2 clicks
        SubletClickTracker.registerClick(allstonFlat);   // 1 click
    }
}
