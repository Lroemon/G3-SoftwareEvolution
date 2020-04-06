/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

/**
 * The exception thrown if an object strictly needs initialization but this has not happened yet.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @see model.Game
 */
public class ObjectNotInitializedException extends RuntimeException {

    private static final long serialVersionUID = 6953213349896267794L;

    /**
     * Constructs a new ObjectNotInitializedException.
     *
     * @param className The class name of the list that is already full.
     *                  Should be retrieved by {@link Class#getCanonicalName()}.
     */
    public ObjectNotInitializedException(String className) {
        super("The object of class " + className + " is not initialized yet and therefore access to it is permitted.");
    }

}
