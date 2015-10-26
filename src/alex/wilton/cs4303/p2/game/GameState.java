package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.screen.CustomPlayScreen;
import alex.wilton.cs4303.p2.game.screen.MainMenuScreen;
import alex.wilton.cs4303.p2.game.screen.Screen;
import alex.wilton.cs4303.p2.game.screen.UnImplementedScreen;

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

    public GameState(){
        galaxy = new Galaxy();
        playerFleet = new ArrayList<>();
        playerName = "";
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
            case CUSTOM_PLAY: return new CustomPlayScreen(this);
            //... generate relevant screen for each stage

            default: return new UnImplementedScreen(this);
        }
    }

    public void setGameStage(Stage gameStage){
        this.gameStage = gameStage;
    }
}