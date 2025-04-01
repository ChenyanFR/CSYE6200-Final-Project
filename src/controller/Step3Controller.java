package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class Step3Controller {

    public void handleYes(ActionEvent event) {
        goToNext("/view/step4.fxml", event); // Register your place
    }

    public void handleNo(ActionEvent event) {
        goToNext("/view/step4_sublet_list.fxml", event); // Sample listings
    }

    private void goToNext(String path, ActionEvent event) {
        try {
            Parent nextView = FXMLLoader.load(getClass().getResource(path));
            Scene currentScene = ((Node) event.getSource()).getScene();
            StackPane rootPane = (StackPane) currentScene.lookup("#rootPane");
            rootPane.getChildren().setAll(nextView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
