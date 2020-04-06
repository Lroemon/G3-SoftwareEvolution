/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.event;

import controller.MainController;
import model.*;
import model.Map.Direction;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class WorkerProcess implements Process {

    private PointContainer points;

    private CoinContainer coins;

    private GhostContainer ghosts;

    private PacmanContainer pacmans;

    private Map map;

    private boolean checkCoinSeconds = false;

    @Override
    public long getTiming() {
        return (long) (1000 / Game.getInstance().getRefreshRate());
    }

    @Override
    public long getStartupDelay() {
        return 0;
    }

    @Override
    public void onLoad() {
        this.points = Game.getInstance().getPointContainer();
        this.coins = Game.getInstance().getCoinContainer();
        this.ghosts = Game.getInstance().getGhostContainer();
        this.pacmans = Game.getInstance().getPacmanContainer();
        this.map = Game.getInstance().getMap();
    }

    @Override
    public void run() {
        try {
            if (this.check()) {
                this.markDynamicObjectsForRendering();

                this.handleCoins();
                this.performCollisions();
                this.handlePacmans();
                this.performCollisions(); // Must be done two times to prevent two objects moving through each other
                this.handleGhosts();

                this.markDynamicObjectsForRendering();
            }
        } catch (Throwable t) {
            MainController.uncaughtExceptionHandler.uncaught(t);
        }
    }

    private void markDynamicObjectsForRendering() {
        for(Pacman p : Game.getInstance().getPacmanContainer()){
            Map.positionsToRender.add(p.getPosition());
        }
        for(Ghost g : Game.getInstance().getGhostContainer()){
            Map.positionsToRender.add(g.getPosition());
        }
    }

    private boolean check() {
        boolean performFurtherActions;

        // Check whether level is completed
        PointContainer pC = Game.getInstance().getPointContainer();
        int pointsEaten = 0;

        for (Point p : pC) {
            if (p.getState() == StaticTarget.State.EATEN) {
                pointsEaten++;
            }
        }

        int size = Game.getInstance().getPointContainer().size();

        performFurtherActions = (pointsEaten != size) && (!Game.getInstance().isGameOver());

        if (pointsEaten == size) {
            Level.getInstance().nextLevel();
        }

        return performFurtherActions;
    }

    private void handlePacmans() {
        PacmanContainer pacmans = Game.getInstance().getPacmanContainer();

        for (Pacman p : pacmans) {
            this.handlePacman(p);
        }
    }

    public void handleCoins() {
        double activeSeconds = Coin.getActiveSeconds();

        if (activeSeconds != Coin.PACMAN_AINT_EATER) {
            Coin.reduceActiveSeconds(1 / Game.getInstance().getRefreshRate());
        }

        if (checkCoinSeconds && Coin.getActiveSeconds() == Coin.PACMAN_AINT_EATER) {
            for (Ghost g : Game.getInstance().getGhostContainer()) {
                if (g.getState() == DynamicTarget.State.HUNTED) {
                    g.changeState(DynamicTarget.State.HUNTER);
                }
            }

            for (Pacman p : Game.getInstance().getPacmanContainer()) {
                p.changeState(DynamicTarget.State.HUNTED);
            }

            checkCoinSeconds = false;
        }
    }

    private void handleGhosts() {
        for (Ghost g : this.ghosts) {
            this.handleGhost(g);
        }
    }

    private void handlePacman(Pacman pac) {
        if (pac.getState() != DynamicTarget.State.MUNCHED && pac.getState() != DynamicTarget.State.WAITING) {
            Position newPosition = Map.getPositionByDirectionIfMovableTo(pac.getPosition(), pac.getHeadingTo());

            if (newPosition != null) {
                pac.move(newPosition);
            }
        }

        Map.positionsToRender.add(pac.getPosition());
    }

    private void performCollisions() {
        for (Pacman p : pacmans) {
            performCollision(p);
        }
    }

    private void performCollision(Pacman pac) {
        MapObjectContainer mapObjectsOnPos = pac.getPosition().getOnPosition();

        for (MapObject mO : mapObjectsOnPos.getAll()) {
            if (mO instanceof StaticTarget) {
                StaticTarget t = (StaticTarget) mO;
                // An already eaten thing hasn't to be eaten again
                if (t.getState() != StaticTarget.State.EATEN) {
                    if (t instanceof Coin) {
                        checkCoinSeconds = true;
                    }
                    pac.eat(t);
                }
            } else if (mO instanceof Ghost) {
                Ghost g = (Ghost) mO;
                if (g.getState() == DynamicTarget.State.HUNTED) {
                    pac.eat(g);
                } else if (g.getState() == DynamicTarget.State.HUNTER) {
                    g.eat(pac);
                }
            }
        }
    }

    private void handleGhost(Ghost g) {
        Position newPosition = Map.getPositionByDirectionIfMovableTo(g.getPosition(), g.getHeadingTo());

        // If the Ghost stands in front of a wall OR it could take another way
        if (newPosition == null || (Map.freeNeighbourFields(g.getPosition()) > 1 && Math.round(Math.random()) == 1)) {
            Direction guessedDirection = Map.Direction.guessDirection(g);
            g.setHeadingTo(guessedDirection);
            newPosition = Map.getPositionByDirectionIfMovableTo(g.getPosition(), guessedDirection);
        }


        if (g.getState() == DynamicTarget.State.HUNTER) {
            g.move(newPosition);
        } else if (g.getState() == DynamicTarget.State.MUNCHED) {
            g.changeState(DynamicTarget.State.WAITING);
        } else if (g.getState() == DynamicTarget.State.WAITING) {
            if (g.getWaitingSeconds() > 0) {
                g.reduceWaitingSeconds(1 / Game.getInstance().getRefreshRate());
            } else if (g.getWaitingSeconds() == 0) {
                Map.StartingPosition startingPositions = Map.startingPositions;
                switch (g.getColour()) {
                    case RED:
                        g.move(startingPositions.GHOST_RED);
                        break;
                    case BLUE:
                        g.move(startingPositions.GHOST_BLUE);
                        break;
                    case ORANGE:
                        g.move(startingPositions.GHOST_ORANGE);
                        break;
                    case PINK:
                        g.move(startingPositions.GHOST_PINK);
                }
                g.changeState(DynamicTarget.State.HUNTER);
            }
        } else if (g.getState() == DynamicTarget.State.HUNTED) {
            if (g.getMovedInLastTurn()) {
                g.setMovedInLastTurn(false);
            } else {
                g.move(newPosition);
                g.setMovedInLastTurn(true);
            }
        }
    }
}
