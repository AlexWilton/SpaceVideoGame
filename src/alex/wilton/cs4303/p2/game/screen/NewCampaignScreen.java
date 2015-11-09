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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NewCampaignScreen extends Screen{
    private static final int MAX_PLAYER_NAME_LENGTH = 20;
    private static final int MINIMUM_PLAYER_NAME_LENGTH = 2;
    private ShipSelector shipSelector;

    public NewCampaignScreen(GameState state) {
        super(state);
        shipSelector = state.getGameSetupShipSelector();
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());
        PlanetLogo.draw();

        app.textSize(50);  app.fill(Color.WHITE.getRGB()); app.noFill();
        app.text("NEW GAME", app.width / 2, 50);
        app.textSize(25);
        app.text("PLAYER NAME: ", app.width / 3, 150);
        String name = state.getPlayerName();
        if(name.equals("")) name = "[ENTER PLAYER NAME HERE]"; //use placeholder for empty name
        createTextBox(name, (int) (app.width * 0.7), 150, 340, 40);
        app.text("SHIP SELECTOR: ", app.width / 3, 230);

        shipSelector.draw();

        if(state.getPlayerName().length() >= MINIMUM_PLAYER_NAME_LENGTH)
            createButton("START", app.width / 2, app.height - 50, 100, 40, Stage.SYSTEM);
    }

    /**
     * Listen for mouse click on a ship (as well as usual listening for clicks on screen buttons)
     */
    @Override
    public void mousePressed() {
        super.mousePressed();
        shipSelector.mousePressed();
    }


    /**
     * Respond to key press events
     * @param e Key Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        String playerName = state.getPlayerName();

        /* Allow letters or digits or spaces to be used for forming player name */
        if(Character.isAlphabetic(e.getKey()) || e.getKey() == ' ' || Character.isDigit(e.getKey())){
            if(playerName.length() >= MAX_PLAYER_NAME_LENGTH) return;
            state.setPlayerName(playerName + e.getKey());
        }

        /* Allow backspace for deleting characters from name */
        if(e.getKeyCode() == App.BACKSPACE){
            if(playerName.length() > 0){
                state.setPlayerName(playerName.substring(0, playerName.length()-1));
            }
        }

        /* Move right or left when respective arrow key is pressed */
        if(e.getKeyCode() == App.LEFT) shipSelector.shiftSelectorLeft();
        if(e.getKeyCode() == App.RIGHT) shipSelector.shiftSelectorRight();


        /* Allow progression to next stage when enter/return is hit and minimum player name is reached */
        if((e.getKey() == App.ENTER || e.getKey() == App.RETURN) && playerName.length() >= MINIMUM_PLAYER_NAME_LENGTH){
            state.setGameStage(Stage.SYSTEM);
        }
    }
}
