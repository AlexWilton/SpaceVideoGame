package cs4303.p2.game.screen;

import cs4303.p2.game.GameState;
import cs4303.p2.game.Mission;
import cs4303.p2.game.Stage;
import processing.core.PConstants;

import java.awt.*;


public class MissionScreen extends AbstractSystemScreen {


    private Mission mission;

    public MissionScreen(GameState state) {
        super(state);
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
        super.draw();

        if(mission == null){ showNoMissionAvailable(); return;}

        app.textSize(30); app.fill(mission.getOriginFaction().getFactionColour().getRGB());
        app.text("MISSION", app.width / 2, 30);
        app.textSize(18);
        app.text("For " + mission.getOriginFaction().name() + " Faction", app.width / 2, 60);

        app.textAlign(PConstants.LEFT);
        app.fill(mission.getOriginFaction().getFactionColour().getRGB());
        app.textSize(20);

        switch (mission.getStatus()){
            case OFFERED: showOffer(); break;
            case ACCEPTED: showCurrentMissionInfo(); break;
        }


    }

    private void showNoMissionAvailable() {
        app.textSize(30); app.fill(Color.WHITE.getRGB());
        app.text("NO POSSIBLE MISSIONS", app.width / 2, 30);
        app.textSize(18);
        app.textAlign(PConstants.LEFT);
        app.textSize(20);
        String msg = "NO MISSIONS CURRENTLY AVAILABLE!";
        app.text(msg, 10 + app.width / 5, 120, (float) (app.width * 0.6 - 20), 200);
        createButton("SYSTEM SCREEN", (int) (app.width * 0.5), 400, 200, 50, Stage.SYSTEM);
    }


    private void showCurrentMissionInfo() {
        String msg = "STATUS: In Progress" +
                "\n\nBRIEF: Neutralise enemy presence" +
                "\n\nTARGET: "+mission.getTargetSystem().getName() + " (Controlled by "+mission.getTargetFaction().name()+" Faction)" +
                "\n\nREWARD: "+mission.getReward()+" GC";
        app.text(msg, 10 + app.width / 5, 120, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());
        String additionInfo = "Target System is highlighted in green on the Galaxy Map.";
        if(state.getLeadsFaction() == mission.getOriginFaction())
            additionInfo += "\n\nNote: You can freely abandon your own factions missions.";
        else
            additionInfo += "\n\nNote: Abandoning the mission will slightly damage your \nreputation with the " + mission.getOriginFaction().name() + " Faction";
        app.textAlign(PConstants.CENTER);app.textSize(15);
        app.text(additionInfo, app.width/2, 310);

        createButton("SYSTEM SCREEN", (int) (app.width * 0.35), 400, 200, 50, Stage.SYSTEM);
        createButton("ABANDON MISSION", (int) (app.width * 0.65), 400, 200, 50, Stage.ABANDON_MISSION);
    }

    private void showOffer() {
        String msg = "Captain " + state.getPlayerName() + ",\n";
        msg +=  "There is a system "+mission.getDistance()+" jumps away called " + mission.getTargetSystem().getName() + " which is currently controlled by the "+mission.getTargetFaction()+" Faction." +
                "\n\nWe require you to go to this system, neutralise any enemy presence and hand over control to our faction." +
                "\nReward: "+mission.getReward()+" GC";
        app.text(msg, 10 + app.width / 5, 120, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());
        String additionInfo = "Success will improve relations with " + mission.getOriginFaction().name() + " \nwhile damaging relations with " + mission.getTargetFaction().name();
        additionInfo += "\n\nTarget System (" + mission.getTargetSystem().getName() + ") will be highlighted in green on the Galaxy Map.";
        app.textAlign(PConstants.CENTER);app.textSize(14);
        app.text(additionInfo, app.width/2, 310);

        createButton("ACCEPT MISSION", (int) (app.width * 0.35), 400, 200, 50, Stage.MISSION_ACCEPTED);
        createButton("DECLINE MISSION", (int) (app.width * 0.65), 400, 200, 50, Stage.DISCARD_MISSION);
    }

}
