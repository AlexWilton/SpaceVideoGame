package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;

import java.awt.*;

public class CustomPlayScreen extends Screen {
    public CustomPlayScreen(GameState state) {
        super(state);
    }

    /**
     * Draw Screen
     */
    @Override
    public void draw() {
        app.background(Color.BLACK.getRGB());
        PlanetLogo.draw();
        app.textSize(50); app.fill(Color.WHITE.getRGB());
        app.text("CUSTOM MODE", app.width / 2, 130);
        createButton("HOST GAME", app.width / 4, 280, 330, 80, Stage.HOST_CUSTOM_PLAY);
        createButton("JOIN GAME",       3 * app.width / 4, 280, 330, 80, Stage.JOIN_CUSTOM_PLAY);

        createButton("MAIN MENU", app.width / 2, app.height - 50, 330, 40, Stage.MAIN_MENU);
    }


}
