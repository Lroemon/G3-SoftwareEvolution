/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

/**
 * The exception thrown if an object is already in a list and this is permitted.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @see model.Container
 */
public class ObjectAlreadyInListException extends ListException {

    private static final long serialVersionUID = -2037496278253932058L;

    /**
     * Constructs a new ObjectAlreadyInListException.
     *
     * @param className The class name of the list that is already full.
     *                  Should be retrieved by {@link Class#getCanonicalName()}.
     */
    public ObjectAlreadyInListException(String className) {
        super("The Object of class " + className + " is already in the list");
    }

}
