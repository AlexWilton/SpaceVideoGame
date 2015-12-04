package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.aiPilots.MoveTowardAndShootPilot;
import alex.wilton.cs4303.p2.game.entity.staticImage.Planet;
import alex.wilton.cs4303.p2.game.screen.FightScreen;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipA;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipD;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipA;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipD;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipA;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipD;
import processing.core.PVector;

import java.util.ArrayList;

public class FightState {
    private App app = App.app;
    public final GalaxySystem location;

    public final DrawableShip player;
    public final ArrayList<DrawableShip> enemies;

    public PVector cameraLocation = new PVector(0,0);


    public FightState(GalaxySystem location, DrawableShip player, ArrayList<DrawableShip> enemies) {
        this.location = location;
        this.player = player;
        this.enemies = enemies;
    }


    public void checkForCollision(){

        //check for enemy taking damage
        for(DrawableShip enemy : enemies){
            boolean collision = false;
            for(PVector weaponDamagePt : player.getWeapaonDamagePts()){
                if(enemy.containsPt(weaponDamagePt)) { collision = true; break; }
            }
            if(collision) {
               enemy.takeDamage(5);
            }
        }

        for(PVector pt : player.getCircumferencePts()) app.ellipse(pt.x, pt.y, 3, 3);
        Planet planet = location.getPlanet();
        for(PVector pt : planet.getCircumferencePts()) app.ellipse(pt.x, pt.y, 3, 3);

        // prevent object (e.g. ships and planet) collision.
        ArrayList<DrawableObject> objects = new ArrayList<>(); objects.addAll(enemies); objects.add(player); objects.add(planet);
        for(DrawableObject objA : objects){
            if(!(objA instanceof DrawableShip)) continue;
            for(PVector ptAroundA : objA.getCircumferencePts()) {
                for (DrawableObject objB : objects) {
                    if(objA == objB) continue;
                    if(objB.containsPt(ptAroundA)){
                        ((DrawableShip) objA).impulseAwayFrom(objB.getCenterPosition());
                        return;
                    }

                }
            }
        }

    }

    public static FightState setupFight(GameState state){
        ArrayList<DrawableShip> enemies = new ArrayList<>();
        DrawableShip enemy = null;
        switch (state.getPlayerLocation().getFaction()){
            case Doloe: enemy = new DoloeShipD().createDrawableShipInstance(); break;
            case Qalz:  enemy = new QalzShipD().createDrawableShipInstance(); break;
            case Villt: enemy = new VilltShipD().createDrawableShipInstance(); break;
        }

        enemy.setCenterPosition(getRandomPosition());
        enemies.add(enemy);

        DrawableShip playerShip = state.getPlayerFleet().get(0).createDrawableShipInstance();
        playerShip.setCenterPosition(getRandomPosition());

        FightState fightState = new FightState(state.getPlayerLocation(), playerShip, enemies);
        enemy.assignAiPilot(new MoveTowardAndShootPilot(enemy, fightState));

        return fightState;
    }

    private static PVector getRandomPosition() {
        float x,y, distanceToPlanet;
        do{
            x = (float) Math.random() * FightScreen.mapRadius/2;
            y = (float) Math.random() * FightScreen.mapRadius/2;
            if(Math.random() < 0.5) x *= -1;
            if(Math.random() < 0.5) y *= -1;
            distanceToPlanet = App.dist(0,0,x,y);
        }while( !(distanceToPlanet < FightScreen.mapRadius/2 - 100 && distanceToPlanet > 150));
        return new PVector(x,y);
    }


}
