package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static final String USER_CREDENTIALS_FILE = "user_credentials.properties";
    private Map<String, String> userCredentials = new HashMap<>();

    public LoginController() {
        loadCredentials();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (login(username, password)) {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
            alert.showAndWait();
        }
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

    public boolean login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    public boolean register(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (userCredentials.containsKey(username)) {
            return false;
        }

        userCredentials.put(username, password);
        saveCredentials();
        return true;
    }

    private void loadCredentials() {
        Properties properties = new Properties();
        File file = new File(USER_CREDENTIALS_FILE);

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
                for (String username : properties.stringPropertyNames()) {
                    userCredentials.put(username, properties.getProperty(username));
                }
            } catch (IOException e) {
                System.err.println("Error loading user credentials: " + e.getMessage());
            }
        } else {
            userCredentials.put("admin", "admin");
            saveCredentials();
        }
    }

    private void saveCredentials() {
        Properties properties = new Properties();
        for (Map.Entry<String, String> entry : userCredentials.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue());
        }

        try (FileOutputStream fos = new FileOutputStream(USER_CREDENTIALS_FILE)) {
            properties.store(fos, "User Credentials");
        } catch (IOException e) {
            System.err.println("Error saving user credentials: " + e.getMessage());
        }
    }
}


