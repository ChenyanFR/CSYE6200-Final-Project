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

/**
 * Controller class for displaying and filtering available sublet listings.
 * Manages the interactive table view of listings, filtering functionality,
 * and navigation to detail and application pages.
 */
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

    /**
     * Initializes the controller.
     * Sets up table columns, initializes filters, and adds action listeners.
     */
    @FXML
    public void initialize() {
    	// Configure table columns to display listing properties
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Create filtered list from all available listings
        filteredList = new FilteredList<>(SubletStorage.getListings(), p -> true);
        tableView.setItems(filteredList);

        // Set up location filter dropdown
        locationFilter.getItems().add("All");
        SubletStorage.getListings().stream()
            .map(SubletListing::getLocation)
            .distinct()
            .forEach(locationFilter.getItems()::add);
        
        // Add event handlers for filter controls
        locationFilter.setValue("All");
        locationFilter.setOnAction(e -> applyFilters());
        priceFilter.textProperty().addListener((obs, oldVal, newVal) -> applyFilters());
        
        // Add interactive details buttons to each row
        addDetailsButtonToTable(); 
    }
    
    /**
     * Handles the Apply button click event.
     * Navigates to the application form for the selected listing.
     * Shows a warning if no listing is selected.
     * 
     * @param event The action event triggered by clicking the Apply button
     */
    @FXML
    private void handleApply(ActionEvent event) {
        SubletListing selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a listing first!");
            alert.showAndWait();
            return;
        }

        try {
        	// Load the application form view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ApplyView.fxml"));
            Parent applyPage = loader.load();

            // Pass the selected listing to the controller
            ApplyController controller = loader.getController();
            controller.setData(selected);

            // Navigate to the application form
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(applyPage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Back button click event.
     * Navigates back to the previous step (step3.fxml).
     * 
     * @param event The action event triggered by clicking the Back button
     */
    public void handleBack(ActionEvent event) {
        try {
            Parent prevView = FXMLLoader.load(getClass().getResource("/view/step3.fxml"));
            Scene currentScene = ((Node) event.getSource()).getScene();
            currentScene.setRoot(prevView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Applies the selected filters to the listing table.
     * Filters listings based on selected location and maximum price.
     */
    private void applyFilters() {
        String selectedLocation = locationFilter.getValue();
        String maxPriceText = priceFilter.getText();
        double maxPrice = Double.MAX_VALUE;

        // Parse maximum price if provided
        try {
            if (!maxPriceText.isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceText);
            }
        } catch (NumberFormatException e) {
            maxPrice = Double.MAX_VALUE;
        }

        double finalMaxPrice = maxPrice;

        // Update the predicate to filter listings
        filteredList.setPredicate(listing -> {
            boolean locationMatch = selectedLocation.equals("All") || listing.getLocation().equals(selectedLocation);
            boolean priceMatch = listing.getPrice() <= finalMaxPrice;
            return locationMatch && priceMatch;
        });
    }
    
    /**
     * Adds Details buttons to each row in the table.
     * Each button opens a detailed view of the corresponding listing when clicked.
     */
    private void addDetailsButtonToTable() {
        detailsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button button = new Button("Details");

            {
            	// Configure button to open detail page for the listing in this row
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
    
    /**
     * Opens the detail page for a specific listing.
     * Loads the sublet_detail.fxml view and passes the listing data to its controller.
     * 
     * @param listing The SubletListing to display details for
     */
    private void openDetailPage(SubletListing listing) {
        try {
        	// Load the detail view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/sublet_detail.fxml"));
            Parent detailView = loader.load();

            // Pass the listing to the detail controller
            SubletDetailController controller = loader.getController();
            controller.setData(listing);

            // Navigate to the detail view
            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(new Scene(detailView));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






