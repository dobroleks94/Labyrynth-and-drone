package sample.control;

import javafx.concurrent.Task;
import javafx.geometry.Side;
import javafx.scene.paint.Color;
import sample.model.Bespilotnik;
import sample.model.Labyrinth;
import sample.view.Drawing;

public class Controller extends Task {
    private boolean verticalSize = false;
    private boolean positiveDirection = false;
    private static int v = 1;
    private int n ;

    public Controller(){v++;
    n = v;}

    private Bespilotnik besp;

    public void setBesp(Bespilotnik besp) {
        this.besp = besp;
    }

    Labyrinth labyrinth;

    @Override
    protected Object call() {
        try {
            synchronized (besp) {
                while (besp.getScene().getWindow().isShowing()) {
                    double length;
                    labyrinth = besp.getLabyrinth();

                    if(!besp.isAutopilot() && besp.isOperated())
                        if (!besp.isFirstStep())
                            besp.wait(); //waiting for allowing to move

                    if (!besp.isOnFinish()) {
                        if(besp.isOperated()){
                            doStep(besp, verticalSize, positiveDirection);
                            for (Bespilotnik b : besp.getChild()) {
                                if (b != null && !b.isCanMove())
                                    b.setCanMove(true);
                            }
                        } else {
                            setCurrent(besp);
                            besp.getBehave().moveToSide(besp.current);
                            length = Math.sqrt(Math.pow((besp.getVerifX() - besp.getLeadBesp().getX()),2) +
                                    Math.pow((besp.getVerifY() - besp.getLeadBesp().getY()),2));
                            while(length>Labyrinth.getCountCells()*2) {
                                besp.setStroke(Color.GREEN);
                                Thread.sleep(100);
                                besp.setStroke(Color.YELLOW);
                                Thread.sleep(100);
                                besp.setStroke(Color.GREEN);

                                setCurrent(besp);
                                besp.getBehave().moveToSide(besp.current);
                                length = Math.sqrt(Math.pow((besp.getVerifX() - besp.getLeadBesp().getX()), 2) +
                                        Math.pow((besp.getVerifY() - besp.getLeadBesp().getY()), 2));
                            }
                            System.out.println(length);
                            doStep(besp, verticalSize, positiveDirection);
                            if(besp.getMain().isOnFinish()){
                                besp.setAutopilot(true);
                                besp.setOperated(true);
                            }

                        }
                    }

                    if (besp.isFirstStep())
                        besp.setFirstStep(false);

                    besp.setCurrentSector(labyrinth.getSector(besp.getY(), besp.getX())); // gets sector after moving
                    Drawing.skyLine(labyrinth,besp.getY(), besp.getX(),besp.getCellY(),besp.getCellX());

                    finishVerifiening(besp);
                    paintBespilotnik(besp);

                    if(besp.isAutopilot() || !besp.isOperated())
                        Thread.sleep(350);

                    if(besp.isAutopilot()) {
                        besp.checkBehaviour();
                        besp.setAutopilotBespil(new Autopilot(besp));
                        besp.getAutopilotBespil().moveAutopilot();
                    }
                    if(besp.isOnFinish() && besp.isOperated()) {
                        for (Bespilotnik b : besp.getChild()) {
                            if (b.getLeadBesp().isOnFinish()) {
                                b.setAutopilot(true);
                                b.setAutopilotBespil(new Autopilot(b));
                                b.getAutopilotBespil().moveAutopilot();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setCurrent(Bespilotnik besp) {
        besp.current = besp.getRandomSide();
        while(besp.current == besp.previous || besp.current == besp.previousOpposite)
            besp.current = besp.getRandomSide();
        besp.previous = besp.current;
        besp.previousOpposite = besp.previous == Side.LEFT ? Side.RIGHT :
                besp.previous == Side.RIGHT ? Side.LEFT :
                        besp.previous == Side.TOP ? Side.BOTTOM :
                                besp.previous == Side.BOTTOM ? Side.TOP : null;
    }

    /**
     *  defines if side, where Bespilotnik moves in Labyrinth, is vertical
     *  and sets TRUE (moves on right or on left) of FALSE (in top or bottom)
     *  Then defines positive direction
     *   if pressed button (side) is Side.TOP, for example, positiveDirection will be false
     */
    public void moveToSide(Side side) {
        verticalSize = side.isVertical();
        positiveDirection = side == (verticalSize ? Side.RIGHT : Side.BOTTOM);
    }

    private void finishVerifiening (Bespilotnik besp){
        if (besp.getCurrentSector() == besp.getFinish()) {
            besp.setOnFinish(true);
            Drawing.drawFin(besp.getLabyrinth());
        }
    }
    private void paintBespilotnik(Bespilotnik besp){
        besp.setFill((besp.getCurrentSector() == besp.getFinish()) ? Color.RED :
                (besp.getCurrentSector() == besp.getStart()) ? Color.BLUE
                        : besp.ownColor());
    }

    private void doStep(Bespilotnik besp, boolean verticalSize, boolean positiveDirection) {
        if (verticalSize) {
            besp.setCellX(besp.getCellX() + (positiveDirection ? 1 : -1));
            if(besp.getCellX()<0 || besp.getCellX() == labyrinth.getCountCells()) {

                besp.setCellX(besp.getCellX() == labyrinth.getCountCells() ?
                        positiveDirection && (!besp.getCurrentSector().isRight()) ?  // Sets coordinates of current cell in sector
                            0 : labyrinth.getCountCells()-1 :
                                (!besp.getCurrentSector().isLeft()) ? labyrinth.getCountCells()-1 : 0
                        );
                besp.setX(besp.getX() + (positiveDirection ?
                        (!besp.getCurrentSector().isRight()) ? 1 : 0 :   // Sets coordinates of current sector
                        (!besp.getCurrentSector().isLeft()) ? -1 : 0));
                besp.setCenterX(besp.getCenterX() + (positiveDirection ?
                        (!besp.getCurrentSector().isRight()) ? besp.getStep() : 0 :  // Sets coordinates of current location
                        (!besp.getCurrentSector().isLeft()) ? -besp.getStep() : 0));

                System.out.printf("Cell - [ %d ; %d ]\n", besp.getCellX(), besp.getCellY());
                System.out.printf("Sector - [ %d ; %d ]\n\n", besp.getX(), besp.getY());
                return;
            }
            besp.setCenterX(besp.getCenterX() + (positiveDirection ? besp.getStep() : -besp.getStep()));
        } else {
            besp.setCellY(besp.getCellY() + (positiveDirection ? 1 : -1));

            if(besp.getCellY()<0 || besp.getCellY() == labyrinth.getCountCells()) {

                besp.setCellY(besp.getCellY() == labyrinth.getCountCells() ?
                        positiveDirection && (!besp.getCurrentSector().isDown()) ?
                                0 : labyrinth.getCountCells()-1 :
                        (!besp.getCurrentSector().isUp()) ? labyrinth.getCountCells()-1 : 0
                );
                besp.setY(besp.getY() + (positiveDirection ?
                        (!besp.getCurrentSector().isDown()) ? 1 : 0 :
                        (!besp.getCurrentSector().isUp()) ? -1 : 0));
                besp.setCenterY(besp.getCenterY() + (positiveDirection ?
                        (!besp.getCurrentSector().isDown()) ? besp.getStep() : 0 :
                        (!besp.getCurrentSector().isUp()) ? -besp.getStep() : 0));

                System.out.printf("Cell - [ %d ; %d ]\n", besp.getCellX(), besp.getCellY());
                System.out.printf("Sector - [ %d ; %d ]\n\n", besp.getX(), besp.getY());
                return;
            }
            besp.setCenterY(besp.getCenterY() + (positiveDirection ? besp.getStep() : -besp.getStep()));
        }

        System.out.printf("Cell - [ %d ; %d ]\n", besp.getCellX(), besp.getCellY());
        System.out.printf("Sector - [ %d ; %d ]\n\n", besp.getX(), besp.getY());
    }

    @Override
    public String toString() {
        return "Controller{" +
                "n=" + n +
                '}';
    }
}