package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    @FXML
    private Canvas canvas;
    private static Stage staticPrimaryStage;
    private static Parent staticRoot;

    public static Parent getStaticRoot() {
        return staticRoot;
    }

    public static Stage getPrimaryStage(){
        return staticPrimaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        staticPrimaryStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        staticRoot = root;
        primaryStage.setTitle("Paint");
        primaryStage.setScene(new Scene(root, 1000, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
