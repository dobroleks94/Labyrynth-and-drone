package sample.control;

import javafx.concurrent.Task;
import javafx.geometry.Side;
import javafx.scene.paint.Color;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.model.Sector;
import sample.view.Drawing;

public class Controller extends Task {
    private boolean verticalSize = false;
    private boolean positiveDirection = false;
    private double step = 30.0;

    private Bespilotnik besp;

    public void setBesp(Bespilotnik besp) {
        this.besp = besp;
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
            synchronized (besp) {
                while (besp.getScene().getWindow().isShowing()) {

                    labyrinth = besp.getLabyrinth();

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
                        doStep(besp, verticalSize, positiveDirection, isNotEdgeX, isNotEdgeY, allowDown, allowLeft, allowRight, allowUp);
                        if (besp.getChild() != null)
                            if (!besp.getChild().isCanMove())
                                besp.getChild().setCanMove(true);
                    }

                    if (besp.isFirstStep())
                        besp.setFirstStep(false);

                    besp.setCurrentSector(labyrinth.getSector(besp.getY(), besp.getX())); // gets sector after moving
                    Drawing.skyLine(labyrinth, besp.getY(), besp.getX());

                    for (Bespilotnik b : besp.getGroup()) {
                        if (!b.isOnFinish() && b.isCanMove()) {
                            //Thread.sleep(50);
                            if (b.getLeadBesp().getCenterX() != b.getLeadBesp().getPreviousCentreX()
                                    || b.getLeadBesp().getCenterY() != b.getLeadBesp().getPreviousCentreY()) {
                                settingCenterCoordinates(b);
                                if (b.getChild() != null)
                                    if (!b.getChild().isCanMove())
                                        b.getChild().setCanMove(true);
                            }

                            b.setCurrentSector(labyrinth.getSector(b.getY(), b.getX()));
                            paintBespilotnik(b);
                        }
                        
                        finishVerifiening(besp);
                        paintBespilotnik(besp);
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

    private void finishVerifiening (Bespilotnik besp){
        if (besp.getCurrentSector() == besp.getFinish()) {
            besp.setOnFinish(true);
            for (Bespilotnik bespilotnik : besp.getGroup())
                if (!bespilotnik.isOnFinish()) {
                    bespilotnik.setCenterX(bespilotnik.getLeadBesp().getCenterX());
                    bespilotnik.setCenterY(bespilotnik.getLeadBesp().getCenterY());
                    bespilotnik.setX(bespilotnik.getLeadBesp().getX());
                    bespilotnik.setY(bespilotnik.getLeadBesp().getY());
                }
        }
    }
    private void paintBespilotnik(Bespilotnik besp){
        besp.setFill((besp.getCurrentSector() == besp.getFinish()) ? Color.RED :
                (besp.getCurrentSector() == besp.getStart()) ? Color.BLUE
                        : besp.ownColor());
    }
    private void settingCenterCoordinates(Bespilotnik b) {
        b.setPreviousCentreX(b.getCenterX());
        b.setCenterX(b.getLeadBesp().getPreviousCentreX());

        b.setPreviousX(b.getX());
        b.setX(b.getLeadBesp().getPreviousX());

        b.setPreviousCentreY(b.getCenterY());
        b.setCenterY(b.getLeadBesp().getPreviousCentreY());

        b.setPreviousY(b.getY());
        b.setY(b.getLeadBesp().getPreviousY());
    }
    private void doStep(Bespilotnik besp, boolean verticalSize, boolean positiveDirection, boolean isNotEdgeX, boolean isNotEdgeY, boolean allowDown, boolean allowLeft, boolean allowRight, boolean allowUp) {
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
    }
}
