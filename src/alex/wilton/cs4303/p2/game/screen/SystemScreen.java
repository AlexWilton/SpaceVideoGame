package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.GameState;


public class SystemScreen extends Screen {
    public SystemScreen(GameState state) {
        super(state);
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(32);
        app.text("space", 100, 100);
    }
}
