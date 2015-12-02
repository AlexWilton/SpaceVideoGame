package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GalaxySystem;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;


public abstract class AbstractSystemScreen extends Screen {

    protected GalaxySystem system;

    public AbstractSystemScreen(GameState state) {
        super(state);
        system = state.getPlayerLocation();
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());

        createdDisabledButton("Credits: " + state.getPlayerCredits() + " GC", 130,30, 200, 40);

        String factionRepInfo = "Faction's perception of you: " + standingDescription(state.getPlayerStanding(system.getFaction()));
        if(state.getLeadsFaction() == state.getPlayerLocation().getFaction())
            factionRepInfo = "You are the " + state.getLeadsFaction().name() + " Faction Leader.";
        createdDisabledButton( factionRepInfo, app.width/2, 500, (int) (0.6 * app.width), 30);

        system.drawPlanetInTopRight();
        DrawableShip ship = state.getPlayerFleet().get(0).createDrawableShipInstance();
        ship.setCenterPosition(new PVector(app.width/10, 250));
        ship.setOrientation(PConstants.PI/2);
        ship.draw();

        app.rectMode(App.CORNER); app.noFill();
        app.rect((float) (0.2 * app.width), 100, (float) (0.6 * app.width), app.height - 200);
    }

    private String standingDescription(int standing){
        String str = "";
        switch (standing - standing % 10){
            case  0: str =  "Traitor!"; break;
            case  10: str = "Dirt!"; break;
            case  20: str =  "Enemy!"; break;
            case  30: str =  "V. Disliked"; break;
            case  40: str =  "Disliked"; break;
            case  50: str =  "Neutral"; break;
            case  60: str =  "Not Bad"; break;
            case  70: str =  "Positive"; break;
            case  80: str =  "V. Positive!"; break;
            case  90: str =  "V.V. Positive!"; break;
            case  100: str =  "Faction Hero!"; break;
        }
        str += " (" + standing + ")";
        return str;
    }
}
