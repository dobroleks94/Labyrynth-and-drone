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
    public void setBesp(Bespilotnik besp) {
        this.besp = besp;
    }

    Sector sector;
    Sector neighborSectorL;
    Sector neighborSectorR;
    Sector neighborSectorU;
    Sector neighborSectorD;

    private int moveRightLeft;
    private int moveUpDown;

    private boolean isNotEdgeX;
    private boolean isNotEdgeY;
    private boolean allowRight;
    private boolean allowLeft;
    private boolean allowUp;
    private boolean allowDown;
    private double UpDown;
    private double LeftRight;

    private void setStartSector(){
        besp.setX(besp.getLabyrinth().getStartX());
        besp.setY(besp.getLabyrinth().getStartY());
    } //setting to bespilotnik coordinates of sector, where it is located

    @Override
    protected Object call() throws Exception {
        synchronized (besp) {
            setStartSector();
            while (besp.getScene().getWindow().isShowing()) {
                if (!firstStep)
                    besp.wait(); //waiting for allowing to move

                moveRightLeft = besp.getX() + (positiveDirection ? 1 : -1); //sets labyrint coordinates while moving
                moveUpDown = besp.getY() + (positiveDirection ? 1 : -1);

                isNotEdgeX = moveRightLeft < besp.getLabyrinth().getSizeX() && moveRightLeft >= 0 ; //verify the end of labyrint
                isNotEdgeY = moveUpDown < besp.getLabyrinth().getSizeY() && moveUpDown >= 0 ; // the same



                condition:
                if (verticalSize) {
                    if(firstStep) {
                        besp.setCenterX(besp.getCenterX() + (positiveDirection ? step : -step));
                        sector = besp.getLabyrinth().getSector(besp.getY(), besp.getX());
                        System.out.println(besp.getX());
                        System.out.println(besp.getY());
                        System.out.println(sector.isUp() + "\n" + sector.isRight() + "\n" + sector.isDown() + "\n" + sector.isLeft());
                        break condition;
                    }
                    if (isNotEdgeX) {
                        besp.setCenterX(besp.getCenterX() + (positiveDirection ? step : -step));
                        if(!firstStep) {
                            besp.setX(besp.getX() + (positiveDirection ? 1 : -1));
                            sector = besp.getLabyrinth().getSector(besp.getY(), besp.getX());
                            System.out.println(besp.getX());
                            System.out.println(besp.getY());
                            System.out.println(sector.isUp() + "\n" + sector.isRight() + "\n" + sector.isDown() + "\n" + sector.isLeft());
                        }
                    }
                } else {
                    if(firstStep) {
                        besp.setCenterY(besp.getCenterY() + (positiveDirection ? step : -step));
                        sector = besp.getLabyrinth().getSector(besp.getY(), besp.getX());
                        System.out.println(besp.getX());
                        System.out.println(besp.getY());
                        System.out.println(sector.isUp() + "\n" + sector.isRight() + "\n" + sector.isDown() + "\n" + sector.isLeft());
                        break condition;
                    }
                    if (isNotEdgeY) {
                        besp.setCenterY(besp.getCenterY() + (positiveDirection ? step : -step));
                        if(!firstStep) {
                            besp.setY(besp.getY() + (positiveDirection ? 1 : -1));
                            sector = besp.getLabyrinth().getSector(besp.getY(), besp.getX());
                            System.out.println(besp.getX());
                            System.out.println(besp.getY());
                            System.out.println(sector.isUp() + "\n" + sector.isRight() + "\n" + sector.isDown() + "\n" + sector.isLeft());
                        }
                    }
                }
                if (firstStep) {
                    firstStep = false;
                    step = 30.0;
                }

                /*neighborSectorL = besp.getLabyrinth().getSector(besp.getX()-1, moveUpDown);
                allowLeft = !sector.isLeft() && !neighborSectorL.isRight();
                System.out.println("left "+allowLeft);

                neighborSectorR = besp.getLabyrinth().getSector(besp.getX()+1, moveUpDown);
                allowRight = !sector.isRight() && !neighborSectorR.isLeft();
                System.out.println("right "+allowRight);

                neighborSectorD = besp.getLabyrinth().getSector(moveRightLeft, besp.getY()-1);
                allowDown = !sector.isDown() && !neighborSectorD.isUp();
                System.out.println("down "+allowDown);

                neighborSectorU = besp.getLabyrinth().getSector(moveRightLeft, besp.getY()+1);
                allowUp = sector.isUp() && neighborSectorU.isDown();
                System.out.println("up "+allowUp);*/









            }
            return null;

        }
    }

    public void moveToSide(Side side) {
        verticalSize = side.isVertical();
        positiveDirection = side == (verticalSize ? Side.RIGHT : Side.BOTTOM);
    }
}
