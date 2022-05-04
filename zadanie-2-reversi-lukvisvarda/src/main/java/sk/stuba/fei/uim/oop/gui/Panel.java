package sk.stuba.fei.uim.oop.gui;

import sk.stuba.fei.uim.oop.logic.GameLogic;
import sk.stuba.fei.uim.oop.positioning.Position;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {
    private int player = 1;
    private int gameSizeInt;
    private final GameLogic logic;
    private String str;
    private String str2;
    private final JLabel label;
    private final JLabel label2;

    public Panel(int panelHeight, int panelWidth, int gameSizeInt) {
        this.gameSizeInt = gameSizeInt;
        this.logic = new GameLogic(new Position(0,0), panelWidth, panelHeight, gameSizeInt);
        this.label = new JLabel();
        this.label2 = new JLabel();
        this.setLayout(new GridLayout(gameSizeInt, gameSizeInt));
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(new Color (27, 145, 82));
        setplayer(1);
        addMouseListener(this);
        addMouseMotionListener(this);
        setVisible(true);
    }

    public void setplayer(int player) {
        this.player = player;
        if (player == 1) {
            if(logic.getValidMoves().size() > 0) {
                logic.updateHighlight(1);
            }else {
                logic.updateHighlight(2);
                if(logic.getValidMoves().size() > 0) {
                    System.out.println("Player can't play");
                    setplayer(2);
                } else {
                    getWinner();
                }
            }
        } else if(player == 2){
            if(logic.getValidMoves().size() > 0) {
                logic.updateHighlight(2);
            }else {
                logic.updateHighlight(1);
                if (logic.getValidMoves().size() > 0) {
                    System.out.println("PC can't play");
                    setplayer(1);
                } else {
                    getWinner();
                }
            }
        }
    }

    public void getWinner() {
        if(logic.getValidMoves().size() == 0) {
            if (logic.getWhite() > logic.getBlack()) {
                str = "White WIN with " + logic.getWhite() + " stones! " + " press R to RESTART" ;
            } else if (logic.getWhite() < logic.getBlack()) {
                str = "Black WIN with " + logic.getBlack() + " stones! " + " press R to RESTART";
            } else {
                str = "DRAW ! press R to RESTART";
            }
        }
    }

    public void takeTurn(Position position) {
        if(!logic.isValidMove(position)) {
            return;
        }
        if(player == 1) {
            logic.play(position, 1);
            logic.countStones();
            setplayer(2);
        } else {
            logic.play(position, 2);
            logic.countStones();
            setplayer(1);
        }
        repaint();
    }

    public void setStr() {
        if (logic.getValidMoves().size() > 0) {
            this.str = "        White:  " + logic.getWhite() + " Black: " + logic.getBlack() + "      " + "Board size: " + gameSizeInt + "X" + gameSizeInt;
        } else {
            getWinner();
        }
    }

    public void setStr2() {
        String playerStr = "White";
        if (player == 1) {
            playerStr = "Black";
        }
        if (logic.getValidMoves().size() > 0) {
            this.str2 = "       Player turn: " + playerStr;
        } else {
            str2 = "";
        }
    }

    public JLabel getLabel() {
        return label;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public void paint(Graphics g) {
        super.paint(g);
        logic.paint(g);
        setStr();
        setStr2();
        label.setText(str);
        label2.setText(str2);
    }


    public void restart(int gameSizeInt) {
        this.gameSizeInt = gameSizeInt;
        setplayer(1);
        logic.restart();
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (player == 1) {
            Position mousePosition = logic.getMousePosition(new Position(e.getX(), e.getY()));
            takeTurn(mousePosition);
            logic.getField()[mousePosition.getX()][mousePosition.getY()].mouseEnteredNewColor(false);
        } while(player == 2) {
            if(logic.getValidMoves().size() > 0) {
                takeTurn(logic.getMaximalMove());
            } else {
                break;
            }

        }
    }

    // highlight highlighted rectangles when mouse moved on that position
    @Override
    public void mouseMoved(MouseEvent e) {
        Position mousePosition = logic.getMousePosition(new Position(e.getX(), e.getY()));
        ArrayList<Position> toLeave = new ArrayList<>();
        if(logic.inField(mousePosition) && logic.getField()[mousePosition.getX()][mousePosition.getY()].isHighlight()) {
            logic.getField()[mousePosition.getX()][mousePosition.getY()].mouseEnteredNewColor(true);
        } else {
            toLeave.addAll(logic.newColorPosition());
            for(int i = 0; i < toLeave.size(); i++) {
                logic.getField()[toLeave.get(i).getX()][toLeave.get(i).getY()].mouseEnteredNewColor(false);
            }
        }
        if (logic.coloredPositionsCounter() > 1) {
            toLeave.addAll(logic.newColorPosition());
            for(int i = 0; i < toLeave.size(); i++) {
                logic.getField()[toLeave.get(i).getX()][toLeave.get(i).getY()].mouseEnteredNewColor(false);
            }
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

}
