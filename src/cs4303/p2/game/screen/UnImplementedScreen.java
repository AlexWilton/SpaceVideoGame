package cs4303.p2.game.screen;

import cs4303.p2.game.GameState;
import cs4303.p2.game.Stage;

import java.awt.*;

public class UnImplementedScreen extends Screen {
    private String desiredScreen;
    public UnImplementedScreen(String name, GameState state) {
        super(state);
        desiredScreen = name;
    }

    /**
     * Draw Screen
     */
    @Override
    public void draw() {

        app.background(Color.LIGHT_GRAY.getRGB());  app.fill(Color.WHITE.getRGB());
        app.text("Screen " + desiredScreen + " Unimplemented!", app.width/2, 100);
        createButton("MAIN MENU", app.width / 2, 230, 330, 40, Stage.MAIN_MENU);
        createButton("SYSTEM SCREEN", app.width / 2, 300, 330, 40, Stage.SYSTEM);
        createButton("EXIT", app.width / 2, 430, 330, 40, Stage.EXIT_GAME);
    }

}
