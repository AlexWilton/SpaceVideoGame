package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.entity.Planet;
import processing.core.*;

/**
 * App class represents the Application as a whole.
 * New Game is created and the frame draw and
 * mouse pressed handlers specified.
 */
public class App extends PApplet{
    /**
     * app object is made globally accessible in order that specific screens can load images into the app
     */
    public static App app;
    public static int WINDOW_WIDTH, WINDOW_HEIGHT;

    public App(){app = this; WINDOW_WIDTH = app.width; WINDOW_HEIGHT = app.height;}

    private GameModel gameModel;

    public void setup() {
        double scalor = 0.8;
        size((int) (displayWidth * scalor), (int)(displayHeight * scalor));
        int numberOfCities = 4;
        Planet planet = new Planet(numberOfCities);
        gameModel = new GameModel(planet);
    }


    public void draw() {
        gameModel.draw();
    }

    public void mousePressed() {
        gameModel.mousePressed();
    }


}