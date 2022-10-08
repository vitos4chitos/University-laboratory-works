package graphics.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import Client.Client;
import Data.Color;
import Data.Country;
import Data.Person;
import communication.Packet;
import graphics.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ShowController {

    Client client = Main.getClient();
    private ResourceBundle cont;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Delete;

    @FXML
    private TextField Id;

    @FXML
    private TextField Name;

    @FXML
    private TextField Coor;

    @FXML
    private TextField CD;

    @FXML
    private TextField Height;

    @FXML
    private TextField PId;

    @FXML
    private TextField HC;

    @FXML
    private TextField Nat;

    @FXML
    private Text IdT;

    @FXML
    private Text NameT;

    @FXML
    private Text CoorT;

    @FXML
    private Text CDT;

    @FXML
    private Text HeighT;

    @FXML
    private Text PassT;

    @FXML
    private Text HairT;

    @FXML
    private TextField Loc;

    @FXML
    private TextField User;

    @FXML
    private Text NatT;

    @FXML
    private Text LocT;

    @FXML
    private Text UserT;

    @FXML
    private Button Update;

    @FXML
    private Button OkButton;




    @FXML
    void initialize() {
        cont = TableController.bundleMain;
        IdT.setText(cont.getString("id") + ":");
        NameT.setText(cont.getString("name") + ":");
        CoorT.setText(cont.getString("coordinates") + ":");
        CDT.setText(cont.getString("creation date") + ":");
        HeighT.setText(cont.getString("height") + ":");
        PassT.setText(cont.getString("Passport id") + ":");
        HairT.setText(cont.getString(("HairColor")) + ":");
        NatT.setText(cont.getString("nationality") + ":");
        LocT.setText(cont.getString("Location") + ":");
        UserT.setText(cont.getString("user") + ":");
        Delete.setText(cont.getString("delete") + ":");
        Update.setText(cont.getString("update") + ":");
        Id.setText(TableController.toShow.getId().toString());
        Name.setText(TableController.toShow.getName());
        Coor.setText(TableController.toShow.getCoordinates().toString());
        CD.setText(getDate(TableController.toShow.getCreationDate()));
        Height.setText(TableController.toShow.getHeight().toString());
        PId.setText(TableController.toShow.getPassportID());
        HC.setText(TableController.toShow.getHairColor().toString());
        Nat.setText(TableController.toShow.getNationality().toString());
        Loc.setText(TableController.toShow.getLocation().toString());
        User.setText(TableController.toShow.getUser());
        Update.setOnAction(event -> {
            Stage stage = (Stage) Update.getScene().getWindow();
            try{
                Person person = new Person(TableController.toShow.getId(), Name.getText(),
                        Integer.parseInt(Coor.getText().split(" ")[0]),
                        Double.valueOf(Coor.getText().split(" ")[1]), null, Float.valueOf(Height.getText()),
                        PId.getText(), Color.valueOf(HC.getText()), Country.valueOf(Nat.getText()), Float.parseFloat(Loc.getText().split(" ")[0]),
                        Long.valueOf(Loc.getText().split(" ")[1]), Long.valueOf(Loc.getText().split(" ")[2]), TableController.toShow.getUser());
                if(!person.equals(TableController.toShow)){
                    Packet packet = client.run("update id", person, TableController.toShow.getId().toString());
                    if(!packet.getMode()) {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/graphics/fxml-s/AccessError.fxml"));
                        try {
                            loader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Parent root = loader.getRoot();
                        Stage stageg = new Stage();
                        stageg.setScene(new Scene(root));
                        stageg.setTitle("Error");
                        stageg.setResizable(false);
                        stageg.showAndWait();
                    }
                    }
                }
            catch (Exception e){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/graphics/fxml-s/RegUpError1.fxml"));
                try {
                    loader.load();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stageg = new Stage();
                stageg.setScene(new Scene(root));
                stageg.setResizable(false);
                stageg.setTitle(cont.getString("Error"));
                stageg.showAndWait();
            }
            stage.close();

        });
        OkButton.setOnAction(event -> {
            Stage stage = (Stage) OkButton.getScene().getWindow();
            stage.close();
        });
        Delete.setOnAction(event -> {
            Stage stage = (Stage) OkButton.getScene().getWindow();
            Packet packet = client.run("remove_by_id", null, TableController.toShow.getId().toString());
            if(!packet.getMode()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/graphics/fxml-s/AccessError.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stageg = new Stage();
                stageg.setScene(new Scene(root));
                stageg.setTitle(cont.getString("Error"));
                stageg.showAndWait();
            }
            stage.close();
        });
    }
    private String getDate(LocalDate localDate){
        String pr[] = localDate.toString().split("-");
        if(TableController.lang.equals("rus")){
            return pr[2] + "." + pr[1] + "." + pr[0];
        }
        else
        if(TableController.lang.equals("eng")){
            return pr[2] + "/" + pr[1] + "/" + pr[0];
        }
        else
        if(TableController.lang.equals("ces")){
            return localDate.toString();
        }
        else {
            return localDate.toString();
        }
    }
}
