package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.GalaxySystem;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;

import java.awt.*;


public class SystemScreen extends Screen {

    private GalaxySystem system;

    public SystemScreen(GameState state) {
        super(state);
        system = state.getPlayerLocation();
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());
        PlanetLogo.draw();

        app.textSize(30); app.fill(Color.WHITE.getRGB());
        app.text(system.getName().toUpperCase() + " SYSTEM", app.width / 2, 30);
        app.textSize(25);
        app.fill(system.getFaction().getFactionColour().getRGB());
        app.text("System Controlled by " + system.getFaction().name(), app.width / 2, 60);


        app.text("PLAYER NAME: ", app.width / 3, 150);
        String name = state.getPlayerName();
        if(name.equals("")) name = "[ENTER PLAYER NAME HERE]"; //use placeholder for empty name
        createTextBox(name, (int) (app.width * 0.7), 150, 340, 40);
        app.text("SHIP SELECTOR: ", app.width / 3, 230);

        createButton("START", app.width / 2, app.height - 50, 100, 40, Stage.SYSTEM);
    }
}
