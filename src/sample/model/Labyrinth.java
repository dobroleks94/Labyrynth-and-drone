package sample.model;

public class Labyrinth {
    private final int sizeX; //Размеры поля
    private final int sizeY;
    private int startX;//Положение начальной точки
    private int startY;
    private int finX;//Положение конечной точки
    private int finY;
    private Sector[][] sectors;

    private static String startWall;
    private static int countCells;

    public Labyrinth(int sizeX, int sizeY, int countCellss) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        countCells = countCellss;
        LabCreator.fillRoad(this);
        LabCreator.fillLab(this);
        LabCreator.setWalls(this);
        LabCreator.fillCells(this);
    }

    public static int getCountCells() {
        return countCells;
    }

    public static void setCountCells(int countCells) {
        Labyrinth.countCells = countCells;
    }

    static String getStartWall() {
        return startWall;
    }

    public void setStartWall(String startWalll) {
        startWall = startWalll;
    }

    private static String finishWall;

    public static String getFinishWall() {
        return finishWall;
    }

    public void setFinishWall(String finishWalll) {
        finishWall = finishWalll;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getFinX() {
        return finX;
    }

    public void setFinX(int finX) {
        this.finX = finX;
    }

    public int getFinY() {
        return finY;
    }

    public void setFinY(int finY) {
        this.finY = finY;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void addSector(Sector s, int indexY, int indexX) {
        sectors[indexY][indexX] = s;
    }

    public Sector getSector(int indexY, int indexX) {
        return sectors[indexY][indexX];
    }

    public void setSectors(Sector[][] sectors) {
        this.sectors = sectors;
    }

    public Sector[][] getSectors() {
        return sectors;
    }

    @Override
    public String toString() {              //Only for debug
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                s.append(sectors[i][j] + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}

