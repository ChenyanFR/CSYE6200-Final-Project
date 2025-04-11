package model;

import java.util.ArrayList;
import java.util.List;

public class AppliedApplication {
    private String name;
    private String email;
    private SubletListing listing;

    private static final List<AppliedApplication> applications = new ArrayList<>();

    public AppliedApplication(String name, String email, SubletListing listing) {
        this.name = name;
        this.email = email;
        this.listing = listing;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public SubletListing getListing() { return listing; }
    //add applications on the list (manual for now)
    public static void addApplication(AppliedApplication app) {
        applications.add(app);
    }
    // return all applications from appliedapplication
    public static List<AppliedApplication> getAll() {
        return applications;
    }
    //"current" = selected status 
    private static AppliedApplication current;

    public static AppliedApplication getCurrent() {
        return current;
    }

    public static void setCurrent(AppliedApplication app) {
        current = app;
    }
}


