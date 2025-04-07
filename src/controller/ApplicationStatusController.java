package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import model.AppliedApplication;

public class ApplicationStatusController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label titleLabel;
    @FXML private Label locationLabel;
    @FXML private Label priceLabel;

    @FXML
    public void initialize() {
        AppliedApplication app = AppliedApplication.getCurrent();
        if (app != null) {
            nameLabel.setText(app.getName());
            emailLabel.setText(app.getEmail());
            titleLabel.setText(app.getListing().getTitle());
            locationLabel.setText(app.getListing().getLocation());
            priceLabel.setText("$" + app.getListing().getPrice());
        }
    }
    
    public void handleBack(ActionEvent event) {
    	goToNext("/view/ApplyView.fxml", event); // Back to prevView
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

