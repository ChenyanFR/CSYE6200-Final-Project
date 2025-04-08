package controller;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.SubletListing;
import model.SubletStorage;

public class Step4Controller {

    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private Label uploadPathLabel;
    @FXML private TextField roommatesField;
    @FXML private TextField durationField;
    
    @FXML private Label titleErrorLabel;
    @FXML private Label imageErrorLabel;
    @FXML private Label descErrorLabel;
    @FXML private Label roommatesErrorLabel;
    @FXML private Label durationErrorLabel;
    
    private String imagePath;

    @FXML
    public void initialize() {
        // Hide all error labels
        titleErrorLabel.setVisible(false);
        imageErrorLabel.setVisible(false);
        descErrorLabel.setVisible(false);
        roommatesErrorLabel.setVisible(false);
        durationErrorLabel.setVisible(false);
        
        // Add validation for numeric fields
        roommatesField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("\\d*")) {
                roommatesField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    public void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.getAbsolutePath();
            uploadPathLabel.setText(file.getName());
            imageErrorLabel.setVisible(false);
        }
    }

    public void handleComplete(ActionEvent event) {
    	boolean hasError = false;
    	
    	// Verify Title
    	if (titleField.getText().trim().isEmpty()) {
            titleErrorLabel.setVisible(true);
            hasError = true;
        } else {
            titleErrorLabel.setVisible(false);
        }
    	
    	// Verify image
        if (imagePath == null || imagePath.isEmpty()) {
            imageErrorLabel.setVisible(true);
            hasError = true;
        } else {
            imageErrorLabel.setVisible(false);
        }
        
        // Verify desc
        if (descArea.getText().trim().isEmpty()) {
            descErrorLabel.setVisible(true);
            hasError = true;
        } else {
            descErrorLabel.setVisible(false);
        }
        
     // Verify roommates number
        if (roommatesField.getText().trim().isEmpty()) {
            roommatesErrorLabel.setVisible(true);
            hasError = true;
        } else {
            try {
                int roommates = Integer.parseInt(roommatesField.getText());
                if (roommates < 0) {
                    roommatesErrorLabel.setText("Number of roommates cannot be negative");
                    roommatesErrorLabel.setVisible(true);
                    hasError = true;
                } else {
                    roommatesErrorLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                roommatesErrorLabel.setText("Please enter a valid number");
                roommatesErrorLabel.setVisible(true);
                hasError = true;
            }
        }
        
        // Verify duration
        if (durationField.getText().trim().isEmpty()) {
            durationErrorLabel.setVisible(true);
            hasError = true;
        } else {
            durationErrorLabel.setVisible(false);
        }
        
        if(!hasError) {
    	String title = titleField.getText();
        String description = descArea.getText();
        String image = imagePath != null ? imagePath : "";

        String location = "User Submitted";
        double price = 0.0;
        String subletMode = "short";

        SubletListing newListing = new SubletListing(title, location, price, description, subletMode, image);
        SubletStorage.addListing(newListing);
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/step5_preview.fxml"));
            Parent previewView = loader.load();

            Step5PreviewController controller = loader.getController();
            controller.setData(
                titleField.getText(),
                descArea.getText(),
                imagePath
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(previewView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

    public void handleBack(ActionEvent event) {
    	try {
    		Parent prevView = FXMLLoader.load(getClass().getResource("/view/step3.fxml"));
    		Scene currentScene = ((Node) event.getSource()).getScene();
    		currentScene.setRoot(prevView);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
}

