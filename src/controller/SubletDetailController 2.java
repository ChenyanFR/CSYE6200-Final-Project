package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import model.SubletListing;

import java.io.File;
import java.io.IOException;

public class SubletDetailController {

    @FXML private Label titleLabel;
    @FXML private Label descriptionLabel;
    @FXML private Label locationLabel;
    @FXML private Label priceLabel;
    @FXML private Label modeLabel;
    @FXML private ImageView imageView;

    public void setData(SubletListing listing) {
        titleLabel.setText(listing.getTitle());
        descriptionLabel.setText(listing.getDescription());
        locationLabel.setText("Location: " + listing.getLocation());
        priceLabel.setText("Price: $" + listing.getPrice());
        modeLabel.setText("Sublet type: " + listing.getSubletMode());

        String imagePath = listing.getImagePath();
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(imagePath);
            if (file.exists()) {
                imageView.setImage(new Image(file.toURI().toString()));
            }
        }
    }

    @FXML
    public void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/step4_sublet_list.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) titleLabel.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
