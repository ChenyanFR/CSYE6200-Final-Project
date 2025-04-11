package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SubletListing;

/**
 * Controller class for the fifth step of the application flow.
 * Displays a preview of the created sublet listing, showing the uploaded image,
 * title, and description. Provides navigation to the listing page or back to the form.
 */
public class Step5PreviewController {

    @FXML private ImageView uploadedImage;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    
    /**
     * Handles the "Go to Sublet List" button click event.
     * Navigates to the sublet listings view (step4_sublet_list.fxml).
     * 
     * @param event The action event triggered by clicking the button
     */
    @FXML
    public void handleGoToSubletList(ActionEvent event) {
        try {
        	// Load the sublet list view
            Parent listView = FXMLLoader.load(getClass().getResource("/view/step4_sublet_list.fxml"));
            
            // Get the current scene and replace its root
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(listView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the data to be displayed in the preview.
     * Updates the title, description, and image from the provided values.
     * 
     * @param title The title of the sublet listing
     * @param desc The description of the sublet listing
     * @param imagePath The file path to the uploaded image
     */
    public void setData(String title, String desc, String imagePath) {
        titleLabel.setText("Title: " + title);
        descLabel.setText("Description: " + desc);
        
        // Load and display the image if a path is provided
        if (imagePath != null) {
            uploadedImage.setImage(new Image("file:" + imagePath));
        }
    }

    /**
     * Handles the Back button click event.
     * Navigates back to the form view (step4.fxml).
     * 
     * @param event The action event triggered by clicking the Back button
     */
    public void handleBack(ActionEvent event) {
        try {
        	// Load the previous view (form)
            Parent prevView = FXMLLoader.load(getClass().getResource("/view/step4.fxml"));
            
            // Get the current scene and replace its root
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(prevView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Alternative method to set data from a SubletListing object.
     * Updates the title and description from the provided listing.
     * 
     * @param listing The SubletListing object containing the data to display
     */
    public void setData(SubletListing listing) {
        titleLabel.setText(listing.getTitle());
        descLabel.setText(listing.getDescription());

    }

}
