package cs4303.p2.game.screen;

import cs4303.p2.game.GameState;
import cs4303.p2.game.Mission;
import cs4303.p2.game.Stage;
import processing.core.PConstants;

import java.awt.*;

public class FightWonScreen extends AbstractSystemScreen {
    public FightWonScreen(GameState gameState) {
        super(gameState);
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        super.draw();

        app.textSize(30); app.fill(Color.WHITE.getRGB());
        app.text("SYSTEM BATTLE WON!", app.width / 2, 30);

        Mission mission = state.getPlayersMission();
        boolean atMissionTargetSystem = mission != null && mission.getTargetSystem() == system;
        if(atMissionTargetSystem){
            app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT); app.textSize(20);
            app.text("Message from the " + mission.getOriginFaction().name() + " Faction:", 10 + app.width / 5, 135);

            app.textAlign(PConstants.LEFT);
            app.fill(mission.getOriginFaction().getFactionColour().getRGB());
            app.textSize(20);
            String msg = "Well Done Captain " + state.getPlayerName() + "!";
            msg += "\n\nAs a reward for transferring control of the " + system.getName() + " System, we will transfer " + state.getPlayersMission().getReward() + " credits to you.";
            app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);
        }else if(state.getLeadsFaction() != null){
            app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT); app.textSize(20);
            app.text("Message from your Faction (" + state.getLeadsFaction() + "):", 10 + app.width / 5, 135);
            app.textAlign(PConstants.LEFT);
            app.fill(state.getLeadsFaction().getFactionColour().getRGB());
            app.textSize(20);
            String msg = "All Hail our mighty " + state.getLeadsFaction().name() + " Leader for conquering the " + system.getName() + " System!";
            msg += "\n\nAs a token of gratitude we will transfer you 500 GC for your personal use.";
            app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);

        }else{
            app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT); app.textSize(20);
            app.text("Message from the " + state.getPlayerLocation().getFaction().name() + " Faction:", 10 + app.width / 5, 135);
            app.textAlign(PConstants.LEFT);
            app.fill(system.getFaction().getFactionColour().getRGB());
            app.textSize(20);
            String msg = "You may have won this battle, but we will be back to reclaim the " + system.getName() + " System as soon as you leave!";
            msg += "\n\nAs we no longer have any forces in this system, we cannot stop you from using our hyper-space link.";
            app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);

        }


        app.fill(Color.WHITE.getRGB());
        createButton("System Screen", (int) (app.width * 0.5), 400, 150, 50, Stage.PROCESS_FIGHT_WIN);
    }


}
