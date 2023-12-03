package com.example.bookshq;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.JSONObject;

public class DetailsController {

    @FXML
    private Label nameLabel, heightLabel, ageLabel, birthdayLabel,
            netWorthLabel, genderLabel, nationalityLabel, isAliveLabel;

    private Stage primaryStage;
    private com.example.bookshq.HomeController homeController;

    public void setHomeController(com.example.bookshq.HomeController homeController) {
        this.homeController = homeController;
    }

    // Method to initialize data in the second scene
    public void initData(JSONObject celebrityData, Stage primaryStage, com.example.bookshq.HomeController homeController) {
        setHomeController(homeController);
        this.primaryStage = primaryStage;
        String name = celebrityData.optString("name", "");
        nameLabel.setText(name);

        // Set other properties as a list
        heightLabel.setText("Height: " + celebrityData.optDouble("height", 0.0));
        ageLabel.setText("Age: " + celebrityData.optInt("age", 0));
        birthdayLabel.setText("Birthday: " + celebrityData.optString("birthday", ""));
        netWorthLabel.setText("Net Worth: " + celebrityData.optDouble("net_worth", 0.0));
        genderLabel.setText("Gender: " + celebrityData.optString("gender", ""));
        nationalityLabel.setText("Nationality: " + celebrityData.optString("nationality", ""));
        isAliveLabel.setText("Is Alive: " + celebrityData.optBoolean("is_alive", false));
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
