package sample;

import java.util.Random;

public class Labyrinth {
    private final int sizeX;
    private final int sizeY;
    private int startX;
    private int startY;
    private int finX;
    private int finY;
    private Sector[][] sectors;

    public Labyrinth(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        fillRoad();
        fillLab();
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

    public void addSector(Sector s, int indexX, int indexY) {
        sectors[indexX][indexY] = s;
    }

    public Sector[][] getSectors() {
        return sectors;
    }

    public void addRandomSector(int indexX, int indexY) {
        Random random = new Random();
        sectors[indexX][indexY] = new Sector(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
    }

    private void fillLab() {
        for (int i = 0; i < sectors.length; i++) {
            for (int j = 0; j < sectors[i].length; j++) {
                if (sectors[i][j] == null)
                    addRandomSector(i, j);
            }
        }
    }

    private void fillRoad() {
        sectors = new Sector[sizeY][sizeX];
        Random random = new Random();
        if(random.nextBoolean()){
            startX = random.nextInt(sizeX);
            if(random.nextBoolean())
                startY=0;
            else
                startY=sizeY-1;
        }
        else {
            startY = random.nextInt(sizeY);
            if(random.nextBoolean())
                startX=0;
            else
                startX=sizeX-1;
        }

        int x = startX;
        int y = startY;
        int count = 0;
        int countAll=0;
        sectors[y][x] = new Sector(false, false, false, false);
        while (true) {
            countAll++;
            switch (random.nextInt(4)) {
                case 0:
                    if (x - 1 >= 0) {
                        if (sectors[y][x - 1] == null) {
                            sectors[y][x].setLeft(false);
                            sectors[y][x - 1] = new Sector(random.nextBoolean(), false, random.nextBoolean(), random.nextBoolean());
                            x--;
                            count++;
                            if (count >= (sizeY + sizeX) / 4 && x == 0) {
                                finX = x;
                                finY = y;
                                return;
                            }
                        }
                    }
                    break;
                case 1:
                    if (y - 1 >= 0) {
                        if (sectors[y - 1][x] == null) {
                            sectors[y][x].setUp(false);
                            sectors[y - 1][x] = new Sector(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), false);
                            y--;
                            count++;
                            if (count >= (sizeY + sizeX) / 4 && y == 0) {
                                finX = x;
                                finY = y;
                                return;
                            }
                        }
                    }
                    break;
                case 2:
                    if (x + 1 <= sizeX - 1) {
                        if (sectors[y][x + 1] == null) {
                            sectors[y][x].setRight(false);
                            sectors[y][x + 1] = new Sector(false, random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
                            x++;
                            count++;
                            if (count >= (sizeY + sizeX) / 4 && x == sizeX - 1) {
                                finX = x;
                                finY = y;
                                return;
                            }
                        }
                    }
                    break;
                case 3:
                    if (y + 1 <= sizeY - 1) {
                        if (sectors[y + 1][x] == null) {
                            sectors[y][x].setDown(false);
                            sectors[y + 1][x] = new Sector(random.nextBoolean(), random.nextBoolean(), false, random.nextBoolean());
                            y++;
                            count++;
                            if (count >= (sizeY + sizeX) / 4 && y == sizeY - 1) {
                                finX = x;
                                finY = y;
                                return;
                            }
                        }
                    }
                    break;
            }
            if(countAll>sizeX*sizeY/2) {
                fillRoad();
                return;
            }
        }
    }

    private void addClearSector(){

    }

    @Override
    public String toString() {
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

