package be.umons.model.container;

import be.umons.model.Map;
import be.umons.model.Position;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Rémy Decocq (built from original implementation)
 *
 */
public class PositionContainer implements Container<Position> {

    private final ConcurrentHashMap<String, Position> positions;

    private final int width;
    private final int height;

    public PositionContainer(int width, int height){
        this.width = width;
        this.height = height;
        this.positions = new ConcurrentHashMap<>(width * height);
    }

    @Override
    public Position get(int i) {
        // No corresponding semantic, use getter by composed coordinates
        return null;
    }

    /**
     * Retrieve a position by it's x and y coordinates.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     *
     * @return The position object.
     */
    public Position get(int x, int y) {
        Position val = positions.get(generateKey(x, y));
        if (val == null) {
            throw new IllegalArgumentException("The point " + x + "|" + y + " doesn't exist.");
        }
        return val;
    }

    @Override
    public Vector<Position> getAll() {
        return new Vector<>(this.positions.values());
    }

    /**
     * Adds a position to the container. DO NOT USE THIS OUTSIDE {@link Map}.
     *
     * @param el The element to add.
     */
    @Override
    public void add(Position el) {
        this.positions.put(generateKey(el), el);
    }

    /**
     * Adds the elements of another container of the same type.
     *
     * @param container The other container.
     */
    @Override
    public void add(Container<Position> container) {
        for (Position p : container) {
            this.add(p);
        }
    }

    /**
     * Instantiate all possible {@link Position}s from {@link #width} and {@link #height}
     * and add them
     */
    public void fill(){
        for (int actX = 0; actX < width; actX++) {
            for (int actY = 0; actY < height; actY++) {
                this.add(new Position(actX, actY));
            }
        }
    }

    /**
     * Removes an element from the container.
     *
     * @param el The element to remove.
     */
    @Override
    public void remove(Position el) {
        this.positions.remove(generateKey(el.getX(), el.getY()));
    }

    @Override
    public void removeAll() {
        this.positions.clear();
    }

    @Override
    public boolean contains(Position p) {
        return this.positions.containsKey(this.generateKey(p));
    }

    @Override
    public int size() {
        return this.positions.size();
    }

    @Override
    public Iterator<Position> iterator() {
        return positions.values().iterator();
    }


    /**
     * Generate a key based on the supplied values.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     *
     * @return The position object.
     */
    private String generateKey(int x, int y) {
        return x + "#" + y;
    }

    private String generateKey(Position pos) {
        return generateKey(pos.getX(), pos.getY());
    }

    /**
     * Instantiate a new {@link PositionContainer} given two {@link Position}s, picking ones in the rectangle defined
     * by their coordinates.
     *
     * @param startPos the leftmost and lowest position to consider for the rectangle
     * @param endPos the rightmost and highest position to consider for the rectangle
     * @return A {@link PositionContainer} containing all Position objects in the rectangle
     */
    public PositionContainer getRange(Position startPos, Position endPos) {
        int startX = startPos.getX();
        int startY = startPos.getY();
        int endX = endPos.getX();
        int endY = endPos.getY();

        PositionContainer ret = new PositionContainer(width, height);

        for (int i = startX; i <= endX; i++) {
            for (int j = startY; j <= endY; j++) {
                ret.add(get(i, j));
            }
        }

        return ret;
    }

    public int getWidth(){return this.width;}

    public int getHeight(){return this.height;}

    public String toString(){
        return this.positions.toString();
    }
}
