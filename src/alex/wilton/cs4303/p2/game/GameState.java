package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.screen.*;
import alex.wilton.cs4303.p2.game.ships.Ship;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for representing the entire state of the game
 */
public class GameState{
    private Galaxy galaxy;
    private List<Ship> playerFleet;
    private String playerName;
    private int numberOfGalacticCredits;
    private Faction leadsFaction; //null if leads no faction
    private Stage gameStage;

    /* 0 rep means most hated enemy, 50 means neutral, 100 means faction couldn't love the player more */
    private int reputationWithVillt;
    private int reputationWithQalz;
    private int reputationWithDoleo;
    private Stage stage;

    public GameState(){
        galaxy = new Galaxy();
        playerFleet = new ArrayList<>();
        playerName = "[ENTER PLAYER NAME HERE]";
        numberOfGalacticCredits = 0;
        leadsFaction = null;
        reputationWithVillt = 50;
        reputationWithQalz = 50;
        reputationWithDoleo = 50;
        gameStage = Stage.MAIN_MENU;
    }



    public Screen generateScreen(){
        switch(gameStage){
            case MAIN_MENU: return new MainMenuScreen(this);
            case CAMPAIGN: return new CampaignScreen(this);
            case CUSTOM_PLAY: return new CustomPlayScreen(this);
            case NEW_CAMPAIGN: return new NewCampaignScreen(this);
            //... generate relevant screen for each stage
            case EXIT_GAME: System.exit(0);
            default: return new UnImplementedScreen(gameStage.name(), this);
        }
    }

    public void setGameStage(Stage gameStage){
        this.gameStage = gameStage;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Stage getStage() {
        return stage;
    }
}