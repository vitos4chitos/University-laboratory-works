package graphics.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import Client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {
    ResourceBundle bundleRu = ResourceBundle.getBundle("graphics.resources.resource");
    ResourceBundle bundleEn = ResourceBundle.getBundle("graphics.resources.resource", new Locale("en", "IE"));
    ResourceBundle bundleHu = ResourceBundle.getBundle("graphics.resources.resource", new Locale("hu"));
    ResourceBundle bundleCs = ResourceBundle.getBundle("graphics.resources.resource", new Locale("cs"));
    private ResourceBundle cont;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtonSignUp;

    @FXML
    private Button ButtonLogIn;
    @FXML
    private MenuButton Languagebuttom;

    @FXML
    private MenuItem rusButton;

    @FXML
    private MenuItem CesButton;

    @FXML
    private MenuItem MagButton;

    @FXML
    private MenuItem engButton;

    @FXML
    void ces(ActionEvent event) {
        TableController.bundleMain = bundleCs;
        TableController.lang = "ces";
        cont = bundleCs;
        UPDL();
    }

    @FXML
    void eng(ActionEvent event) {
        TableController.bundleMain = bundleEn;
        cont = bundleEn;
        TableController.lang = "eng";
        UPDL();
    }

    @FXML
    void mag(ActionEvent event) {
        TableController.bundleMain = bundleHu;
        cont = bundleHu;
        TableController.lang = "mag";
        UPDL();
    }

    @FXML
    void rus(ActionEvent event) {
        TableController.bundleMain = bundleRu;
        cont = bundleRu;
        TableController.lang = "rus";
        UPDL();
    }

    @FXML
    void initialize() {
            if(TableController.lang.equals("default")) {
                TableController.bundleMain = bundleEn;
                cont = bundleEn;
                TableController.lang = "eng";
            }
            else{
                UPDL();
            }
            ButtonLogIn.setOnAction(event -> {
                ButtonLogIn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/graphics/fxml-s/LogInButton.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.initOwner(((Node)event.getSource()).getScene().getWindow());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("My app");
                stage.setResizable(false);
                stage.showAndWait();
            });

        ButtonSignUp.setOnAction(event -> {
            ButtonSignUp.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/graphics/fxml-s/SignUpButton.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initOwner(((Node)event.getSource()).getScene().getWindow());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("My app");
            stage.setResizable(false);
            stage.showAndWait();
        });
    }
    private void UPDL(){
        cont = TableController.bundleMain;
        Languagebuttom.setText(cont.getString("language"));
        ButtonLogIn.setText(cont.getString("Log in"));
        ButtonSignUp.setText(cont.getString("Sign up"));
    }
}
