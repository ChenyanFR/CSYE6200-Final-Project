package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import model.SubletStorage;
import java.io.IOException;
import java.util.stream.Collectors;


public class Step2Controller {
	
	@FXML
    private ComboBox<String> locationComboBox;
	
	@FXML
    public void initialize() {
        locationComboBox.getItems().addAll(
            SubletStorage.getListings().stream()
                .map(listing -> listing.getLocation())
                .distinct()
                .sorted()
                .collect(Collectors.toList())
        );
    }


    public void handleNext(ActionEvent event) {
        String selectedLocation = locationComboBox.getValue();
        if (selectedLocation == null || selectedLocation.isEmpty()) {
            System.out.println("Location is not selected");
            return;
        }
        try {
            Parent nextView = FXMLLoader.load(getClass().getResource("/view/step3.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
//            StackPane rootPane = (StackPane) currentScene.lookup("#rootPane");
//            rootPane.getChildren().setAll(nextView);
            currentScene.setRoot(nextView);
        } catch (IOException e) {
            e.printStackTrace();
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
