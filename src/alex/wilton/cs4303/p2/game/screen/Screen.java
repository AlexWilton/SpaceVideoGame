package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;

/**
 * Screen.
 * Show information to player and wait for input (Mouse click)
 */
public abstract class Screen{
    protected alex.wilton.cs4303.p2.game.GameModel gameModel;
    protected App app = App.app;

    public Screen(alex.wilton.cs4303.p2.game.GameModel gameModel){
        this.gameModel = gameModel;
    }

    /**
     * Draw Screen
     */
    public abstract void draw();

    /**
     * Respond to mouse click events (e.g. listen for button clicks)
     */
    public abstract void mousePressed();

}
