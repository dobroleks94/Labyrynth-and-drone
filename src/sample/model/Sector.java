package sample.model;

import javafx.scene.shape.Line;
import sample.view.Drawing;

public class Sector {
    private boolean left=false;//Наличие стенок с определенной стороны
    private boolean right=false;
    private boolean up=false;
    private boolean down=false;

    private Line leftLine;
    private Line rightLine;
    private Line upLine;
    private Line downLine;

    private Cell[][]cells;

    public Sector(boolean left, boolean right, boolean up, boolean down) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        cells=new Cell[Labyrinth.getCountCells()][Labyrinth.getCountCells()];
    }
    public Sector(){}
    public Cell[][] getCells() {
        return cells;
    }
    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    public void setCell(Cell cell,int y,int x){
        if(cells[y][x]==null)
            cells[y][x]=cell;
    }
    public Cell getCell(int y, int x){
        return cells[y][x];
    }

    public void setLeft(boolean left) {
        this.left = left;
    }
    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }
    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }
    public boolean isDown() {
        return down;
    }

    public Line getLeftLine() {
        return leftLine;
    }
    public void setLeftLine(Line leftLine) {
        leftLine.setOpacity(0);
        this.leftLine = leftLine;
    }

    public Line getRightLine() {
        return rightLine;
    }
    public void setRightLine(Line rightLine) {
        rightLine.setOpacity(0);
        this.rightLine = rightLine;
    }

    public Line getUpLine() {
        return upLine;
    }
    public void setUpLine(Line upLine) {
        upLine.setOpacity(0);
        this.upLine = upLine;
    }

    public Line getDownLine() {
        return downLine;
    }
    public void setDownLine(Line downLine) {
        downLine.setOpacity(0);
        this.downLine = downLine;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        if ((left)) {
            s.append("T");
        } else {
            s.append("F");
        }

        if ((right)) {
            s.append("T");
        } else {
            s.append("F");
        }

        if ((up)) {
            s.append("T");
        } else {
            s.append("F");
        }

        if ((down)) {
            s.append("T");
        } else {
            s.append("F");
        }
        return s.toString();
    }
}
