package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Step5PreviewController {

    @FXML private ImageView uploadedImage;
    @FXML private Label titleLabel;
    @FXML private Label descLabel;

    public void setData(String title, String desc, String imagePath) {
        titleLabel.setText("Title: " + title);
        descLabel.setText("Description: " + desc);
        if (imagePath != null) {
            uploadedImage.setImage(new Image("file:" + imagePath));
        }
    }
}
