package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.positioning.Position;
import sk.stuba.fei.uim.oop.positioning.Cell;

import java.awt.*;

public class Grid extends Cell {
    private boolean highlight;
    private int stone;
    private boolean setNewColor;

    public Grid(Position position, int width, int height) {
        super(position, width, height);
        this.highlight = false;
        this.setNewColor = false;
    }
    public void restart() {
        stone = 0;
        highlight = false;
        setNewColor = false;
    }

    public void setHighlight(boolean highlight) {
        this.highlight = highlight;
    }

    public void setStone(int stone) {
        this.stone = stone;
    }

    public int getStone() {
        return stone;
    }

    public boolean isHighlight() {
        return highlight;
    }

    public void mouseEnteredNewColor(boolean setNewColor) {
        this.setNewColor = setNewColor;
    }


    public boolean isSetNewColor() {
        return setNewColor;
    }

    public void paint(Graphics g) {
        if(highlight) {
            g.setColor(new Color(0, 201, 255));
            g.drawOval(getPosition().getX() , getPosition().getY() , getWidth() - getWidth()/4/10, getHeight() - getHeight()/4/10);
        }

        if(setNewColor) {
            g.setColor(new Color(0,201,255));
            g.fillOval(getPosition().getX(), getPosition().getY(), getWidth() - getWidth()/4/10, getHeight() - getHeight()/4/10);
        }

        if(stone == 0) {
            return;
        }

        g.setColor(stone == 1 ? Color.BLACK : Color.WHITE);
        g.fillOval(getPosition().getX(), getPosition().getY(), getWidth(), getHeight());
    }
}
