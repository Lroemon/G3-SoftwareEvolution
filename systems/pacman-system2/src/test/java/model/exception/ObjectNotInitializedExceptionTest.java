/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

import org.junit.Test;

/**
 * ObjectNotInitializedExceptionTest
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
@SuppressWarnings("unused")
public class ObjectNotInitializedExceptionTest {

    @Test(expected = ObjectNotInitializedException.class)
    public void testConstruct() {
        throw new ObjectNotInitializedException(this.getClass().getCanonicalName());
    }
}
