package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.screen.Screen;
import alex.wilton.cs4303.p2.game.ships.playerShip.ShipB;
import processing.core.*;
import processing.data.JSONObject;
import processing.event.KeyEvent;

/**
 * App class represents the Application as a whole.
 * New Game is created and the frame draw and
 * mouse pressed handlers specified.
 */
public class App extends PApplet{
    public static App app; //app object is made globally accessible in order that specific screens can load images into the app
    public static PFont font;
    public App(){app = this;}

    public static final int SCREEN_WIDTH = 960, SCREEN_HEIGHT = 540;
    private static final String SAVED_GAME_FILE_PATH = "savedGame.json";

    private GameState gameState;
    private Screen currentScreen;

    public void setup() {
        frame.setResizable(false);
        size(SCREEN_WIDTH, SCREEN_HEIGHT);
        gameState = GameState.createNewGameState();
        font = App.app.loadFont("fonts/DejaVuSansCondensed-Bold-48.vlw");
        app.textFont(font);
        gameState.setGameStage(Stage.MAIN_MENU);
        gameState.getPlayerFleet().add(new ShipB());
//        gameState.setPlayerName("Alex Testing123");
        gameState.setPlayerCredits(100);
        gameState.setPlayerStanding(Faction.Villt, 0);
        gameState.setPlayerStanding(Faction.Doloe, 0);
        gameState.setPlayerStanding(Faction.Qalz, 0);

    }

    public void draw(){
        currentScreen = gameState.processStageThenGenerateScreen();
        currentScreen.drawScreen();
    }

    public void loadGame(){
        JSONObject jsonState = app.loadJSONObject(SAVED_GAME_FILE_PATH);
        gameState.setGameStage(Stage.LOADING);
        gameState = GameState.parseJson(jsonState);
    }


    public void saveGame() {
        app.saveJSONObject(gameState.asJSONObject(), SAVED_GAME_FILE_PATH);
    }

    public void mousePressed(){ currentScreen.mousePressed();}
    public void keyPressed(KeyEvent e){ currentScreen.keyPressed(e);}
    public void keyReleased(KeyEvent e){ currentScreen.keyReleased(e);}
}