/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

/**
 * Global Settings for the gameplay of the {@link Game}.
 *
 * @author Philipp Winter
 * @author Rémy Decocq (modification)
 */
public class Settings {

    private Game.Mode gameMode = Game.Mode.SINGLEPLAYER;

    private int initPlayerLifes = 3;

    private static Settings instance = new Settings();

    public Settings() {

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

    public int getInitPlayerLifes(){return initPlayerLifes;}

    public void setInitPlayerLifes(int initPlayerLifes){this.initPlayerLifes = initPlayerLifes;}

}
