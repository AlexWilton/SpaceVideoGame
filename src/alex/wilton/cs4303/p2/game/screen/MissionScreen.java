package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.*;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;


public class MissionScreen extends Screen {

    private Faction sysFaction;
    private Mission mission;

    public MissionScreen(GameState state) {
        super(state);
        sysFaction = state.getPlayerLocation().getFaction();
        mission = state.getPlayersMission();
        if(mission == null){
            mission = Mission.createMission(state);
            state.setPlayersMission(mission);
        }
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());


        app.textSize(30); app.fill(mission.getOriginFaction().getFactionColour().getRGB());
        app.text("MISSION", app.width / 2, 30);
        app.textSize(18);
        app.text("Offered by " + mission.getOriginFaction().name() + " Faction", app.width / 2, 60);

        //todo Replace generic planet logo with specific planet image. Use: currentSystem.drawPlanetInTopRight();
        PlanetLogo.draw();
        DrawableShip ship = state.getPlayerFleet().get(0).createDrawableShipInstance();
        ship.setCenterPosition(new PVector(app.width / 10, 250));
        ship.setOrientation(PConstants.PI / 2);
        ship.draw();

        app.rectMode(App.CORNER);
        app.noFill();
        app.rect((float) (0.2 * app.width), 100, (float) (0.6 * app.width), app.height - 200);

        app.fill(Color.WHITE.getRGB());
        app.textAlign(PConstants.LEFT);
        app.textSize(20);

        app.textAlign(PConstants.LEFT);
        app.fill(sysFaction.getFactionColour().getRGB());
        app.textSize(20);

        switch (mission.getStatus()){
            case OFFERED: showOffer(); break;
            case ACCEPTED: showCurrentMissionInfo(); break;
        }


    }

    private void showCurrentMissionInfo() {

    }

    private void showOffer() {
        String msg = "Captain " + state.getPlayerName() + ",\n";
        msg +=  "There is a system "+mission.getDistance()+" jumps away called " + mission.getTargetSystem().getName() + " which is currently controlled by the "+mission.getTargetFaction()+" Faction." +
                "\n\nWe require you to go to this system, naturalise any enemy presence and hand over control to our faction." +
                "\nReward: "+mission.getReward()+" GC";
        app.text(msg, 10 + app.width / 5, 120, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());
        String additionInfo = "Success will improve relations with " + mission.getOriginFaction().name() + " \nwhile damaging relations with " + mission.getTargetFaction().name();
        app.textAlign(PConstants.CENTER);app.textSize(14);
        app.text(additionInfo, app.width/2, 310);

        createButton("JUMP", (int) (app.width * 0.3), 400, 100, 50, Stage.HYPER_JUMP);
        createButton("REQUEST\nMISSION", (int) (app.width * 0.5), 400, 100, 50, Stage.REQUEST_MISSION);
        createButton("HANGAR", (int) (app.width * 0.7), 400, 100, 50, Stage.HANGAR);
    }

}
