package sample;

import javafx.application.Application;
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

        primaryStage.setTitle("Course work");

        AnchorPane root = new AnchorPane();


        Labyrinth lab = new Labyrinth(20, 20);
        Scene scene = new Scene(root, lab.getSizeX()*35, lab.getSizeY()*35);
        scene.setFill(null);
        Drawing.drawLabyrinth(root, lab);


        Bespilotnik  bespilotnik = new Bespilotnik(Bespilotnik.getCentre()[0], Bespilotnik.getCentre()[1], 12, 12);
        root.getChildren().add(bespilotnik);

        scene.setOnKeyPressed(bespilotnik);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
