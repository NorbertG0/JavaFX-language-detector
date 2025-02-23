import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Title
        Label title = new Label("\uD83C\uDF0D Language detector");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        title.setStyle("-fx-text-fill: f2f2f2; -fx-effect: dropshadow(three-pass-box, #1f1e1e, 5, 0.5, 2, 2)");
        VBox.setMargin(title, new Insets(40, 0, 0, 70));

        // Sentence text field
        TextArea textArea = new TextArea();
        textArea.setPrefWidth(200);
        textArea.setPrefHeight(100);
        textArea.setStyle("-fx-background-color: #363636; -fx-control-inner-background: #363636; -fx-text-fill: f2f2f2; -fx-font-size: 14px; -fx-padding: 10px; -fx-border-color: #2b2b2b; -fx-border-width: 1px; -fx-effect: dropshadow(three-pass-box, #1f1e1e, 5, 0.5, 2, 2)");
        Label textLabel = new Label("Type your sentence to detect the language");
        textLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 10));
        textLabel.setStyle("-fx-text-fill: f2f2f2; -fx-effect: dropshadow(three-pass-box, #1f1e1e, 5, 0.5, 2, 2)");
        VBox.setMargin(textArea, new Insets(0, 90, 0, 90));
        VBox.setMargin(textLabel, new Insets(20, 90, 0, 90));

        // Label to display the detected language
        Label resultLabel = new Label();
        resultLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        resultLabel.setStyle("-fx-text-fill: f2f2f2; -fx-effect: dropshadow(three-pass-box, #1f1e1e, 5, 0.5, 2, 2)");
        VBox.setMargin(resultLabel, new Insets(20, 0, 0, 40));

        // Submit button
        Button submitButton = new Button("Detect ✅");
        submitButton.setStyle("-fx-background-color: #363636; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px; -fx-effect: dropshadow(three-pass-box, #1f1e1e, 5, 0.5, 2, 2)");
        VBox.setMargin(submitButton, new Insets(20, 150, 0, 210));

        submitButton.setOnAction(e -> {
            // Getting text from user
            String userInput = textArea.getText();

            // Check if field is not empty
            if (userInput.isEmpty()) {
                resultLabel.setText("\t\t\t\t\tType something!");
                return;
            }

            // API request after clicking submit button
            try {
                String query = URLEncoder.encode(userInput, "UTF-8");
                String apiKey = "your API key";
                URL url = new URL("https://ws.detectlanguage.com/0.2/detect?q=" + query + "&key=" + apiKey);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // API request method
                connection.setRequestMethod("GET");

                // Getting response code
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK - Success

                    // Reading response data
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder(); // Collecting response lines

                    // Reading the response data line by line
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close(); // Closing the BufferedReader after reading the data

                    // JSON parsing
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    JSONArray detections = jsonResponse.getJSONObject("data").getJSONArray("detections");
                    JSONObject firstDetection = detections.getJSONObject(0);

                    String language = firstDetection.getString("language");
                    boolean isReliable = firstDetection.getBoolean("isReliable");
                    double confidence = firstDetection.getDouble("confidence");
                    String reliable;

                    if (isReliable == true){
                        reliable = "✅";
                    }
                    else{
                        reliable = "❌";
                    }

                    // Results
                    resultLabel.setText("Detected language: " + language + " |  Reliability: " + reliable + " |  Confidence: " + confidence);
                } else {
                    resultLabel.setText("GET request failed with response code: " + responseCode);
                }
                connection.disconnect();

            } catch (Exception ex) {

                // Exception if response is wrong
                resultLabel.setText("API error");
                ex.printStackTrace();
            }
        });

        // Creating layout
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-background-color: #424242;");
        vbox.getChildren().addAll(title, textLabel, textArea, submitButton, resultLabel);

        // Creating scene
        Scene scene = new Scene(vbox, 500, 400);

        primaryStage.setTitle("Language Detector");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
