package sample;

import java.util.Random;

public class Labyrinth {
    private final int sizeX;
    private final int sizeY;
    private Sector[][] sectors;

    public Labyrinth(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        sectors=new Sector[sizeX][sizeY];
        fillLab();
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void addSector(Sector s,int indexX,int indexY){
        sectors[indexX][indexY]=s;
    }

    public void addRandomSector(int indexX,int indexY){
        Random random=new Random();
        boolean left=random.nextBoolean();
        boolean right=random.nextBoolean();
        boolean up=random.nextBoolean();
        boolean down=random.nextBoolean();
        sectors[indexX][indexY]=new Sector(left,right,up,down);
    }

    public void fillLab(){
        for(int i=0;i<sectors.length;i++){
            for (int j=0;j<sectors[i].length;j++){
                addRandomSector(i,j);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();
        for(int i=0;i<sectors.length;i++){
            for(int j=0;j<sectors[i].length;j++){
                s.append(sectors[i][j]+" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}

