/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.controller;

import be.umons.model.Game;
import be.umons.model.exception.BasicUncaughtExceptionHandler;
import be.umons.view.MainGui;

import javax.swing.*;

/**
 * The main controller that controls the Game and the main view.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class MainController extends Thread {

    static {
        MainController.reset(false);
    }

    public final static BasicUncaughtExceptionHandler uncaughtExceptionHandler = new BasicUncaughtExceptionHandler();

    private static MainController instance;

    /**
     * The game instance.
     */
    private Game game;

    /**
     * The main window.
     */
    private MainGui gui;

    /**
     * State of the game
     */
    private boolean gameActive = false;

    /**
     * The main method, the entry point of our user to the game.
     *
     * @param args The command line arguments given to the program.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainController.instance.prepare();
            }

        });
    }

    public static void reset() {
        reset(true);
    }

    /**
     * Assign a new Controller instance
     * @param prepare Reset the game and create new game instance and GUI
     */
    public static void reset(boolean prepare) {
        if (MainController.instance != null)
            MainController.instance.pauseGame(); // shutdown event handler manager
        MainController.instance = new MainController();
        if (prepare) {
            MainController.instance.prepare();
        }
    }

    public MainGui getGui() {
        return gui;
    }

    public static MainController getInstance() {
        return instance;
    }

    private MainController() {

    }

    private void prepare() {
        Game.reset();
        game = Game.getInstance();
        gui = new MainGui();
    }

    /**
     * Start the game.
     */
    public void startGame() {
        this.gameActive = true;
        this.gui.showGame();
        this.game.start();
    }

    /**
     * Stop the game until next start.
     */
    public void pauseGame() {
        this.gameActive = false;
        if (this.gui != null)
            this.gui.showPreGame();
        if (this.game != null)
            this.game.pause();
    }

    public boolean isGameActive() {
        return gameActive;
    }

}
