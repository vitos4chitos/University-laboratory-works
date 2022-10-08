package graphics.controllers;

import java.io.IOException;
import java.net.URL;
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

public class SignUpController {

    Client client = Main.getClient();
    private ResourceBundle cont;

    @FXML
    private ResourceBundle resources;

    @FXML
    private Label TextT;

    @FXML
    private URL location;

    @FXML
    private TextField Login_info;

    @FXML
    private PasswordField password_info;

    @FXML
    private Button ButtonSignUp;

    @FXML
    private Button BackButton;

    @FXML
    void initialize() {
        cont = TableController.bundleMain;
        BackButton.setText(cont.getString("Back"));
        ButtonSignUp.setText(cont.getString("Sign up"));
        TextT.setText(cont.getString("Enter your username and password for registration"));
        Login_info.setPromptText(cont.getString("Username (maximum of 15 characters)"));
        password_info.setPromptText(cont.getString("Password (maximum of 15 characters)"));
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
            stage.show();
        });

        ButtonSignUp.setOnAction(event -> {
            if(Login_info.getText().length() > 15 || Login_info.getText().length() < 1 || password_info.getText().length() > 15
                    || password_info.getText().length() < 1) {
                Login_info.clear();
                password_info.clear();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/graphics/fxml-s/RegUpError1.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle(cont.getString("Error"));
                stage.setResizable(false);
                stage.showAndWait();
            }
            else{
                if(client.logRegIn("reg", Login_info.getText(), password_info.getText())){
                    Stage stage = (Stage) ButtonSignUp.getScene().getWindow();
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
                else{
                    Login_info.clear();
                    password_info.clear();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/graphics/fxml-s/RegUpError2.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(cont.getString("Error"));
                    stage.setResizable(false);
                    stage.showAndWait();
                }
            }
        });
    }
}
