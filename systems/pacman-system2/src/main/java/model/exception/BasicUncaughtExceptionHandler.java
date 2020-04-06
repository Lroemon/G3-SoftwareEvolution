/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model.exception;

/**
 * BasicUncaughtExceptionHandler
 *
 * @author Philipp Winter
 */
public class BasicUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("Uncaught Exception in thread " + t.getName() + ":");
        handle(e);
    }

    public void uncaught(Throwable e) {
        handle(e);
    }

    private void handle(Throwable t) {
        t.printStackTrace();
    }
}
