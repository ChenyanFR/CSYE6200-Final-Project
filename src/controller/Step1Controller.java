package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Step1Controller {

    public void handleSublet(ActionEvent event) {
        goToNext(event, "/view/step2.fxml");
    }

    public void handleRoommates(ActionEvent event) {
        goToNext(event, "/view/step2.fxml");
    }

    private void goToNext(ActionEvent event, String path) {
        try {
            Parent nextView = FXMLLoader.load(getClass().getResource(path));
            Scene currentScene = ((Node) event.getSource()).getScene();
//            StackPane rootPane = (StackPane) currentScene.lookup("#rootPane");
//            rootPane.getChildren().setAll(nextView);
            currentScene.setRoot(nextView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
