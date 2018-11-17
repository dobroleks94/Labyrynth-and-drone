package sample.control;

import javafx.scene.paint.Color;
import sample.model.*;
import javafx.concurrent.Task;
import javafx.geometry.Side;
import sample.view.Drawing;


public class BespilotnikBehaviour extends Task {
    private boolean verticalSize = false;
    private boolean positiveDirection = false;
    private double step = 30.0;

    private Bespilotnik _besp;

    public void setBesp(Bespilotnik besp) {
        this._besp = besp;
    }

    private Sector sector;
    private Sector sector2;


    private int moveRightLeft;
    private int moveUpDown;

    private boolean isNotEdgeX;
    private boolean isNotEdgeY;
    private boolean allowRight;
    private boolean allowLeft;
    private boolean allowUp;
    private boolean allowDown;
    Labyrinth labyrinth;


    @Override
    protected Object call() {
        try {
            synchronized (_besp) {
                while (_besp.getScene().getWindow().isShowing()) {
                    labyrinth = _besp.getLabyrinth();
                    everyBesp:
                    for (Bespilotnik besp : GroupBespil.getGroup()) {
                        if (besp.isOperated()) {
                            if (!besp.isFirstStep())
                                besp.wait(); //waiting for allowing to move

                            moveRightLeft = besp.getX() + (positiveDirection ? 1 : -1); //sets labyrint coordinates while moving
                            moveUpDown = besp.getY() + (positiveDirection ? 1 : -1);

                            isNotEdgeX = moveRightLeft < besp.getLabyrinth().getSizeX() && moveRightLeft >= 0; //verify the end of labyrint
                            isNotEdgeY = moveUpDown < besp.getLabyrinth().getSizeY() && moveUpDown >= 0; // the same

                            /**
                             * Verifies available directions, reacted on walls
                             */
                            allowDown = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isDown();
                            allowUp = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isUp();
                            allowLeft = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isLeft();
                            allowRight = !besp.getLabyrinth().getSector(besp.getY(), besp.getX()).isRight();



                            sector = besp.getLabyrinth().getSector(besp.getY(), besp.getX()); // gets sector before moving

                            besp.setPreviousCentreX(besp.getCenterX());
                            besp.setPreviousCentreY(besp.getCenterY());
                            besp.setPreviousX(besp.getX());
                            besp.setPreviousY(besp.getY());

                            if (!besp.isOnFinish()) {
                                if (verticalSize) {
                                    if (isNotEdgeX) {
                                        besp.setCenterX(besp.getCenterX() + (positiveDirection ?
                                                (allowRight) ? step : 0 :
                                                (allowLeft) ? -step : 0));
                                        besp.setX(besp.getX() + (positiveDirection ?
                                                (allowRight) ? 1 : 0 :
                                                (allowLeft) ? -1 : 0));
                                    }
                                } else {
                                    if (isNotEdgeY) {
                                        besp.setCenterY(besp.getCenterY() + (positiveDirection ?
                                                (allowDown) ? step : 0 :
                                                (allowUp) ? -step : 0));
                                        besp.setY(besp.getY() + (positiveDirection ?
                                                (allowDown) ? 1 : 0 :
                                                (allowUp) ? -1 : 0));
                                    }
                                }
                                if(besp.getChild() != null)
                                    if(!besp.getChild().isCanMove())
                                        besp.getChild().setCanMove(true);
                            }

                        } else if (!besp.isOperated()) {
                            if (!besp.isOnFinish() && besp.isCanMove()) {
                                if(besp.getLeadBesp().getCenterX() != besp.getLeadBesp().getPreviousCentreX()
                                  || besp.getLeadBesp().getCenterY() != besp.getLeadBesp().getPreviousCentreY()) {
                                    besp.setPreviousCentreX(besp.getCenterX());
                                    besp.setCenterX(besp.getLeadBesp().getPreviousCentreX());

                                    besp.setPreviousX(besp.getX());
                                    besp.setX(besp.getLeadBesp().getPreviousX());

                                    besp.setPreviousCentreY(besp.getCenterY());
                                    besp.setCenterY(besp.getLeadBesp().getPreviousCentreY());

                                    besp.setPreviousY(besp.getY());
                                    besp.setY(besp.getLeadBesp().getPreviousY());

                                    if(besp.getChild() != null)
                                        if(!besp.getChild().isCanMove())
                                            besp.getChild().setCanMove(true);
                                }
                            }
                        }

                        besp.setCurrentSector(labyrinth.getSector(besp.getY(), besp.getX())); // gets sector after moving

                        Drawing.skyLine(labyrinth, besp.getY(), besp.getX());

                        if (besp.isFirstStep())
                            besp.setFirstStep(false);


                        besp.setFill((besp.getCurrentSector() == besp.getFinish()) ? Color.RED :
                                (besp.getCurrentSector() == besp.getStart()) ? Color.BLUE
                                        : besp.ownColor());

                        if (besp.getCurrentSector() == besp.getFinish()) {
                            besp.setOnFinish(true);
                            for (Bespilotnik bespilotnik : GroupBespil.getGroup())
                                if (!bespilotnik.isOnFinish()) {
                                    bespilotnik.setCenterX(bespilotnik.getLeadBesp().getCenterX());
                                    bespilotnik.setCenterY(bespilotnik.getLeadBesp().getCenterY());
                                    bespilotnik.setX(bespilotnik.getLeadBesp().getX());
                                    bespilotnik.setY(bespilotnik.getLeadBesp().getY());
                                }
                        }

                        Thread.sleep(25);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void moveToSide(Side side) {
        verticalSize = side.isVertical(); // defines if side, where Bespilotnik moves in Labyrinth, is vertical
        // and sets TRUE (moves on right or on left) of FALSE (in top or bottom)
        positiveDirection = side == (verticalSize ? Side.RIGHT : Side.BOTTOM); // defines positive direction
        //if pressed button (side) is Side.TOP, for example, positiveDirection will be false
    }
}
