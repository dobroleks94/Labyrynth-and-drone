package sample;

public class Sector {
    private final boolean left;
    private final boolean right;
    private final boolean up;
    private final boolean down;

    public Sector(boolean left, boolean right, boolean up, boolean down) {
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
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
        return s.toString();
    }
}
