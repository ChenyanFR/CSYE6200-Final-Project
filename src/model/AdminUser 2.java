package model;

//polymorphism & inheritance
public class AdminUser extends User {
    public AdminUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String getRole() {
        return "ADMIN";
    }
}
