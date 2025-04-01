package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Step2Controller {

    public void handleNext(ActionEvent event) {
        try {
            Parent nextView = FXMLLoader.load(getClass().getResource("/view/step3.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            StackPane rootPane = (StackPane) currentScene.lookup("#rootPane");
            rootPane.getChildren().setAll(nextView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
