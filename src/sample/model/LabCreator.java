package sample.model;

import java.util.Random;

public class LabCreator {
    public static void addRandomSector(int indexY, int indexX,Labyrinth lab) { //Сектор с рандомными значениями стен
        Random random = new Random();
        lab.addSector(new Sector(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean()),indexX,indexY);
    }

    public static void fillLab(Labyrinth lab) { //Заполнение лабиринта секторами
        for (int i = 0; i < lab.getSizeY(); i++) {
            for (int j = 0; j < lab.getSizeX(); j++) {
                if (lab.getSector(i,j) == null)
                    addRandomSector(j,i,lab);
            }
        }
    }

    public static void fillRoad(Labyrinth lab) { // "Прокладывание дороги" в лабиринте
        lab.setSectors( new Sector[lab.getSizeY()][lab.getSizeX()]);
        Random random = new Random();
        if(random.nextBoolean()){
            lab.setStartX(random.nextInt(lab.getSizeX()));
            if(random.nextBoolean())
                lab.setStartY(0);
            else
                lab.setStartY(lab.getSizeY()-1);
        }
        else {
            lab.setStartY( random.nextInt(lab.getSizeY()));
            if(random.nextBoolean())
                lab.setStartX(0);
            else
                lab.setStartX(lab.getSizeX()-1);
        }

        int x =  lab.getStartX();
        int y =  lab.getStartY();
        int count = 0;
        int countAll=0;
        lab.addSector(new Sector(false, false, false, false),y,x);
        while (true) {
            countAll++;
            switch (random.nextInt(4)) {
                case 0:
                    if (x - 1 >= 0) {
                        if (lab.getSector(y,x - 1) == null) {
                            lab.getSector(y,x ).setLeft(false);
                            lab.addSector( new Sector(random.nextBoolean(), false, random.nextBoolean(), random.nextBoolean()),y,x-1);
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
                        if (lab.getSector(y-1, x ) == null) {
                            lab.getSector(y,x ).setUp(false);
                            lab.addSector(new Sector(random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), false),y-1,x);
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
                        if (lab.getSector(y,x + 1) == null) {
                            lab.getSector(y,x ).setRight(false);
                            lab.addSector( new Sector(false, random.nextBoolean(), random.nextBoolean(), random.nextBoolean()),y,x+1);
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
                        if (lab.getSector(y+1,x ) == null) {
                            lab.getSector(y,x ).setDown(false);
                            lab.addSector( new Sector(random.nextBoolean(), random.nextBoolean(), false, random.nextBoolean()),y+1,x);
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
            if(countAll>lab.getSizeX()*lab.getSizeY()/2) { //Предостережение зацикливания
                fillRoad(lab);
                return;
            }
        }
    }

}
