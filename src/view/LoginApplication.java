package view;

import controller.LoginController;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * JavaFX Login Application that serves as the entry point to the Image Processing Application.
 * Provides login and registration functionality.
 */
public class LoginApplication {

    private LoginController loginController;
    private Stage primaryStage;
    private Scene loginScene;
    private Scene registerScene;
    private Text statusText;

    /**
     * Displays the login window.
     */
    public void show() {
        primaryStage = new Stage();
        this.loginController = new LoginController();
        
        // Create both scenes
        createLoginScene();
        createRegisterScene();
        
        // Set initial scene to login
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Image Processing - Login");
        primaryStage.show();
    }


    /**
     * Creates the login scene with username and password fields.
     */
    private void createLoginScene() {
        // Main layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Title
        Text sceneTitle = new Text("Welcome to Image Processing");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        // Username label and field
        Label userName = new Label("Username:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        // Password label and field
        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);
        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        // Login button
        Button loginBtn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginBtn);
        grid.add(hbBtn, 1, 4);

        // Register link
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register here.");
        grid.add(registerLink, 1, 5);

        // Status text for error messages
        statusText = new Text();
        statusText.setFill(Color.RED);
        grid.add(statusText, 1, 6);

        // Login button action
        loginBtn.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();
            
            if (loginController.login(username, password)) {
                statusText.setText("Login successful!");
                statusText.setFill(Color.GREEN);
                
                // Launch the main application
                launchMainApplication();
            } else {
                statusText.setText("Invalid username or password");
                statusText.setFill(Color.RED);
            }
        });

        // Register link action
        registerLink.setOnAction(e -> primaryStage.setScene(registerScene));

        // Create scene
        loginScene = new Scene(grid, 400, 300);
    }

    /**
     * Creates the registration scene with username, password, and confirm password fields.
     */
    private void createRegisterScene() {
        // Main layout
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(25, 25, 25, 25));

        // Title
        Text sceneTitle = new Text("Register New Account");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        vbox.getChildren().add(sceneTitle);

        // Grid for form fields
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        vbox.getChildren().add(grid);

        // Username
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);

        // Password
        Label pwLabel = new Label("Password:");
        PasswordField pwField = new PasswordField();
        grid.add(pwLabel, 0, 1);
        grid.add(pwField, 1, 1);

        // Confirm Password
        Label confirmPwLabel = new Label("Confirm Password:");
        PasswordField confirmPwField = new PasswordField();
        grid.add(confirmPwLabel, 0, 2);
        grid.add(confirmPwField, 1, 2);

        // Status text
        Text registerStatus = new Text();
        registerStatus.setFill(Color.RED);
        vbox.getChildren().add(registerStatus);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back to Login");
        buttonBox.getChildren().addAll(registerBtn, backBtn);
        vbox.getChildren().add(buttonBox);

        // Register button action
        registerBtn.setOnAction(e -> {
            String username = userField.getText();
            String password = pwField.getText();
            String confirmPassword = confirmPwField.getText();
            
            if (username.isEmpty() || password.isEmpty()) {
                registerStatus.setText("Username and password cannot be empty");
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                registerStatus.setText("Passwords do not match");
                return;
            }
            
            if (loginController.register(username, password)) {
                registerStatus.setText("Registration successful! You can now login.");
                registerStatus.setFill(Color.GREEN);
                
                // Clear fields
                userField.clear();
                pwField.clear();
                confirmPwField.clear();
            } else {
                registerStatus.setText("Username already exists");
                registerStatus.setFill(Color.RED);
            }
        });

        // Back button action
        backBtn.setOnAction(e -> primaryStage.setScene(loginScene));

        // Create scene
        registerScene = new Scene(vbox, 400, 300);
    }

    /**
     * Launches the main image processing application after successful login.
     */
    private void launchMainApplication() {
        try {
            // Close the login window
            primaryStage.close();
            
         // Create and show the main application
            Platform.runLater(() -> {
                main.ImageProcessingApplication.startApplicationDirectly(new String[]{});
            });
        } catch (Exception e) {
            e.printStackTrace();
            statusText.setText("Error launching application: " + e.getMessage());
            statusText.setFill(Color.RED);
        }
    }

//    public static void main(String[] args) {
//        launch(args);
//    }
}
