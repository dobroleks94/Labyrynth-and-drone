package sample.model;

import javafx.scene.paint.Paint;
import sample.control.*;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Bespilotnik extends Ellipse implements EventHandler<KeyEvent> {

    private static int name = 1;
    private int n ;
    public Bespilotnik(double xCentre, double yCentre, double horizSize, double vertSize, Labyrinth lab) {
        super(xCentre, yCentre, horizSize, vertSize);
        color = Color.BLACK;
        setLabyrinth(lab);
        /**
         * Defines the start sector coordinates and sets start point for Bespilotnik
         */
        setCenterX(getCenterX() + (Labyrinth.getStartWall().equals("L") ? 15 :
                Labyrinth.getStartWall().equals("R") ? -15 :
                        0 ));
        setCenterY(getCenterY() + (Labyrinth.getStartWall().equals("T") ? 15 :
                Labyrinth.getStartWall().equals("B") ? -15 :
                        0 ));

        /**
         * Sets for Bespilotnik start coordinates of Labyrinth
         */
        setX(lab.getStartX());
        setY(lab.getStartY());
        isOperated = true;
        GroupBespil.addToGroup(this);
        n = name;
        name++;
    }
    public Bespilotnik(Bespilotnik bespilotnik){

        setLeadBesp(bespilotnik);

        setRadiusX(getLeadBesp().getRadiusX());
        setRadiusY(getLeadBesp().getRadiusY());
        setCenterX(getLeadBesp().getCenterX());
        setCenterY(getLeadBesp().getCenterY());
        setX(getLeadBesp().getX());
        setY(getLeadBesp().getY());

        isOperated = false;
        color = Color.GREEN;

        bespilotnik.setChild(this);

        GroupBespil.addToGroup(this);
        n = name;
        name++;
    }

    private Paint color;

    private static Sector start;
    private static Sector finish;
    private Sector currentSector;

    private Labyrinth labyrinth;
    private BespilotnikBehaviour behave;

    private int X;
    private int Y;
    double previousCentreX;
    double previousCentreY;
    int previousX;
    int previousY;

    private boolean isOperated;

    private Bespilotnik leadBesp; //previous bespilotnik, which leads this one
    private Bespilotnik child; //  leadBesp follower
    private boolean firstStep = true;
    private boolean isOnFinish;

    public boolean isOnFinish() {
        return isOnFinish;
    }
    public void setOnFinish(boolean onFinish) {
        isOnFinish = onFinish;
    }

    public Paint ownColor() {
        return color;
    }

    /**
     * Sets previous labyrint`s and field`s coordinates of leader Bespilotnik
     */
    public double getPreviousCentreX() {
        return previousCentreX;
    }
    public void setPreviousCentreX(double previousCentreX) {
        this.previousCentreX = previousCentreX;
    }
    public double getPreviousCentreY() {
        return previousCentreY;
    }
    public void setPreviousCentreY(double previousCentreY) {
        this.previousCentreY = previousCentreY;
    }

    public int getPreviousX() {
        return previousX;
    }
    public void setPreviousX(int previousX) {
        this.previousX = previousX;
    }
    public int getPreviousY() {
        return previousY;
    }
    public void setPreviousY(int previousY) {
        this.previousY = previousY;
    }
    //****************************************************************************

    public boolean isFirstStep() {
        return firstStep;
    }
    public void setFirstStep(boolean firstStep) {
        this.firstStep = firstStep;
    }

    public Sector getCurrentSector() {
        return currentSector;
    }
    public void setCurrentSector(Sector currentSector) {
        this.currentSector = currentSector;
    }

    public Bespilotnik getLeadBesp() {
        return leadBesp;
    }
    public void setLeadBesp(Bespilotnik besp) {this.leadBesp = besp;}

    public void setChild(Bespilotnik child) {
        this.child = child;
    }
    public Bespilotnik getChild() {
        return child;
    }

    /**
     * defines bespilotnik, which will be controlled by operator
     */
    public boolean isOperated() {
        return isOperated;
    }
    //*******************************************************************************

    public Sector getStart() {
        return start;
    }
    public static void setStart(Sector start_) {
        start = start_;
    }

    /**
     * gets Finish sector
     */
    public Sector getFinish() {
        return finish;
    }
    public static void setFinish(Sector fin) {
        finish = fin;
    }

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }
    public Labyrinth getLabyrinth() {
        return labyrinth;
    }

    /**
     * Coordinates of sector where Bespilotnik is
     */
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
    public static double[] startCoordinates(double x1, double x2, double y1, double y2){
        double []coordinates = {
                (x1+x2)/2,
                (y1+y2)/2
        };
        return coordinates;
    }
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

    @Override
    public String toString() {
        return ""+n;
    }

}
