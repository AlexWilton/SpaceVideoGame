package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.GameState;

import java.awt.*;

public class UnImplementedScreen extends Screen {
    public UnImplementedScreen(GameState state) {
        super(state);
    }

    /**
     * Draw Screen
     */
    @Override
    public void draw() {

        app.background(Color.LIGHT_GRAY.getRGB());
        app.text("Unimplemented Screen", 100, 100);
    }

    /**
     * Respond to mouse click events (e.g. listen for button clicks)
     */
    @Override
    public void mousePressed() {

    }

    /**
     * Respond to key press events
     */
    @Override
    public void keyPressed() {

    }

}
