package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static final String USER_CREDENTIALS_FILE = "user_credentials.properties";
    private Map<String, String[]> userCredentials = new HashMap<>();

    public LoginController() {
        loadCredentials();
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (login(username, password)) {
        	String role = userCredentials.get(username)[1];
            try {
                Parent root;

                if ("admin".equalsIgnoreCase(role)) {
                    root = FXMLLoader.load(getClass().getResource("/view/admin_dashboard.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("/view/main_view.fxml"));
                }
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

        String[] credentials = userCredentials.get(username);
        return credentials != null && credentials[0].equals(password);
    }

    public boolean register(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        if (userCredentials.containsKey(username)) {
            return false;
        }

        userCredentials.put(username, new String[]{password, "user"});
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
                    String[] parts = properties.getProperty(username).split(":");
                    if (parts.length == 2) {
                        String password = parts[0];
                        String role = parts[1];
                        userCredentials.put(username, new String[]{password, role});
                    }
                }
            } catch (IOException e) {
                System.err.println("Error loading user credentials: " + e.getMessage());
            }
        } else {
            userCredentials.put("admin", new String[]{"admin", "admin"});
            saveCredentials();
        }
    }

    private void saveCredentials() {
        Properties properties = new Properties();
        for (Map.Entry<String, String[]> entry : userCredentials.entrySet()) {
            String username = entry.getKey();
            String[] credentials = entry.getValue(); 
            properties.setProperty(username, credentials[0] + ":" + credentials[1]);
        }

        try (FileOutputStream fos = new FileOutputStream(USER_CREDENTIALS_FILE)) {
            properties.store(fos, "User Credentials");
        } catch (IOException e) {
            System.err.println("Error saving user credentials: " + e.getMessage());
        }
    }
}