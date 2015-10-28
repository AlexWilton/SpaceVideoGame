package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import processing.core.PImage;
import processing.event.KeyEvent;

import java.awt.*;

public class MainMenuScreen extends Screen {
    private static PImage logoImg = App.app.loadImage("images/logoWhite.png");
    public MainMenuScreen(GameState state) {
        super(state);
    }

    /**
     * Draw Screen
     */
    @Override
    public void draw() {
        app.background(Color.BLACK.getRGB());
        app.imageMode(App.CENTER);
        app.image(logoImg, app.width / 2, 120);
        createButton("CAMPAIGN (SINGLE PLAYER)",    app.width / 2, 250, 330, 40, Stage.CAMPAIGN);
        createButton("CUSTOM (MULTIPLAYER)",        app.width / 2, 310, 330, 40, Stage.CUSTOM_PLAY);
        createButton("GAME OPTIONS",                app.width / 2, 370, 330, 40, Stage.GAME_OPTIONS);
        createButton("GalacticConquests.com",       app.width / 2, 430, 330, 40,Stage.GOTO_GC_WEBSITE);
        createButton("EXIT",                        app.width / 2, 490, 330, 40, Stage.EXIT_GAME);
    }

}
