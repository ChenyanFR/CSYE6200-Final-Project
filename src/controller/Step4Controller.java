package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.IOException;
import javafx.scene.Node;

public class Step4Controller {

    @FXML private TextField titleField;
    @FXML private TextArea descArea;
    @FXML private Label uploadPathLabel;
    private String imagePath;

    public void handleUpload(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            imagePath = file.getAbsolutePath();
            uploadPathLabel.setText(file.getName());
        }
    }

    public void handleComplete(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/step5_preview.fxml"));
            Parent previewView = loader.load();

            Step5PreviewController controller = loader.getController();
            controller.setData(
                titleField.getText(),
                descArea.getText(),
                imagePath
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(previewView);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

