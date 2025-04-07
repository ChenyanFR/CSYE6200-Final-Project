package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import model.SubletListing;
import model.SubletStorage;

public class AdminDashboardController implements Initializable {

	@FXML
	private BarChart<String, Number> regionPriceChart;

	@FXML
	private PieChart subletModeChart;

	@FXML
	private void handleGoHome() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_view.fxml"));
	        Parent root = loader.load();
	        Stage stage = (Stage) regionPriceChart.getScene().getWindow();
	        stage.setScene(new Scene(root));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadRegionAveragePrices();
		loadSubletModeStats();
	}
	
	private void loadRegionAveragePrices() {
	    Map<String, List<Double>> regionPrices = new HashMap<>();

	    for (SubletListing listing : SubletStorage.getListings()) {
	        String region = listing.getLocation(); // Changed from getRegion() to getLocation()
	        regionPrices.putIfAbsent(region, new ArrayList<>());
	        regionPrices.get(region).add(listing.getPrice());
	    }

	    XYChart.Series<String, Number> series = new XYChart.Series<>();
	    series.setName("Rent Average Stats by Region");

	    for (Map.Entry<String, List<Double>> entry : regionPrices.entrySet()) {
	        String region = entry.getKey();
	        List<Double> prices = entry.getValue();
	        double avg = prices.stream().mapToDouble(Double::doubleValue).average().orElse(0);
	        series.getData().add(new XYChart.Data<>(region, avg));
	    }

	    regionPriceChart.getData().add(series);
	}

	private void loadSubletModeStats() {
		int shortTerm = 0;
		int longTerm = 0;

		for (SubletListing listing: SubletStorage.getListings()) {
			if (listing.getSubletMode().equalsIgnoreCase("short")) {
				shortTerm++;
			} else {
				longTerm++;
			}
		}


		ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
				new PieChart.Data("short term", shortTerm),
				new PieChart.Data("long term", longTerm)
						);
				subletModeChart.setData(pieData);

	}



}
