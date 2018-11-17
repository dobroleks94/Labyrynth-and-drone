package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.control.GroupBespil;
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


        Bespilotnik  bespilotnik = new Bespilotnik(Bespilotnik.getCentre()[0], Bespilotnik.getCentre()[1], 12, 12, lab);
        root.getChildren().add(bespilotnik);

        Bespilotnik  bespilotnik2 = new Bespilotnik(bespilotnik);
        root.getChildren().add(bespilotnik2);

        Bespilotnik  bespilotnik3 = new Bespilotnik(bespilotnik2);
        root.getChildren().add(bespilotnik3);

        Bespilotnik  bespilotnik4 = new Bespilotnik(bespilotnik3);
        root.getChildren().add(bespilotnik4);
        Bespilotnik  bespilotnik5 = new Bespilotnik(bespilotnik4);
        root.getChildren().add(bespilotnik5);

        Bespilotnik  bespilotnik6 = new Bespilotnik(bespilotnik5);
        root.getChildren().add(bespilotnik6);

        Bespilotnik  bespilotnik7 = new Bespilotnik(bespilotnik6);
        root.getChildren().add(bespilotnik7);
        Bespilotnik  bespilotnik8 = new Bespilotnik(bespilotnik7);
        root.getChildren().add(bespilotnik8);

        Bespilotnik  bespilotnik9 = new Bespilotnik(bespilotnik8);
        root.getChildren().add(bespilotnik9);

        Bespilotnik  bespilotnik10 = new Bespilotnik(bespilotnik9);
        root.getChildren().add(bespilotnik10);

        System.out.println(lab.getFinX() + " " + lab.getFinY());

        scene.setOnKeyPressed(bespilotnik);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
