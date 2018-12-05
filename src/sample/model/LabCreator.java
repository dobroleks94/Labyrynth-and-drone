package sample.model;

import java.util.Random;

public class LabCreator {

    public static void addRandomSector(int indexY, int indexX, Labyrinth lab) { //Сектор с рандомными значениями стен
        Random random = new Random();
        lab.addSector(new Sector(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean()), indexX, indexY);
    }


    public static void fillLab(Labyrinth lab) { //Заполнение лабиринта секторами
        for (int i = 0; i < lab.getSizeY(); i++) {
            for (int j = 0; j < lab.getSizeX(); j++) {
                if (lab.getSector(i, j) == null)
                    addRandomSector(j, i, lab);
            }
        }
    }
    public static void fillRoad(Labyrinth lab) { // "Прокладывание дороги" в лабиринте
        lab.setSectors(new Sector[lab.getSizeY()][lab.getSizeX()]);
        Random random = new Random();
        do {
            if (random.nextBoolean()) {
                lab.setStartX(random.nextInt(lab.getSizeX()));
                if (random.nextBoolean())
                    lab.setStartY(0);
                else
                    lab.setStartY(lab.getSizeY() - 1);
            } else {
                lab.setStartY(random.nextInt(lab.getSizeY()));
                if (random.nextBoolean())
                    lab.setStartX(0);
                else
                    lab.setStartX(lab.getSizeX() - 1);
            }
        } while ((lab.getStartY() == 0 && lab.getStartX() == 0) ||
                (lab.getStartY() == 0 && lab.getStartX() == lab.getSizeX() - 1) ||
                (lab.getStartY() == lab.getSizeY() - 1 && lab.getStartX() == 0) ||
                (lab.getStartY() == lab.getSizeY() - 1 && lab.getStartX() == lab.getSizeX() - 1));
        int x = lab.getStartX();
        int y = lab.getStartY();
        int count = 0;
        int countAll = 0;
        lab.addSector(new Sector(false, false, false, false), y, x);
        while (true) {
            countAll++;
            switch (random.nextInt(4)) {
                case 0:
                    if (x - 1 >= 0) {
                        if (lab.getSector(y, x - 1) == null) {
                            lab.getSector(y, x).setLeft(false);
                            lab.addSector(new Sector(random.nextBoolean(), false, random.nextBoolean(), random.nextBoolean()), y, x - 1);
                            x--;
                            count++;
                            if (count >= (lab.getSizeY() + lab.getSizeX()) / 4 && x == 0) {
                                lab.setFinX(x);
                                lab.setFinY(y);
                                return;
                            }
                        }
                    }
                    break;
                case 1:
                    if (y - 1 >= 0) {
                        if (lab.getSector(y - 1, x) == null) {
                            lab.getSector(y, x).setUp(false);
                            lab.addSector(new Sector(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), false), y - 1, x);
                            y--;
                            count++;
                            if (count >= (lab.getSizeY() + lab.getSizeX()) / 4 && y == 0) {
                                lab.setFinX(x);
                                lab.setFinY(y);
                                return;
                            }
                        }
                    }
                    break;
                case 2:
                    if (x + 1 <= lab.getSizeX() - 1) {
                        if (lab.getSector(y, x + 1) == null) {
                            lab.getSector(y, x).setRight(false);
                            lab.addSector(new Sector(false, random.nextBoolean(), random.nextBoolean(), random.nextBoolean()), y, x + 1);
                            x++;
                            count++;
                            if (count >= (lab.getSizeY() + lab.getSizeX()) / 4 && x == lab.getSizeX() - 1) {
                                lab.setFinX(x);
                                lab.setFinY(y);
                                return;
                            }
                        }
                    }
                    break;
                case 3:
                    if (y + 1 <= lab.getSizeY() - 1) {
                        if (lab.getSector(y + 1, x) == null) {
                            lab.getSector(y, x).setDown(false);
                            lab.addSector(new Sector(random.nextBoolean(), random.nextBoolean(), false, random.nextBoolean()), y + 1, x);
                            y++;
                            count++;
                            if (count >= (lab.getSizeY() + lab.getSizeX()) / 4 && y == lab.getSizeY() - 1) {
                                lab.setFinX(x);
                                lab.setFinY(y);
                                return;
                            }
                        }
                    }
                    break;
            }
            if (countAll > lab.getSizeX() * lab.getSizeY() / 2) { //Предостережение зацикливания
                fillRoad(lab);
                return;
            }
        }
    }
    public static void fillCells(Labyrinth lab) {
        Sector[][] sectors = lab.getSectors();
        for (Sector[] sector : sectors) {
            for (int x = 0; x < sector.length; x++) {
                for (int yc = 0; yc < sector[x].getCells().length; yc++) {
                    for (int xc = 0; xc < sector[x].getCells()[yc].length; xc++) {
                        sector[x].setCell(new Cell(), yc, xc);
                        if (sector[x].isUp() && yc == 0) {
                            sector[x].getCells()[yc][xc].setUp(true);
                        }
                        if (sector[x].isRight() && xc == Labyrinth.getCountCells() - 1) {
                            sector[x].getCells()[yc][xc].setRight(true);
                        }
                        if (sector[x].isDown() && yc == Labyrinth.getCountCells() - 1) {
                            sector[x].getCells()[yc][xc].setDown(true);
                        }
                        if (sector[x].isLeft() && xc == 0) {
                            sector[x].getCells()[yc][xc].setLeft(true);
                        }
                    }
                }
            }
        }
    }

    public static void setWalls(Labyrinth lab) {
        Sector[][] sectors = lab.getSectors();
        for (int y = 0; y < sectors.length; y++) {
            for (int x = 0; x < sectors[y].length; x++) {

                if (x == 0)
                    sectors[y][x].setLeft(true);
                else if (!sectors[y][x].isLeft() || !sectors[y][x - 1].isRight()) {
                    sectors[y][x].setLeft(false);
                    sectors[y][x - 1].setRight(false);
                }

                if (x == sectors[y].length - 1)
                    sectors[y][x].setRight(true);
                else if (!sectors[y][x].isRight() || !sectors[y][x + 1].isLeft()) {
                    sectors[y][x].setRight(false);
                    sectors[y][x + 1].setLeft(false);
                }

                if (y == 0) {
                    sectors[y][x].setUp(true);
                } else if (!sectors[y][x].isUp() || !sectors[y - 1][x].isDown()) {
                    sectors[y][x].setUp(false);
                    sectors[y - 1][x].setDown(false);
                }

                if (y == sectors.length - 1)
                    sectors[y][x].setDown(true);
                else if (!sectors[y][x].isDown() || !sectors[y + 1][x].isUp()) {
                    sectors[y][x].setDown(false);
                    sectors[y + 1][x].setUp(false);
                }

            }
        }
    }
}
