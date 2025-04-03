package controller;

import javafx.fxml.FXML;
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
}

