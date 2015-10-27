package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.ships.Ship;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;
import alex.wilton.cs4303.p2.game.ships.playerShip.*;
import alex.wilton.cs4303.p2.util.ShipSelector;
import processing.event.KeyEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class NewCampaignScreen extends Screen{
    private static final int MAX_PLAYER_NAME_LENGTH = 20;

    public NewCampaignScreen(GameState state) {
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
        app.text("NEW GAME", app.width / 2, 50);
        app.textSize(25);
        app.text("PLAYER NAME: ", app.width / 3, 150);
        createTextBox(state.getPlayerName(), (int) (app.width * 0.7), 150, 340, 40);
        app.text("SHIP SELECTOR: ", app.width / 3, 230);

        List<? extends Ship> shipOptions = Arrays.asList(new ShipA(), new ShipB(), new ShipC(), new ShipD(), new ShipE());
        new ShipSelector(shipOptions, app.width/16, 250, app.width * 7 / 8, app.height-350);

        createButton("START", app.width / 2, app.height - 50, 100, 40, Stage.SYSTEM);
    }

    /**
     * Respond to key press events
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        String playerName = state.getPlayerName();
        if(playerName.equals("[ENTER PLAYER NAME HERE]")) playerName = "";
        if(Character.isAlphabetic(e.getKey()) || e.getKey() == ' ' || Character.isDigit(e.getKey())){
            if(playerName.length() >= MAX_PLAYER_NAME_LENGTH) return;
            state.setPlayerName(playerName + e.getKey());
        }

        if(e.getKeyCode() == App.BACKSPACE){
            if(playerName.length() > 0){
                state.setPlayerName(playerName.substring(0, playerName.length()-1));
            }
        }
    }
}
