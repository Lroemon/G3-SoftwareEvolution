/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.exception;

import be.umons.controller.MainController;
import be.umons.model.Game;
import be.umons.model.mapobject.Pacman;
import be.umons.model.container.Containers;
import be.umons.model.container.LimitedObjectContainer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * ListFullExceptionTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */

public class ListFullExceptionTest {

    @Test(expected = be.umons.model.exception.ListFullException.class)
    public void testConstruct() {
        MainController.reset();

        LimitedObjectContainer<Pacman> container = Containers.getPacmanContainer();

        assertEquals(2, container.max);
        container.add(new Pacman(Game.getInstance().getMap().getPositionContainer().get(0, 0), Pacman.Sex.MALE));
        container.add(new Pacman(Game.getInstance().getMap().getPositionContainer().get(0, 1), Pacman.Sex.MALE));
        container.add(new Pacman(Game.getInstance().getMap().getPositionContainer().get(0, 2), Pacman.Sex.MALE));

        MainController.reset();
    }
}
