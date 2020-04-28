package be.umons.model.container;

import be.umons.model.exception.ListFullException;
import be.umons.model.exception.ObjectAlreadyInListException;
import be.umons.model.mapobject.MapObject;

import java.util.Vector;

/**
 * @author Rémy Decocq (built from original implementation)
 *
 */
public class LimitedObjectContainer<E extends MapObject> extends ObjectContainer<E> {

    public final int max;

    public LimitedObjectContainer(int max){
        if (max < 0)
            throw new IllegalArgumentException("A Container cannot have a negative size");
        this.max = max;
        this.elmts = new Vector<E>(max);
    }

    @Override
    public void add(E el) {
        if (!this.elmts.contains(el)) {
            if (this.elmts.size() < this.max) {
                this.elmts.add(el);
            } else {
                throw new ListFullException(elmts.getClass().getCanonicalName());
            }
        } else {
            throw new ObjectAlreadyInListException(el.getClass().getCanonicalName(), this.getClass().getCanonicalName());
        }
    }

    public int getMax(){
        return this.max;
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof LimitedObjectContainer) {
                LimitedObjectContainer<E> other = (LimitedObjectContainer<E>) o;
                boolean sameContent = this.getAll().equals(other.getAll());
                return sameContent && other.getMax() == this.getMax();
            }
        }
        return false;
    }

}
