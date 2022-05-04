package sk.stuba.fei.uim.oop.logic;

import sk.stuba.fei.uim.oop.gui.Grid;
import sk.stuba.fei.uim.oop.positioning.Position;
import sk.stuba.fei.uim.oop.positioning.Cell;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameLogic extends Cell {
    private final List<Position> validMoves;
    private final Grid[][] field;
    private int white;
    private int black;
    private final Position position;
    private final int gameSizeInt;
    private final int cellWidth;
    private final int cellHeight;

    public GameLogic(Position position, int width, int height, int gameSizeInt) {
        super(position, width, height);
        this.gameSizeInt = gameSizeInt;
        this.position = position;
        field = new Grid[gameSizeInt][gameSizeInt];
        cellWidth = width / gameSizeInt;
        cellHeight = height / gameSizeInt;
        for (int i = 0; i < gameSizeInt; i++) {
            for (int j = 0; j < gameSizeInt; j++) {
                field[i][j] = new Grid(new Position(position.getX() + cellWidth * i, position.getY() + cellHeight * j), cellWidth, cellHeight);
            }
        }

        this.validMoves = new ArrayList<>();
        start();
        updateHighlight(1);
    }

    public List<Position> getValidMoves() {
        return validMoves;
    }

    public Position getMaximalMove() {
        Position pos = new Position(0, 0);
        Map<Integer, List<Position>> map;
        int max = 0;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < gameSizeInt; j++) {
                if (field[i][j].getStone() == 0 && getChangedStones(new Position(i, j), 2).size() > 0) {
                    map = (getChangedStones(new Position(i, j), 2));
                    for (Integer key : map.keySet()) {
                        if (key >= max) {
                            max = key;
                            pos = new Position(i,j);
                        }
                    }
                }
            }
        }
        return pos;
    }


    public List<Position> newColorPosition() {
        List<Position> list = new ArrayList<>();

        for(int i = 0; i < field.length; i++) {
            for (int j = 0; j < gameSizeInt; j++) {
                if(field[i][j].isSetNewColor()) {
                    list.add(new Position(i, j));
                }
            }
        }
        return list;
    }

    public int coloredPositionsCounter() {
        int counter = 0;
        for(int i = 0; i < field.length; i++) {
            for (int j = 0; j < gameSizeInt; j++) {
                if(field[i][j].isSetNewColor()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public boolean isValidMove(Position position) {
        for (Position validMove : validMoves) {
            if (validMove.getX() == position.getX() && validMove.getY() == position.getY()) {
                return true;
            }
        }
        return false;
    }

    public void restart() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < gameSizeInt; j++) {
                field[i][j].restart();
            }
        }
        start();
        updateHighlight(1);
    }

    private void drawGridLines(Graphics g) {
        g.setColor(Color.BLACK);
        int y2 = position.getY() + getHeight();
        int y1 = position.getY();
        for (int i = 0; i < field.length + 1; i++)
            g.drawLine(position.getX() + i * cellWidth, y1, position.getX() + i * cellWidth, y2);

        int x2 = position.getX() + getWidth();
        int x1 = position.getX();
        for (int j = 0; j < gameSizeInt + 1; j++)
            g.drawLine(x1, position.getY() + j * cellHeight, x2, position.getY() + j * cellHeight);
    }

    public void paint(Graphics g) {
        drawGridLines(g);
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < gameSizeInt; j++) {
                field[i][j].paint(g);
            }
        }
    }

    public void start() {
        field[(gameSizeInt / 2) - 1][(gameSizeInt / 2) - 1].setStone(1);
        field[gameSizeInt / 2][(gameSizeInt / 2) - 1].setStone(2);
        field[(gameSizeInt / 2) - 1][gameSizeInt / 2].setStone(2);
        field[gameSizeInt / 2][gameSizeInt / 2].setStone(1);
        countStones();
    }

    public void countStones() {
        black = 0;
        white = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                if (field[i][j].getStone() == 1) {
                    black++;
                }
                if (field[i][j].getStone() == 2) {
                    white++;
                }
            }
        }
    }

    public void updateHighlight(int player) {
        for (int i = 0; i < validMoves.size(); i++) {
            field[validMoves.get(i).getX()][validMoves.get(i).getY()].setHighlight(false);
        }
        validMoves.clear();
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < gameSizeInt; j++) {
                if (field[i][j].getStone() == 0 && getChangedStones(new Position(i, j), player).size() > 0) {
                    validMoves.add(new Position(i, j));
                }
            }
        }

        for (int i = 0; i < validMoves.size(); i++) {
            field[validMoves.get(i).getX()][validMoves.get(i).getY()].setHighlight(true);
        }

    }

    public Map<Integer, List<Position>> getChangedStones(Position position, int player) {
        Map<Integer,List<Position>> map = new HashMap<>();
        map.putAll(movingInGrid(position, Position.UP, player));
        map.putAll(movingInGrid(position, Position.DOWN, player));
        map.putAll(movingInGrid(position, Position.LEFT, player));
        map.putAll(movingInGrid(position, Position.RIGHT, player));
        map.putAll(movingInGrid(position, Position.UP_LEFT, player));
        map.putAll(movingInGrid(position, Position.UP_RIGHT, player));
        map.putAll(movingInGrid(position, Position.DOWN_LEFT, player));
        map.putAll(movingInGrid(position, Position.DOWN_RIGHT, player));

        return map;
    }

    public Map<Integer, List<Position>> movingInGrid(Position position, Position direction, int player) {
        List<Position> isValid = new ArrayList<>();
        Map<Integer, List<Position>> map = new HashMap<>();
        int player2;
        if (player == 1) player2 = 2;
        else player2 = 1;
        int max = 0;

        Position movingInGrid = new Position(position.getX(), position.getY());
        movingInGrid.addDirection(direction);

        while (inField(movingInGrid) && field[movingInGrid.getX()][movingInGrid.getY()].getStone() == player2) {
            max++;
            isValid.add(new Position(movingInGrid.getX(), movingInGrid.getY()));
            movingInGrid.addDirection(direction);
        }
        if(isValid.size() > 0) {
            map.put(max, isValid);
        }
        if (!inField(movingInGrid) || field[movingInGrid.getX()][movingInGrid.getY()].getStone() != player) {
            map.clear();
        }

        return map;
    }

    public Position getMousePosition(Position mousePosition) {
        int posX = (mousePosition.getX() - getPosition().getX()) / cellWidth;
        int posY = (mousePosition.getY() - getPosition().getY()) / cellHeight;
        if (posX >= field.length || posX < 0 || posY >= gameSizeInt || posY < 0) {
            return new Position(-1, -1);
        }
        return new Position(posX, posY);
    }

    public void play(Position position, int player) {
        int player2;
        if (player == 1) player2 = 2;
        else player2 = 1;
        field[position.getX()][position.getY()].setStone(player);
        Map<Integer, List<Position>> changedStones = getChangedStones(position, player);
        for(List<Position> value: changedStones.values()) {
            for (int i = 0; i < value.size(); i++) {
                field[value.get(i).getX()][value.get(i).getY()].setStone(player);
            }
        }
        updateHighlight(player2);
    }

    public boolean inField(Position position) {
        if (position.getX() < 0 || position.getX() >= field.length || position.getY() < 0 || position.getY() >= gameSizeInt) {
            return false;
        }
        return true;
    }

    public Grid[][] getField() {
        return field;
    }

    public int getWhite() {
        return white;
    }

    public int getBlack() {
        return black;
    }
}
