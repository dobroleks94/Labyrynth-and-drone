package sample.control;

import sample.model.*;
import javafx.concurrent.Task;
import javafx.geometry.Side;


public class BespilotnikBehaviour extends Task {
    private boolean verticalSize = false;
    private boolean positiveDirection = false;
    private double step = 10.0;

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
                besp.wait(); //waiting for allowing to move
                moveRightLeft = besp.getCenterX() + (positiveDirection ? step : -step); // setting a direction on horisontal line
                moveUpDown = besp.getCenterY() + (positiveDirection ? step : -step); // the same on vertical line

                isEdgeX = moveRightLeft != besp.getScene().getWidth() && moveRightLeft != 0; //verify the end of scene
                isEdgeY = moveUpDown != besp.getScene().getHeight() && moveUpDown != 0; // the same

                    if (verticalSize) {
                        if (isEdgeX)
                            besp.setCenterX(moveRightLeft);
                    }
                    else {
                        if (isEdgeY)
                            besp.setCenterY(moveUpDown);
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
