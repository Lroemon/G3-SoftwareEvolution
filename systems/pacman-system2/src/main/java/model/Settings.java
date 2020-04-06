/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

/**
 * Settings
 *
 * @author Philipp Winter
 */
public class Settings {

    private Game.Mode gameMode = Game.Mode.SINGLEPLAYER;

    private static Settings instance = new Settings();

    public Settings() {
        // TODO Implement class
    }

    public static Settings getInstance() {
        return instance;
    }

    public static void setInstance(Settings instance) {
        Settings.instance = instance;
    }

    public Game.Mode getGameMode() {
        return gameMode;
    }

    public void setGameMode(Game.Mode gameMode) {
        this.gameMode = gameMode;
    }
}
