package model;

//polymorphism & inheritance
public class StudentUser extends User {
    public StudentUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public String getRole() {
        return "STUDENT";
    }
}
