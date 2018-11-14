package sample.model;

import sample.control.*;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Bespilotnik extends Ellipse implements EventHandler<KeyEvent> {

    public Bespilotnik(double xCentre, double yCentre, double horizSize, double vertSize) {
        super(xCentre, yCentre, horizSize, vertSize);
        setFill(Color.BLACK);
    }

    public Bespilotnik(Labyrinth lab, double horizSize, double vertSize) {
        super((lab.getStartX()==0)?lab.getStartX()-15:lab.getStartX()+15,
                (lab.getStartY()==0)?lab.getStartY()-15:lab.getStartY()+15,
                horizSize, vertSize);
        setFill(Color.BLACK);
    }

    private Labyrinth labyrinth;
    private BespilotnikBehaviour behave;
    private int X;
    private int Y;

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }
    public Labyrinth getLabyrinth() {
        return labyrinth;
    }

    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }
    public void setY(int y) {
        Y = y;
    }

    /**
     * sets the centre of start line
     * where our bespilotnik will be located
     */
    public static double[] startCoordinates(double x1, double x2, double y1, double y2) {
        return new double[]{
                (x1 + x2) / 2,
                (y1 + y2) / 2};
    }
   /* public static double[] startCoordinates(double x1, double x2, double y1, double y2,Labyrinth lab) {
        double x = (x1 + x2) / 2;
        double y = (y1 + y2) / 2;
        if (lab.getStartX() == 0)
            x += 15;
        else if (lab.getStartX() == lab.getSizeX() - 1)
            x -= 15;
        else if (lab.getStartY() == 0)
            y += 15;
        else
            y -= 15;
        return new double[]{x, y};
    }*/
    private static double[] centre;
    public static void setCentre(double[] startCoordinates) {
        centre = startCoordinates;
    }
    public static double[] getCentre() {
        return centre;
    }

    /**
     * Checking bespilotnik object on having any action;
     * If bespilotnik doesn`t have an action, it gets one;
     */
    public void checkBehaviour() {
        if (behave == null) {
            behave = new BespilotnikBehaviour();
            behave.setBesp(this);
            new Thread(behave).start();
        }
    }

    /**
     * Handling the pressing on directing buttons (up, down, left, right on the keyboard)
     * Firstly it is checking object on having a behaviour, than it points bespilotnik on nessesary direction
     * and notifies it to move there
     * @param event
     */
    @Override
    public void handle(KeyEvent event) {
        synchronized (this) {
            checkBehaviour();
            switch (event.getCode()) {
                case UP:
                    behave.moveToSide(Side.TOP);
                    break;
                case DOWN:
                    behave.moveToSide(Side.BOTTOM);
                    break;
                case LEFT:
                    behave.moveToSide(Side.LEFT);
                    break;
                case RIGHT:
                    behave.moveToSide(Side.RIGHT);
                    break;
            }
            notify();
        }
    }

}
