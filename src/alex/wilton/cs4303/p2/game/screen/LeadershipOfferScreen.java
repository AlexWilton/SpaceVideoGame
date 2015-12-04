package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.Faction;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Mission;
import alex.wilton.cs4303.p2.game.Stage;
import processing.core.PConstants;

import java.awt.*;

public class LeadershipOfferScreen extends AbstractSystemScreen {
    private Faction faction;
    public LeadershipOfferScreen(GameState gameState) {
        super(gameState);
        faction = state.getPlayerLocation().getFaction();
        if(state.getPlayerStanding(faction) != 100)
            state.setGameStage(Stage.SYSTEM);
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        super.draw();

        app.textSize(30); app.fill(Color.WHITE.getRGB());
        app.text("Urgent Communication!", app.width / 2, 30);

        app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT); app.textSize(20);
        app.text("Message from the " + faction.name() + " Faction:", 10 + app.width / 5, 135);

        app.textAlign(PConstants.LEFT);
        app.fill(faction.getFactionColour().getRGB());
        app.textSize(20);
        String msg = "Well Done Captain " + state.getPlayerName() + "! ";
        msg += "You have continued to impress us! Your dedication to our faction is irrefutable!" +
                "\n\nWe would like to extend to you the offer our being our Faction Leader." +
                "\n\nWould you accept this honour?";
        app.text(msg, 10 + app.width / 5, 150, (float) (app.width * 0.6 - 20), 200);

        app.fill(Color.WHITE.getRGB());
        createButton("LEAD FACTION", (int) (app.width * 0.3), 400, 150, 50, Stage.BECOME_LEADER);
        createButton("DECLINE OFFER", (int) (app.width * 0.7), 400, 150, 50, Stage.REJECT_LEADERSHIP_OFFER);
    }


}
