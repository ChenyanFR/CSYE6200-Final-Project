package controller;


import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane rootPane;

    @FXML
    public void initialize() {
        loadStep("/view/step1.fxml");
    }

    public void loadStep(String fxmlPath) {
        try {
            Parent stepContent = FXMLLoader.load(getClass().getResource(fxmlPath));
            rootPane.getChildren().setAll(stepContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}