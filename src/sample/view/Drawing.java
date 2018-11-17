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
                    sectors[y][x].setLeftLine(setLine(otstup, otstup, otstup, length, x, y),0);
                    root.getChildren().add(sectors[y][x].getLeftLine()); //ЛЕВАЯ СТЕНКА
                    sectors[y][x].setLeft(true);
                } else if (sectors[y][x].isLeft() && sectors[y][x - 1].isRight()) {
                    sectors[y][x].setLeftLine(setLine(otstup, otstup, otstup, length, x, y),0);
                    root.getChildren().add(sectors[y][x].getLeftLine()); //ЛЕВАЯ СТЕНКА
                } else {
                    sectors[y][x].setLeft(false);
                    sectors[y][x - 1].setRight(false);
                }

                if (x == sectors[y].length - 1) {
                    sectors[y][x].setRightLine(setLine(length, otstup, length, length, x, y),0);
                    root.getChildren().add(sectors[y][x].getRightLine()); //ПРАВАЯ СТЕНКА
                    sectors[y][x].setRight(true);
                } else if (sectors[y][x].isRight() && sectors[y][x + 1].isLeft()) {
                    sectors[y][x].setRightLine(setLine(length, otstup, length, length, x, y),0);
                    root.getChildren().add(sectors[y][x].getRightLine()); //ПРАВАЯ СТЕНКА
                } else {
                    sectors[y][x].setRight(false);
                    sectors[y][x + 1].setLeft(false);
                }

                if (y == 0) {
                    sectors[y][x].setUpLine(setLine(otstup, otstup, length, otstup, x, y),0); //ВЕРХНЯЯ СТЕНКА
                    root.getChildren().add(sectors[y][x].getUpLine());
                    sectors[y][x].setUp(true);
                } else if (sectors[y][x].isUp() && sectors[y - 1][x].isDown()) {
                    sectors[y][x].setUpLine(setLine(otstup, otstup, length, otstup, x, y),0); //ВЕРХНЯЯ СТЕНКА
                    root.getChildren().add(sectors[y][x].getUpLine());
                } else {
                    sectors[y][x].setUp(false);
                    sectors[y - 1][x].setDown(false);
                }

                if (y == sectors.length - 1) {
                    sectors[y][x].setDownLine(setLine(otstup, length, length, length, x, y),0); //НИЖНЯЯ СТЕНКА
                    root.getChildren().add(sectors[y][x].getDownLine());
                    sectors[y][x].setDown(true);
                } else if (sectors[y][x].isDown() && sectors[y + 1][x].isUp()) {
                    sectors[y][x].setDownLine(setLine(otstup, length, length, length, x, y),0); //НИЖНЯЯ СТЕНКА
                    root.getChildren().add(sectors[y][x].getDownLine());
                } else {
                    sectors[y][x].setDown(false);
                    sectors[y + 1][x].setUp(false);
                }
            }
        }
        drawStart(root, lab);
        drawFin(root, lab);
    }






    private static Line setLine(int x1, int y1, int x2, int y2, int x, int y) {
        return new Line(x * otstup + x1, y * otstup + y1, x * otstup + x2, y * otstup + y2);//Установка линии по заданым координатам
    }

    private static void drawStart(AnchorPane root, Labyrinth lab) { //ВЫДЕЛЕНИЕ СТАРТА
        Line start;
        if (lab.getStartX() == 0) {
            start = setLine(otstup, otstup, otstup, length, lab.getStartX(), lab.getStartY()); //ЛЕВАЯ СТЕНКА
            lab.setStartWall("L");
        }
        else if (lab.getStartX() == lab.getSizeX() - 1){
            start = setLine(length, otstup, length, length, lab.getStartX(), lab.getStartY()); //ПРАВАЯ СТЕНКА
            lab.setStartWall("R");
        }
        else if (lab.getStartY() == 0){
            start = setLine(otstup, otstup, length, otstup, lab.getStartX(), lab.getStartY());  //ВЕРХНЯЯ СТЕНКА
            lab.setStartWall("T");
        }
        else{
            start = setLine(otstup, length, length, length, lab.getStartX(), lab.getStartY()); //НИЖНЯЯ СТЕНКА
            lab.setStartWall("B");
        }
        start.setStroke(Color.BLUE);
        start.setStrokeWidth(4);

        Bespilotnik.setStart(lab.getSector(lab.getStartY(), lab.getStartX()));

        /**
         * start line`s centre coordinates
         */
        Bespilotnik.setCentre(Bespilotnik.startCoordinates(start.getStartX(), start.getEndX(), start.getStartY(), start.getEndY()));

        root.getChildren().add(start);
    }

    private static void drawFin(AnchorPane root, Labyrinth lab) { //ВЫДЕЛЕНИЕ ФИНИША
        Line fin;
        if (lab.getFinX() == 0) {
            fin = setLine(otstup, otstup, otstup, length, lab.getFinX(), lab.getFinY()); //ЛЕВАЯ СТЕНКА
            lab.setFinishWall("L");
        }
        else if (lab.getFinX() == lab.getSizeX() - 1) {
            fin = setLine(length, otstup, length, length, lab.getFinX(), lab.getFinY());   //ПРАВАЯ СТЕНКА
            lab.setFinishWall("R");
        }
        else if (lab.getFinY() == 0) {
            fin = setLine(otstup, otstup, length, otstup, lab.getFinX(), lab.getFinY());  //ВЕРХНЯЯ СТЕНКА
            lab.setFinishWall("T");
        }
        else {
            fin = setLine(otstup, length, length, length, lab.getFinX(), lab.getFinY()); //НИЖНЯЯ СТЕНКА
            lab.setFinishWall("B");
        }
        fin.setStroke(Color.RED);
        fin.setStrokeWidth(4);

        Bespilotnik.setFinish(lab.getSector(lab.getFinY(), lab.getFinX()));
        root.getChildren().add(fin);
    }

    public static Line typeLine(Line line, int type){
        switch (type) {
            case 0:
                line.setStroke(Color.WHITE);
                return line;
            case 1:
                line.setStroke(Color.BLUE);
                return line;
            default:
                line.setStroke(Color.RED);
                return line;
        }
    }

    public static void skyLine(Labyrinth lab,int y,int x) {
        try {
            Sector[][] sectors = lab.getSectors();

            visibleLine(sectors[y][x]);
            if (y != 0) {
                visibleLine(sectors[y - 1][x]);
                if (x != 0)
                    visibleLine(sectors[y - 1][x - 1]);
                if (x != lab.getSizeX() - 1)
                    visibleLine(sectors[y - 1][x + 1]);
            }
            if (y != lab.getSizeY()-1) {
                visibleLine(sectors[y + 1][x]);
                if (x != 0)
                    visibleLine(sectors[y + 1][x - 1]);
                if (x != lab.getSizeX() - 1)
                    visibleLine(sectors[y + 1][x + 1]);
            }
            if (x != 0)
                visibleLine(sectors[y][x - 1]);
            if (x != lab.getSizeX() - 1)
                visibleLine(sectors[y][x + 1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void visibleLine(Sector sector){
        if(sector.getLeftLine()!=null)
            sector.getLeftLine().setStroke(Color.BLACK);
        if(sector.getRightLine()!=null)
            sector.getRightLine().setStroke(Color.BLACK);
        if(sector.getUpLine()!=null)
            sector.getUpLine().setStroke(Color.BLACK);
        if(sector.getDownLine()!=null)
            sector.getDownLine().setStroke(Color.BLACK);
    }
}
