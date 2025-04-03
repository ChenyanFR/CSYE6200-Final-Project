package model;

public class AppliedApplication {
    private static AppliedApplication current;

    private final String name;
    private final String email;
    private final SubletListing listing;

    public AppliedApplication(String name, String email, SubletListing listing) {
        this.name = name;
        this.email = email;
        this.listing = listing;
    }

    public static void setCurrent(AppliedApplication app) {
        current = app;
    }

    public static AppliedApplication getCurrent() {
        return current;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public SubletListing getListing() { return listing; }
}

