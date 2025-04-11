package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Controller class for the first step of the application flow.
 * Handles navigation from the initial screen to the second step
 * based on the user's selection (sublet or roommates).
 */
public class Step1Controller {

	/**
     * Event handler for the "Sublet" option.
     * Navigates to the second step of the application flow.
     * 
     * @param event The action event triggered by clicking the sublet button
     */
    public void handleSublet(ActionEvent event) {
        goToNext(event, "/view/step2.fxml");
    }

    /**
     * Event handler for the "Roommates" option.
     * Navigates to the second step of the application flow.
     * 
     * @param event The action event triggered by clicking the roommates button
     */
    public void handleRoommates(ActionEvent event) {
        goToNext(event, "/view/step2.fxml");
    }

    /**
     * Helper method that handles navigation to the next view.
     * Loads the specified FXML file and sets it as the root of the current scene.
     * 
     * @param event The action event from the triggering UI element
     * @param path The resource path to the FXML file to be loaded
     */
    private void goToNext(ActionEvent event, String path) {
        try {
        	// Load the next view from the specified FXML file
            Parent nextView = FXMLLoader.load(getClass().getResource(path));
            
            // Get the current scene from the event source
            Scene currentScene = ((Node) event.getSource()).getScene();
            
            // Replace the root of the current scene with the new view
            currentScene.setRoot(nextView);
        } catch (IOException e) {
        	// Print stack trace if loading fails
            e.printStackTrace();
        }
    }
}
