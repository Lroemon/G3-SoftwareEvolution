package be.umons.model.mapobject;

import be.umons.model.Game;
import be.umons.model.Position;
import be.umons.model.Scorable;

/**
 * Eating this fruit has an instant effect of a bomb
 * all the ghosts that are within a maximum of 4 spaces of Pacman are killed instantly.
 */
public class Grenade extends StaticTarget implements Scorable {

    private final int range = 4;

    public Grenade(Position pos) {
        this.state = State.AVAILABLE;
        this.setPosition(pos);
    }

    @Override
    public void changeState(State s) {
        if (s == null) {
            throw new IllegalArgumentException("A null state is not allowed.");
        } else if (state == s) {
            throw new IllegalArgumentException("The new state must differ from the old one.");
        }

        if (s == State.EATEN) {
            setVisible(false);
        } else if (s == State.AVAILABLE) {
            setVisible(true);
        }

        this.state = s;
    }

    @Override
    public void gotEaten() {
        this.changeState(State.EATEN);
        this.explode();
    }


    /**
     * Explode the grenade and kill all ghost in a range of 4 blocks
     */
    private void explode() {
        for (Ghost g : Game.getInstance().getGhostContainer()) {
            if (g.getState() != DynamicTarget.State.WAITING) {
                Position gpos = g.position;
                if ( Math.abs(this.position.getX() - gpos.getX()) <= range &&  Math.abs(this.position.getY() - gpos.getY()) <= range)
                    g.changeState(DynamicTarget.State.MUNCHED);
            }
        }
    }

    public String toString() {
        return "Grenade [" + position + ", " + state + ", visible: " + visible + "]";
    }

    @Override
    public int getScore() {
        return 0;
    }
}
