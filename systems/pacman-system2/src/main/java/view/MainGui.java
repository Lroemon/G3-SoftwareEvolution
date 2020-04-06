/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package view;

import controller.MainController;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

public class MainGui extends JFrame {

    private static final long serialVersionUID = 6812319670817799344L;

    private final MainController controller;

    private final ImageOrganizer imgOrganizer;

    private final Renderer renderer;

    private JPanel pnlPreGame;

    private GamePanel pnlGame;

    private JPanel pnlButtons;

    private JLabel lblBackground;

    private JButton btnPlaySingleplayer;

    private JButton btnPlayMultiplayer;

    private JButton btnPause;

    private ActionListener toggleGameStateListener;

    private boolean initialized;

    public MainGui() {
        controller = MainController.getInstance();
        imgOrganizer = ImageOrganizer.getInstance();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        renderer = new Renderer(this);

        this.initialize();
    }

    private synchronized void initialize() {
        if (!this.initialized) {
            setTitle("Pacman");

            try {
                setIconImage(ImageIO.read(this.getClass().getResource("/graphics/resized/pacman/4_east.png")));
            } catch (IOException e) {
                MainController.uncaughtExceptionHandler.uncaught(e);
            }

            int windowWidth = renderer.mapWidth + (renderer.leftSpace * 2);
            int windowHeight = renderer.mapHeight + (renderer.topSpace * 3);

            System.out.println(windowWidth + "x" + windowHeight);

            setSize(windowWidth, windowHeight);
            setLocationRelativeTo(null);
            setResizable(false);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            getContentPane().setLayout(new BorderLayout());

            initializeComponents();
            initializeListeners();

            setVisible(true);

            this.initialized = true;
        }
    }

    private void initializeComponents() {
        pnlPreGame = new JPanel();
        pnlPreGame.setLayout(new BorderLayout());

        pnlGame = new GamePanel();
        pnlGame.setLayout(new FlowLayout());

        lblBackground = new JLabel(new ImageIcon(this.getClass().getResource("/graphics/background/main_background_middle.jpg")));
        lblBackground.setLayout(new FlowLayout());

        pnlButtons = new JPanel();
        pnlButtons.setLayout(new FlowLayout());
        pnlButtons.setOpaque(false);

        Font fontBtn = new Font("Agency FB", Font.PLAIN, 22);

        btnPlaySingleplayer = new JButton("Singleplayer");
        btnPlaySingleplayer.setFont(fontBtn);

        btnPlayMultiplayer = new JButton("Multiplayer");
        btnPlayMultiplayer.setFont(fontBtn);

        btnPause = new JButton("Pause");
        btnPause.setFont(fontBtn);

        pnlGame.add(btnPause);

        pnlButtons.add(btnPlaySingleplayer);
        Dimension dim = new Dimension(200, 0);
        pnlButtons.add(new Box.Filler(dim, dim, dim));
        pnlButtons.add(btnPlayMultiplayer);

        lblBackground.add(pnlButtons);

        pnlPreGame.add(lblBackground);

        getContentPane().add(pnlPreGame);
    }

    private void initializeListeners() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Highscore.getInstance().writeToFile();
                super.windowClosing(e);
            }
        });

        toggleGameStateListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.isGameActive()) {
                    controller.pauseGame();
                } else {
                    if(e.getSource() == btnPlaySingleplayer){
                        Settings.getInstance().setGameMode(Game.Mode.SINGLEPLAYER);
                    } else if(e.getSource() == btnPlayMultiplayer){
                        Settings.getInstance().setGameMode(Game.Mode.MULTIPLAYER);
                    }
                    controller.startGame();
                }
                repaint();
            }

        };

        btnPause.addActionListener(toggleGameStateListener);

        btnPlaySingleplayer.addActionListener(toggleGameStateListener);
        btnPlayMultiplayer.addActionListener(toggleGameStateListener);

        KeyListener keyEventListener = new KeyboardProcesser();
        this.addKeyListener(keyEventListener);
        pnlGame.addKeyListener(keyEventListener);
    }

    public void showPreGame() {
        btnPause.setText("Play");
        setTitle("Pacman: Game paused");
    }

    public void showGame() {
        btnPlaySingleplayer.setText("Pause");
        btnPause.setText("Pause");
        getContentPane().removeAll();
        getContentPane().add(pnlGame);
        setTitle("Pacman: Game running");

        Map.getInstance().markAllForRendering();
        renderer.markReady();
        //repaint();
    }

    public JPanel getPnlGame() {
        return pnlGame;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void onGameOver() {
        btnPause.setText("GAME OVER");
        btnPause.removeActionListener(toggleGameStateListener);
        btnPause.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(pnlPreGame);
                btnPause.removeActionListener(this);
                btnPause.addActionListener(toggleGameStateListener);
                btnPlaySingleplayer.setText("Singleplayer");
                dispose();
                MainController.reset();
                repaint();
            }
        });
    }

    private class GamePanel extends JPanel {

        private static final long serialVersionUID = -6138420780196252730L;

        @Override
        public void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            JPanel pnl = this;
            Graphics2D g = (Graphics2D) graphics;

            g.clearRect(
                    0,
                    0,
                    pnl.getWidth(),
                    pnl.getHeight());

            for (Position pos : Map.positionsToRender) {
                final int paintX = pos.getX() * renderer.multiplier + renderer.leftSpace;
                final int paintY = pos.getY() * renderer.multiplier + renderer.topSpace;

                g.clearRect(
                        paintX,
                        paintY,
                        renderer.multiplier,
                        renderer.multiplier
                );

                Vector<MapObject> mapObjects = pos.getOnPosition().getAll();
                for (MapObject mO : mapObjects) {
                    if (mO.isVisible()) {
                        BufferedImage img = imgOrganizer.get(mO);
                        g.drawImage(
                                img,
                                null,
                                paintX,
                                paintY
                        );
                    }
                }
            }

            int i = 0;

            for (Pacman p : Game.getInstance().getPacmanContainer()) {
                renderer.drawString(g, "Highscore of " + p.getName() + ":\t" + p.getScore().getScore(), ++i);
            }

            renderer.drawString(g, "Player Lifes: " + Game.getInstance().getPlayerLifes(), ++i);
            renderer.drawString(g, "Level: " + Game.getInstance().getLevel().getLevel(), ++i);

            int j = 0;
            for (Score s : Highscore.getInstance().getScores()) {
                renderer.drawString(g, "Score #" + (++j) + " " + s.toString(), ++i);
            }

            requestFocusInWindow();
        }
    }
}
