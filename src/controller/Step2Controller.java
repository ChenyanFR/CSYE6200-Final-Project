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

	@FXML
    public void initialize() {
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
        
        maxBudgetField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty() && !newValue.matches("\\d*")) {
                maxBudgetField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    

    public void handleNext(ActionEvent event) {
    	boolean hasError = false;
    	
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
                int min = Integer.parseInt(minBudget);
                int max = Integer.parseInt(maxBudget);
                
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

    public void handleBack(ActionEvent event) {
    	try {
    		Parent prevView = FXMLLoader.load(getClass().getResource("/view/step1.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
//            StackPane rootPane = (StackPane) currentScene.lookup("#rootPane");
//            rootPane.getChildren().setAll(prevView);
            currentScene.setRoot(prevView);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

}
