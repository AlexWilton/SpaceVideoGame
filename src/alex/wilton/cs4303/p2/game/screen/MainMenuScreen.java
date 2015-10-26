package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import processing.core.PImage;

import java.awt.*;

public class MainMenuScreen extends Screen {
    private static PImage img = App.app.loadImage("logoWhite.png");
    public MainMenuScreen(GameState state) {
        super(state);
    }

    /**
     * Draw Screen
     */
    @Override
    public void draw() {
        app.text("main", 100, 100);
    }

    /**
     * Respond to mouse click events (e.g. listen for button clicks)
     */
    @Override
    public void mousePressed() {
        state.setGameStage(Stage.CUSTOM_PLAY);
    }

    /**
     * Respond to key press events
     */
    @Override
    public void keyPressed() {

    }
}
