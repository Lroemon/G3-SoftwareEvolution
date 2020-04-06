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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyboardProcesser
 *
 * @author Philipp Winter
 */
public class KeyboardProcesser implements KeyListener {

    /**
     * Invoked when a key has been typed.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key typed event.
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // We don't want to do anything here..
    }

    /**
     * Invoked when a key has been pressed.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key pressed event.
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (MainController.getInstance().isGameActive()) {
            PacmanContainer pacmanContainer = Game.getInstance().getPacmanContainer();
            Pacman mrPacman = null;
            Pacman mrsPacman = null;
            for (Pacman p : pacmanContainer) {
                if (p.getSex() == Pacman.Sex.MALE) {
                    mrPacman = p;
                } else {
                    mrsPacman = p;
                }
            }
            if (mrPacman == null) {
                throw new RuntimeException("Couldn't find Mr. Pacman");
            }
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    mrPacman.setHeadingTo(Map.Direction.NORTH);
                    break;
                case KeyEvent.VK_LEFT:
                    mrPacman.setHeadingTo(Map.Direction.WEST);
                    break;
                case KeyEvent.VK_DOWN:
                    mrPacman.setHeadingTo(Map.Direction.SOUTH);
                    break;
                case KeyEvent.VK_RIGHT:
                    mrPacman.setHeadingTo(Map.Direction.EAST);
                    break;
            }

            if (Settings.getInstance().getGameMode() == Game.Mode.MULTIPLAYER) {
                if (mrsPacman == null) {
                    throw new RuntimeException("Couldn't find Mrs. Pacman");
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        mrsPacman.setHeadingTo(Map.Direction.NORTH);
                        break;
                    case KeyEvent.VK_A:
                        mrsPacman.setHeadingTo(Map.Direction.WEST);
                        break;
                    case KeyEvent.VK_S:
                        mrsPacman.setHeadingTo(Map.Direction.SOUTH);
                        break;
                    case KeyEvent.VK_D:
                        mrsPacman.setHeadingTo(Map.Direction.EAST);
                        break;
                }
            }
        }
    }

    /**
     * Invoked when a key has been released.
     * See the class description for {@link java.awt.event.KeyEvent} for a definition of
     * a key released event.
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // We don't want to do anything here..
    }
}
