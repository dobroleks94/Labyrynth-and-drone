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

    @Override
    protected Object call() throws Exception {
        while (besp.getScene().getWindow().isShowing()) {
            if (verticalSize)
                besp.setCenterX(besp.getCenterX() + (positiveDirection ? step : -step));
            else
                besp.setCenterY(besp.getCenterY() + (positiveDirection ? step : -step));
            Thread.sleep(250);
        }
        return null;
    }

    public void moveToSide(Side side) {
        verticalSize = side.isVertical();
        positiveDirection = side == (verticalSize ? Side.RIGHT : Side.BOTTOM);
    }
}
