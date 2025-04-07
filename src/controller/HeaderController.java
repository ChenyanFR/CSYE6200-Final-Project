package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class HeaderController {

    
    @FXML
    private Button homeButton;
    /**
    @FXML
    private Button backButton;
    
    @FXML
    private void handleBack() {
        
        try {
            Parent previousView = FXMLLoader.load(getClass().getResource("/view/step1.fxml"));
            Scene currentScene = backButton.getScene();
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(new Scene(previousView, 600, 1000));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }**/
    
    @FXML
    private void handleHome() {
        
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("/view/step1.fxml"));
            Scene currentScene = homeButton.getScene();
            Stage stage = (Stage) currentScene.getWindow();
            stage.setScene(new Scene(homeView, 600, 800));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
