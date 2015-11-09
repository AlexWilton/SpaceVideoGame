package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.screen.*;
import alex.wilton.cs4303.p2.game.ships.Ship;

import alex.wilton.cs4303.p2.util.JSONconvertable;
import alex.wilton.cs4303.p2.util.ShipSelector;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

/**
 * Class for representing the entire state of the game
 */
public class GameState implements JSONconvertable {
    private Galaxy galaxy;
    private GalaxySystem playerLocation;
    private ArrayList<Ship> playerFleet;
    private String playerName;
    private int numberOfGalacticCredits;
    private Faction leadsFaction; //null if leads no faction
    private Stage gameStage;

    /* 0 rep means most hated enemy, 50 means neutral, 100 means faction couldn't love the player more */
    private int reputationWithVillt;
    private int reputationWithQalz;
    private int reputationWithDoleo;

    private boolean isGameSetupCompleted;
    private ShipSelector gameSetupShipSelector;
    private GalaxySystem destinationSystem; //used for selecting a target system in a hyper-space jump


    public GameState(Galaxy galaxy, GalaxySystem playerLocation, ArrayList<Ship> playerFleet, String playerName, int numberOfGalacticCredits, Faction leadsFaction, Stage gameStage, int reputationWithVillt, int reputationWithQalz, int reputationWithDoleo, boolean isGameSetupCompleted, ShipSelector gameSetupShipSelector) {
        this.galaxy = galaxy;
        this.playerLocation = playerLocation;
        this.playerFleet = playerFleet;
        this.playerName = playerName;
        this.numberOfGalacticCredits = numberOfGalacticCredits;
        this.leadsFaction = leadsFaction;
        this.gameStage = gameStage;
        this.reputationWithVillt = reputationWithVillt;
        this.reputationWithQalz = reputationWithQalz;
        this.reputationWithDoleo = reputationWithDoleo;
        this.isGameSetupCompleted = isGameSetupCompleted;
        this.gameSetupShipSelector = gameSetupShipSelector;
    }



    public static GameState createNewGameState(){
        Galaxy galaxy = Galaxy.createRandomGalaxy();
        return new GameState(
        galaxy,
        galaxy.selectRandomSystem(),
        new ArrayList<Ship>(),
        "", //empty name
        0, //no credits
        null, //leads no faction
        Stage.MAIN_MENU, //begin with main menu
        50, //neutral Villt reputation
        50, //neutral Qalz reputation
        50, //netural Dol'eo reputation
        false, //game not yet set up
        ShipSelector.createDefaultSelector()
        );
    }

    /**
     * For the current frame and game state, process stage then generate appropriate Screen
     * @return
     */
    public Screen processStageThenGenerateScreen(){
        switch(gameStage){
            case MAIN_MENU:     return new MainMenuScreen(this);
            case CAMPAIGN:      return new CampaignScreen(this);
            case CUSTOM_PLAY:   return new CustomPlayScreen(this);
            case NEW_CAMPAIGN:  return new NewCampaignScreen(this);
            case HYPER_JUMP:    return new HyperJumpScreen(this);
            case SYSTEM:
                                if(!isGameSetupCompleted){ setupGame(); break;}
                                return new SystemScreen(this);

            //... generate relevant screen for each stage

            case LOAD_SAVED_GAME: App.app.loadGame(); break;
            case GOTO_GC_WEBSITE:
                App.app.link("http://galacticconquests.com/");
                gameStage = Stage.MAIN_MENU;
                break;
            case MAKE_JUMP:
                playerLocation = destinationSystem;
                destinationSystem = null;
                gameStage = Stage.SYSTEM;
                break;
            case EXIT_GAME: System.exit(0);
            default: return new UnImplementedScreen(gameStage.name(), this);
        }

        return processStageThenGenerateScreen();
    }

    private void setupGame() {
        playerFleet.add(gameSetupShipSelector.getSelectedShip());
        playerLocation = galaxy.selectRandomSystem();
        isGameSetupCompleted = true;
        setGameStage(Stage.SYSTEM);
    }



    public void setGameStage(Stage gameStage){
        this.gameStage = gameStage;

        if(gameStage == Stage.SYSTEM) App.app.saveGame();
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ShipSelector getGameSetupShipSelector() {
        return gameSetupShipSelector;
    }


    public JSONObject asJSONObject() {
        JSONObject state = new JSONObject();
        state.setJSONObject("galaxy", galaxy.asJSONObject());
        state.setJSONObject("playerLocation", playerLocation.asJSONObject());
        JSONArray fleetArray = new JSONArray();
        for(Ship ship : playerFleet) fleetArray.append(ship.asJsonObject());
        state.setJSONArray("playerFleet", fleetArray);
        state.setString("playerName", playerName);
        state.setInt("numberOfGalacticCredits", numberOfGalacticCredits);
        state.setString("leadsFaction", (leadsFaction == null) ? "NONE" : leadsFaction.name());
        state.setString("gameStage", gameStage.name());
        state.setInt("reputationWithVillt", reputationWithVillt);
        state.setInt("reputationWithQalz", reputationWithQalz);
        state.setInt("reputationWithDoleo", reputationWithDoleo);
        state.setBoolean("isGameSetupCompleted", isGameSetupCompleted);
        state.setJSONObject("gameSetupShipSelector", gameSetupShipSelector.asJSONObject());
        return state;
    }

    public static GameState parseJson(JSONObject jsonState) {
//        Galaxy galaxy = Galaxy.parseJson(jsonState.getString("galaxy"));
//        GalaxySystem playerLocation = GalaxySystem.parseJson(jsonState.getString("playerLocation"));
//        ArrayList<Ship> playerFleet = new ArrayList<>();
//        return new GameState(galaxy, playerLocation, playerFleet);
        GameState gameState = createNewGameState(); //replace with parsed game state
        gameState.isGameSetupCompleted = true;
        return createNewGameState();
    }

    public GalaxySystem getPlayerLocation() {
        return playerLocation;
    }

    public int getPlayerStanding(Faction faction) {
        switch (faction){
            case Doloe: return reputationWithDoleo;
            case Qalz: return reputationWithQalz;
            case Villt: return reputationWithVillt;
            default: return -1; //faction doesn't exist
        }
    }


    public ArrayList<Ship> getPlayerFleet() {
        return playerFleet;
    }

    public Galaxy getGalaxy() {
        return galaxy;
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public void setDestinationSystem(GalaxySystem destinationSystem) {
        this.destinationSystem = destinationSystem;
    }

    public GalaxySystem getDestinationSystem() {
        return destinationSystem;
    }
}