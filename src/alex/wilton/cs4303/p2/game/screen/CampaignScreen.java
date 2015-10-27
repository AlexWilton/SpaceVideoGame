package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;

import java.awt.*;

public class CampaignScreen extends Screen{
    public CampaignScreen(GameState state) {
        super(state);
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());
        PlanetLogo.draw();
        app.textSize(50);
        app.text("CAMPAIGN MODE", app.width / 2, 130);
        createButton("LOAD SAVED GAME",    app.width / 4, 280, 330, 80, Stage.LOAD_SAVED_GAME);
        createButton("NEW GAME",       3 * app.width / 4, 280, 330, 80, Stage.NEW_CAMPAIGN);
        createButton("MAIN MENU",                app.width / 2, app.height-50, 330, 40, Stage.MAIN_MENU);
    }

}
