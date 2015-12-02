package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import processing.core.PConstants;

import java.awt.*;


public class SystemScreen extends AbstractSystemScreen {

    public SystemScreen(GameState state) {
        super(state);
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
            app.fill(system.getFaction().getFactionColour().getRGB());
            app.text("System Controlled by " + system.getFaction().name(), app.width / 2, 60);

        app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT); app.textSize(20);
        app.text("Message from the " + system.getFaction().name() + " Faction:", 10 + app.width / 5, 135);

        boolean doesPlayerHaveNegativeStandingWithOwningFaction = state.getPlayerStanding(system.getFaction()) < 50;
        boolean atMissionTargetSystem = state.getPlayersMission() != null && state.getPlayersMission().getTargetSystem() == system;


        if(atMissionTargetSystem){
            drawMissionTargetTextAndButtons();
        }else {
            if(state.getLeadsFaction() == system.getFaction()) { drawFactionLeaderMsgAndButtons(); return;}
            if(doesPlayerHaveNegativeStandingWithOwningFaction)
                drawPrepareForBattleTextAndButtons();
            else
                drawFriendlyMsgAndButtons();
        }

    }

    private void drawMissionTargetTextAndButtons() {
        app.textAlign(PConstants.LEFT);
        app.fill(system.getFaction().getFactionColour().getRGB());
        app.textSize(20);
        String msg = "So you think think you can take this System from us Captain " + state.getPlayerName() + "?!";
        msg += "\n\nPrepare to FIGHT and DIE.";

        app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());
        createButton("FIGHT", (int) (app.width * 0.5), 400, 100, 50, Stage.FIGHT);
    }

    private void drawPrepareForBattleTextAndButtons() {
        app.textAlign(PConstants.LEFT);
        app.fill(system.getFaction().getFactionColour().getRGB());
        app.textSize(20);
        String msg = "You are trespassing in the  " + system.getName() + " System Captain " + state.getPlayerName() + "!";
        msg += "\n\nPrepare to FIGHT and DIE.";

        app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());
        createButton("FIGHT", (int) (app.width * 0.3), 400, 100, 50, Stage.FIGHT);
        createButton("BRIBE", (int) (app.width * 0.7), 400, 100, 50, Stage.BRIBE);
    }

    private void drawFriendlyMsgAndButtons() {
        app.textAlign(PConstants.LEFT);
        app.fill(system.getFaction().getFactionColour().getRGB());
        app.textSize(20);
        String msg = "Welcome to the " + system.getName() + " System Captain " + state.getPlayerName() + ".";
        msg += "\n\nWe Have no quarrel with you. Our services are available to you (for a charge). You are cleared" +
                " to use the hyper-jump link to jump to nearby systems.";
        if(state.getPlayersMission() == null)
            msg += "\n\nWant to prove yourself! Undertake a mission for us!";

        app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());
        createButton("JUMP", (int) (app.width * 0.3), 400, 100, 50, Stage.HYPER_JUMP);
        String missionBtnText = (state.getPlayersMission() == null) ? "REQUEST\nMISSION" : "CURRENT\nMISSION";
        createButton(missionBtnText, (int) (app.width * 0.5), 400, 100, 50, Stage.MISSION);
        createButton("HANGAR", (int) (app.width * 0.7), 400, 100, 50, Stage.HANGAR);
    }

    private void drawFactionLeaderMsgAndButtons() {
        app.textAlign(PConstants.LEFT);
        app.fill(system.getFaction().getFactionColour().getRGB());
        app.textSize(20);
        String msg = "All Hail our mighty Faction Leader " + state.getPlayerName() + "!";
        msg += "\n\nYou have the full undivided support of the Faction. Make yourself at home.";
        msg += "\n\nIn order to make you The Galactic Ruler, oh benevolent leader,  " +
                "please help us take the remaining systems not yet under our control!";

        app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);
        app.fill(Color.WHITE.getRGB());
        createButton("JUMP", (int) (app.width * 0.3), 400, 100, 50, Stage.HYPER_JUMP);
        String missionBtnText = (state.getPlayersMission() == null) ? "DEMAND\nMISSION" : "CURRENT\nMISSION";
        createButton(missionBtnText, (int) (app.width * 0.5), 400, 100, 50, Stage.MISSION);
        createButton("HANGAR", (int) (app.width * 0.7), 400, 100, 50, Stage.HANGAR);
    }
}
