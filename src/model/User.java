package model;

//polymorphism & inheritance
public abstract class User {
    protected String username;
    protected String email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public abstract String getRole();
}

