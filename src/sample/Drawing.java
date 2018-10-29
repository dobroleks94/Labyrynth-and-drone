package sample;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class Drawing {
    public static void drawLabyrinth(AnchorPane root, Labyrinth lab) {
        Sector[][] sectors = lab.getSectors();
        Line oxLine1 = new Line(0, 0, 30, 0);
        oxLine1.setStrokeWidth(1);
        oxLine1.setStroke(Color.BLUE);
        int otstup = 30;
        int length = otstup * 2;

        for (int y = 0; y < sectors.length; y++) {
            for (int x = 0; x < sectors[y].length; x++) {

                if (x == 0)
                    root.getChildren().add(new Line(x * otstup + otstup, y * otstup + otstup, x * otstup + otstup, y * otstup + length)); //ЛЕВАЯ СТЕНКА
                else if (sectors[y][x].isLeft() && sectors[y][x - 1].isRight())
                    root.getChildren().add(new Line(x * otstup + otstup, y * otstup + otstup, x * otstup + otstup, y * otstup + length)); //ЛЕВАЯ СТЕНКА


                if (x == sectors[y].length - 1)
                    root.getChildren().add(new Line(x * otstup + length, y * otstup + otstup, x * otstup + length, y * otstup + length)); //ПРАВАЯ СТЕНКА
                else if (sectors[y][x].isRight() && sectors[y][x + 1].isLeft())
                    root.getChildren().add(new Line(x * otstup + length, y * otstup + otstup, x * otstup + length, y * otstup + length)); //ПРАВАЯ СТЕНКА


                if (y == 0)
                    root.getChildren().add(new Line(x * otstup + otstup, y * otstup + otstup, x * otstup + length, y * otstup + otstup)); //ВЕРХНЯЯ СТЕНКА
                else if (sectors[y][x].isUp() && sectors[y - 1][x].isDown())
                    root.getChildren().add(new Line(x * otstup + otstup, y * otstup + otstup, x * otstup + length, y * otstup + otstup)); //ВЕРХНЯЯ СТЕНКА


                if (y == sectors.length - 1)
                    root.getChildren().add(new Line(x * otstup + otstup, y * otstup + length, x * otstup + length, y * otstup + length)); //НИЖНЯЯ СТЕНКА
                else if (sectors[y][x].isDown() && sectors[y + 1][x].isUp())
                    root.getChildren().add(new Line(x * otstup + otstup, y * otstup + length, x * otstup + length, y * otstup + length)); //НИЖНЯЯ СТЕНКА
            }
        }




        Line fin;
        if (lab.getFinX() == 0)
            fin = new Line(lab.getFinX() * otstup + otstup, lab.getFinY() * otstup + otstup, lab.getFinX() * otstup + otstup, lab.getFinY() * otstup + length); //ЛЕВАЯ СТЕНКА
        else if (lab.getFinX() == lab.getSizeX() - 1)
            fin = new Line(lab.getFinX() * otstup + length, lab.getFinY() * otstup + otstup, lab.getFinX() * otstup + length, lab.getFinY() * otstup + length); //ПРАВАЯ СТЕНКА
        else if (lab.getFinY() == 0)
            fin = new Line(lab.getFinX() * otstup + otstup, lab.getFinY() * otstup + otstup, lab.getFinX() * otstup + length, lab.getFinY() * otstup + otstup); //ВЕРХНЯЯ СТЕНКА
        else
            fin = new Line(lab.getFinX() * otstup + otstup, lab.getFinY() * otstup + length, lab.getFinX() * otstup + length, lab.getFinY() * otstup + length);//НИЖНЯЯ СТЕНКА
        fin.setStroke(Color.WHITE);

        Line start;
        if (lab.getStartX() == 0)
            start = new Line(lab.getStartX() * otstup + otstup, lab.getStartY() * otstup + otstup, lab.getStartX() * otstup + otstup, lab.getStartY() * otstup + length); //ЛЕВАЯ СТЕНКА
        else if (lab.getStartX() == lab.getSizeX() - 1)
            start = new Line(lab.getStartX() * otstup + length, lab.getStartY() * otstup + otstup, lab.getStartX() * otstup + length, lab.getStartY() * otstup + length); //ПРАВАЯ СТЕНКА
        else if (lab.getStartY() == 0)
            start = new Line(lab.getStartX() * otstup + otstup, lab.getStartY() * otstup + otstup, lab.getStartX() * otstup + length, lab.getStartY() * otstup + otstup);  //ВЕРХНЯЯ СТЕНКА
        else
            start = new Line(lab.getStartX() * otstup + otstup, lab.getStartY() * otstup + length, lab.getStartX() * otstup + length, lab.getStartY() * otstup + length);  //НИЖНЯЯ СТЕНКА
        start.setStroke(Color.YELLOW);


        root.getChildren().addAll(start,fin);
    }
}
