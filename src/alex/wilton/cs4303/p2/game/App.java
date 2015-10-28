package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.screen.Screen;
import processing.core.*;
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

    private GameState gameState;
    private Screen currentScreen;

    public void setup() {
        frame.setResizable(true);
        size(SCREEN_WIDTH,SCREEN_HEIGHT);
        gameState = new GameState();
        font = App.app.loadFont("fonts/DejaVuSansCondensed-Bold-48.vlw");
        app.textFont(font);
        gameState.setGameStage(Stage.NEW_CAMPAIGN);
    }

    public void draw(){
        currentScreen = gameState.generateScreen();
        currentScreen.drawScreen();
    }

    public void mousePressed(){ currentScreen.mousePressed();}
    public void keyPressed(KeyEvent e){ currentScreen.keyPressed(e);}

}