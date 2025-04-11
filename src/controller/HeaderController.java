package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HeaderController {

	/**
     * Button that navigates to the home screen when clicked.
     * This is injected by JavaFX FXMLLoader.
     */
    @FXML
    private Button homeButton;

    /**
     * Event handler for the home button.
     * Loads the home view (step1.fxml) and displays it with dimensions 600x800.
     * 
     * This method is called when the home button is clicked.
     */
    @FXML
    private void handleHome() {
        
        try {
        	// Load the home view from FXML file
            Parent homeView = FXMLLoader.load(getClass().getResource("/view/step1.fxml"));
            
            // Get the current scene from the home button
            Scene currentScene = homeButton.getScene();
            
            // Get the stage (window) from the current scene
            Stage stage = (Stage) currentScene.getWindow();
            
            // Set the new scene with the home view
            stage.setScene(new Scene(homeView, 600, 800));
        } catch (IOException e) {
        	// Print stack trace if loading the FXML file fails
            e.printStackTrace();
        }
    }
}
