package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Controller for handling user authentication and registration.
 * Manages user credentials and provides login and registration functionality.
 */

public class LoginController {
    private static final String USER_CREDENTIALS_FILE = "user_credentials.properties";
    private Map<String, String> userCredentials;
    
    /**
     * Constructs a new LoginController and loads existing user credentials.
     */
    public LoginController() {
        userCredentials = new HashMap<>();
        loadCredentials();
    }
    
    /**
     * Attempts to authenticate a user with the given username and password.
     *
     * @param username the username
     * @param password the password
     * @return true if authentication is successful, false otherwise
     */
    public boolean login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        
        String storedPassword = userCredentials.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }
    
    /**
     * Registers a new user with the given username and password.
     *
     * @param username the username
     * @param password the password
     * @return true if registration is successful, false if the username already exists
     */
    public boolean register(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        
        // Check if username already exists
        if (userCredentials.containsKey(username)) {
            return false;
        }
        
        // Add new user and save to file
        userCredentials.put(username, password);
        saveCredentials();
        return true;
    }
    
    /**
     * Loads user credentials from the properties file.
     */
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
            // Create a default admin account for initial login
            userCredentials.put("admin", "admin");
            saveCredentials();
        }
    }
    
    /**
     * Saves user credentials to the properties file.
     */
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
