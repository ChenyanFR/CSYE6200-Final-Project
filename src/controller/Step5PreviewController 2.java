package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.SubletListing;

public class Step5PreviewController {

    @FXML private ImageView uploadedImage;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;
    
    @FXML
    public void handleGoToSubletList(ActionEvent event) {
        try {
            Parent listView = FXMLLoader.load(getClass().getResource("/view/step4_sublet_list.fxml"));
            Scene scene = ((Node) event.getSource()).getScene();
            scene.setRoot(listView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(String title, String desc, String imagePath) {
        titleLabel.setText("Title: " + title);
        descLabel.setText("Description: " + desc);
        if (imagePath != null) {

            uploadedImage.setImage(new Image("file:" + imagePath));
        }
    }

    public void handleBack(ActionEvent event) {
        try {
            Parent prevView = FXMLLoader.load(getClass().getResource("/view/step4.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(prevView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setData(SubletListing listing) {

        titleLabel.setText(listing.getTitle());
        descLabel.setText(listing.getDescription());

    }

}
