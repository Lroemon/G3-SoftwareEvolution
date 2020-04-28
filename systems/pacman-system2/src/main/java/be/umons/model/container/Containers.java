package be.umons.model.container;

import be.umons.model.mapobject.*;

/**
 * Class serving as an instantiation point for Containers specific to the game
 *
 * @author Rémy Decocq
 */
public class Containers {

    public static final int NBR_MAX_COIN = 4;
    public static final int NBR_MAX_PACMAN = 2;
    public static final int NBR_MAX_GHOST = 4;

    public static PositionContainer getPositionContainer(int width, int height){
        return new PositionContainer(width, height);
    }

    public static ObjectContainer<MapObject> getMapObjectsContainer(){
        return new ObjectContainer<MapObject>();
    }

    public static PointContainer getPointContainer(){
        return new PointContainer();
    }

    public static LimitedObjectContainer<Coin> getCoinContainer() {
        return new LimitedObjectContainer<Coin>(NBR_MAX_COIN);
    }

    public static ObjectContainer<MapObject> getSpecialObjectsContainer() {
        return new ObjectContainer<MapObject>();
    }

    public static ObjectContainer<DynamicTarget> getSpecialDynamicTargetContainer() {
        return new ObjectContainer<DynamicTarget>();
    }

    public static LimitedObjectContainer<Pacman> getPacmanContainer(){
        return new LimitedObjectContainer<Pacman>(NBR_MAX_PACMAN);
    }

    public static LimitedObjectContainer<Ghost> getGhostContainer(){
        return new LimitedObjectContainer<Ghost>(NBR_MAX_GHOST);
    }

}
