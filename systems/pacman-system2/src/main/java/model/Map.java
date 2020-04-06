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
public class Map {

    public static final PositionContainer positionsToRender = new PositionContainer(Map.getInstance().width, Map.getInstance().height);

    private static Map instance;

    private PositionContainer positionContainer;

    public final int width;

    public final int height;

    private boolean objectsPlaced = false;

    public static final StartingPosition startingPositions = new StartingPosition();

    public static Map getInstance() {
        if (Map.instance == null) {
            Map.instance = new Map();
        }

        return Map.instance;
    }

    public static void reset() {
        Map.instance = new Map();
    }

    private Map() {
        this(20, 10);
    }

    private Map(int width, int height) {
        this.width = width;
        this.height = height;

        this.positionContainer = new PositionContainer(width, height);

        // Create all position instances for this map
        for (int actX = 0; actX < width; actX++) {
            for (int actY = 0; actY < height; actY++) {
                this.positionContainer.add(new Position(actX, actY));
            }
        }
    }

    public PositionContainer getPositionContainer() {
        return this.positionContainer;
    }

    public static int freeNeighbourFields(Position pos) {
        int count = 0;
        for (Direction d : Direction.values()) {
            if (getPositionByDirectionIfMovableTo(pos, d) != null) {
                count++;
            }
        }
        return count;
    }

    public static Position getPositionByDirectionIfMovableTo(Position prevPos, Direction movingTo) {
        Position p = null;
        if (prevPos == null) {
            throw new IllegalArgumentException("prevPos cannot be null.");
        }
        try {
            if (movingTo == Direction.NORTH) {
                p = Map.getInstance().getPositionContainer().get(prevPos.getX(), prevPos.getY() - 1);
            } else if (movingTo == Direction.EAST) {
                p = Map.getInstance().getPositionContainer().get(prevPos.getX() + 1, prevPos.getY());
            } else if (movingTo == Direction.WEST) {
                p = Map.getInstance().getPositionContainer().get(prevPos.getX() - 1, prevPos.getY());
            } else if (movingTo == Direction.SOUTH) {
                p = Map.getInstance().getPositionContainer().get(prevPos.getX(), prevPos.getY() + 1);
            }
            if (p != null && p.isMoveableTo()) {
                return p;
            } else {
                return null;
            }
        } catch (IllegalArgumentException ex) {
            // Just return null to signalize, that the point doesn't exist
            return null;
        }
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Map) {
                return this.getPositionContainer().equals(((Map) o).getPositionContainer())
                        && this.objectsPlaced == ((Map) o).isObjectsPlaced();
            }
        }
        return false;
    }

    public void placeObjects() {
        placeDynamicObjects();
        placeStaticObjects();
        spawnStaticTargets();

        this.markAllForRendering();
    }

    private void placeDynamicObjects() {
        Game g = Game.getInstance();

        // --------- PACMANS ---------
        PacmanContainer pacC = g.getPacmanContainer();

        pacC.add(new Pacman(startingPositions.PACMAN_MALE, Pacman.Sex.MALE));

        if (Settings.getInstance().getGameMode() == Game.Mode.MULTIPLAYER) {
            pacC.add(new Pacman(startingPositions.PACMAN_FEMALE, Pacman.Sex.FEMALE));
        }

        // --------- GHOSTS ---------
        GhostContainer gC = g.getGhostContainer();
        gC.add(new Ghost(positionContainer.get(8, 3), Ghost.Colour.BLUE));
        gC.add(new Ghost(positionContainer.get(9, 3), Ghost.Colour.ORANGE));
        gC.add(new Ghost(positionContainer.get(10, 3), Ghost.Colour.PINK));
        gC.add(new Ghost(positionContainer.get(11, 3), Ghost.Colour.RED));
    }

    private void placeStaticObjects() {
        // --------- WALLS ---------

        PositionContainer wallPositions = new PositionContainer(width, height);
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
                positionContainer.get(2, 7),
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
                positionContainer.get(17, 7)
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

        // ------- PLACEHOLDER -------

        PositionContainer placeholderPositions = new PositionContainer(width, height);

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

        Map.positionsToRender.add(wallPositions);
        Map.positionsToRender.add(placeholderPositions);
    }

    public void spawnStaticTargets() {
        // --------- COINS ---------
        CoinContainer cC = Game.getInstance().getCoinContainer();
        PointContainer pC = Game.getInstance().getPointContainer();

        cC.removeAll();
        pC.removeAll();

        positionsToRender.add((positionContainer.get(1, 1)));
        positionsToRender.add((positionContainer.get(1, 8)));
        positionsToRender.add((positionContainer.get(18, 1)));
        positionsToRender.add((positionContainer.get(18, 8)));

        cC.add(new Coin(positionContainer.get(1, 1)));
        cC.add(new Coin(positionContainer.get(1, 8)));
        cC.add(new Coin(positionContainer.get(18, 1)));
        cC.add(new Coin(positionContainer.get(18, 8)));

        // --------- POINTS ---------
        for (Position p : positionContainer) {
            if (p.getOnPosition().size() == 0) {
                pC.add(new Point(p));
                positionsToRender.add(p);
            }
        }


    }

    public void onNextLevel() {
        this.replaceDynamicObjects();

        for(Coin c : Game.getInstance().getCoinContainer()){
            if(c.getState() == StaticTarget.State.EATEN) {
                c.changeState(StaticTarget.State.AVAILABLE);
            }
        }
        for(Point p : Game.getInstance().getPointContainer()){
            if(p.getState() == StaticTarget.State.EATEN){
                p.changeState(StaticTarget.State.AVAILABLE);
            }
        }

        this.markAllForRendering();
    }

    public void onPacmanGotEaten() {
        this.replaceDynamicObjects();
    }

    public static class StartingPosition {

        public final Position GHOST_RED = Map.getInstance().positionContainer.get(11, 3);
        public final Position GHOST_PINK = Map.getInstance().positionContainer.get(10, 3);
        public final Position GHOST_BLUE = Map.getInstance().positionContainer.get(8, 3);
        public final Position GHOST_ORANGE = Map.getInstance().positionContainer.get(9, 3);

        public final Position PACMAN_MALE = Map.getInstance().positionContainer.get(13, 8);
        public final Position PACMAN_FEMALE = Map.getInstance().positionContainer.get(6, 8);

    }

    private void replaceDynamicObjects() {
        GhostContainer gC = Game.getInstance().getGhostContainer();

        for(Ghost g : gC) {
            switch(g.getColour()) {
                case RED: g.move(startingPositions.GHOST_RED);
                    break;
                case PINK: g.move(startingPositions.GHOST_PINK);
                    break;
                case BLUE: g.move(startingPositions.GHOST_BLUE);
                    break;
                case ORANGE: g.move(startingPositions.GHOST_ORANGE);
                    break;
                default:
                    throw new RuntimeException("Bla");
            }
        }

        PacmanContainer pC = Game.getInstance().getPacmanContainer();

        for(Pacman p : pC) {
            switch(p.getSex()) {
                case MALE:
                    p.move(startingPositions.PACMAN_MALE);
                    break;
                case FEMALE:
                    p.move(startingPositions.PACMAN_FEMALE);
                    break;
            }
            positionsToRender.add(p.getPosition());
        }

    }

    public boolean isObjectsPlaced() {
        return objectsPlaced;
    }

    public void markAllForRendering() {
        positionsToRender.add(positionContainer);
    }

    public enum Direction {

        NORTH, WEST, EAST, SOUTH;

        public static Direction guessDirection(MapObject mO) {
            Direction[] directions = Direction.values();
            Position guessedPosition = null;
            Direction guessedDirection = null;

            Helper.shuffle(directions);

            for (Direction direction : directions) {
                guessedPosition = Map.getPositionByDirectionIfMovableTo(mO.getPosition(), direction);
                if (guessedPosition != null) {
                    guessedDirection = direction;
                    break;
                }
            }
            if (guessedPosition == null) {
                throw new RuntimeException("Couldn't find any free position :(");
            } else {
                return guessedDirection;
            }
        }

    }

}
