package sample.model;

public class Sector {
    private  boolean left;
    private  boolean right;
    private  boolean up;
    private  boolean down;
    private static int numver=0;
    private int number;

    public Sector(boolean left, boolean right, boolean up, boolean down) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        numver++;
        number=numver;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public static int getNumver() {
        return numver;
    }

    public static void setNumver(int numver) {
        Sector.numver = numver;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    @Override
    public String toString() {
        StringBuilder s=new StringBuilder();
        if ((left)) {
            s.append("T");
        } else {
            s.append("F");
        }

        if ((right)) {
            s.append("T");
        } else {
            s.append("F");
        }

        if ((up)) {
            s.append("T");
        } else {
            s.append("F");
        }

        if ((down)) {
            s.append("T");
        } else {
            s.append("F");
        }
        return s.toString()+" "+number;
    }
}
