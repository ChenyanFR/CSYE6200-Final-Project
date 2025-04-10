package controller;

import java.io.IOException;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.SubletListing;
import model.SubletStorage;

public class Step4SubletController {

    @FXML private TableView<SubletListing> tableView;
    @FXML private TableColumn<SubletListing, String> titleColumn;
    @FXML private TableColumn<SubletListing, String> locationColumn;
    @FXML private TableColumn<SubletListing, Double> priceColumn;
    @FXML private TableColumn<SubletListing, String> descriptionColumn;
    @FXML private ComboBox<String> locationFilter;
    @FXML private TextField priceFilter;
    @FXML private TableColumn<SubletListing, Void> detailsColumn;


    private FilteredList<SubletListing> filteredList;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        filteredList = new FilteredList<>(SubletStorage.getListings(), p -> true);
        tableView.setItems(filteredList);

        locationFilter.getItems().add("All");
        SubletStorage.getListings().stream()
            .map(SubletListing::getLocation)
            .distinct()
            .forEach(locationFilter.getItems()::add);
        locationFilter.setValue("All");
        locationFilter.setOnAction(e -> applyFilters());
        priceFilter.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        
        addDetailsButtonToTable(); 
    }
    @FXML
    private void handleApply(ActionEvent event) {
        SubletListing selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a listing first!");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ApplyView.fxml"));
            Parent applyPage = loader.load();

            ApplyController controller = loader.getController();
            controller.setData(selected);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(applyPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    private void applyFilters() {
        String selectedLocation = locationFilter.getValue();
        String maxPriceText = priceFilter.getText();
        double maxPrice = Double.MAX_VALUE;

        try {
            if (!maxPriceText.isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceText);
            }
        } catch (NumberFormatException e) {
            maxPrice = Double.MAX_VALUE;
        }

        double finalMaxPrice = maxPrice;

        filteredList.setPredicate(listing -> {
            boolean locationMatch = selectedLocation.equals("All") || listing.getLocation().equals(selectedLocation);
            boolean priceMatch = listing.getPrice() <= finalMaxPrice;
            return locationMatch && priceMatch;
        });
    }
    private void addDetailsButtonToTable() {
        detailsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button button = new Button("Details");

            {
                button.setOnAction(event -> {
                    SubletListing listing = getTableView().getItems().get(getIndex());
                    openDetailPage(listing);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }
    private void openDetailPage(SubletListing listing) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sublet_detail.fxml"));
            Parent detailView = loader.load();

            SubletDetailController controller = loader.getController();
            controller.setData(listing);

            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(detailView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}






