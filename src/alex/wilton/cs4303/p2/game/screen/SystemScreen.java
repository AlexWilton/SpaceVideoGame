package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GalaxySystem;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;
import processing.core.PConstants;

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


        app.textSize(30); app.fill(Color.WHITE.getRGB());
        app.text(system.getName().toUpperCase() + " SYSTEM", app.width / 2, 30);
        app.textSize(25);
        app.fill(system.getFaction().getFactionColour().getRGB());
        app.text("System Controlled by " + system.getFaction().name(), app.width / 2, 60);

        //todo Replace generic planet logo with specific planet image. Use: system.drawPlanetInTopRight();
        PlanetLogo.draw();

        app.rectMode(App.CORNER); app.noFill();
        app.rect((float) (0.2 * app.width), 100, (float) (0.6 * app.width), app.height - 200);

        app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT); app.textSize(20);
        app.text("Message from the " + system.getFaction().name() + " Faction:", 10 + app.width / 5, 135);

        boolean doesPlayerHaveNegativeStandingWithOwningFaction = state.getPlayerStanding(system.getFaction()) < 50;
//        if(doesPlayerHaveNegativeStandingWithOwningFaction)
            drawPrepareForBattleTextAndButtons();
//        else
//            drawFriendlyMsgAndButtons();

    }

    private void drawPrepareForBattleTextAndButtons() {
        app.textAlign(PConstants.LEFT);
        app.fill(system.getFaction().getFactionColour().getRGB());
        app.textSize(20);
        String msg = "You are trespassing in the  " + system.getName() + " System Captain " + state.getPlayerName() + "!";
        msg += "\n\nPrepare to FIGHT and DIE.";
        app.text(msg, 10 + app.width/5, 150, (float) (app.width * 0.6 - 20), 200);
        createButton("FIGHT", (int) (app.width * 0.3), 400, 100, 50, Stage.FIGHT);
        createButton("BRIBE", (int) (app.width * 0.7), 400, 100, 50, Stage.BRIBE);
    }

    private void drawFriendlyMsgAndButtons() {

    }
}
