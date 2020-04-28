/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.model.event;

/**
 * Process, stating for an event that have to be processed at a given time
 *
 * @author Philipp Winter
 */
public interface Process extends Runnable {

    long getTiming();
    long getStartupDelay();

    void onLoad();
    void run();
}
