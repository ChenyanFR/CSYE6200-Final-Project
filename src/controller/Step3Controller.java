package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Controller class for the third step of the application flow.
 * Handles user decisions about whether they want to register their place
 * or view available sublet listings.
 */
public class Step3Controller {

	/**
     * Handles the "Yes" button click event.
     * Navigates to the place registration screen (step4.fxml).
     * 
     * @param event The action event triggered by clicking the Yes button
     */
    public void handleYes(ActionEvent event) {
        goToNext("/view/step4.fxml", event); // Register your place
    }

    /**
     * Handles the "No" button click event.
     * Navigates to the sublet listings screen (step4_sublet_list.fxml).
     * 
     * @param event The action event triggered by clicking the No button
     */
    public void handleNo(ActionEvent event) {
        goToNext("/view/step4_sublet_list.fxml", event); // Sample listings
    }

    /**
     * Handles the "Back" button click event.
     * Navigates back to the previous step (step2.fxml).
     * 
     * @param event The action event triggered by clicking the Back button
     */
    public void handleBack(ActionEvent event) {
    	goToNext("/view/step2.fxml", event); // Back to prevView
    }

    /**
     * Helper method that handles navigation to the specified view.
     * Loads the FXML file and sets it as the root of the current scene.
     * 
     * @param path The resource path to the FXML file to be loaded
     * @param event The action event from the triggering UI element
     */
    private void goToNext(String path, ActionEvent event) {
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
