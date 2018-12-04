package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.view.Drawing;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class Main extends Application {
    private static int sizeX;
    private static int sizeY;
    private static int countCells;
    private static int radius;
    private static boolean autopilot=false;

    @Override
    public void start(final Stage primaryStage) {
        Label label1=new Label("Висота лабіринта (>=10) = ");
        Label label2=new Label("Ширина лабіринта (>=10)= ");
        Label label3=new Label("Кількість комірок в секторі (>1)= ");
        Label label4=new Label("Радіус видимості беспілотника (>=1) = ");
        Label label5=new Label("Автопілот");
        CheckBox checkBox=new CheckBox();
        TextField textField1=new TextField();
        TextField textField2=new TextField();
        TextField textField3=new TextField();
        TextField textField4=new TextField();
        Button ok=new Button("OK");
        primaryStage.setOnCloseRequest(event->primaryStage.close());
        ok.setOnAction(event1 -> {
            try {
                sizeY=Integer.valueOf(textField1.getText());
                sizeX=Integer.valueOf(textField2.getText());
                countCells=Integer.valueOf(textField3.getText());
                radius=Integer.valueOf(textField4.getText());
                if(sizeX>=10&&sizeY>=10&&countCells>1&&radius>=1){
                    if(checkBox.isSelected())
                        autopilot=true;
                }
                else
                    System.out.println("Перевірте правильність введених даних");
            }catch (Exception g){
                System.out.println("Перевірте правильність введених даних");
                g.printStackTrace();
                return;
            }
            AnchorPane root1 = new AnchorPane();
            Labyrinth lab = new Labyrinth(sizeX, sizeY,countCells);
            Drawing.drawLabyrinth(root1, lab);
            Bespilotnik  bespilotnik = new Bespilotnik(autopilot, Bespilotnik.getCentre()[0], Bespilotnik.getCentre()[1], 15.0/Labyrinth.getCountCells(), 15.0/Labyrinth.getCountCells(), lab,radius);
            root1.getChildren().add(bespilotnik);
            Scene scene1 = new Scene(root1, lab.getSizeX()*35, lab.getSizeY()*35);
            Stage generalStage=new Stage();
            generalStage.setOnCloseRequest(event->generalStage.close());
            generalStage.setTitle("Course work");
            generalStage.setScene(scene1);
            generalStage.initModality(Modality.WINDOW_MODAL);
            generalStage.initOwner(primaryStage);
            System.out.println(lab.getFinX() + " " + lab.getFinY());
            scene1.setOnKeyPressed(bespilotnik);
            primaryStage.close();
            generalStage.show();


        });
        GridPane root=new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.add(label1,0,0,1,1);
        root.add(label2,0,1,1,1);
        root.add(label3,0,2,1,1);
        root.add(label4,0,3,1,1);
        root.add(label5,0,4,1,1);
        root.add(textField1,1,0,1,1);
        root.add(textField2,1,1,1,1);
        root.add(textField3,1,2,1,1);
        root.add(textField4,1,3,1,1);
        root.add(checkBox,1,4,1,1);
        root.add(ok,0,5,1,1);
        Scene scene=new Scene(root,500,200);

        primaryStage.setTitle("Course work");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}
