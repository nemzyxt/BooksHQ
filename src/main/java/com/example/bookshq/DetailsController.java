package com.example.bookshq;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONObject;

public class DetailsController {
    @FXML
    private Label nameLabel, authorsLabel, ratingLabel, createdEditionsLabel, yearLabel;

    private Stage primaryStage;
    private com.example.bookshq.HomeController homeController;

    public void setHomeController(com.example.bookshq.HomeController homeController) {
        this.homeController = homeController;
    }

    // Method to initialize data in the second scene
    public void initData(JSONObject bookData, Stage primaryStage, com.example.bookshq.HomeController homeController) {
        setHomeController(homeController);
        this.primaryStage = primaryStage;

        // Set book name centrally at the top
        String name = bookData.optString("name", "");
        System.out.println(name);
        nameLabel.setText(name);

        // Set other properties
        authorsLabel.setText("Authors: " + bookData.optString("authors", ""));
        ratingLabel.setText("Rating: " + bookData.optDouble("rating", 0.0));
        createdEditionsLabel.setText("Created Editions: " + bookData.optInt("created_editions", 0));
        yearLabel.setText("Year: " + bookData.optInt("year", 0));
    }

    // Method to handle the back button click
    @FXML
    public void handleBackButtonClick() {
        // Switch back to the first scene
        Scene scene1 = homeController.getScene1();
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
}
