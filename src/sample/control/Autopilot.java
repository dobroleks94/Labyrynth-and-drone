package sample.control;

import javafx.geometry.Side;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;


public class Autopilot {

    Bespilotnik bespilotnik;

    public Autopilot(Bespilotnik bespilotnik){
        this.bespilotnik = bespilotnik;
    }

    public static boolean moveDown = false;
    public static boolean moveUp = false ;
    public static boolean moveRight = false;
    public static boolean moveLeft = false;
    public static boolean clocksPointer = true;

    public void moveAutopilot() {
        if (moveRight) {
            bespilotnik.getBehave().moveToSide(Side.RIGHT);
            if (bespilotnik.getCurrentSector().isRight() && bespilotnik.getCellX() == (Labyrinth.getCountCells() - 1)) {
                if (clocksPointer) {
                    moveRight = false;
                    moveDown = true;
                    bespilotnik.getBehave().moveToSide(Side.BOTTOM);
                } else {
                    moveRight = false;
                    moveUp = true;
                    bespilotnik.getBehave().moveToSide(Side.TOP);
                }
                return;
            }
            if (clocksPointer) {
                if (!bespilotnik.getCurrentSector().isUp()) {
                    moveRight = false;
                    moveUp = true;
                    bespilotnik.getBehave().moveToSide(Side.TOP);
                    return;
                }
            } else {
                if (!bespilotnik.getCurrentSector().isDown()) {
                    moveRight = false;
                    moveDown = true;
                    bespilotnik.getBehave().moveToSide(Side.BOTTOM);
                    return;
                }
            }

        }
        if (moveDown) {
            bespilotnik.getBehave().moveToSide(Side.BOTTOM);
            if (clocksPointer) {
                if (!bespilotnik.getCurrentSector().isRight()) {
                    moveDown = false;
                    moveRight = true;
                    bespilotnik.getBehave().moveToSide(Side.RIGHT);
                    return;
                }
            } else {
                if (!bespilotnik.getCurrentSector().isLeft()) {
                    moveDown = false;
                    moveLeft = true;
                    bespilotnik.getBehave().moveToSide(Side.LEFT);
                    return;
                }
            }
            if (bespilotnik.getCurrentSector().isDown() && bespilotnik.getCellY() == (Labyrinth.getCountCells() - 1)) {
                if(clocksPointer) {
                    moveDown = false;
                    moveLeft = true;
                    bespilotnik.getBehave().moveToSide(Side.LEFT);
                    return;
                } else{
                    moveDown = false;
                    moveRight = true;
                    bespilotnik.getBehave().moveToSide(Side.RIGHT);
                    return;
                }
            }


        }
        if (moveUp) {
            bespilotnik.getBehave().moveToSide(Side.TOP);
            if (bespilotnik.getCurrentSector().isUp() && bespilotnik.getCellY() == 0) {
                if(clocksPointer) {
                    moveUp = false;
                    moveRight = true;
                    bespilotnik.getBehave().moveToSide(Side.RIGHT);
                    return;
                } else {
                    moveUp = false;
                    moveLeft = true;
                    bespilotnik.getBehave().moveToSide(Side.LEFT);
                    return;
                }
            }

            if(clocksPointer) {
                if (!bespilotnik.getCurrentSector().isLeft()) {
                    moveUp = false;
                    moveLeft = true;
                    bespilotnik.getBehave().moveToSide(Side.LEFT);
                    return;
                }
            } else{
                if (!bespilotnik.getCurrentSector().isRight()) {
                    moveUp = false;
                    moveRight = true;
                    bespilotnik.getBehave().moveToSide(Side.RIGHT);
                    return;
                }
            }

        }

        if (moveLeft) {
            bespilotnik.getBehave().moveToSide(Side.LEFT);
            if (bespilotnik.getCurrentSector().isLeft() && bespilotnik.getCellX() == 0) {
                if(clocksPointer) {
                    moveLeft = false;
                    moveUp = true;
                    bespilotnik.getBehave().moveToSide(Side.TOP);
                    return;
                } else{
                    moveLeft = false;
                    moveDown = true;
                    bespilotnik.getBehave().moveToSide(Side.BOTTOM);
                    return;
                }
            }
            if(clocksPointer) {
                if (!bespilotnik.getCurrentSector().isDown() && bespilotnik.getCellY() == (Labyrinth.getCountCells() - 1)) {
                    moveLeft = false;
                    moveDown = true;
                    bespilotnik.getBehave().moveToSide(Side.BOTTOM);
                    return;
                }
            } else{
                if (!bespilotnik.getCurrentSector().isUp() && bespilotnik.getCellY() == 0) {
                    moveLeft = false;
                    moveUp = true;
                    bespilotnik.getBehave().moveToSide(Side.TOP);
                    return;
                }
            }


        }
    }
}
