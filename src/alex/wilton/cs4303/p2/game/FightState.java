package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.aiPilots.MoveTowardAndShootPilot;
import alex.wilton.cs4303.p2.game.entity.staticImage.Planet;
import alex.wilton.cs4303.p2.game.screen.FightScreen;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipA;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipB;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipC;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipD;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipA;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipB;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipC;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipD;
import alex.wilton.cs4303.p2.game.ships.Ship;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipA;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipB;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipC;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipD;
import alex.wilton.cs4303.p2.game.ships.playerShip.PlayerShip;
import processing.core.PVector;
import processing.data.JSONObject;

import java.util.ArrayList;

public class FightState {
    public final GalaxySystem location;

    public final DrawableShip player;
    private JSONObject shipBefore;
    public final ArrayList<DrawableShip> enemies;

    public PVector cameraLocation = new PVector(0,0);


    public FightState(GalaxySystem location, DrawableShip player, ArrayList<DrawableShip> enemies) {
        this.location = location;
        this.player = player;
        this.enemies = enemies;
        shipBefore = player.getShip().asJsonObject();
    }


    public void checkForCollision(){

        for(DrawableShip enemy : enemies){

            //check for enemy taking damage
            for(PVector weaponDamagePt : player.getWeapaonDamagePts()){
                if(enemy.containsPt(weaponDamagePt)) {
                    enemy.takeDamage(5);
                    break;
                }
            }

            //check for player taking damage
            for(PVector weaponDamagePtFromEnemy : enemy.getWeapaonDamagePts()){
                if(player.containsPt(weaponDamagePtFromEnemy)){
                    player.takeDamage(5);
                    break;
                }
            }

        }

        // prevent object (e.g. ships and planet) collision.
        ArrayList<DrawableObject> objects = new ArrayList<>();
        objects.addAll(enemies);
        objects.add(player);
        objects.add(location.getPlanet());
        objects.add(FightScreen.getDisallowedArea());

        for(DrawableObject objA : objects){
            if(!(objA instanceof DrawableShip)) continue;
            for(PVector ptAroundA : objA.getCircumferencePts()) {
                for (DrawableObject objB : objects) {
                    if(objA == objB) continue;
                    if(objB.containsPt(ptAroundA)){
                        ((DrawableShip) objA).impulseAwayFrom(objB.getCenterPosition(), objB.impulseAwayFromCenter());
                        return;
                    }

                }
            }
        }

    }


    private static ArrayList<DrawableShip> setupEnemies(Faction faction, int battleType){
        ArrayList<DrawableShip> enemies = new ArrayList<>();

        for(int i=0; i<4-battleType; i++) {
            DrawableShip enemy = null;
            switch (faction) {
                case Doloe:
                    switch (battleType){
                        case 0 : enemy = new DoloeShipA().createDrawableShipInstance(); break;
                        case 1 : enemy = new DoloeShipB().createDrawableShipInstance(); break;
                        case 2 : enemy = new DoloeShipC().createDrawableShipInstance(); break;
                        case 3 : enemy = new DoloeShipD().createDrawableShipInstance(); break;
                    }
                    break;
                case Qalz:
                    switch (battleType){
                        case 0 : enemy = new QalzShipA().createDrawableShipInstance(); break;
                        case 1 : enemy = new QalzShipB().createDrawableShipInstance(); break;
                        case 2 : enemy = new QalzShipC().createDrawableShipInstance(); break;
                        case 3 : enemy = new QalzShipD().createDrawableShipInstance(); break;
                    }
                    break;
                case Villt:
                    switch (battleType){
                        case 0 : enemy = new VilltShipA().createDrawableShipInstance(); break;
                        case 1 : enemy = new VilltShipB().createDrawableShipInstance(); break;
                        case 2 : enemy = new VilltShipC().createDrawableShipInstance(); break;
                        case 3 : enemy = new VilltShipD().createDrawableShipInstance(); break;
                    }
                    break;
            }
            enemy.setCenterPosition(getRandomPosition());
            enemies.add(enemy);
        }
        return enemies;
    }

    public static FightState setupFight(GameState state){
        ArrayList<DrawableShip> enemies = setupEnemies(state.getPlayerLocation().getFaction(), state.getPlayerLocation().getId() % 4);

        DrawableShip playerShip = state.getPlayerShip().createDrawableShipInstance();
        playerShip.setCenterPosition(getRandomPosition());

        FightState fightState = new FightState(state.getPlayerLocation(), playerShip, enemies);
        for(DrawableShip enemy : enemies) enemy.assignAiPilot(new MoveTowardAndShootPilot(enemy, fightState));

        return fightState;
    }

    public PlayerShip getPlayerShipInPreBattleState(){
        return (PlayerShip) Ship.parseJson(shipBefore);
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
