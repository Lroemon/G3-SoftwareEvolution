/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package view;

import model.*;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ImageOrganizer
 *
 * @author Philipp Winter
 */
public class ImageOrganizer {

    private static ImageOrganizer instance;

    private final HashMap<String, BufferedImage> images;

    public static ImageOrganizer getInstance() {
        if (instance == null) {
            instance = new ImageOrganizer();
        }
        return instance;
    }

    public BufferedImage get(MapObject mO) {
        String key;
        if (mO != null) {
            key = mO.getClass().getCanonicalName();
        } else {
            key = null;
        }

        if (mO instanceof Ghost) {
            Ghost g = (Ghost) mO;
            if (g.getState() == DynamicTarget.State.HUNTER) {
                key += ">" + g.getColour();
                key += ">" + g.getHeadingTo();
            } else if (g.getState() == DynamicTarget.State.HUNTED) {
                key += ">SCARE>" + (g.getMovedInLastTurn() ? "BLUE" : "WHITE");
            } else if (g.getState() == DynamicTarget.State.WAITING) {
                key += ">SCARE>" + "BLUE";
            }
        }

        if (mO instanceof Pacman) {
            Pacman p = (Pacman) mO;
            key += ">" + p.getHeadingTo();
        }

        if (images.containsKey(key)) {
            return images.get(key);
        } else {
            throw new IllegalArgumentException("There is no image for the key \"" + key + "\"!");
        }
    }

    private ImageOrganizer() {
        images = new HashMap<>(30);

        Class<?> c = this.getClass();

        ArrayList<String[]> data = new ArrayList<>();
        // WALL
        data.add(
                new String[]{"/graphics/primitive/black_big.png", Wall.class.getCanonicalName()}
        );
        // PACMAN
        data.add(
                new String[]{"/graphics/resized/pacman/4_north.png", Pacman.class.getCanonicalName() + ">NORTH"}
        );
        data.add(
                new String[]{"/graphics/resized/pacman/4_east.png", Pacman.class.getCanonicalName() + ">EAST"}
        );
        data.add(
                new String[]{"/graphics/resized/pacman/4_south.png", Pacman.class.getCanonicalName() + ">SOUTH"}
        );
        data.add(
                new String[]{"/graphics/resized/pacman/4_west.png", Pacman.class.getCanonicalName() + ">WEST"}
        );

        // SCARED GHOST
        data.add(
                new String[]{"/graphics/resized/ghosts/scared/blue.png", Ghost.class.getCanonicalName() + ">SCARE>BLUE"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/scared/white.png", Ghost.class.getCanonicalName() + ">SCARE>WHITE"}
        );

        // BLINKY
        data.add(
                new String[]{"/graphics/resized/ghosts/blinky/0.png", Ghost.class.getCanonicalName() + ">RED" + ">WEST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/blinky/2.png", Ghost.class.getCanonicalName() + ">RED" + ">NORTH"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/blinky/4.png", Ghost.class.getCanonicalName() + ">RED" + ">EAST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/blinky/6.png", Ghost.class.getCanonicalName() + ">RED" + ">SOUTH"}
        );

        // CLYDE
        data.add(
                new String[]{"/graphics/resized/ghosts/clyde/0.png", Ghost.class.getCanonicalName() + ">ORANGE" + ">WEST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/clyde/2.png", Ghost.class.getCanonicalName() + ">ORANGE" + ">NORTH"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/clyde/4.png", Ghost.class.getCanonicalName() + ">ORANGE" + ">EAST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/clyde/6.png", Ghost.class.getCanonicalName() + ">ORANGE" + ">SOUTH"}
        );

        // INKY
        data.add(
                new String[]{"/graphics/resized/ghosts/inky/0.png", Ghost.class.getCanonicalName() + ">BLUE" + ">WEST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/inky/2.png", Ghost.class.getCanonicalName() + ">BLUE" + ">NORTH"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/inky/4.png", Ghost.class.getCanonicalName() + ">BLUE" + ">EAST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/inky/6.png", Ghost.class.getCanonicalName() + ">BLUE" + ">SOUTH"}
        );

        // PINKY
        data.add(
                new String[]{"/graphics/resized/ghosts/pinky/0.png", Ghost.class.getCanonicalName() + ">PINK" + ">WEST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/pinky/2.png", Ghost.class.getCanonicalName() + ">PINK" + ">NORTH"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/pinky/4.png", Ghost.class.getCanonicalName() + ">PINK" + ">EAST"}
        );
        data.add(
                new String[]{"/graphics/resized/ghosts/pinky/6.png", Ghost.class.getCanonicalName() + ">PINK" + ">SOUTH"}
        );

        // COIN
        data.add(
                new String[]{"/graphics/resized/dots/orange.png", Coin.class.getCanonicalName()}
        );

        // POINT
        data.add(
                new String[]{"/graphics/resized/dots/blue_filled.png", Point.class.getCanonicalName()}
        );

        // NOTHING
        data.add(
                new String[]{"/graphics/primitive/white_big.png", null}
        );

        //PLACEHOLDER
        data.add(
                new String[]{"/graphics/primitive/white_big.png", Placeholder.class.getCanonicalName()}
        );

            for (String[] d : data) {
                //Load image
                try {
                    BufferedImage before = ImageIO.read(c.getResource(d[0]));
                    //Scale it
                    int w = before.getWidth();
                    int h = before.getHeight();
                    BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    AffineTransform at = new AffineTransform();
                    at.scale(1, 1);
                    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    after = scaleOp.filter(before, after);
                    //Put it in the list
                    images.put(d[1], after);
                } catch(IllegalArgumentException e) {
                    System.out.println("Failed to load resource picture for path " + d[0] + " and key " + d[1]);
                    e.printStackTrace();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
    }
}
