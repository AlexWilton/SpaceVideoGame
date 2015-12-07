package cs4303.p2.game.screen;

import cs4303.p2.game.*;
import cs4303.p2.game.drawable.DrawableObject;
import cs4303.p2.game.drawable.DrawableShip;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.awt.*;
import java.util.*;
import java.util.List;


public class FightScreen extends Screen {

    private final FightState fState;
    private final DrawableShip ship;
    private final ArrayList<DrawableShip> enemies;

    public static final int mapRadius = 1500;

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
        ship.drawAndUpdateMissiles();
        for(DrawableShip enemyShip : enemies) enemyShip.draw();
        fState.checkForCollision();
        state.getPlayerLocation().drawPlanetInCenterForFightScreen();
        checkForEndFight();

        app.translate(fState.cameraLocation.x, fState.cameraLocation.y);

        app.fill(Color.white.getRGB()); app.textSize(18);
        app.text("MISSILES: " + fState.player.getShip().getMissiles() + "   HULL HEALTH: " + fState.player.getShip().getHull()
                +"   LASER: " + ((ship.getLaserRechargeTime() == 0) ? "READY" : "CHARGING ("+(ship.getLaserRechargeTime()/60)+")")
                , app.width/2, 30);
    }

    private void checkForEndFight() {
        boolean playerWins = true;
        for(DrawableShip enemy : enemies) if(!enemy.isExploded()) playerWins = false;
        if(playerWins) state.setGameStage(Stage.FIGHT_WON);

        if(ship.getShip().getHull() <= 0) state.setGameStage(Stage.GAME_LOST);
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
            case App.UP:    ship.accelerate(); break;
            case App.DOWN: ship.accelarateBackwards(); break;
            case App.RIGHT: ship.turnRight(); break;
            case App.LEFT: ship.turnLeft(); break;
            case App.ENTER:case App.RETURN:
                ship.fireMissile(); break;
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
                ship.accelerate();
                break;
            case 'S':
            case 's':
                ship.accelarateBackwards(); break;
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
                ship.stopAcceleration();
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
                ship.stopAcceleration(); break;
            case ' ':
                ship.stopFiringWeapon(); break;
        }
    }

    private static DrawableObject disallowedArea = null;
    public static DrawableObject getDisallowedArea() {
        if (disallowedArea == null) {
            disallowedArea = new DrawableObject() {
                public boolean impulseAwayFromCenter(){
                    return false;
                }
                @Override
                public List<PVector> getCircumferencePts() {
                    ArrayList<PVector> pts = new ArrayList<>();
                    for (int deg = 0; deg < 360; deg++) {
                        float rads = App.radians(deg);
                        pts.add(new PVector(mapRadius * App.cos(rads), mapRadius * App.sin(rads)));
                    }
                    return pts;
                }

                @Override
                public boolean containsPt(PVector pt) {
                    return App.dist(0, 0, pt.x, pt.y) > mapRadius/2;
                }

                @Override
                public PVector getCenterPosition() {
                    return new PVector(0,0);
                }

            };
        }
        return disallowedArea;
    }
}
