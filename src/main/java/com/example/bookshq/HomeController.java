package com.example.bookshq;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HomeController {
    // Assuming you have a class level variable to hold your JSON string
    private JSONArray booksJsonArray = new JSONArray();

    @FXML
    private TextField bookNameTextField;

    @FXML
    private ListView<String> booksListView;

    private BooksHQ mainApp;
    private DetailsController detailsController;

    // Reference to the main application
    public void setMainApp(BooksHQ mainApp) {
        this.mainApp = mainApp;
    }

    // Set the second scene controller
    public void setDetailsController(DetailsController detailsController) {
        this.detailsController = detailsController;
    }

    // Method to set the JSON response (for example, in your HTTP request method)
    private void setBooksJsonArray(JSONArray jsonArray) {
        this.booksJsonArray = jsonArray;
    }

    private Optional<JSONObject> findJsonObjectByName(String name) {
        for (int i = 0; i < booksJsonArray.length(); i++) {
            JSONObject jsonObject = booksJsonArray.getJSONObject(i);
            String bookNameFromJson = jsonObject.optString("name", "");

            if (bookNameFromJson.equals(name)) {
                return Optional.of(jsonObject);
            }
        }

        return Optional.empty();
    }

    @FXML
    private void handleGetInfoButtonClick() {
        String apiKey = "vx6VNkE8Do7iHftxWoyPlw==yLBDyAnUFQT3CB11";
        String bookName = bookNameTextField.getText();

        try {
            URL url = new URL("https://api.api-ninjas.com/v1/celebrity?name=" + bookName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Api-Key", apiKey);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response into a JSONArray
                JSONArray jsonArray = new JSONArray(response.toString());
                // Clear existing items in the ListView
                booksListView.getItems().clear();

                // Loop through each JSONObject in the array
                for (int i = 0; i < jsonArray.length(); i++) {
                    // Get the current JSONObject
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // Extract the "name" property from the JSONObject
                    String celebrityNameProp = jsonObject.optString("name", "");

                    // Add the celebrityName to the ListView
                    booksListView.getItems().add(celebrityNameProp);
                }

                // Set the JSON response
                setBooksJsonArray(jsonArray);
            } else {
                System.out.println("Error: " + responseCode);
            }

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(booksJsonArray);
    }

    @FXML
    private void handleListViewItemClick() {
        String selectedItem = booksListView.getSelectionModel().getSelectedItem();
        Optional<JSONObject> optionalJsonObject = findJsonObjectByName(selectedItem);

        // Check if the JSONObject is present in the Optional
        if (optionalJsonObject.isPresent()) {
            // Unwrap the JSONObject from the Optional
            JSONObject jsonObject = optionalJsonObject.get();

            // Pass the JSONObject to showDetailsScene
            mainApp.showDetailsScene(jsonObject);
        }
    }

    // Method to retrieve the first scene
    public Scene getScene1() {
        return bookNameTextField.getScene();
    }

    // Method to create the second scene dynamically
    public Scene getScene2(JSONObject selectedItemJson, Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(BooksHQ.class.getResource("details-view.fxml"));
        Scene scene2 = new Scene(loader.load(), 640, 480);

        // Pass the selected item and primaryStage to the second scene controller
        DetailsController detailsController = loader.getController();
        detailsController.initData(selectedItemJson, primaryStage, this);

        return scene2;
    }
}
