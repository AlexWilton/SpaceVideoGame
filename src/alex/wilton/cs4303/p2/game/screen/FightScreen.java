package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.*;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.awt.*;
import java.util.ArrayList;


public class FightScreen extends Screen {

    private final FightState fState;
    private final DrawableShip ship;
    private final ArrayList<DrawableShip> enemies;

    public static final int mapRadius = 1200;

    public FightScreen(GameState state) {
        super(state);
        fState = state.getFightState();
        ship = fState.player;
        enemies = fState.enemies;
    }

    /**
     * Draw Screen Elements
     */
    @Override
    protected void draw() {
        app.fill(Color.WHITE.getRGB());
        app.text("Camera Location: (" + (int)fState.cameraLocation.x + "," + (int)fState.cameraLocation.y + ")", 100, 20 );
        app.text("SHIP Location: (" + (int)ship.getCenterPosition().x + "," + (int)ship.getCenterPosition().y + ")", 100, 50 );
        updateCameraPosition();
        app.translate(-fState.cameraLocation.x, -fState.cameraLocation.y);

        drawNonShipObjects();
        ship.draw();
        for(DrawableShip enemyShip : enemies) enemyShip.draw();
        fState.checkForCollision();
        checkForEndFight();

        app.translate(fState.cameraLocation.x, fState.cameraLocation.y);

        app.fill(Color.white.getRGB());
        app.text("FPS: " + (int)app.frameRate, app.width/2, 30);
    }

    private void checkForEndFight() {
        boolean playerWins = true;
        for(DrawableShip enemy : enemies) if(!enemy.isExploded()) playerWins = false;
        if(playerWins) state.setGameStage(Stage.FIGHT_WON);
    }

    private void drawNonShipObjects() {

        app.background(Color.gray.getRGB());
        app.fill(Color.BLACK.getRGB());
        app.ellipse(0, 0, mapRadius, mapRadius);

        /* Grid Lines*/
        app.strokeWeight(1);
        app.stroke(Color.GRAY.getRGB());
        int gridSpacing = 400;
        for (int i = -mapRadius; i < mapRadius+gridSpacing; i+=gridSpacing) {
            app.line(i, -mapRadius, i, mapRadius);
        }
        for (int i = -mapRadius; i < mapRadius+gridSpacing; i+=gridSpacing) {
            app.line(-mapRadius, i, mapRadius, i);
        }

        /* Planet */
        state.getPlayerLocation().drawPlanetInCenterForFightScreen();


    }

    private void updateCameraPosition() {
        PVector shipLoc = ship.getCenterPosition();
        PVector camLoc = fState.cameraLocation;

        //check for shift right
        if( shipLoc.x > (camLoc.x + app.width * 0.7))
            camLoc.x = (float) (shipLoc.x - app.width * 0.7);

        //check for shift left
        if( shipLoc.x < (camLoc.x + app.width * 0.3))
            camLoc.x = (float) (shipLoc.x - app.width * 0.3);

        //check for shift up
        if( shipLoc.y < (camLoc.y + app.height * 0.3))
            camLoc.y = (float) (shipLoc.y - app.height * 0.3);

        //check for shift down
        if( shipLoc.y > (camLoc.y + app.height * 0.7))
            camLoc.y = (float) (shipLoc.y  - app.height * 0.7);

    }


    @Override
    public void mousePressed() {
        super.mousePressed();
    }

    /**
     * Respond to key press events
     * @param e Key Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);

        switch (e.getKeyCode()){
            case App.UP: ship.accelerate(0.1f); break;
            case App.DOWN: ship.brake(); break;
            case App.RIGHT: ship.turnRight(); break;
            case App.LEFT: ship.turnLeft(); break;
        }
        switch (e.getKey()){
            case 'A':
            case 'a':
                ship.leftThrust();
                break;
            case 'D':
            case 'd':
                ship.rightThrust();
                break;
            case 'W':
            case 'w':
                ship.accelerate(0.1f); break;
            case 'S':
            case 's':
                ship.brake(); break;
            case ' ':
                ship.fireWeapon(); break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e){
        super.keyReleased(e);

        switch (e.getKeyCode()){
            case App.UP:
            case App.DOWN:
                ship.setAcceleration(new PVector(0,0));
                break;
            case App.LEFT:
            case App.RIGHT:
                ship.stopTurning();
                break;
        }
        switch (e.getKey()){
            case 'A':
            case 'a':
            case 'D':
            case 'd':
                ship.stopSideThrust();
                break;
            case 'W': case 'w':
            case 'S': case 's':
                ship.setAcceleration(new PVector(0,0)); break;
            case ' ':
                ship.stopFiringWeapon(); break;
        }
    }

}
