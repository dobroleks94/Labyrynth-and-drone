package sample.model;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import sample.control.*;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Bespilotnik extends Ellipse implements EventHandler<KeyEvent> {

    private static int name = 1;
    private int n ;

    private Paint color;

    private static Sector start;
    private static Sector finish;
    private Sector currentSector;
    private static Cell startC;
    private static Cell finishC;
    private Cell currentCell;

    private Labyrinth labyrinth;
    private Controller behave;

    private int X;
    private int Y;
    private int cellX;
    private int cellY;
    double previousCentreX;
    double previousCentreY;
    int previousX;
    int previousY;

    private boolean isOperated;
    private Operator operator;

    private Bespilotnik main; //previous bespilotnik, which leads this one
    private GroupBespil group;
    private Bespilotnik leadBesp; //previous bespilotnik, which leads this one
    private Bespilotnik child; //  leadBesp follower
    private boolean firstStep = true;
    private boolean isOnFinish;
    private boolean canMove;
    private static double step;

    public GroupBespil getGroup() {
        return group;
    }

    public Bespilotnik(double xCentre, double yCentre, double horizSize, double vertSize, Labyrinth lab) {
        super(xCentre, yCentre, horizSize, vertSize);
        setStep(horizSize*2);
        //setMain(this);
        color = Color.BLACK;
        setLabyrinth(lab);

        setCenterX(getCenterX() + (Labyrinth.getStartWall().equals("L") ? getStepLine() :
                Labyrinth.getStartWall().equals("R") ? -getStepLine() :
                        0 ));
        setCenterY(getCenterY() + (Labyrinth.getStartWall().equals("T") ? getStepLine() :
                Labyrinth.getStartWall().equals("B") ? -getStepLine() :
                        0 ));

        // sets center coordinates of operated bespilotnik (it always located at [0;0] position
        if(Labyrinth.getStartWall().equals("R"))
            setCenterX(getCenterX() - ((getStep()-1)*(double)lab.getCountCells()));
        else if(Labyrinth.getStartWall().equals("B"))
            setCenterY(getCenterY() - ((getStep()-1)*(double)lab.getCountCells()));
        //*************************************************************************************


        setX(lab.getStartX());
        setY(lab.getStartY());
        setCellX(0);
        setCellY(0);
        setCurrentSector(lab.getSector(getY(), getX()));
        setCurrentCell(getCurrentSector().getCell(getCellY(), getCellX()));

        isOperated = true;
        //group = new GroupBespil();
        n = name;
        name++;
    }




    public Bespilotnik(Bespilotnik bespilotnik){

        setLeadBesp(bespilotnik);
        setMain(getLeadBesp().getMain());

        setRadiusX(getLeadBesp().getRadiusX());
        setRadiusY(getLeadBesp().getRadiusY());
        setCenterX(getLeadBesp().getCenterX());
        setCenterY(getLeadBesp().getCenterY());
        setX(getLeadBesp().getX());
        setY(getLeadBesp().getY());

        isOperated = false;
        color = Color.GREEN;

        bespilotnik.setChild(this);

        //getMain().getGroup().addToGroup(this);
        n = name;
        name++;
    }

    public void setLabyrinth(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }
    public Labyrinth getLabyrinth() {
        return labyrinth;
    }

    public Paint ownColor() {
        return color;
    }

    public Bespilotnik getMain() {
        return main;
    }
    public void setMain(Bespilotnik main) {
        this.main = main;
    }

    // defines opportunity of moving, sets and gets the value of bespilotnik`s step
    // and verifies if bespilotnik has done first step
    public boolean isCanMove() {
        return canMove;
    }
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    public static double getStep() {
        return step;
    }
    public void setStep(double step) {
        this.step = step;
    }
    //*****************************************************************************

    public boolean isFirstStep() {
        return firstStep;
    }
    public void setFirstStep(boolean firstStep) {
        this.firstStep = firstStep;
    }

    //sets and gets current cell and sector, where bespilotnik is located **************
    public Sector getCurrentSector() {
        return currentSector;
    }
    public void setCurrentSector(Sector currentSector) {
        this.currentSector = currentSector;
    }
    public Cell getCurrentCell() {
        return currentCell;
    }
    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }
    //**********************************************************************************


    /**
     * Coordinates of sector and cell in sector where Bespilotnik is
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
    public int getCellX() {
        return cellX;
    }
    public void setCellX(int cellX) {
        this.cellX = cellX;
    }
    public int getCellY() {
        return cellY;
    }
    public void setCellY(int cellY) {
        this.cellY = cellY;
    }
    //*******************************************


    // sets and gets start sector and cell *********************************************
    public Sector getStart() {
        return start;
    }
    public static void setStart(Sector start_) {
        start = start_;
    }
    public static Cell getStartC() {
        return startC;
    }
    public static void setStartC(Cell startC) {
        Bespilotnik.startC = startC;
    }
    //**************************************************************************


    /**
     * looks for start line
     * sets the location on start line where bespilotnik will be located
     * location depends on count of cells in the sector
     * operated bespilotnik is always located in the start cell [0;0] of start sector
     */
    private static Line startLine;
    public static Line getStartLine() {
        return startLine;
    }
    public static void setStartLine(Line startLine) {
        Bespilotnik.startLine = startLine;
    }

    public static double stepLineSize(double x1, double x2, double y1, double y2){
        double lineLength = Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
        return lineLength/(Labyrinth.getCountCells()*2);
    }
    public static double[] startCoordinates(Labyrinth lab){
        double x = getStartLine().getStartX() + ((Labyrinth.getStartWall().equals("B") || Labyrinth.getStartWall().equals("T"))
                    ? getStepLine() : 0 );
        double y = getStartLine().getStartY() + ((Labyrinth.getStartWall().equals("L") || Labyrinth.getStartWall().equals("R"))
                    ? getStepLine() : 0 );
        double []coordinates = { x, y};
        return coordinates;
    }
    //************************************************************************************


    // location of Bespilotnik on start sector + on start sector`s cell ******************
    private static double[] centre;
    public static void setCentre(double[] startCoordinates) {
        centre = startCoordinates;
    }
    public static double[] getCentre() {
        return centre;
    }
    private static double stepLine;
    public static double getStepLine() {
        return stepLine;
    }
    public static void setStepLine(double stepLine) {
        Bespilotnik.stepLine = stepLine;
    }
    //*************************************************************************************



    public Sector getFinish() {
        return finish;
    }
    public static void setFinish(Sector fin) {
        finish = fin;
    }
    public boolean isOnFinish() {
        return isOnFinish;
    }
    public void setOnFinish(boolean onFinish) {
        isOnFinish = onFinish;
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


    /**
     * defines bespilotnik, which will be controlled by operator
     */
    public boolean isOperated() {
        return isOperated;
    }
    //*******************************************************************************

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
     * Checking bespilotnik object on having any action;
     * If bespilotnik doesn`t have an action, it gets one;
     */
    public void checkBehaviour() {
        if (behave == null) {
            behave = new Controller();
            behave.setBesp(this);
            new Thread(behave).start();
        }
    }

    /**
     * Handling the pressing on directing buttons (up, down, left, right on the keyboard)
     * Firstly it is checking object on having a behaviour, than it points bespilotnik on nessesary direction
     * and notifies it to move there
     */
    @Override
    public void handle(KeyEvent event) {
        synchronized (this) {
            checkBehaviour();
//            if(this.getOperator() == Operator.FIRST) {
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
                case W:
                    behave.moveToSide(Side.TOP);
                    break;
                case S:
                    behave.moveToSide(Side.BOTTOM);
                    break;
                case A:
                    behave.moveToSide(Side.LEFT);
                    break;
                case D:
                    behave.moveToSide(Side.RIGHT);
                    break;
                default:
                    behave.moveToSide(null);
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
