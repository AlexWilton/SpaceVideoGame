package cs4303.p2.game.screen;

import cs4303.p2.game.App;
import cs4303.p2.game.Faction;
import cs4303.p2.game.GameState;
import cs4303.p2.game.Stage;
import processing.core.PConstants;
import processing.event.KeyEvent;

import java.awt.*;


public class BribeScreen extends AbstractSystemScreen {

    private final Faction faction;
    private final int bribeAmount, playerCredits;

    public BribeScreen(GameState state) {
        super(state);
        faction = state.getPlayerLocation().getFaction();
        bribeAmount = state.getBribeAmount();
        playerCredits = state.getPlayerCredits();
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        super.draw();

        app.textSize(30); app.fill(Color.WHITE.getRGB());
        app.text(system.getName().toUpperCase() + " SYSTEM", app.width / 2, 30);
        app.textSize(25);
        app.fill(faction.getFactionColour().getRGB());
        app.text("System Controlled by " + system.getFaction().name(), app.width / 2, 60);


        app.textAlign(PConstants.LEFT);
        app.fill(faction.getFactionColour().getRGB());
        app.textSize(20);

        String msg = "What do you want Captain " + state.getPlayerName() + "?";
        app.text(msg, 10 + app.width / 5, 120, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());

        String additionInfo = "Attempt to bribe " + faction.name() + " official with:";
        app.text(additionInfo, 10 + app.width / 5, 180);

        app.textAlign(PConstants.CENTER);
        createdDisabledButton(bribeAmount + "", (app.width / 3), 230, 100, 40);


        createButton("OFFER BRIBE", (int) (app.width * 0.35), 400, 200, 50, Stage.OFFER_BRIBE);
        createButton("BACK", (int) (app.width * 0.65), 400, 200, 50, Stage.SYSTEM);

    }

    /**
     * Respond to key press events
     * @param e Key Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        /* Allow Digits to append to amount */
        if(Character.isDigit(e.getKey())){
            int newBribeAmount = bribeAmount * 10 + (e.getKey() - '0');
            if(newBribeAmount > playerCredits) return;
            state.setBribeAmount(newBribeAmount);
        }

        /* Allow backspace for deleting characters from name */
        if(e.getKeyCode() == App.BACKSPACE){
            int newBribeAmount;
            if(bribeAmount < 10)
                newBribeAmount = 0;
            else
                newBribeAmount = Integer.parseInt((bribeAmount + "").substring(0, (bribeAmount + "").length() - 1));
            state.setBribeAmount(newBribeAmount);
        }

        /* Move right or left when respective arrow key is pressed */
        if(e.getKeyCode() == App.LEFT || e.getKeyCode() == App.DOWN){
            int newBribeAmount = bribeAmount - 10;
            if(newBribeAmount < 0) newBribeAmount = 0;
            state.setBribeAmount(newBribeAmount);
        }
        if(e.getKeyCode() == App.RIGHT || e.getKeyCode() == App.UP){
            int newBribeAmount = bribeAmount + 10;
            if(newBribeAmount > playerCredits) newBribeAmount = playerCredits;
            state.setBribeAmount(newBribeAmount);
        }


        /* Allow progression to next stage when enter/return is hit and minimum player name is reached */
        if((e.getKey() == App.ENTER || e.getKey() == App.RETURN)){
            state.setGameStage(Stage.OFFER_BRIBE);
        }
    }


}
