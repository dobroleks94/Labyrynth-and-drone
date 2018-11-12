package sample.control;

import sample.model.*;
import javafx.concurrent.Task;
import javafx.geometry.Side;


public class BespilotnikBehaviour extends Task {
    private boolean verticalSize = false;
    private boolean positiveDirection = false;
    private double step = 15.0;
    private boolean firstStep = true;


    private Bespilotnik besp;
    public Bespilotnik setBesp(Bespilotnik b) {
        return besp = b;
    }

    private double moveRightLeft;
    private double moveUpDown;

    private boolean isEdgeX ;
    private boolean isEdgeY;

    @Override
    protected Object call() throws Exception {
        synchronized (besp) {
            while (besp.getScene().getWindow().isShowing()) {
                if(!firstStep)
                    besp.wait(); //waiting for allowing to move
                moveRightLeft = besp.getCenterX() + (positiveDirection ? step : -step); // setting a direction on horisontal line
                moveUpDown = besp.getCenterY() + (positiveDirection ? step : -step); // the same on vertical line

                isEdgeX = moveRightLeft <= besp.getScene().getWidth() - step && moveRightLeft > 0; //verify the end of scene
                isEdgeY = moveUpDown <= besp.getScene().getHeight() - step && moveUpDown > 0; // the same

                if (verticalSize) {
                    if (isEdgeX)
                        besp.setCenterX(moveRightLeft);
                } else {
                    if (isEdgeY)
                        besp.setCenterY(moveUpDown);
                }
                if (firstStep) {
                    firstStep = false;
                    step = 30.0;
                }
            }
            return null;

        }


    }

    public void moveToSide(Side side) {
        verticalSize = side.isVertical();
        positiveDirection = side == (verticalSize ? Side.RIGHT : Side.BOTTOM);
    }
}
