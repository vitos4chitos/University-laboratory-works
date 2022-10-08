package graphics;

import Client.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Client client;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml-s/sample.fxml"));
        primaryStage.setTitle("WWTP");
        primaryStage.setScene(new Scene(root, 800, 310));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        client = new Client();
        if(client.setSocketAddress(Integer.parseInt(args[0]), args[1])){
            launch(args);
        }

    }

    public static Client getClient(){
        return client;
    }
}
