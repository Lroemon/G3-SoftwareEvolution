/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.mapobject;

import be.umons.model.Position;
import be.umons.model.Scorable;

/**
 * Ghosts are the little beasts {@link Pacman} can hunt after eating a {@link Coin}.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */
public class Ghost extends DynamicTarget implements Scorable {

    private final static int SCORE_VALUE = 200;

    /**
     * The current colour of this ghost.
     */
    private Colour colour;

    /**
     * The name of the ghost.
     */
    private String name;

    private double waitingSeconds = -1.;

    public final static double WAIT_SECONDS = 5.;

    private boolean movedInLastTurn = false;

    public Ghost(Position pos, Colour colour) {
        this(pos, colour, 6);
    }

    public Ghost(Position pos, Colour colour, int speed) {
        this.setSpeed(speed);
        switch (colour) {
            case RED:
                this.setName("Blinky");
                break;
            case ORANGE:
                this.setName("Clyde");
                break;
            case BLUE:
                this.setName("Inky");
                break;
            case PINK:
                this.setName("Pinky");
                break;
            default:
                throw new IllegalArgumentException("You cannot construct a ghost with the colour " + colour);
        }
        this.colour = colour;
        this.state = State.HUNTER;
        this.setPosition(pos);
    }

    /**
     * Gets the current colour of the ghost.
     *
     * @return The colour.
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Sets the colour of the ghost.
     *
     * @param colour The new colour.
     */
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    /**
     * Gets the name of the ghost.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets a new name of the ghost.
     *
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Let the object eat a subclass of Target.
     *
     * @param target The object to be eaten.
     */
    @Override
    public void eat(Target target) {
        if (target instanceof Pacman) {
            target.gotEaten();
        }
    }

    /**
     * Changes the state of the ghost and performs needed operations related to new state.
     *
     * @param state The new state.
     */
    public void changeState(State state) {
        if (state == State.WAITING) {
            if(this.waitingSeconds == -1.){
                this.waitingSeconds = WAIT_SECONDS;
            } else {
                this.waitingSeconds += WAIT_SECONDS;
            }
        } else if (state == State.HUNTER) {
            this.changeSpeed(this.getInitialSpeed());
            this.waitingSeconds = -1.;
        } else if (state == State.MUNCHED){
            // Has been eaten, increase the number of ghosts caught in a row by pacman for the current Coin time
            Coin.nbr_ghosts_eaten_in_a_row++;
        } else if (state == State.HUNTED_STOP || state == State.HUNTER_STOP) {
            this.changeSpeed(0);
        }else if (state == State.HUNTED) {
            this.changeSpeed(1);
        }
        this.state = state;
    }

    /**
     * @return The number of remaining seconds before ghost stops waiting
     * or -1. if he's in Hunter {@link DynamicTarget.State#HUNTER} State
     */
    public double getWaitingSeconds() {
        return waitingSeconds;
    }

    public boolean getMovedInLastTurn() {
        return movedInLastTurn;
    }

    public void setMovedInLastTurn(boolean b) {
        this.movedInLastTurn = b;
    }

    @Override
    public int getScore() {
        int score;
        switch (Coin.nbr_ghosts_eaten_in_a_row){
            case 2: score = 400;
                break;
            case 3: score = 800;
                break;
            case 4: score = 1600;
                break;
            default:
                score = SCORE_VALUE;
        }
        return score;
    }

    @Override
    public void gotEaten() {
        this.changeState(State.MUNCHED);
    }

    /**
     * Reduce the still remaining time the ghost has to wait until be freed
     * @param amount The number of seconds to reduce the counter
     */
    public void reduceWaitingSeconds(double amount) {
        if (this.state == State.WAITING) {
            if (amount <= 0) {
                throw new IllegalArgumentException("The amount has to be positive");
            } else {
                this.waitingSeconds -= amount;
                if (this.waitingSeconds < 0) {
                    this.waitingSeconds = 0; // will be freed
                }
            }
        }
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Ghost) {
                Ghost g = (Ghost) o;
                boolean sameWait = this.getWaitingSeconds() == g.getWaitingSeconds();
                boolean samePos = this.getPosition().equals(g.getPosition());
                boolean sameState = this.getState().equals(g.getState());
                boolean sameMoved = this.getMovedInLastTurn() == (g.getMovedInLastTurn());
                boolean sameHeading = this.getHeadingTo().equals(g.getHeadingTo());
                boolean sameColor = this.getColour().equals(g.getColour());
                boolean sameName = this.getName().equals(g.getName());
                return sameName && sameColor && samePos && sameState && sameMoved && sameWait && sameHeading;
            }
        }
        return false;
    }

    public enum Colour {

        RED, PINK, BLUE, ORANGE

    }

    public String toString() {
        return "Ghost [" + position + ", " + state + ", " + colour + ", visible: " + visible + "]";
    }
}
