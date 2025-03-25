//package CSYE6200-Final-Project.controller;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.User;

import java.util.ArrayList;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static ArrayList<User> users = new ArrayList<>();

    // 테스트용 유저 추가
    static {
        users.add(new User("admin", "1234"));
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                loadMainView();
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
        alert.showAndWait();
    }

    @FXML
    private void handleSignupRedirect() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/signup.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMainView() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User user) {
        users.add(user);
    }
}
