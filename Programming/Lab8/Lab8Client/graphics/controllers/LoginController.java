package graphics.controllers;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Client.Client;
import graphics.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    Client client = Main.getClient();
    private ResourceBundle cont;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Login_info;

    @FXML
    private PasswordField password_info;

    @FXML
    private Button ButtonLogIn;

    @FXML
    private Button BackButton;

    @FXML
    private Label TextT;

    @FXML
    void initialize() {
        cont = TableController.bundleMain;
        BackButton.setText(cont.getString("Back"));
        ButtonLogIn.setText(cont.getString("Log in"));
        TextT.setText(cont.getString("Enter your username and password"));
        Login_info.setPromptText(cont.getString("username"));
        password_info.setPromptText(cont.getString("password"));
        BackButton.setOnAction(event -> {
            Stage stage = (Stage) BackButton.getScene().getWindow();
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/graphics/fxml-s/sample.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("WWTP");
            stage.setResizable(false);
            stage.show();
        });

        ButtonLogIn.setOnAction(event -> {
            if(client.logRegIn("log", Login_info.getText(), password_info.getText())){
                Stage stage = (Stage) ButtonLogIn.getScene().getWindow();
                stage.close();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/graphics/fxml-s/WorkingSpace.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stageg = new Stage();
                stageg.setScene(new Scene(root));
                stageg.setTitle("WWTP");
                stageg.setResizable(false);
                stageg.show();
            }
            else {
                Login_info.clear();
                password_info.clear();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/graphics/fxml-s/LoginError.fxml"));
                try{
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stageg = new Stage();
                stageg.setScene(new Scene(root));
                stageg.setTitle(cont.getString("Error"));
                stageg.setResizable(false);
                stageg.show();
            }
        });

    }
}