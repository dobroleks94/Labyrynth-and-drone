package sample.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.model.Sector;


public class Drawing {
    static int otstup = 30;
    static int length = otstup * 2;
    public static void drawLabyrinth(AnchorPane root, Labyrinth lab) { //Отображение всех секторов лабиринта
        Sector[][] sectors = lab.getSectors();


        for (int y = 0; y < sectors.length; y++) {
            for (int x = 0; x < sectors[y].length; x++) {

                if (x == 0) {
                    root.getChildren().add(setLine(otstup, otstup, otstup, length, x, y)); //ЛЕВАЯ СТЕНКА
                    sectors[y][x].setLeft(true);
                } else if (sectors[y][x].isLeft() && sectors[y][x - 1].isRight()) {
                    root.getChildren().add(setLine(otstup, otstup, otstup, length, x, y)); //ЛЕВАЯ СТЕНКА
                    sectors[y][x].setLeft(true);
                    sectors[y][x - 1].setRight(true);
                }

                if (x == sectors[y].length - 1) {
                    root.getChildren().add(setLine(length, otstup, length, length, x, y)); //ПРАВАЯ СТЕНКА
                    sectors[y][x].setRight(true);
                } else if (sectors[y][x].isRight() && sectors[y][x + 1].isLeft()) {
                    root.getChildren().add(setLine(length, otstup, length, length, x, y)); //ПРАВАЯ СТЕНКА
                    sectors[y][x].setRight(true);
                    sectors[y][x + 1].setLeft(true);
                }

                if (y == 0) {
                    root.getChildren().add(setLine(otstup, otstup, length, otstup, x, y)); //ВЕРХНЯЯ СТЕНКА
                    sectors[y][x].setUp(true);
                } else if (sectors[y][x].isUp() && sectors[y - 1][x].isDown()) {
                    root.getChildren().add(setLine(otstup, otstup, length, otstup, x, y)); //ВЕРХНЯЯ СТЕНКА
                    sectors[y][x].setUp(true);
                    sectors[y - 1][x].setDown(true);
                }

                if (y == sectors.length - 1) {
                    root.getChildren().add(setLine(otstup, length, length, length, x, y)); //НИЖНЯЯ СТЕНКА
                    sectors[y][x].setDown(true);
                } else if (sectors[y][x].isDown() && sectors[y + 1][x].isUp()) {
                    root.getChildren().add(setLine(otstup, length, length, length, x, y)); //НИЖНЯЯ СТЕНКА
                    sectors[y][x].setDown(true);
                    sectors[y + 1][x].setUp(true);
                }
            }
        }
        drawStart(root, lab);
        drawFin(root, lab);
    }

    private static Line setLine(int x1,int y1,int x2,int y2,int x,int y){
        return new Line(x*otstup+x1,y*otstup+y1,x*otstup+x2,y*otstup+y2);//Установка линии по заданым координатам
    }

    private static void drawStart(AnchorPane root,Labyrinth lab){ //ВЫДЕЛЕНИЕ СТАРТА
        Line start;
        if (lab.getStartX() == 0)
            start = setLine(otstup,otstup,otstup,length,lab.getStartX(),lab.getStartY()); //ЛЕВАЯ СТЕНКА
        else if (lab.getStartX() == lab.getSizeX() - 1)
            start =setLine(length,otstup,length,length,lab.getStartX(),lab.getStartY()); //ПРАВАЯ СТЕНКА
        else if (lab.getStartY() == 0)
            start = setLine(otstup,otstup,length,otstup,lab.getStartX(),lab.getStartY());  //ВЕРХНЯЯ СТЕНКА
        else
            start = setLine(otstup,length,length,length,lab.getStartX(),lab.getStartY()); //НИЖНЯЯ СТЕНКА
        start.setStroke(Color.BLUE);
        start.setStrokeWidth(4);

        /**
         * start line`s centre coordinates
         */
        Bespilotnik.setCentre(Bespilotnik.startCoordinates(start.getStartX(), start.getEndX(), start.getStartY(), start.getEndY()));


        root.getChildren().add(start);
    }

    private static void drawFin(AnchorPane root,Labyrinth lab){ //ВЫДЕЛЕНИЕ ФИНИША
        Line fin;
        if (lab.getFinX() == 0)
            fin = setLine(otstup,otstup,otstup,length,lab.getFinX(),lab.getFinY()); //ЛЕВАЯ СТЕНКА
        else if (lab.getFinX() == lab.getSizeX() - 1)
            fin = setLine(length,otstup,length,length,lab.getFinX(),lab.getFinY());   //ПРАВАЯ СТЕНКА
        else if (lab.getFinY() == 0)
            fin = setLine(otstup,otstup,length,otstup,lab.getFinX(),lab.getFinY());  //ВЕРХНЯЯ СТЕНКА
        else
            fin = setLine(otstup,length,length,length,lab.getFinX(),lab.getFinY()); //НИЖНЯЯ СТЕНКА
        fin.setStroke(Color.RED);
        fin.setStrokeWidth(4);

        root.getChildren().add(fin);
    }
}
