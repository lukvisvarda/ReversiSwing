package sk.stuba.fei.uim.oop.positioning;

public class Cell {
    private final Position position;
    private final int width;
    private final int height;

    public Cell(Position position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Position getPosition() {
        return position;
    }
}
