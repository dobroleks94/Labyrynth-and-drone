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

    public void checkBehaviour() {
        if (behave == null) {
            behave = new BespilotnikBehaviour();
            behave.setBesp(this);
            new Thread(behave).start();
        }
    }

    @Override
    public void handle(KeyEvent event) {
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
    }

}
