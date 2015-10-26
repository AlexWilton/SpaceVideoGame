package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;

/**
 * Screen.
 * Show information to player and wait for input (Mouse click)
 */
public abstract class Screen{
    protected GameState state;
    protected App app = App.app;

    public Screen(GameState state){this.state = state;}

    /**
     * Draw Screen
     */
    public abstract void draw();

    /**
     * Respond to mouse click events (e.g. listen for button clicks)
     */
    public abstract void mousePressed();

    /**
     * Respond to key press events
     */
    public abstract void keyPressed();

}
