package be.umons.model.mapobject;

/**
 * A {@link Target} is a {@link MapObject} that can do special action when someone is on it.
 *
 */
public abstract class Boxes extends MapObject {

    public abstract void action(Pacman pacman);
    public abstract void action(Ghost ghost);

}