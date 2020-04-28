/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.mapobject;

import be.umons.model.*;
import be.umons.model.container.ObjectContainer;
import be.umons.model.exception.ObjectAlreadyInListException;

/**
 * Pacman is playable as male/female and both can be played in the same time in MULTIPLAYER mode
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @author Rémy Decocq (modification)
 */
public class Pacman extends DynamicTarget {

    private String name;

    private final Score score;

    private final Sex sex;

    public Pacman(Position pos, Sex sex) {
        this(pos, sex, 7);
    }

    public Pacman(Position pos, Sex sex, int speed) {
        this.score = new Score();
        this.state = State.HUNTED;
        this.setSpeed(speed);
        switch (sex) {
            case MALE:
                this.setName("Mr. Pacman");
                break;
            case FEMALE:
                if (Settings.getInstance().getGameMode() == Game.Mode.SINGLEPLAYER) {
                    throw new IllegalArgumentException("There can be no female Pacman in Singleplayer mode");
                }
                this.setName("Mrs. Pacman");
                break;
            default:
                throw new IllegalArgumentException("Something went wrong, there cannot be a sexless Pacman");
        }
        this.sex = sex;
        this.setPosition(pos);
    }

    @Override
    public void tryMoving(Position pos){
        Position prevPos = this.getPosition();
        if (canMove()){
            move(pos);
            makeSpecialAction(pos);
            if (prevPos != this.getPosition() && this.getBridgeState() != Bridge.BridgeState.NOT_ON_BRIDGE){
                this.changeBridgeState(Bridge.BridgeState.NOT_ON_BRIDGE);
            }
        }
    }

    private void makeSpecialAction(Position pos) {
        if (this.getState() == State.FIRE){
            this.createFireball(pos);
        }
    }

    private void createFireball(Position pos){
        ObjectContainer<DynamicTarget> dynamicTargets = Game.getInstance().getSpecialDynamicTargetContainer();
        try {
            dynamicTargets.add(new Fireball(pos, this.getHeadingTo()));
        } catch (ObjectAlreadyInListException e) {
            //doesn't create the fireball
        }
    }

    @Override
    public void gotEaten() {
        this.changeState(State.MUNCHED);
    }

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
        if (target instanceof Ghost) {
            Ghost g = (Ghost) target;
            g.gotEaten();
        } else if (target instanceof StaticTarget) {
            StaticTarget staticTarget = (StaticTarget) target;
            if (staticTarget.getState() != StaticTarget.State.EATEN) {
                target.gotEaten();
            }
        } else {
            throw new IllegalArgumentException("A pacman is no cannibal");
        }

        this.score.addToScore(((Scorable) target));
    }

    @Override
    public void changeState(State s) {
        if (s == State.MUNCHED) {
            State prevState = state;
            Game.getInstance().reducePLayerLifes();
            if (Game.getInstance().getPlayerLifes() <= 0) {
                Game.getInstance().gameOver();
            }
            Game.getInstance().onPacmanGotEaten();
            this.changeState(State.HUNTED);
        } else {
            this.state = s;
        }
    }


    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Pacman) {
                boolean sameScore = this.getScore().equals(((Pacman) o).getScore());
                boolean samePos = this.getPosition().equals(((Pacman) o).getPosition());
                boolean sameState = this.getState().equals(((Pacman) o).getState());
                boolean sameHeading = this.getHeadingTo().equals(((Pacman) o).getHeadingTo());
                boolean sameName = this.getName().equals(((Pacman) o).getName());
                return sameName && samePos && sameState && sameScore && sameHeading;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public Score getScore() {
        return score;
    }

    public String toString() {
        return "Pacman [" + position + ", " + state + ", " + sex + ", " + score + ", visible: " + visible + "]";
    }

    public enum Sex {
        MALE, FEMALE
    }

}
