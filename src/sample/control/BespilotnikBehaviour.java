package sample.control;

import javafx.scene.paint.Color;
import sample.model.*;
import javafx.concurrent.Task;
import javafx.geometry.Side;


public class BespilotnikBehaviour extends Task {
    private boolean verticalSize = false;
    private boolean positiveDirection = false;
    private double step = 30.0;
    private boolean firstStep = true;

    private Bespilotnik besp;
    public void setBesp(Bespilotnik besp) {
        this.besp = besp;
    }

    Sector sector;
    Sector sector2;


    private int moveRightLeft;
    private int moveUpDown;

    private boolean isNotEdgeX;
    private boolean isNotEdgeY;
    private boolean allowRight;
    private boolean allowLeft;
    private boolean allowUp;
    private boolean allowDown;


    @Override
    protected Object call() throws Exception {
        synchronized (besp) {
            while (besp.getScene().getWindow().isShowing()) {
                if (!firstStep)
                    besp.wait(); //waiting for allowing to move

                moveRightLeft = besp.getX() + (positiveDirection ? 1 : -1); //sets labyrint coordinates while moving
                moveUpDown = besp.getY() + (positiveDirection ? 1 : -1);

                isNotEdgeX = moveRightLeft < besp.getLabyrinth().getSizeX() && moveRightLeft >= 0 ; //verify the end of labyrint
                isNotEdgeY = moveUpDown < besp.getLabyrinth().getSizeY() && moveUpDown >= 0 ; // the same

                /**
                 * Verifies available directions, reacted on walls
                 */
                allowDown = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isDown();
                allowUp = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isUp();
                allowLeft = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isLeft();
                allowRight = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isRight();

                sector = besp.getLabyrinth().getSector(besp.getY(), besp.getX()); // gets sector before moving

                condition:
                if (verticalSize) {
                    if (isNotEdgeX) {
                        besp.setCenterX(besp.getCenterX() + (positiveDirection ?
                                (allowRight)? step : 0 :
                                        (allowLeft) ? -step : 0 ));
                        besp.setX(besp.getX() + (positiveDirection ?
                                (allowRight)? 1 : 0 :
                                    (allowLeft) ? -1 : 0 ));
                    }
                } else {
                    if (isNotEdgeY) {
                        besp.setCenterY(besp.getCenterY() + (positiveDirection ?
                                (allowDown)? step : 0 :
                                    (allowUp) ? -step : 0 ));
                        besp.setY(besp.getY() + (positiveDirection ?
                                (allowDown)? 1 : 0 :
                                    (allowUp) ? -1 : 0 ));
                    }
                }
                sector2 = besp.getLabyrinth().getSector(besp.getY(), besp.getX()); // gets sector after moving

                besp.setFill((sector2 == besp.getFinish()) ? Color.RED :
                        (sector2 == besp.getStart()) ? Color.BLUE
                                : Color.BLACK);

                if (firstStep)
                    firstStep = false;

            }
            return null;
        }
    }

    public void moveToSide(Side side) {
        verticalSize = side.isVertical(); // defines if side, where Bespilotnik moves in Labyrinth, is vertical
        // and sets TRUE (moves on right or on left) of FALSE (in top or bottom)
        positiveDirection = side == (verticalSize ? Side.RIGHT : Side.BOTTOM); // defines positive direction
        //if pressed button (side) is Side.TOP, for example, positiveDirection will be false
    }
}
