package sk.stuba.fei.uim.oop.positioning;

public class Position {
    public static final Position DOWN = new Position(0,1);
    public static final Position UP = new Position(0,-1);
    public static final Position LEFT = new Position(-1,0);
    public static final Position RIGHT = new Position(1,0);
    public static final Position DOWN_RIGHT = new Position(1,1);
    public static final Position DOWN_LEFT = new Position(-1,1);
    public static final Position UP_RIGHT = new Position(1,-1);
    public static final Position UP_LEFT = new Position(-1,-1);

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addDirection(Position direction) {
        this.x += direction.x;
        this.y += direction.y;
    }
}


