/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model;

import be.umons.controller.MainController;
import be.umons.model.container.*;
import be.umons.model.event.RendererProcess;
import be.umons.model.event.Scheduler;
import be.umons.model.event.TimerProcess;
import be.umons.model.event.WorkerProcess;
import be.umons.model.mapobject.*;

import java.util.ArrayList;

/**
 * The Game class is kind of a <i>master</i>-class, organizing all other business logic objects.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */
public class Game {

    static {
        Game.reset();
    }

    /**
     * The singleton instance.
     */
    private static Game instance;

    /**
     * Whether the game was initialized already.
     */
    private static boolean initialized;

    /**
     * A timer used in game
     */
    private Timer timer;

    /**
     * A container of all ghosts.
     */
    private LimitedObjectContainer<Ghost> ghostContainer;

    /**
     * A container of all coins.
     */
    private LimitedObjectContainer<Coin> coinContainer;
    private ObjectContainer<MapObject> specialObjectContainer;
    private ObjectContainer<DynamicTarget> specialDynamicTargetContainer;

    /**
     * A container of all points.
     */
    private PointContainer pointContainer;

    /**
     * A container of all pacmans.
     */
    private LimitedObjectContainer<Pacman> pacmanContainer;

    /**
     * The event handler reacts on events happening in the game.
     */
    private Scheduler eventHandlerManager;

    /**
     * The map is like a two dimensional array of positions, containing all map objects
     */
    private Map map;

    public static final double BASIC_REFRESH_RATE = 4.;

    /**
     * The amount of time our UI will be repainted.
     * Also how often the user is able to interact with it's character, e.g. by pressing a key.
     */
    private double refreshRate = BASIC_REFRESH_RATE;

    private static final double REFRESH_RATE_POW = 5. / 7.;

    /**
     * The level of the game.
     */
    private Level level;

    private boolean isOver = false;

    private int playerLifes;

    /**
     * Constructs a new Game object.
     */
    private Game() {

    }

    /**
     * Returns the singleton instance.
     *
     * @return The game singleton.
     */
    public static Game getInstance() {
        return Game.instance;
    }

    /**
     * Reset the game, for instance necessary when the user wants to start a new try.
     */
    public synchronized static void reset() {
        Game.initialized = true;
        Game.instance = new Game();
        // Initialization work must be done in a new method in order to retrieve the game object during the following work
        Game.instance.initializeInternal();
    }

    /**
     * The internal initialization method.
     */
    private synchronized void initializeInternal() {
        Timer.reset();
        Map.reset();
        Coin.resetCoinsState();
        Level.reset();

        this.playerLifes = Settings.getInstance().getInitPlayerLifes();

        this.timer = Timer.getInstance();

        this.map = Map.getInstance();

        this.ghostContainer = Containers.getGhostContainer();
        this.coinContainer = Containers.getCoinContainer();
        this.specialObjectContainer = Containers.getSpecialObjectsContainer();
        this.specialDynamicTargetContainer = Containers.getSpecialDynamicTargetContainer();
        this.pointContainer = Containers.getPointContainer();
        this.pacmanContainer = Containers.getPacmanContainer();
        this.level = Level.getInstance();

        this.eventHandlerManager = new Scheduler();
        this.eventHandlerManager.register(new WorkerProcess());
        this.eventHandlerManager.register(new RendererProcess());
        this.eventHandlerManager.register(new TimerProcess());

    }

    /**
     * Is the Game already initialized?
     */
    public static boolean isInitialized() {
        return Game.initialized;
    }

    /**
     * Decrease remaining player's life counter by one
     */
    public void reducePLayerLifes() {
        this.playerLifes -= 1;
    }

    /**
     * Increase remaining player's life counter by one
     */
    public void increasePlayerLifes() {
        this.playerLifes++;
    }

    /**
     * Changes the refresh rate depending on the level.
     * Can be expressed by the equation <code>RefreshRate(level) = (level^5)^(1/7)</code>.
     *
     * @param l The level which is used as a parameter in the mathematical equation to generate a new refresh rate.
     */
    public void changeRefreshRate(Level l) {
        // f(x) = (x^5)^(1/7) or "The refresh rate per second is the 7th root of the level raised to 5"
        this.refreshRate = Math.pow(l.getLevel(), REFRESH_RATE_POW) + BASIC_REFRESH_RATE;
    }

    /**
     * Starts the game, in detail it causes all {@link model.event.WorkerProcess}'s to start working.
     *
     * @see Scheduler#startExecution()
     */
    public void start() {
        if(pointContainer.size() == 0){
            this.map.placeObjects();
        }
        this.playerLifes = Settings.getInstance().getInitPlayerLifes();
        this.timer.resume_increment();
        this.eventHandlerManager.startExecution();
    }

    /**
     * Pauses the game, by stopping/pausing all {@link model.event.WorkerProcess}'s.
     *
     * @see Scheduler#pauseExecution()
     */
    public void pause() {
        this.timer.pause_increment();
        this.eventHandlerManager.pauseExecution();
    }

    /**
     * Make the current game over
     */
    public void gameOver() {
        this.isOver = true;
        Game.getInstance().getEventHandlerManager().pauseExecution();
        MainController.getInstance().getGui().onGameOver();
        MainController.getInstance().getGui().getRenderer().markReady();
    }

    public void onPacmanGotEaten() {
        Map.getInstance().onPacmanGotEaten();
    }

    public int getPlayerLifes() {
        return playerLifes;
    }

    public boolean isGameOver() {
        return this.isOver;
    }

    public Timer getTimer(){
        return timer;
    }

    /**
     * Gets the current difficulty Level
     *
     * @return The {@link Level} instance parametrizing game difficulty
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Gets the ghost container.
     *
     * @return The container used to manage all instance of {@link Ghost}'s in the object tree.
     */
    public LimitedObjectContainer<Ghost> getGhostContainer() {
        return ghostContainer;
    }

    /**
     * Gets the coin container.
     *
     * @return The container used to manage all instance of {@link Coin}'s in the object tree.
     */
    public LimitedObjectContainer<Coin> getCoinContainer() {
        return coinContainer;
    }

    /**
     * Gets the special objects container.
     *
     * @return The container used to manage all instance of {@link Grenade}'s in the object tree.
     */
    public ObjectContainer<MapObject> getSpecialObjectsContainer() {
        return specialObjectContainer;
    }

    /**
     * Gets the special dynamic target container.
     *
     * @return The container used to manage all instance of {@link DynamicTarget}'s in the object tree.
     */
    public ObjectContainer<DynamicTarget> getSpecialDynamicTargetContainer() {
        return specialDynamicTargetContainer;
    }

    /**
     * Gets the point container.
     *
     * @return The container used to manage all instance of {@link Point}'s in the object tree.
     */
    public PointContainer getPointContainer() {
        return pointContainer;
    }

    /**
     * Gets the pacman container.
     *
     * @return The container used to manage all instance of {@link Pacman}'s in the object tree.
     */
    public LimitedObjectContainer<Pacman> getPacmanContainer() {
        return pacmanContainer;
    }

    /**
     * Gets the map of the game.
     *
     * @return The map.
     */
    public Map getMap() {
        return map;
    }

    /**
     * Gets the refresh rate.
     *
     * @return The refresh rate.
     */
    public double getRefreshRate() {
        return this.refreshRate;
    }

    public Scheduler getEventHandlerManager() {
        return eventHandlerManager;
    }

    public ArrayList<ObjectContainer<? extends MapObject>> getAllMapobjecContainers(){
        ArrayList<ObjectContainer<? extends MapObject>> all = new ArrayList<ObjectContainer<? extends MapObject>>();
        all.add(pacmanContainer);
        all.add(ghostContainer);
        all.add(pointContainer);
        all.add(coinContainer);
        return all;
    }

    /**
     * Compares two objects for equality.
     *
     * @param o The other object.
     *
     * @return Whether both objects are equal.
     */
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Game) {
                // As it is a singleton, checking for reference equality is enough
                return this == o;
            }
        }
        return false;
    }

    public String toString(){
        String s = "Object GAME (lifes :" + this.playerLifes + " RefrRate :" + this.refreshRate + ")\n";
        s += "\n + current level " + this.level;
        s += "\n +- Pacmans : " + this.pacmanContainer;
        s += "\n +- Ghosts : " + this.ghostContainer;
        s += "\n +- Coins : " + this.coinContainer;
        s += "\n +- Points : " + this.pointContainer;
        s += "\n + current map\n" + this.map.toString(true);
        return s;
    }

    public enum Mode {
        SINGLEPLAYER, MULTIPLAYER
    }
}
