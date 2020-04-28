package be.umons.model.container;

import be.umons.model.Position;
import be.umons.model.exception.ObjectAlreadyInListException;
import be.umons.model.mapobject.MapObject;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author Rémy Decocq (built from original implementation)
 *
 */
public class ObjectContainer<E extends MapObject> implements Container<E> {

    protected Vector<E> elmts;

    public ObjectContainer(){
        this.elmts = new Vector<E>();
    }

    @Override
    public E get(int i) {
        return this.elmts.get(i);
    }

    /**
     * Returns a {@link MapObject} element on the position among contained ones (first matching).
     *
     * @param pos The position.
     *
     * @return The {@link MapObject} on the {@link Position} pos or none if no one matches.
     */
    public E get(Position pos) {
        for (E mapObject : this.elmts) {
            if (mapObject.isOnPosition(pos)) {
                return mapObject;
            }
        }
        return null;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public Vector<E> getAll() {
        return (Vector<E>) this.elmts.clone();
    }

    /**
     * Returns {@link MapObject}s elements on the position among contained ones (all matching).
     *
     * @param pos The position.
     *
     * @return A
     */
    public Vector<E> getAll(Position pos) {
        Vector<E> onPosition = new Vector<>();
        for (E mapObject : this.elmts) {
            if (mapObject.isOnPosition(pos)) {
                onPosition.add(mapObject);
            }
        }
        return onPosition;
    }

    @Override
    public void add(E el) {
        if (!this.elmts.contains(el)) {
            this.elmts.add(el);
        } else {
            throw new ObjectAlreadyInListException(el.getClass().getCanonicalName(), this.getClass().getCanonicalName());
        }
    }

    @Override
    public void add(Container<E> container) {
        for (E elmt : container){
            this.add(elmt);
        }
    }

    @Override
    public void remove(E el) {
        this.elmts.remove(el);
    }

    @Override
    public void removeAll(){
        this.elmts.clear();
    }

    @Override
    public boolean contains(E o) {
        return this.elmts.contains(o);
    }

    @Override
    public int size() {
        return this.elmts.size();
    }

    @Override
    public Iterator<E> iterator() {
        return this.elmts.iterator();
    }

    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof ObjectContainer) {
                return this.getAll().equals(((ObjectContainer<E>) o).getAll());
            }
        }
        return false;
    }

    public String toString(){
        return "Container of size " + size() + " : " + this.elmts;
    }
}
