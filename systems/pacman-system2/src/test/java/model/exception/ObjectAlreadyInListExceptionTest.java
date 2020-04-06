/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

import model.Pacman;
import model.PacmanContainer;
import model.Position;
import org.junit.Test;

/**
 * ObjectAlreadyInListExceptionTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public class ObjectAlreadyInListExceptionTest {

    @Test(expected = ObjectAlreadyInListException.class)
    public void testConstruct() {
        Pacman p = new Pacman(new Position(0, 0), Pacman.Sex.MALE);
        PacmanContainer pC = new PacmanContainer();

        // Add the same object two times to cause the expected exception to be thrown.
        pC.add(p);
        pC.add(p);
    }
}
