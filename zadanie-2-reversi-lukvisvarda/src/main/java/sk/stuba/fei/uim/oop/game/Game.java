package sk.stuba.fei.uim.oop.game;

import sk.stuba.fei.uim.oop.gui.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, ActionListener {
    private Panel panel;
    private int width;
    private int height;
    private int gameSizeInt;
    private JPanel borderMenu;
    private final JButton restartButton;
    private final JFrame frame;
    private final JMenuItem size6;
    private final JMenuItem size8;
    private final JMenuItem size10;
    private final JMenuItem size12;

    public Game() {
        this.width = 600;
        this.height = 600;
        this.gameSizeInt = 6;
        frame = new JFrame("Reversi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(450, 150);
        frame.setLayout(new BorderLayout());

        panel = new Panel(height, width, gameSizeInt);
        borderMenu = new JPanel();
        borderMenu.setSize(width, 200);
        restartButton = new JButton("Restart");
        restartButton.addActionListener(this);

        JMenuBar menu = new JMenuBar();
        JMenu gameSize = new JMenu("GameSize");
        size6 = new JMenuItem("6X6");
        size8 = new JMenuItem("8X8");
        size10 = new JMenuItem("10X10");
        size12 = new JMenuItem("12X12");

        menu.add(gameSize);
        gameSize.add(size6);
        gameSize.add(size8);
        gameSize.add(size10);
        gameSize.add(size12);

        size6.addActionListener(this);
        size8.addActionListener(this);
        size10.addActionListener(this);
        size12.addActionListener(this);

        borderMenu.add(restartButton);
        borderMenu.add(panel.getLabel());
        borderMenu.add(panel.getLabel2());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(borderMenu, BorderLayout.PAGE_END);
        frame.setJMenuBar(menu);
        frame.pack();
        frame.setFocusable(true);
        restartButton.setFocusable(false);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }



    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if(e.getKeyCode() == KeyEvent.VK_R) {
            panel.restart(gameSizeInt);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restartButton) {
            panel.restart(gameSizeInt);
        } else if (e.getSource() == size6) {
            frame.remove(panel);
            frame.remove(borderMenu);
            gameSizeInt = 6;
            init();
        } else if (e.getSource() == size8) {
            frame.remove(panel);
            frame.remove(borderMenu);
            gameSizeInt = 8;
            init();
        } else if (e.getSource() == size10) {
            frame.remove(panel);
            frame.remove(borderMenu);
            gameSizeInt = 10;
            init();
        } else if (e.getSource() == size12) {
            frame.remove(panel);
            frame.remove(borderMenu);
            gameSizeInt = 12;
            init();
        }
    }

    private void init() {
        width = 600;
        height = 600;
        panel = new Panel(height, width, gameSizeInt);
        borderMenu = new JPanel();
        borderMenu.add(restartButton);
        borderMenu.add(panel.getLabel());
        borderMenu.add(panel.getLabel2());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(borderMenu, BorderLayout.PAGE_END);
        frame.pack();
    }
}


