package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.AppliedApplication;
import model.SubletListing;

public class ApplyController {

    @FXML private Label titleLabel;
    @FXML private Label locationLabel;
    @FXML private Label priceLabel;
    @FXML private Label descriptionLabel;

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;

    @FXML private CheckBox creditCheckBox;
    @FXML private VBox creditNoticeBox;
    @FXML private CheckBox agreeCheckBox;

    private SubletListing listing;

    public void setData(SubletListing listing) {
        this.listing = listing;
        titleLabel.setText(listing.getTitle());
        locationLabel.setText("Location: " + listing.getLocation());
        priceLabel.setText("Price: $" + listing.getPrice());
        descriptionLabel.setText("Description: " + listing.getDescription());
    }

    @FXML
    private void handleCreditCheck() {
        creditNoticeBox.setVisible(!creditCheckBox.isSelected());
    }

    @FXML
    private void handleConfirm() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || phoneField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all your contact information.");
            return;
        }

        if (!agreeCheckBox.isSelected()) {
            showAlert(Alert.AlertType.WARNING, "You must agree to the terms to apply.");
            return;
        }
        AppliedApplication app = new AppliedApplication(nameField.getText(), emailField.getText(), listing);
        AppliedApplication.setCurrent(app);
        AppliedApplication.addApplication(app);  

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Application submitted!\nDo you want to check your application status?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                    Parent statusView = FXMLLoader.load(getClass().getResource("/view/application_status.fxml"));
                    Stage stage = (Stage) nameField.getScene().getWindow();
                    stage.setScene(new Scene(statusView));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }
    public void handleBack(ActionEvent event) {
        goToNext("/view/step4_sublet_list.fxml", event);
    }

    private void goToNext(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent nextView = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(nextView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}