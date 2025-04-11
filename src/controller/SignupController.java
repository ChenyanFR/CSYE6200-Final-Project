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

    /**
     * Handles the register button click event.
     * Validates all input fields, ensures email has .edu domain,
     * and attempts to register the user via LoginController.
     * If successful, redirects to the login screen.
     */
    @FXML
    private void handleRegister() {
    	// Get values from all input fields
        String username = usernameField.getText();
        String password = passwordField.getText();
        String school = schoolField.getText();
        String email = emailField.getText();
        LocalDate graduationDate = graduationDatePicker.getValue();

        // Validate that all fields are filled in
        if (username.isEmpty() || password.isEmpty() || school.isEmpty() || email.isEmpty() || graduationDate == null) {
            showAlert(Alert.AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        // Validate that email has a .edu domain
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.edu$")) {
            showAlert(Alert.AlertType.ERROR, "Please use a valid .edu school email address.");
            return;
        }

        // Simulate registration logic
        LoginController loginController = new LoginController();
        boolean success = loginController.register(username, password); // You might expand this later to save school/email/date

        if (success) {
        	// Show success message and navigate to login screen
            showAlert(Alert.AlertType.INFORMATION, "User registered successfully!");

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
        	// Show error message if username already exists
            showAlert(Alert.AlertType.ERROR, "Username already exists.");
        }
    }

    /**
     * Handles the back/cancel button click event.
     * Navigates back to the login screen without registering.
     */
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

    /**
     * Utility method to display alert dialogs.
     * 
     * @param type The type of alert to show (ERROR, INFORMATION, etc.)
     * @param message The message to display in the alert dialog
     */
    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}



