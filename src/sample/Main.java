package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.view.Drawing;
import sample.model.Labyrinth;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        final Scene scene = new Scene(root, 1024, 768);
        scene.setFill(null);
        Labyrinth lab = new Labyrinth(25, 25);
        System.out.println(lab);
        Drawing.drawLabyrinth(root, lab);
        primaryStage.setTitle("");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
