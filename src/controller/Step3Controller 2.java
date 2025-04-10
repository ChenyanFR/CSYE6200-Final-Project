package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Step3Controller {

    public void handleYes(ActionEvent event) {
        goToNext("/view/step4.fxml", event); // Register your place
    }

    public void handleNo(ActionEvent event) {
        goToNext("/view/step4_sublet_list.fxml", event); // Sample listings
    }

    public void handleBack(ActionEvent event) {
    	goToNext("/view/step2.fxml", event); // Back to prevView
    }

    private void goToNext(String path, ActionEvent event) {
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
