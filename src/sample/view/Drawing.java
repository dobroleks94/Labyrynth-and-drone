package sample.view;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.model.Sector;

import java.util.HashSet;

public class Drawing {
    private static int otstup = 30;
    private static int length = otstup * 2;
    private static boolean theme;
    public static void drawLabyrinth(AnchorPane root, Labyrinth lab,boolean themes) { //Отображение всех секторов лабиринта
        Sector[][] sectors = lab.getSectors();
        theme=themes;
        HashSet<Line> lines = new HashSet<>();
        for (int y = 0; y < sectors.length; y++) {
            for (int x = 0; x < sectors[y].length; x++) {

                if (sectors[y][x].isLeft()) {
                    sectors[y][x].setLeftLine(setLine(otstup, otstup, otstup, length, x, y));  //ЛЕВАЯ СТЕНКА
                    lines.add(sectors[y][x].getLeftLine());
                }

                if (sectors[y][x].isRight()) {
                    sectors[y][x].setRightLine(setLine(length, otstup, length, length, x, y)); //ПРАВАЯ СТЕНКА
                    lines.add(sectors[y][x].getRightLine());
                }

                if (sectors[y][x].isUp()) {
                    sectors[y][x].setUpLine(setLine(otstup, otstup, length, otstup, x, y)); //ВЕРХНЯЯ СТЕНКА
                    lines.add(sectors[y][x].getUpLine());
                }

                if (sectors[y][x].isDown()) {
                    sectors[y][x].setDownLine(setLine(otstup, length, length, length, x, y)); //НИЖНЯЯ СТЕНКА
                    lines.add(sectors[y][x].getDownLine());
                }
            }
        }
        drawStart(root, lab);
        Bespilotnik.setFinish(lab.getSector(lab.getFinY(), lab.getFinX()));
        root.getChildren().addAll(lines);
    }


    private static Line setLine(int x1, int y1, int x2, int y2, int x, int y) {
        Line line = new Line(x * otstup + x1, y * otstup + y1, x * otstup + x2, y * otstup + y2);//Установка линии по заданым координатам
        if(theme)
            line.setStroke(Color.WHITE);
        return line;
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
        start.setOpacity(1);
        start.setStrokeWidth(4);

        Bespilotnik.setStartLine(start); // start line
        Bespilotnik.setStart(lab.getSector(lab.getStartY(), lab.getStartX())); // start sector

        Bespilotnik.setStepLine(Bespilotnik.stepLineSize(start.getStartX(), start.getEndX(), start.getStartY(), start.getEndY())); // remote location size from start line
        Bespilotnik.setCentre(Bespilotnik.startCoordinates(lab)); //coordinates for bespilotnik at start sector, including remoting, what is upper

        root.getChildren().add(start);
    }

    public static void drawFin(Labyrinth lab) { //ВЫДЕЛЕНИЕ ФИНИША
        if (lab.getFinX() == 0) {
            lab.getSector(lab.getFinY(),lab.getFinX()).getLeftLine().setStroke(Color.RED);
            lab.getSector(lab.getFinY(),lab.getFinX()).getLeftLine().setOpacity(1);
            lab.setFinishWall("L");
            lab.getSector(lab.getFinY(),lab.getFinX()).setLeft(false);
        } else if (lab.getFinX() == lab.getSizeX() - 1) {
            lab.getSector(lab.getFinY(),lab.getFinX()).getRightLine().setStroke(Color.RED);
            lab.getSector(lab.getFinY(),lab.getFinX()).getRightLine().setOpacity(1);
            lab.setFinishWall("R");
            lab.getSector(lab.getFinY(),lab.getFinX()).setRight(false);
        } else if (lab.getFinY() == 0) {
            lab.getSector(lab.getFinY(),lab.getFinX()).getUpLine().setStroke(Color.RED);
            lab.getSector(lab.getFinY(),lab.getFinX()).getUpLine().setOpacity(1);
            lab.setFinishWall("T");
            lab.getSector(lab.getFinY(),lab.getFinX()).setUp(false);
        } else {
            lab.getSector(lab.getFinY(),lab.getFinX()).getDownLine().setStroke(Color.RED);
            lab.getSector(lab.getFinY(),lab.getFinX()).getDownLine().setOpacity(1);
            lab.setFinishWall("B");
            lab.getSector(lab.getFinY(),lab.getFinX()).setDown(false);
        }
    }


    public static void skyLine(Labyrinth lab,int y,int x,int yc,int xc) {
        Sector[][] sectors = lab.getSectors();
        if (sectors[y][x].getUpLine() != null && yc < Bespilotnik.radius) {
            sectors[y][x].getUpLine().setOpacity(1);
            if (y != 0)
                sectors[y-1][x].getDownLine().setOpacity(1);
        }

        if (sectors[y][x].getRightLine() != null && Labyrinth.getCountCells()-xc<=Bespilotnik.radius) {
            sectors[y][x].getRightLine().setOpacity(1);
            if (x != lab.getSizeX()-1)
                sectors[y][x + 1].getLeftLine().setOpacity(1);
        }

        if (sectors[y][x].getDownLine() != null && Labyrinth.getCountCells()-yc<=Bespilotnik.radius) {
            sectors[y][x].getDownLine().setOpacity(1);
            if (y != lab.getSizeY()-1)
                sectors[y+1][x].getUpLine().setOpacity(1);
        }

        if (sectors[y][x].getLeftLine() != null && xc < Bespilotnik.radius) {
            sectors[y][x].getLeftLine().setOpacity(1);
            if (x != 0)
                sectors[y][x - 1].getRightLine().setOpacity(1);
        }
    }
}
