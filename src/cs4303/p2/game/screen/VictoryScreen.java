package cs4303.p2.game.screen;

import cs4303.p2.game.GameState;
import cs4303.p2.game.Stage;
import cs4303.p2.util.ImageCache;
import cs4303.p2.game.Galaxy;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.*;


public class VictoryScreen extends Screen {

    private final Galaxy galaxy;

    public VictoryScreen(GameState state) {
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
        createdDisabledButton("VICTORY!\n\nCONGRATULATIONS PLAYER " + state.getPlayerName().toUpperCase()
                        + "\n\nYou have successfully conquered the galaxy through your excellency leadership of the "
                        + state.getLeadsFaction().name() + " Faction to utter supremacy!",
                app.width / 2, app.height / 2, app.width / 2, app.height / 3);

        drawButtons();
    }

    private void drawButtons() {
        createButton("MAIN MENU", 130, (int) (app.height * 0.75), 180, 50, Stage.RESET);
        createButton("QUIT", 130, (int) (app.height * 0.9), 180, 50, Stage.EXIT_GAME);
    }


    private void drawGalaxyMapBackground() {
        PImage img = ImageCache.getImage("images/galaxy.png");
        app.imageMode(PConstants.CORNER);
        app.image(img, 0, 0, app.width, app.height);
    }


}
