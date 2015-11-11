package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GalaxySystem;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;


public abstract class AbstractSystemScreen extends Screen {

    protected GalaxySystem system;

    public AbstractSystemScreen(GameState state) {
        super(state);
        system = state.getPlayerLocation();
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());

        createdDisabledButton("Credits: " + state.getPlayerCredits() + " GC", 130,30, 200, 40);

        system.drawPlanetInTopRight();
        DrawableShip ship = state.getPlayerFleet().get(0).createDrawableShipInstance();
        ship.setCenterPosition(new PVector(app.width/10, 250));
        ship.setOrientation(PConstants.PI/2);
        ship.draw();

        app.rectMode(App.CORNER); app.noFill();
        app.rect((float) (0.2 * app.width), 100, (float) (0.6 * app.width), app.height - 200);
    }
}
