/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * The highscore is the collection of numbers, the user will be proud of.
 *
 * @author Philipp Winter
 * @author Jonas Heidecke
 * @author Niklas Kaddatz
 */
@SuppressWarnings("unused")
public class Score implements Serializable, Comparable<Score>{

    /**
     * The score, starting at zero.
     */
    private long score = 0;

    private Date time;

    /**
     * Constructs a new Highscore.
     */
    public Score() {
        Highscore.getInstance().add(this);
        this.time = Calendar.getInstance().getTime();
    }

    /**
     * Gets the score.
     *
     * @return The current score.
     */
    public long getScore() {
        return score;
    }

    /**
     * Adds an amount of points to the highscore.
     *
     * @param i A positive integer.
     *
     * @throws java.lang.IllegalArgumentException When <i>i</i> is not positive.
     */
    private void addToScore(int i) {
        if (i > 0) {
            this.score += i;
        } else {
            throw new IllegalArgumentException("The amount of points must be positive.");
        }
    }

    public void addToScore(Scorable s) {
        this.addToScore(s.getScore());
    }

    public boolean equals(Object o) {
        if (o != null) {
            if (o instanceof Score) {
                return this.getScore() == ((Score) o).getScore();
            }
        }
        return false;
    }

    @Override
    public int compareTo(Score o) {
        if(this.getScore() < o.getScore()) {
            return -1;
        } else if (this.getScore() > o.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        return String.valueOf(score) + ", " + time;
    }
}
