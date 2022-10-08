package graphics.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoginErrorController {
    private ResourceBundle cont;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button OkButton;

    @FXML
    private Label TextT;

    @FXML
    void initialize() {
        cont = TableController.bundleMain;
        TextT.setText(cont.getString("Invalid username or password"));
        OkButton.setOnAction(event -> {
            Stage stage = (Stage) OkButton.getScene().getWindow();
            stage.close();
        });
    }
}
