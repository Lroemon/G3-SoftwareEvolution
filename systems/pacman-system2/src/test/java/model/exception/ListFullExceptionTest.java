/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

import controller.MainController;
import model.Game;
import model.Pacman;
import model.PacmanContainer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * ListFullExceptionTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
@SuppressWarnings("unused")
public class ListFullExceptionTest {

    @Test(expected = model.exception.ListFullException.class)
    public void testConstruct() {
        MainController.reset();

        PacmanContainer container = new PacmanContainer();

        assertEquals(2, container.max);
        container.add(new Pacman(Game.getInstance().getMap().getPositionContainer().get(0, 0), Pacman.Sex.MALE));
        container.add(new Pacman(Game.getInstance().getMap().getPositionContainer().get(0, 1), Pacman.Sex.MALE));
        container.add(new Pacman(Game.getInstance().getMap().getPositionContainer().get(0, 2), Pacman.Sex.MALE));

        MainController.reset();
    }
}
