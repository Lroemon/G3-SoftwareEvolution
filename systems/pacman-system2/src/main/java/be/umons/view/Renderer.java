/******************************************************************************
 * This work is applicable to the conditions of the MIT License,              *
 * which can be found in the LICENSE file, or at                              *
 * https://github.com/philippwinter/pacman/blob/master/LICENSE                *
 *                                                                            *
 * Copyright (c) 2013 Philipp Winter, Jonas Heidecke & Niklas Kaddatz         *
 ******************************************************************************/

package be.umons.view;

import be.umons.controller.MainController;
import be.umons.model.*;

import java.awt.*;

/**
 * Renderer, paint the GUI panel and add texts for Highscore, Player Lifes and current Level, adjusted
 *
 * @author Philipp Winter
 */
public class Renderer {

    private boolean readyForReRendering = false;

    private final MainController controller;
    private final MainGui gui;

    public final int mapHeight;
    public final int mapWidth;

    public final int topSpace;
    public final int leftSpace;

    public final int multiplier = 20;
    private final int TOP_SPACE_FACTOR = 2;
    private final int LEFT_SPACE_FACTOR = 4;
    private final int LEFT_PADDING = 15;

    private final ImageOrganizer imgOrganizer;

    public Renderer(MainGui gui) {
        this.controller = MainController.getInstance();
        this.imgOrganizer = ImageOrganizer.getInstance();
        this.gui = gui;

        this.mapHeight = (Map.getInstance().height * multiplier);
        this.mapWidth = (Map.getInstance().width * multiplier);

        this.topSpace = (mapHeight / TOP_SPACE_FACTOR);
        this.leftSpace = (mapWidth / LEFT_SPACE_FACTOR);
    }

    private void render() {
        if (!controller.isGameActive() || !this.readyForReRendering) {
            System.out.println("Returned without rendering: " + System.currentTimeMillis());
            return;
        }

        try {
            gui.getPnlGame().repaint();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            this.readyForReRendering = false;
        }

    }

    protected void drawString(Graphics2D target, String s, int offset) {
        target.drawString(
                s,
                LEFT_PADDING + leftSpace,
                (Map.getInstance().height * multiplier) + (offset * multiplier) + topSpace
        );
    }

    /**
     * Set ready for an fresh new rendering and execute it
     */
    public void markReady() {
        if (readyForReRendering) {
            System.out.println("Rendering must be completed before rendering again");
        }
        this.readyForReRendering = true;
        render();
    }
}
