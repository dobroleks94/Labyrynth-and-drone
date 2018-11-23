package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.model.Operator;
import sample.view.Drawing;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Course work");

        AnchorPane root = new AnchorPane();


        Labyrinth lab = new Labyrinth(20, 20,5);
        Scene scene = new Scene(root, lab.getSizeX()*35, lab.getSizeY()*35);
        scene.setFill(null);
        Drawing.drawLabyrinth(root, lab);


        Bespilotnik  bespilotnik = new Bespilotnik(Bespilotnik.getCentre()[0], Bespilotnik.getCentre()[1], 15.0/Labyrinth.getCountCells(), 15.0/Labyrinth.getCountCells(), lab);
        root.getChildren().add(bespilotnik);

        System.out.println(lab.getFinX() + " " + lab.getFinY());

        scene.setOnKeyPressed(bespilotnik);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
