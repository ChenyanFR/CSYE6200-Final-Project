package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField schoolField;
    @FXML private TextField emailField;
    @FXML private DatePicker graduationDatePicker;

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String school = schoolField.getText();
        String email = emailField.getText();
        LocalDate graduationDate = graduationDatePicker.getValue();

        if (username.isEmpty() || password.isEmpty() || school.isEmpty() || email.isEmpty() || graduationDate == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.edu$")) {
            showAlert(Alert.AlertType.ERROR, "Please use a valid .edu school email address.");
            return;
        }

        // Simulate registration logic
        LoginController loginController = new LoginController();
        boolean success = loginController.register(username, password); // You might expand this later to save school/email/date

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "User registered successfully!");

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Username already exists.");
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



