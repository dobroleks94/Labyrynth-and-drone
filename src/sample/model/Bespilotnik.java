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

    private BespilotnikBehaviour behave;


    /**
     * sets the centre of start line
     * where our bespilotnik will be located
     */
    public static double[] startCoordinates(int x1, int x2, int y1, int y2){
        double []coordinates = {
                (x1+x2)/2,
                (y1+y2)/2
        };
        return coordinates;
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
