/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class Pacman extends DynamicTarget {

    private String name;

    private Score score;

    private Sex sex;

    public Pacman(Position pos, Sex sex) {
        this.score = new Score();
        this.state = State.HUNTED;
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
    public void gotEaten() {
        this.changeState(State.MUNCHED);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Score getScore() {
        return score;
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
            this.changeState(prevState);
        } else {
            this.state = s;
        }
    }


    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Pacman) {
                return this.getScore().equals(((Pacman) o).getScore())
                        && this.getPosition().equals(((Pacman) o).getPosition())
                        && this.getState().equals(((Pacman) o).getState())
                        && this.getHeadingTo().equals(((Pacman) o).getHeadingTo())
                        && this.getName().equals(((Pacman) o).getName());
            }
        }
        return false;
    }

    public Sex getSex() {
        return sex;
    }

    public enum Sex {
        MALE, FEMALE
    }

    public String toString() {
        return "Pacman [" + position + ", " + state + ", " + sex + ", " + score + ", visible: " + visible + "]";
    }

}
