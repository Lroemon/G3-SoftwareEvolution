package be.umons.model.mapobject;

import be.umons.model.Position;

/**
 * A teleporter makes the {@link Target} teleports to another teleporter if it's linked to one.
 *
 */
public class Teleporter extends Boxes {

    private Teleporter linkedTeleporter = null;
    public boolean isPrincipal = false;

    public Teleporter(Position position) {
        this.setPosition(position);
    }

    public Teleporter(Position position, Teleporter linked) {
        this.setPosition(position);
        linkedTeleporter = linked;
        isPrincipal = true;
    }

    public String toString() {
        return "Teleporter [" + position + "]";
    }

    @Override
    public void action(final Pacman pacman) {
        if (isPrincipal){
            pacman.move(linkedTeleporter.position);
        }
    }

    @Override
    public void action(Ghost ghost) {
        //nothing
    }

}
