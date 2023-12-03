package com.example.bookshq;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class BooksHQ extends Application {
    private Stage primaryStage;
    private HomeController homeController;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        // Load the first scene
        FXMLLoader homeLoader = new FXMLLoader(BooksHQ.class.getResource("home-view.fxml"));
        Parent homeRoot = homeLoader.load();
        homeController = homeLoader.getController();

        Scene scene1 = new Scene(homeRoot, 640, 480);

        // Set the controller for the first scene
        homeController.setMainApp(this);

        // Load the second scene (details-view.fxml)
        FXMLLoader detailsLoader = new FXMLLoader(BooksHQ.class.getResource("details-view.fxml"));
        Parent detailsRoot = detailsLoader.load();
        DetailsController detailsController = detailsLoader.getController();

        // Set the second scene controller in the first scene controller
        homeController.setDetailsController(detailsController);

        // Set the HelloController reference in DetailsController
        detailsController.setHomeController(homeController);

        Scene scene2 = new Scene(detailsRoot, 400, 300);

        // Set the second scene controller in the first scene controller
        homeController.setDetailsController(detailsController);

        // Set the initial scene
        primaryStage.setScene(scene1);
        primaryStage.setTitle("Books HQ");
        primaryStage.show();
    }

    // Method to switch to the second scene
    public void showDetailsScene(JSONObject selectedItemJson) {
        try {
            primaryStage.setScene(homeController.getScene2(selectedItemJson, primaryStage));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
