/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Highscore
 *
 * @author Philipp Winter
 */
public class Highscore implements Serializable {

    private static final long serialVersionUID = -5739894473572621875L;

    private static Highscore instance = null;

    private static final File file = new File("highscore.dat");

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private ArrayList<Score> scores;

    private Highscore() {
        scores = new ArrayList<Score>();

        if (file.exists()) {
            try {
                this.inputStream = new ObjectInputStream(new FileInputStream(file.getAbsolutePath()));
                this.scores = (ArrayList<Score>) inputStream.readObject();

                Helper.quickSort(scores, new Comparator<Score>() {
                    @Override
                    public int compare(Score o1, Score o2) {
                        return o1.compareTo(o2);
                    }
                });

                while(scores.size() > 3){
                    scores.remove(0);
                }
            } catch(IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("An error occurred while reading the Highscore");
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                boolean newFile = file.createNewFile();
                if (!newFile) {
                    throw new RuntimeException("Could not create Highscore data file.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void add(Score s) {
        scores.add(s);
    }

    public static Highscore getInstance() {
        if(instance == null){
            instance = new Highscore();
        }
        return instance;
    }

    public void writeToFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(file));

            outputStream.writeObject(instance.scores);
        } catch (IOException e) {
            System.out.println("Could not save highscores");
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Score> getScores() {
        return scores;
    }
}
