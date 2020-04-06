/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

/**
 * Ghosts are the little beasts {@link Pacman} can hunt after eating a {@link Coin}.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class Ghost extends DynamicTarget implements Scorable {

    /**
     * The current colour of this ghost.
     */
    private Colour colour;

    /**
     * The name of the ghost.
     */
    private String name;

    private double waitingSeconds = -1;

    private double speed = 1;

    private boolean movedInLastTurn = false;

    public Ghost(Position pos, Colour colour) {
        this.colour = colour;
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
        this.setName(name);
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
    public void eat(Target target) {
        if (target instanceof Pacman) {
            ((Pacman) target).changeState(State.MUNCHED);
        }
    }

    /**
     * Changes the state of the ghost and performs needed operations.
     *
     * @param state The new state.
     */
    public void changeState(State state) {
        if (state == State.WAITING) {
            this.speed *= 0.5;
            if(this.waitingSeconds == 0){
                this.waitingSeconds = 4.;
            } else {
                this.waitingSeconds += 4.;
            }
        } else if (state == State.HUNTER) {
            this.speed *= 2;
            this.waitingSeconds = -1.;
        }
        this.state = state;
    }

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
        return 200;
    }

    @Override
    public void gotEaten() {
        this.changeState(State.MUNCHED);
    }

    public void reduceWaitingSeconds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("The amount has to be positive");
        } else {
            this.waitingSeconds -= amount;
            if(this.waitingSeconds < 0) {
                this.waitingSeconds = 0;
            }
        }
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Ghost) {
                Ghost g = (Ghost) o;
                return this.getWaitingSeconds() == g.getWaitingSeconds()
                        && this.getPosition().equals(g.getPosition())
                        && this.getState().equals(g.getState())
                        && this.getMovedInLastTurn() == (g.getMovedInLastTurn())
                        && this.getColour().equals(g.getColour())
                        && this.getName().equals(g.getName())
                        && this.getHeadingTo().equals(g.getHeadingTo());
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
