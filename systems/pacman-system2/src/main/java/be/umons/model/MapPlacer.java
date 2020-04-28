package be.umons.model;

import be.umons.model.container.*;
import be.umons.model.mapobject.*;

/**
 * @author Rémy Decocq
 *
 */
public class MapPlacer {

    /**
     * Place new instances of actors in the game (Pacmans and Ghosts) in the current {@link Map}, at the predefined
     * {@link StartingPositions}.
     */
    public static void placeAllDynamicObjects() {
        Game g = Game.getInstance();
        Map m = Map.getInstance();

        // --------- PACMANS ---------
        LimitedObjectContainer<Pacman> pacC = g.getPacmanContainer();

        pacC.add(new Pacman(m.getActualPosition(StartingPositions.PACMAN_MALE), Pacman.Sex.MALE));

        if (Settings.getInstance().getGameMode() == Game.Mode.MULTIPLAYER) {
            pacC.add(new Pacman(m.getActualPosition(StartingPositions.PACMAN_FEMALE), Pacman.Sex.FEMALE));
        }

        // --------- GHOSTS ---------
        LimitedObjectContainer<Ghost> gC = g.getGhostContainer();
        gC.add(new Ghost(m.getActualPosition(StartingPositions.GHOST_BLUE), Ghost.Colour.BLUE, 2));
        gC.add(new Ghost(m.getActualPosition(StartingPositions.GHOST_ORANGE), Ghost.Colour.ORANGE, 2));
        gC.add(new Ghost(m.getActualPosition(StartingPositions.GHOST_PINK), Ghost.Colour.PINK, 2));
        gC.add(new Ghost(m.getActualPosition(StartingPositions.GHOST_RED), Ghost.Colour.RED, 2));
    }

    /**
     * From an arbitrary Position considered for holding (x,y) couple, retrieve the corresponding indexed one in
     * a {@link PositionContainer}
     *
     * @param positionContainer The container holding {@link Position}s instances to look in
     * @param sPos An arbitrary position whose coordinates are looked up for corresponding entry
     * @return positionContainer.get(sPos.getX(), sPos.getY())
     */
    public static Position getActualPosition(PositionContainer positionContainer, Position sPos){
        return positionContainer.get(sPos.getX(), sPos.getY());
    }

    /**
     * Statically predefined starting positions for all actors of the game, stocked as {@link Position}s standing
     * as (x, y) couples
     *
     */
    public static class StartingPositions {

        public static final Position GHOST_RED = new Position(11, 3);
        public static final Position GHOST_PINK = new Position(10, 3);
        public static final Position GHOST_BLUE = new Position(8, 3);
        public static final Position GHOST_ORANGE = new Position(9, 3);

        public static final Position PACMAN_MALE = new Position(13, 8);
        public static final Position PACMAN_FEMALE = new Position(6, 8);

        public static Position getCoordStartingPosition(DynamicTarget t){
            if(t instanceof Ghost){
                switch(((Ghost)t).getColour()) {
                    case RED: return GHOST_RED;
                    case PINK: return GHOST_PINK;
                    case BLUE: return GHOST_BLUE;
                    case ORANGE: return GHOST_ORANGE;
                    default:
                        throw new RuntimeException("Unknown ghost color " + t);
                }
            } else if (t instanceof Pacman){
                switch(((Pacman)t).getSex()) {
                    case MALE: return PACMAN_MALE;
                    case FEMALE: return PACMAN_FEMALE;
                    default:
                        throw new RuntimeException("Unknown Pacman sex " + t);
                }
            }
            throw new RuntimeException("Unknown DynamicTarget " + t);
        }
    }

    /**
     * Replace a any actor to its {@link StartingPositions} for the current {@link Map} (setting its heading
     * to NORTH also).
     *
     * @param t
     */
    public static void replaceDynamicObject(DynamicTarget t){
        Map m = Map.getInstance();
        Position startingPos = StartingPositions.getCoordStartingPosition(t);
        t.setPosition(m.getActualPosition(startingPos));
        // By default, every target must spawn heading to NORTH
        t.setHeadingTo(Map.Direction.NORTH);
    }

    /**
     * Replace all actors of the game, see {@link MapPlacer#replaceDynamicObject}
     */
    public static void replaceDynamicObjects() {
        LimitedObjectContainer<Ghost> gC = Game.getInstance().getGhostContainer();
        for(Ghost g : gC) {
            replaceDynamicObject(g);
        }

        LimitedObjectContainer<Pacman> pC = Game.getInstance().getPacmanContainer();
        for(Pacman p : pC) {
            replaceDynamicObject(p);
        }
    }

    public static void placeAllStaticObjects(){
        MapPlacer.placeStaticObjects();
        MapPlacer.spawnStaticTargets();
    }

    /**
     * Place all static eatable elements (Coins and Points) in the current {@link Map}.
     */
    public static void spawnStaticTargets() {
        PositionContainer positionContainer = Map.getInstance().getPositionContainer();

        // Origin is leftmost upper point
        // --------- COINS ---------
        LimitedObjectContainer<Coin> cC = Game.getInstance().getCoinContainer();
        PointContainer pC = Game.getInstance().getPointContainer();
        ObjectContainer<MapObject> sObjs = Game.getInstance().getSpecialObjectsContainer();

        cC.removeAll();
        pC.removeAll();

        cC.add(new Coin(positionContainer.get(1, 1)));
        cC.add(new Coin(positionContainer.get(1, 8)));
        cC.add(new Coin(positionContainer.get(18, 1)));
        cC.add(new Coin(positionContainer.get(18, 8)));


        sObjs.add(new Grenade(positionContainer.get(18, 5)));
        sObjs.add(new Fish(positionContainer.get(17, 8)));
        sObjs.add(new RedBean(positionContainer.get(13, 6)));
        sObjs.add(new Pepper(positionContainer.get(1, 8)));
        sObjs.add(new Potato(positionContainer.get(10, 5)));
        sObjs.add(new Tomato(positionContainer.get(15, 8)));

        // --------- POINTS ---------
        for (Position p : positionContainer) {
            if (p.getOnPosition().size() == 0) {
                pC.add(new Point(p));

            }
        }
    }

    /**
     * Place all static non-eatable elements (Walls and Placeholders) in the current {@link Map}.
     */
    public static void placeStaticObjects() {
        PositionContainer positionContainer = Map.getInstance().getPositionContainer();

        // Origin is leftmost upper point
        // --------- WALLS ---------
        int width = positionContainer.getWidth();
        int height = positionContainer.getHeight();

        PositionContainer wallPositions = Containers.getPositionContainer(width, height);
        // Top border
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(0, 0),
                positionContainer.get(19, 0)
        ));
        // Left border
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(0, 1),
                positionContainer.get(0, 9)
        ));
        // Bottom border
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(1, 9),
                positionContainer.get(19, 9)
        ));
        // Right border
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(19, 1),
                positionContainer.get(19, 8)
        ));

        // Left Side
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(2, 2),
                positionContainer.get(2, 5)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(3, 2),
                positionContainer.get(5, 2)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(5, 3),
                positionContainer.get(5, 5)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(3, 5),
                positionContainer.get(4, 5)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(3, 7),
                positionContainer.get(5, 7)
        ));

        // Right Side
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(14, 2),
                positionContainer.get(14, 5)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(15, 2),
                positionContainer.get(17, 2)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(17, 3),
                positionContainer.get(17, 5)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(15, 5),
                positionContainer.get(16, 5)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(14, 7),
                positionContainer.get(16, 7)
        ));

        // Center Top
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(7, 2),
                positionContainer.get(7, 4)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(8, 4),
                positionContainer.get(12, 4)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(12, 2),
                positionContainer.get(12, 3)
        ));

        // Center Bottom
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(7, 6),
                positionContainer.get(7, 8)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(8, 6),
                positionContainer.get(12, 6)
        ));
        wallPositions.add(positionContainer.getRange(
                positionContainer.get(12, 7),
                positionContainer.get(12, 8)
        ));

        for (Position p : wallPositions) {
            new Wall(p);
        }

        PositionContainer trapPositions = Containers.getPositionContainer(width, height);
        trapPositions.add(
                positionContainer.get(8, 1)
        );
        for (Position p : trapPositions) {
            new Trap(p);
        }

        PositionContainer bridgePositions = Containers.getPositionContainer(width, height);
        bridgePositions.add(
                positionContainer.get(13, 5)
        );
        for (Position p : bridgePositions) {
            new Bridge(p, Bridge.Type.NORTH_SOUTH);
        }

        PositionContainer telePositions = Containers.getPositionContainer(width, height);
        telePositions.add(
                positionContainer.get(17, 7)
        );
        telePositions.add(
                positionContainer.get(2, 7)
        );
        Teleporter linked = new Teleporter(positionContainer.get(17, 7));
        new Teleporter(positionContainer.get(2, 7), linked);


        // ------- PLACEHOLDER -------

        PositionContainer placeholderPositions = Containers.getPositionContainer(width, height);

        // LEFT
        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(3, 3),
                        positionContainer.get(3, 4)
                )
        );

        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(4, 3),
                        positionContainer.get(4, 4)
                )
        );

        // RIGHT
        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(15, 3),
                        positionContainer.get(15, 4)
                )
        );

        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(16, 3),
                        positionContainer.get(16, 4)
                )
        );

        // TOP
        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(8, 2),
                        positionContainer.get(11, 2)
                )
        );
        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(8, 3),
                        positionContainer.get(11, 3)
                )
        );

        // BOTTOM
        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(8, 7),
                        positionContainer.get(11, 7)
                )
        );

        placeholderPositions.add(
                positionContainer.getRange(
                        positionContainer.get(8, 8),
                        positionContainer.get(11, 8)
                )
        );

        for (Position p : placeholderPositions) {
            new Placeholder(p);
        }
    }
}
