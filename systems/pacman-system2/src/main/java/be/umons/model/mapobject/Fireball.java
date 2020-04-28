package be.umons.model.mapobject;

import be.umons.model.*;
import be.umons.model.container.ObjectContainer;

/**
 * Fireball is a very hot mass destruction weapon, if a ghost if touched by this hell's weapon he is exterminated
 * The fireball is so hot and powerfull that it can doesn't care of bridges, it kills ghost that are covered under the bridge or above
 * The fireball is so powerfull that it can even kill multiple ghost
 *
 * Be carefull ghosts, these satan's balls are here for you
 */
public class Fireball extends DynamicTarget{

    private final int SPEED = 10;

    public Fireball(Position pos, Map.Direction direction) {
        this.state = State.HUNTED;
        this.setHeadingTo(direction);
        this.setSpeed(SPEED);
        this.setPosition(pos);
    }

    /**
     * Move the object to the new position.
     *
     * @param pos The new position of this object.
     */
    @Override
    public void move(Position pos) {
        boolean wallOnPosition = false;
        boolean placeHolderOnPosition = false;
        for (MapObject m : pos.getOnPosition()) {
            if (m instanceof Wall) {
                wallOnPosition = true;
                break;
            } else if (m instanceof Placeholder) {
                placeHolderOnPosition = true;
            }
        }
        if (!wallOnPosition && !placeHolderOnPosition) {
            try {
                this.setPosition(pos);
            }catch (Exception ObjectAlreadyInListException){
                // If there is already a fireball on this positions destroy itself, only fireballs can kill fireballs
                ObjectContainer<DynamicTarget> dynamicTargets = Game.getInstance().getSpecialDynamicTargetContainer();
                dynamicTargets.remove(this);
            }
        }
    }

    @Override
    public void eat(Target target) {

    }

    @Override
    public void gotEaten() {

    }

    @Override
    public void changeState(State state) {
    }

    public String toString() {
        return "Fireball [" + position + "]";
    }
}
