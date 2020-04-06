/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

/**
 * The parent class of exceptions that can occur while managing a list.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
public abstract class ListException extends RuntimeException {

    private static final long serialVersionUID = 4212167949047671695L;

    /**
     * Construct a new ListException.
     *
     * @param s The message.
     */
    public ListException(String s) {
        super(s);
    }

}
