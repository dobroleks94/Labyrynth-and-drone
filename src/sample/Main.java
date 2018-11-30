package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.view.Drawing;
import javax.swing.*;
import java.awt.*;

public class Main extends Application {
    private static int sizeX;
    private static int sizeY;
    private static int countCells;
    private static int radius;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Course work");
        AnchorPane root = new AnchorPane();

        Labyrinth lab = new Labyrinth(sizeX, sizeY,countCells);
        Scene scene = new Scene(root, lab.getSizeX()*35, lab.getSizeY()*35);
        scene.setFill(null);
        Drawing.drawLabyrinth(root, lab);


        Bespilotnik  bespilotnik = new Bespilotnik(true, Bespilotnik.getCentre()[0], Bespilotnik.getCentre()[1], 15.0/Labyrinth.getCountCells(), 15.0/Labyrinth.getCountCells(), lab,radius);
        root.getChildren().add(bespilotnik);

        System.out.println(lab.getFinX() + " " + lab.getFinY());
        scene.setOnKeyPressed(bespilotnik);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        modalWindow(args);
    }

    private static void modalWindow(String[] args){
        JFrame frame=new JFrame("Course work");
        frame.setBounds(500,250,600,200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label1=new JLabel("Висота лабіринта (>=10) = ");
        JLabel label2=new JLabel("Ширина лабіринта (>=10)= ");
        JLabel label3=new JLabel("Кількість комірок в секторі (>1)= ");
        JLabel label4=new JLabel("Радіус видимості беспілотника (>=1) = ");
        label4.setSize(10,100);
        JTextField textField1=new JTextField(5);
        JTextField textField2=new JTextField(5);
        JTextField textField3=new JTextField(5);
        JTextField textField4=new JTextField(5);
        JButton ok=new JButton("OK");
        ok.addActionListener(e -> {
            try {
                sizeY=Integer.valueOf(textField1.getText());
                sizeX=Integer.valueOf(textField2.getText());
                countCells=Integer.valueOf(textField3.getText());
                radius=Integer.valueOf(textField4.getText());
                if(sizeX>=10&&sizeY>=10&&countCells>1&&radius>=1){
                    frame.dispose();
                    launch(args);
                }
                else
                    JOptionPane.showMessageDialog(null,"Перевірте правильність введених даних");
            }catch (Exception g){
                JOptionPane.showMessageDialog(null,"Перевірте правильність введених даних");

                g.printStackTrace();
            }
        });
        GridLayout gridLayout=new GridLayout(5,2);
        gridLayout.setHgap(50);
        frame.setLayout(gridLayout);
        frame.add(label1);
        frame.add(textField1);
        frame.add(label2);
        frame.add(textField2);
        frame.add(label3);
        frame.add(textField3);
        frame.add(label4);
        frame.add(textField4);
        frame.add(new JLabel());
        frame.add(ok);
        frame.setVisible(true);
    }

}
