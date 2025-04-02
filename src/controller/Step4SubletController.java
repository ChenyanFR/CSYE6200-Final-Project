package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import model.SubletListing;
import model.SubletStorage;

public class Step4SubletController {

    @FXML private TableView<SubletListing> tableView;
    @FXML private TableColumn<SubletListing, String> titleColumn;
    @FXML private TableColumn<SubletListing, String> locationColumn;
    @FXML private TableColumn<SubletListing, Double> priceColumn;
    @FXML private TableColumn<SubletListing, String> descriptionColumn;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableView.setItems(SubletStorage.getListings());
    }
    
    public void handleBack(ActionEvent event) {
        try {
            Parent prevView = FXMLLoader.load(getClass().getResource("/view/step3.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(prevView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

