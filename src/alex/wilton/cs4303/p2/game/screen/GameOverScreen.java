package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.Galaxy;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.util.ImageCache;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.*;


public class GameOverScreen extends Screen {

    private final Galaxy galaxy;

    public GameOverScreen(GameState state) {
        super(state);
        galaxy = state.getGalaxy();
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());
        drawGalaxyMapBackground();
        galaxy.drawConnectionsOnMap();
        galaxy.drawSystemsOnMap();
        app.fill(Color.BLACK.getRGB());
        app.rect(app.width/2, app.height/2, app.width/2, app.height/3);
        createdDisabledButton("GAME OVER!\n\nPLAYER " + state.getPlayerName().toUpperCase()
                        + "\n\nLike a good Captain you went down with your ship as it was destroyed by a " +
                        state.getPlayerLocation().getFaction().name() + " Faction member." +
                        "\n\nBetter luck in the next life!",
                app.width / 2, app.height / 2, app.width / 2, app.height / 3);

        drawButtons();
    }

    private void drawButtons() {
        createButton("CONTINUE\n(UNDO BATTLE)", 130, (int) (app.height * 0.6), 180, 50, Stage.UNDO_FIGHT);
        createButton("NEW GAME", 130, (int) (app.height * 0.75), 180, 50, Stage.RESET);
        createButton("QUIT", 130, (int) (app.height * 0.9), 180, 50, Stage.EXIT_GAME);
    }


    private void drawGalaxyMapBackground() {
        PImage img = ImageCache.getImage("images/galaxy.png");
        app.imageMode(PConstants.CORNER);
        app.image(img, 0, 0, app.width, app.height);
    }


}
