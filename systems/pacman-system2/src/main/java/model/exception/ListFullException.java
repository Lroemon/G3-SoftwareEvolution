/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

/**
 * The exception thrown if someone is trying to add a value to a list with a fixed maximal size.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 * @see model.Container
 */
public class ListFullException extends ListException {

    private static final long serialVersionUID = 928833290405863244L;

    /**
     * Construct a new ListFullException.
     *
     * @param className The class name of the list that is already full.
     *                  Should be retrieved by {@link Class#getCanonicalName()}.
     */
    public ListFullException(String className) {
        super("The list of " + className + "'s is full.");
    }

}
