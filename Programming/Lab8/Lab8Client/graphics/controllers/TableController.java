package graphics.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import Client.Client;
import Data.*;
import communication.Packet;
import graphics.Main;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TableController {
    static Person toShow;
    Client client = Main.getClient();
    ObservableList<Person> persons = FXCollections.observableArrayList();
    Boolean filter = false;
    Boolean search = false;
    String sort;
    String serchstr;
    ResourceBundle bundleRu = ResourceBundle.getBundle("graphics.resources.resource");
    ResourceBundle bundleEn = ResourceBundle.getBundle("graphics.resources.resource", new Locale("en", "IE"));
    ResourceBundle bundleHu = ResourceBundle.getBundle("graphics.resources.resource", new Locale("hu"));
    ResourceBundle bundleCs = ResourceBundle.getBundle("graphics.resources.resource", new Locale("cs"));
    static ResourceBundle bundleMain;
    static String lang = "default";

    @FXML
    private ResourceBundle resources;

    @FXML
    private Text AnswerT;

    @FXML
    private URL location;

    @FXML
    private Button AddButton;

    @FXML
    private Button RemoveByIdButton;

    @FXML
    private Button ClearButton;

    @FXML
    private Button HelpButton;

    @FXML
    private Button InfoButton;

    @FXML
    private Button UpdateIdButton;

    @FXML
    private Button ExecuteScriptButton;

    @FXML
    private Button AddIfMaxButton;

    @FXML
    private Button RemoveLowerButton;

    @FXML
    private Button HistroyButton;

    @FXML
    private Button FilterButton;

    @FXML
    private Button SearchButton;

    @FXML
    private Button UniqueLocationButton;

    @FXML
    private Button ShowButton;

    @FXML
    private TextArea outputField;

    @FXML
    private Button ExitButton;

    @FXML
    private TableView<Person> dbTable;

    @FXML
    private TableColumn<Person, Integer> idColoumn;

    @FXML
    private TableColumn<Person, String> nameColoumn;

    @FXML
    private TableColumn<Person, Coordinates> coordColoumn;

    @FXML
    private TableColumn<Person, java.time.LocalDate> creatColoumn;

    @FXML
    private TableColumn<Person, Double> heightColoumn;

    @FXML
    private TableColumn<Person, String> passportColoumn;

    @FXML
    private TableColumn<Person, Color> haircolorColoumn;

    @FXML
    private TableColumn<Person, Country> nationalityColoumn;

    @FXML
    private TableColumn<Person, Location> locationColoumn;

    @FXML
    private TableColumn<Person, String> userColoumn;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField coordField;

    @FXML
    private TextField creatField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField passportField;

    @FXML
    private TextField haircolorField;

    @FXML
    private TextField nationalityField;

    @FXML
    private TextField locationField;

    @FXML
    private TextField userField;

    @FXML
    private Canvas canvas;

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
    private TextField filePath;

    @FXML
    private MenuButton SortMenu;

    @FXML
    private MenuButton SearchMenu;

    @FXML
    private TextField SearchField;

    public TableController(){
    }


    @FXML
    void ces(ActionEvent event) {
        bundleMain = bundleCs;
        lang = "ces";
        UPDLanguage();
    }

    @FXML
    void eng(ActionEvent event) {
        bundleMain = bundleEn;
        lang = "eng";
        UPDLanguage();
    }

    @FXML
    void mag(ActionEvent event) {
        bundleMain = bundleHu;
        lang = "mag";
        UPDLanguage();
    }

    @FXML
    void rus(ActionEvent event) {
        bundleMain = bundleRu;
        lang = "rus";
        UPDLanguage();

    }
    @FXML
    void showFromTable(MouseEvent event) {
        Person person = (Person) dbTable.getSelectionModel().getSelectedItem();
        try {
            person.getName();
            toShow = person;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/graphics/fxml-s/show.fxml"));
            try{
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stageg = new Stage();
            stageg.setScene(new Scene(root));
            stageg.setTitle("WWTP");
            stageg.initOwner(((Node)event.getSource()).getScene().getWindow());
            stageg.initModality(Modality.APPLICATION_MODAL);
            stageg.setResizable(false);
            stageg.showAndWait();
        }
        catch (Exception e){

        }
        UPDTable();
    }

    @FXML
    void showPerson(MouseEvent mouseEvent) {
        double mouse_x = mouseEvent.getX();
        double mouse_y = mouseEvent.getY();
        ArrayList<Person> people = new ArrayList<>(persons);
        for (Person person : people) {
            int x = person.getCoordinates().getX() + 400;
            int y = (int) Double.parseDouble(person.getCoordinates().getY().toString()) + 250;
            int size = (int) (person.getHeight() / 2);
            double offset = System.currentTimeMillis() / 300.;

            if(mouse_x > x && mouse_x < x + size && mouse_y > y +Math.sin(offset)*20&& mouse_y < y +Math.sin(offset)*20+size){
                toShow = person;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/graphics/fxml-s/show.fxml"));
                try{
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stageg = new Stage();
                stageg.setScene(new Scene(root));
                stageg.setTitle("WWTP");
                stageg.initOwner(((Node)mouseEvent.getSource()).getScene().getWindow());
                stageg.initModality(Modality.APPLICATION_MODAL);
                stageg.setResizable(false);
                stageg.showAndWait();
                UPDTable();
            }
            }
    }

    @FXML
    private Button ClearAns;

    @FXML
    private MenuItem SortName;

    @FXML
    private MenuItem SortId;

    @FXML
    private MenuItem SortCoor;

    @FXML
    private MenuItem SortCreat;

    @FXML
    private MenuItem SortHeight;

    @FXML
    private MenuItem SortPass;

    @FXML
    private MenuItem SortHair;

    @FXML
    private MenuItem SortNat;

    @FXML
    private MenuItem SortLoc;

    @FXML
    private MenuItem SortUser;

    @FXML
    private MenuItem DefaultSort;

    @FXML
    private MenuItem SearchId;

    @FXML
    private MenuItem SearchName;

    @FXML
    private MenuItem SearchCoor;

    @FXML
    private MenuItem SearchDate;

    @FXML
    private MenuItem SearchHeight;

    @FXML
    private MenuItem SearchPassport;

    @FXML
    private MenuItem SearchHair;

    @FXML
    private MenuItem SearchNat;

    @FXML
    private MenuItem Searchloc;

    @FXML
    private MenuItem searchUser;
    @FXML
    private Tab DrawbTab;
    @FXML
    private Tab TableTab;

    @FXML
    private MenuItem DefaultSerch;
    private List<javafx.scene.paint.Color> colors = new ArrayList<>();
    private ArrayList<String> users = new ArrayList<>();
    private DoubleProperty opacity;
    private DoubleProperty koef;
    private AnimationTimer timer;
    private Timeline timeline;



    @FXML
    void initialize() {
        colors.add(javafx.scene.paint.Color.GREEN);
        colors.add(javafx.scene.paint.Color.RED);
        colors.add(javafx.scene.paint.Color.ORANGE);
        colors.add(javafx.scene.paint.Color.HOTPINK);
        colors.add(javafx.scene.paint.Color.AQUA);
        colors.add(javafx.scene.paint.Color.SILVER);
        colors.add(javafx.scene.paint.Color.CHOCOLATE);
        UPDLanguage();
        UPDTable();

        opacity  = new SimpleDoubleProperty(0.5);
        koef  = new SimpleDoubleProperty(0.3);
        timeline = new Timeline(

                new KeyFrame(Duration.seconds(0),
                        new KeyValue(opacity, 0),
                        new KeyValue(koef, 0.3)
                ),
                new KeyFrame(Duration.seconds(2),
                        new KeyValue(opacity, 1),
                        new KeyValue(koef, 0.7)
                )

        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(Timeline.INDEFINITE);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                draw(opacity, koef);
            }
        };


        SortMenu.setStyle("-fx-background-color: red");
        SearchMenu.setStyle("-fx-background-color: red");
        outputField.setText(bundleMain.getString("Hello") + "\n" + bundleMain.getString("Your login:") +
                " " + client.getLogin());

        ExitButton.setOnAction(event -> {
            System.exit(0);
        });
        ShowButton.setOnAction(event -> {
            search = false;
            filter = false;
            SortMenu.setStyle("-fx-background-color: red");
            SortMenu.setText(bundleMain.getString("Sorted by"));
            SearchMenu.setStyle("-fx-background-color: red");
            SearchMenu.setText(bundleMain.getString("Search"));
            SearchField.clear();
            UPDTable();
            outputField.setText(outputField.getText() + '\n' + bundleMain.getString("Colleсtion was update"));
            idField.clear();
            nameField.clear();
            coordField.clear();
            creatField.clear();
            heightField.clear();
            passportField.clear();
            haircolorField.clear();
            nationalityField.clear();
            locationField.clear();
            userField.clear();
            userField.setText(client.getLogin());
        });
        AddButton.setOnAction(event -> {
            try {
                String[] strings = new String[13];
                strings[0] = null;
                strings[1] = nameField.getText();
                strings[2] = coordField.getText().split(" ")[0];
                strings[3] = coordField.getText().split(" ")[1];
                strings[4] = null;
                strings[5] = heightField.getText();
                strings[6] = passportField.getText();
                strings[7] = haircolorField.getText();
                strings[8] = nationalityField.getText();
                strings[9] = locationField.getText().split(" ")[0];
                strings[10] = locationField.getText().split(" ")[1];
                strings[11] = locationField.getText().split(" ")[2];
                strings[12] = userField.getText();
                if (checkDate(strings)) {
                    Packet packet = client.run("add", makePerson(strings, false), null);
                    if (packet.getMode()) {
                        idField.clear();
                        nameField.clear();
                        coordField.clear();
                        creatField.clear();
                        heightField.clear();
                        passportField.clear();
                        haircolorField.clear();
                        nationalityField.clear();
                        locationField.clear();
                        userField.clear();
                        userField.setText(client.getLogin());
                        outputField.setText(outputField.getText() + '\n' + bundleMain.getString("Item added to the collection"));
                    } else {
                        makeError();
                    }
                } else {
                    makeError();
                }
            }
            catch (Exception e){
                makeError();
            }
            UPDTable();
            dbTable.setItems(persons);

        });
        RemoveByIdButton.setOnAction(event -> {
            int id;
            try{
                id = Integer.parseInt(idField.getText());
                Packet packet = client.run("remove_by_id", null, idField.getText());
                if(packet.getMode()){
                    outputField.setText(outputField.getText() + '\n' + bundleMain.getString("The item was removed from the collection"));
                }
                else{
                    makeAccessError();
                }
            }
            catch (Exception e){
                makeError();
            }
            UPDTable();
        });
        ClearButton.setOnAction(event -> {
            client.run("clear", null, null);
            outputField.setText(outputField.getText() + '\n' + bundleMain.getString("Items were removed from the collection"));
            UPDTable();
        });
        HelpButton.setOnAction(event -> {
            try {
                Scanner scanner = new Scanner(new File(bundleMain.getString("PathHelp")));
                while (scanner.hasNext()){
                    outputField.setText(outputField.getText() + '\n' + scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        ClearAns.setOnAction(event -> {
            outputField.clear();
            outputField.setText(bundleMain.getString("Your login:") + " " + client.getLogin());
        });
        InfoButton.setOnAction(event -> {
            Packet packet = client.run("info", null, null);
            for(String s: packet.giveAnswer())
                outputField.setText(outputField.getText() + '\n' + bundleMain.getString("Collection type") + " HashSet" + "\n" +
                        bundleMain.getString("Items type:") +
                        " Person" + "\n" + bundleMain.getString("Count of items:") + " " + s);
            UPDTable();
        });
        UpdateIdButton.setOnAction(event -> {
            try {
                String[] strings = new String[13];
                strings[0] = idField.getText();
                strings[1] = nameField.getText();
                strings[2] = coordField.getText().split(" ")[0];
                strings[3] = coordField.getText().split(" ")[1];
                strings[4] = null;
                strings[5] = heightField.getText();
                strings[6] = passportField.getText();
                strings[7] = haircolorField.getText();
                strings[8] = nationalityField.getText();
                strings[9] = locationField.getText().split(" ")[0];
                strings[10] = locationField.getText().split(" ")[1];
                strings[11] = locationField.getText().split(" ")[2];
                strings[12] = userField.getText();
                int id = Integer.parseInt(idField.getText());
                if (checkDate(strings)) {
                    Packet packet = client.run("update id", makePerson(strings, false), idField.getText());
                    if (packet.getMode()) {
                        idField.clear();
                        nameField.clear();
                        coordField.clear();
                        creatField.clear();
                        heightField.clear();
                        passportField.clear();
                        haircolorField.clear();
                        nationalityField.clear();
                        locationField.clear();
                        userField.clear();
                        userField.setText(client.getLogin());
                        outputField.setText(outputField.getText() + '\n' +
                                bundleMain.getString("The object is updated in the collection"));
                    } else {
                        makeAccessError();
                    }
                } else {
                    makeError();
                }
            }
            catch (Exception e){
                makeError();
            }
            UPDTable();

        });
        ExecuteScriptButton.setOnAction((event -> {
            //реализую позже
        }));
        AddIfMaxButton.setOnAction(event -> {
            try {
                String[] strings = new String[13];
                strings[0] = null;
                strings[1] = nameField.getText();
                strings[2] = coordField.getText().split(" ")[0];
                strings[3] = coordField.getText().split(" ")[1];
                strings[4] = null;
                strings[5] = heightField.getText();
                strings[6] = passportField.getText();
                strings[7] = haircolorField.getText();
                strings[8] = nationalityField.getText();
                strings[9] = locationField.getText().split(" ")[0];
                strings[10] = locationField.getText().split(" ")[1];
                strings[11] = locationField.getText().split(" ")[2];
                strings[12] = userField.getText();
                if (checkDate(strings)) {
                    Packet packet = client.run("add_if_max", makePerson(strings, false), null);
                    if (packet.getMode()) {
                        idField.clear();
                        nameField.clear();
                        coordField.clear();
                        creatField.clear();
                        heightField.clear();
                        passportField.clear();
                        haircolorField.clear();
                        nationalityField.clear();
                        locationField.clear();
                        userField.clear();
                        userField.setText(client.getLogin());
                        outputField.setText(outputField.getText() + '\n' + bundleMain.getString("Item added to the collection"));
                    } else {
                        outputField.setText(outputField.getText() + '\n' +
                                bundleMain.getString("The element was not added because the value is not the maximum"));
                        makeError();
                    }
                } else {
                    makeError();
                }
            }
            catch (Exception e){
                makeError();
            }
            UPDTable();
        });
        RemoveLowerButton.setOnAction(event -> {
            try {
                Float height = Float.valueOf(heightField.getText());
                if(Float.parseFloat(height.toString()) - 0.00001 <= 0.0f) {
                    makeError();
                }
                else{
                    client.run("remove_lower", null, height.toString());
                    outputField.setText(outputField.getText() + '\n' +
                            bundleMain.getString("Items were removed from the collection"));
                }
            } catch (Exception e) {
                makeError();
            }
            UPDTable();
        });
        HistroyButton.setOnAction(event -> {
            Packet packet = client.run("history", null, null);
            outputField.setText(outputField.getText() + '\n' +  bundleMain.getString("history") + ":");
            for(String s: packet.giveAnswer()){
                outputField.setText(outputField.getText() + '\n' + s);
            }
        });
        UniqueLocationButton.setOnAction(event -> {
            Packet packet = client.run("print_unique_location", null, null);
            outputField.setText(outputField.getText() + "\n" + bundleMain.getString("Unique values of the Location field:"));
            for(String s: packet.giveAnswer())
                outputField.setText(outputField.getText() + "\n" + s);
        });
        SortId.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("id"));
            sort = "id";
            UPDTable();
        });
        SortName.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("name"));
            sort = "name";
            UPDTable();
        });
        SortCoor.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("coordinates"));
            sort = "coor";
            UPDTable();
        });
        SortCreat.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("creation date"));
            sort = "create";
            UPDTable();
        });
        SortHair.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("HairColor"));
            sort = "hair";
            UPDTable();
        });
        SortHeight.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("height"));
            sort = "height";
            UPDTable();
        });
        SortNat.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("nationality"));
            sort = "nat";
            UPDTable();
        });
        SortLoc.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("Location"));
            sort = "loc";
            UPDTable();
        });
        SortPass.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("Passport id"));
            sort = "pass";
            UPDTable();
        });
        SortUser.setOnAction(event -> {
            filter = true;
            SortMenu.setStyle("-fx-background-color: green");
            SortMenu.setText(bundleMain.getString("Sorted by") + bundleMain.getString("user"));
            sort = "user";
            UPDTable();
        });
        DefaultSort.setOnAction(event -> {
            filter = false;
            SortMenu.setStyle("-fx-background-color: red");
            SortMenu.setText(bundleMain.getString("Sorted by"));
            sort = "def";
            UPDTable();
        });
        SearchId.setOnAction(event -> {
            search = true;
            serchstr = "id";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("id"));
            UPDTable();
        });
        SearchName.setOnAction(event -> {
            search = true;
            serchstr = "name";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("name"));
            UPDTable();
        });
        SearchCoor.setOnAction(event -> {
            search = true;
            serchstr = "coor";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("coordinates"));
            UPDTable();
        });
        SearchDate.setOnAction(event -> {
            search = true;
            serchstr = "create";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("creation date"));
            UPDTable();
        });
        SearchHeight.setOnAction(event -> {
            search = true;
            serchstr = "height";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("height"));
            UPDTable();
        });
        SearchPassport.setOnAction(event -> {
            search = true;
            serchstr = "pass";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("Passport id"));
            UPDTable();
        });
        SearchHair.setOnAction(event -> {
            search = true;
            serchstr = "hair";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("HairColor"));
            UPDTable();
        });
        SearchNat.setOnAction(event -> {
            search = true;
            serchstr = "nat";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("nationality"));
            UPDTable();
        });
        Searchloc.setOnAction(event -> {
            search = true;
            serchstr = "loc";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("Location"));
            UPDTable();
        });
        searchUser.setOnAction(event -> {
            search = true;
            serchstr = "user";
            SearchMenu.setStyle("-fx-background-color: green");
            SearchMenu.setText(bundleMain.getString("user"));
            UPDTable();
        });
        DefaultSerch.setOnAction(event -> {
            search = false;
            serchstr = "default";
            SearchMenu.setStyle("-fx-background-color: red");
            SearchMenu.setText(bundleMain.getString("Search"));
            SearchField.clear();
            UPDTable();
        });
        DrawbTab.setOnSelectionChanged(event -> {
            startDraw();
        });
        TableTab.setOnSelectionChanged(event -> {
            startDraw();
        });


    }

    private synchronized void UPDTable(){
        Packet packet = client.run("show", null, null);
        ArrayList<String> first = packet.giveAnswer();
        persons.clear();
        for(String s: first){
            String[] se = s.split(" ");
            persons.add(makePerson(se, true));
        }
        if(filter){
            filtring();
        }
        if(search){
            searching();
        }
        dbTable.setEditable(true);
        idColoumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColoumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordColoumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        creatColoumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        heightColoumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        passportColoumn.setCellValueFactory(new PropertyValueFactory<>("passportID"));
        haircolorColoumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        nationalityColoumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        locationColoumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        userColoumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        creatColoumn.setCellFactory(column -> {
            TableCell<Person, LocalDate> cell = new TableCell<Person, LocalDate>() {

                @Override
                protected void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    }
                    else {
                        setText(getDate(item));
                    }
                }
            };

            return cell;
        });
        dbTable.setItems(persons);
        if(DrawbTab.isSelected()) {
            startDraw();
        }
    }

    private Person makePerson(String[] property, boolean read){
        Integer id = null;
        if(read){
            id = Integer.valueOf(property[0]);
        }
        String name = property[1];
        int cx = Integer.parseInt(property[2]);
        Double cy = Double.valueOf(property[3]);
        LocalDate creationDate = null;
        if(read) {
            int year = Integer.parseInt(property[4].split("-")[0]),
                    day = Integer.parseInt(property[4].split("-")[2]),
                    month = Integer.parseInt(property[4].split("-")[1]);
            creationDate = LocalDate.of(year, month, day);
        }
        Float height = Float.valueOf(property[5]);
        String passportID = property[6];
        Color hairColor = Color.valueOf(property[7]);
        Country nationality = Country.valueOf(property[8]);
        float lx = Float.parseFloat(property[9]);
        Long ly = Long.valueOf(property[10]);
        Long lz = Long.valueOf(property[11]);
        String user = client.getLogin();
        if(read){
            user = property[12];
        }
        return new Person(id, name, cx, cy, creationDate, height, passportID, hairColor, nationality, lx, ly, lz, user);
    }

    private boolean checkDate(String[] property){
        try{
            String name = property[1];
            if(name.length() < 1){
                return false;
            }
            int cx = Integer.parseInt(property[2]);
            Double cy = Double.valueOf(property[3]);
            Float height = Float.valueOf(property[5]);
            String passportID = property[6];
            if(passportID.length() > 20 || passportID.length() < 1){
                return false;
            }
            Color hairColor = Color.valueOf(property[7]);
            Country nationality = Country.valueOf(property[8]);
            float lx = Float.parseFloat(property[9]);
            Long ly = Long.valueOf(property[10]);
            Long lz = Long.valueOf(property[11]);
            String user = property[12];
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    private void makeError(){
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
        stage.setTitle(bundleMain.getString("Error"));
        stage.showAndWait();
    }
    private void makeAccessError(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/graphics/fxml-s/AccessError.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(bundleMain.getString("Error"));
        stage.showAndWait();
    }
    private synchronized void searching(){
        List<Person> p;
        if(serchstr.equals("id")) {
            p = persons.stream().filter(x -> x.getId().toString().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("name")) {
            p = persons.stream().filter(x -> x.getName().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("coor")) {
            p = persons.stream().filter(x -> x.getCoordinates().toString().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("create")) {
            p = persons.stream().filter(x -> getDate(x.getCreationDate()).matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("hair")) {
            p = persons.stream().filter(x -> x.getHairColor().toString().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("height")) {
            p = persons.stream().filter(x -> x.getHeight().toString().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("nat")) {
            p = persons.stream().filter(x -> x.getNationality().toString().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("loc")) {
            p = persons.stream().filter(x -> x.getLocation().toString().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("pass")) {
            p = persons.stream().filter(x -> x.getPassportID().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
        if(serchstr.equals("user")) {
            p = persons.stream().filter(x -> x.getUser().matches(SearchField.getText() + "(.*)"))
                    .collect(Collectors.toList());
            persons = FXCollections.observableArrayList(p);
        }
    }
    private synchronized void filtring() {
        List<Person> p;
        if(sort.equals("id")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        Integer.parseInt(o1.getId().toString()) - Integer.parseInt(o2.getId().toString()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("name")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getName().compareTo(o2.getName()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("coor")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getCoordinates().toString().compareTo(o2.getCoordinates().toString()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("create")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        getDate(o1.getCreationDate()).compareTo(getDate(o1.getCreationDate())))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("hair")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getHairColor().toString().compareTo(o2.getHairColor().toString()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("height")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getHeight().compareTo(o2.getHeight()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("nat")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getNationality().toString().compareTo(o2.getNationality().toString()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("loc")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getLocation().toString().compareTo(o2.getLocation().toString()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("pass")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getPassportID().compareTo(o2.getPassportID()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        if(sort.equals("user")) {
                p = persons.stream().sorted((Comparator<Person>) (o1, o2) ->
                        o1.getUser().compareTo(o2.getUser()))
                        .collect(Collectors.toList());
                persons = FXCollections.observableArrayList(p);
            }
        }
    public void drawPerson(int x, int y, int size, int color, DoubleProperty op, DoubleProperty koef) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(colors.get(color).deriveColor(0, 1, 1, op.get()));
        gc.fillRect(x, y, koef.doubleValue() * size, koef.doubleValue() * size);


    }

    public void draw(DoubleProperty op,DoubleProperty koef) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        users.clear();
        for (Person person : persons) {
            if(!users.contains(person.getUser())){
                users.add(person.getUser());
            }
            int color_owner_id = users.indexOf(person.getUser()) % 7;
            int x = person.getCoordinates().getX() + 400;
            int y = (int) Double.parseDouble(person.getCoordinates().getY().toString()) + 250;
            int size = (int) (person.getHeight() / 2);
            drawPerson(x, y, size, color_owner_id, op, koef);
        }

    }
    private void startDraw(){
        timer.start();
        timeline.play();
    }
    private void stopDraw(){
        timer.stop();
        timeline.stop();
    }
    private void UPDLanguage(){
        Languagebuttom.setText(bundleMain.getString("language"));
        TableTab.setText(bundleMain.getString("table"));
        DrawbTab.setText(bundleMain.getString("draw"));
        idField.setPromptText(bundleMain.getString("id"));
        nameField.setPromptText(bundleMain.getString("name"));
        coordField.setPromptText(bundleMain.getString("coordinates"));
        creatField.setPromptText(bundleMain.getString("creation date"));
        heightField.setPromptText(bundleMain.getString("height"));
        passportField.setPromptText(bundleMain.getString("Passport id"));
        haircolorField.setPromptText(bundleMain.getString("HairColor"));
        nationalityField.setPromptText(bundleMain.getString("nationality"));
        locationField.setPromptText(bundleMain.getString("Location"));
        userField.setPromptText(bundleMain.getString("user"));
        idColoumn.setText(bundleMain.getString("id"));
        nameColoumn.setText(bundleMain.getString("name"));
        coordColoumn.setText(bundleMain.getString("coordinates"));
        creatColoumn.setText(bundleMain.getString("creation date"));
        heightColoumn.setText(bundleMain.getString("height"));
        passportColoumn.setText(bundleMain.getString("Passport id"));
        haircolorColoumn.setText(bundleMain.getString("HairColor"));
        nationalityColoumn.setText(bundleMain.getString("nationality"));
        locationColoumn.setText(bundleMain.getString("Location"));
        userColoumn.setText(bundleMain.getString("user"));
        AddButton.setText(bundleMain.getString("add"));
        RemoveByIdButton.setText(bundleMain.getString("remove by id"));
        ClearButton.setText(bundleMain.getString("Clear"));
        HelpButton.setText(bundleMain.getString("help"));
        InfoButton.setText(bundleMain.getString("Info"));
        UpdateIdButton.setText(bundleMain.getString("Udate id"));
        ExecuteScriptButton.setText(bundleMain.getString("Execute script"));
        AddIfMaxButton.setText(bundleMain.getString("Add if max"));
        RemoveLowerButton.setText(bundleMain.getString("Remove lower"));
        HistroyButton.setText(bundleMain.getString("history"));
        UniqueLocationButton.setText(bundleMain.getString("Print unique location"));
        ShowButton.setText(bundleMain.getString("Show"));
        SortMenu.setText(bundleMain.getString("Sorted by"));
        SearchMenu.setText(bundleMain.getString("Search"));
        filePath.setPromptText(bundleMain.getString("File path to execute"));
        SearchField.setPromptText(bundleMain.getString("What to search"));
        ExitButton.setText(bundleMain.getString("Exit"));
        AnswerT.setText(bundleMain.getString("Answers"));
        ClearAns.setText(bundleMain.getString("Clear answer"));
        outputField.clear();
        outputField.setText(bundleMain.getString("Your login:") + " " + client.getLogin());
        SortId.setText(bundleMain.getString("id"));
        SortName.setText(bundleMain.getString("name"));
        SortCoor.setText(bundleMain.getString("coordinates"));
        SortCreat.setText(bundleMain.getString("creation date"));
        SortHeight.setText(bundleMain.getString("height"));
        SortPass.setText(bundleMain.getString("Passport id"));
        SortHair.setText(bundleMain.getString("HairColor"));
        SortNat.setText(bundleMain.getString("nationality"));
        SortLoc.setText(bundleMain.getString("Location"));
        SortUser.setText(bundleMain.getString("user"));
        SearchId.setText(bundleMain.getString("id"));
        SearchName.setText(bundleMain.getString("name"));
        SearchCoor.setText(bundleMain.getString("coordinates"));
        SearchDate.setText(bundleMain.getString("creation date"));
        SearchHeight.setText(bundleMain.getString("height"));
        SearchPassport.setText(bundleMain.getString("Passport id"));
        SearchHair.setText(bundleMain.getString("HairColor"));
        SearchNat.setText(bundleMain.getString("nationality"));
        Searchloc.setText(bundleMain.getString("Location"));
        searchUser.setText(bundleMain.getString("user"));
        DefaultSerch.setText(bundleMain.getString("Default"));
        DefaultSort.setText(bundleMain.getString("Default"));
        UPDTable();
    }
    private String getDate(LocalDate localDate){
        String pr[] = localDate.toString().split("-");
        if(lang.equals("rus")){
            return pr[2] + "." + pr[1] + "." + pr[0];
        }
        else
            if(lang.equals("eng")){
                return pr[2] + "/" + pr[1] + "/" + pr[0];
            }
            else
            if(lang.equals("ces")){
                return localDate.toString();
            }
            else {
            return localDate.toString();
        }
    }
    }


