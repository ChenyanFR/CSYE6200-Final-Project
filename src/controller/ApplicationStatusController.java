package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import model.AppliedApplication;
import java.io.IOException;

public class ApplicationStatusController {

    @FXML private TableView<AppliedApplication> tableView;
    @FXML private TableColumn<AppliedApplication, String> nameColumn;
    @FXML private TableColumn<AppliedApplication, String> emailColumn;
    @FXML private TableColumn<AppliedApplication, String> titleColumn;

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        titleColumn.setCellValueFactory(data -> {
            return new javafx.beans.property.SimpleStringProperty(
                data.getValue().getListing().getTitle()
            );
        });

        ObservableList<AppliedApplication> data = FXCollections.observableArrayList(AppliedApplication.getAll());
        tableView.setItems(data);
    }

    public void handleBack(ActionEvent event) {
        goToNext("/view/ApplyView.fxml", event);
    }

    private void goToNext(String path, ActionEvent event) {
        try {
            Parent nextView = FXMLLoader.load(getClass().getResource(path));
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(nextView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


