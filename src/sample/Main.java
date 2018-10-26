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

import java.awt.*;
import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane root = new AnchorPane();
        final Scene scene = new Scene(root, 1024, 768);
        scene.setFill(null);
        Labyrinth lab = new Labyrinth(10, 10);
        drawLabyrinth(root, lab);
        primaryStage.setTitle("JavaFX Line (o7planning.org)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void drawLabyrinth(AnchorPane root, Labyrinth lab) {
        Sector[][] sectors = lab.getSector();
        LinkedList<Line> lines = new LinkedList<>();
        Line oxLine1 = new Line(0, 0, 30, 0);
        oxLine1.setStrokeWidth(1);
        oxLine1.setStroke(Color.BLUE);
        int o=50;
        int cons=20;
        int stena=70;
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors.length; j++) {



                if(i==0&&j!=sectors[i].length-1)
                    root.getChildren().add(new Line(i * o + cons, j * o + cons, i * o + cons, j * o + stena));// установка левой стенки сектора
                else if(sectors[i][j].isUp()&&j!=sectors[i].length-1)
                    if(!sectors[i-1][j].isDown())
                        root.getChildren().add(new Line(i * o + cons, j * o + cons, i * o + cons, j * o + stena));



                if(i==sectors.length-1&&j!=0)
                    root.getChildren().add(new Line(i * o + stena, j * o + cons, i * o + stena, j * o + stena));// установка правой стенки сектора
                else if (sectors[i][j].isDown()&&j!=0)
                    if (!sectors[i + 1][j].isUp())
                        root.getChildren().add(new Line(i * o + stena, j * o + cons, i * o + stena, j * o + stena));



                if(j==0&&j!=sectors[i].length-1)
                    root.getChildren().add(new Line(i * o + cons, j * o + cons, i * o + stena, j * o + cons));// установка левой стенки сектора

                else if(sectors[i][j].isLeft()&&j!=sectors[i].length-1)
                    if (!sectors[i][j-1].isRight())
                        root.getChildren().add(new Line(i * o + cons, j * o + cons, i * o + stena, j * o + cons));


                if(j==sectors[j].length-1&&j!=0)
                    root.getChildren().add(new Line(i * o + cons, j * o + stena, i * o + stena, j * o + stena));//установка правой стенки сектора
                else if(sectors[i][j].isRight()&&j!=0)
                    if (!sectors[i][j+1].isLeft())
                        root.getChildren().add(new Line(i * o + cons, j * o + stena, i * o + stena, j * o + stena));//

            }
        }
    }
}
