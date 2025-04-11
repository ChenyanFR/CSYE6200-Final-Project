package controller;

import java.io.IOException;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.SubletStorage;

/**
 * Controller class for the second step of the application flow.
 * Manages location selection and budget range input for filtering sublet listings.
 * Provides validation for user inputs and navigation to previous and next steps.
 */
public class Step2Controller {

	@FXML
    private ComboBox<String> locationComboBox;
	
	@FXML
    private TextField minBudgetField;
    
    @FXML
    private TextField maxBudgetField;
    
    @FXML
    private Label locationErrorLabel;
    
    @FXML
    private Label budgetErrorLabel;

    /**
     * Initializes the controller.
     * Populates the location dropdown with distinct locations from available listings.
     * Sets up input validation for budget fields and hides error labels.
     */
	@FXML
    public void initialize() {
		// Populate location dropdown with distinct locations from sublet listings
        locationComboBox.getItems().addAll(
            SubletStorage.getListings().stream()
                .map(listing -> listing.getLocation())
                .distinct()
                .sorted()
                .collect(Collectors.toList())
        );
        
        // Hide error message label
    	locationErrorLabel.setVisible(false);
        budgetErrorLabel.setVisible(false);
        
        // Add a listener to the budget input field to ensure only numbers are entered
        minBudgetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("\\d*")) {
                minBudgetField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        // Add a listener to the maximum budget input field to ensure only numbers are entered
        maxBudgetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("\\d*")) {
                maxBudgetField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    
	/**
     * Handles the Next button click event.
     * Validates user inputs (location and budget range) before proceeding to the next step.
     * Displays appropriate error messages if validation fails.
     * 
     * @param event The action event triggered by clicking the Next button
     */
    public void handleNext(ActionEvent event) {
    	boolean hasError = false;
    	
    	// Validate location selection
        String selectedLocation = locationComboBox.getValue();
        if (selectedLocation == null || selectedLocation.isEmpty()) {
        	locationErrorLabel.setVisible(true);
            hasError = true;
        } else {
            locationErrorLabel.setVisible(false);
        }
        
        // Verify budget values
        String minBudget = minBudgetField.getText();
        String maxBudget = maxBudgetField.getText();
        
        // Checks if at least one budget field is filled
        if (minBudget.isEmpty() || maxBudget.isEmpty()) {
            budgetErrorLabel.setText("Please enter both minimum and maximum budget values");
            budgetErrorLabel.setVisible(true);
            hasError = true;
        }else {
            try {
            	// Parse and validate budget range
                int min = Integer.parseInt(minBudget);
                int max = Integer.parseInt(maxBudget);
                
                // Ensure minimum budget is not greater than maximum
                if (min > max) {
                    budgetErrorLabel.setText("Minimum budget cannot be greater than maximum");
                    budgetErrorLabel.setVisible(true);
                    hasError = true;
                } else {
                    budgetErrorLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                budgetErrorLabel.setText("Please enter valid budget values");
                budgetErrorLabel.setVisible(true);
                hasError = true;
            }
        }
        
        // If there are no errors, continue to the next step
        if (!hasError) {
            try {
                Parent nextView = FXMLLoader.load(getClass().getResource("/view/step3.fxml"));
                Scene currentScene = ((Node) event.getSource()).getScene();
                currentScene.setRoot(nextView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the Back button click event.
     * Navigates back to the previous step (step1.fxml).
     * 
     * @param event The action event triggered by clicking the Back button
     */
    public void handleBack(ActionEvent event) {
    	try {
    		Parent prevView = FXMLLoader.load(getClass().getResource("/view/step1.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(prevView);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}
