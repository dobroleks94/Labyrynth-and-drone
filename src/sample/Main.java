package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.view.Drawing;

import java.awt.*;
import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*AnchorPane root = new AnchorPane();
        final Scene scene = new Scene(root, 1024, 768);
        scene.setFill(null);
        Labyrinth lab = new Labyrinth(10, 10);
        Drawing.drawLabyrinth(root, lab);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();*/
        Bespilotnik bespilotnik = new Bespilotnik(10, 250, 10, 10);
        Group group = new Group(bespilotnik);
        Scene scene = new Scene(group, 500, 500);
        scene.setOnKeyPressed(bespilotnik);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
