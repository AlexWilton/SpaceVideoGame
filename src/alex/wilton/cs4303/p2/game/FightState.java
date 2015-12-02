package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.screen.FightScreen;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipA;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipA;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipA;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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
        app.stroke(Color.white.getRGB());
        for(DrawableShip enemy : enemies){
            app.line(player.getCenterPosition().x, player.getCenterPosition().y, enemy.getCenterPosition().x, enemy.getCenterPosition().y);
            boolean collision = false;
            for(PVector weaponDamagePt : player.getWeapaonDamagePts()){
                if(enemy.containsPt(weaponDamagePt)) { collision = true; break; }
                app.ellipse(weaponDamagePt.x, weaponDamagePt.y, 4,4);
            }
            if(collision) {
               enemy.takeDamage(5);
            }
        }
    }

    public static FightState setupFight(GameState state){
        ArrayList<DrawableShip> enemies = new ArrayList<>();
        DrawableShip enemy = null;
        switch (state.getPlayerLocation().getFaction()){
            case Doloe: enemy = new DoloeShipA().createDrawableShipInstance(); break;
            case Qalz:  enemy = new QalzShipA().createDrawableShipInstance(); break;
            case Villt: enemy = new VilltShipA().createDrawableShipInstance(); break;
        }

        enemy.setCenterPosition(getRandomPosition());
        enemies.add(enemy);

        DrawableShip playerShip = state.getPlayerFleet().get(0).createDrawableShipInstance();
        playerShip.setCenterPosition(new PVector(0,0));
        return new FightState(state.getPlayerLocation(), playerShip, enemies);
    }

    private static PVector getRandomPosition() {
        float x,y, distanceToPlanet;
        do{
            x = (float) Math.random() * FightScreen.mapRadius;
            y = (float) Math.random() * FightScreen.mapRadius;
            if(Math.random() < 0.5) x *= -1;
            if(Math.random() < 0.5) y *= -1;
            distanceToPlanet = App.dist(0,0,x,y);
        }while( !(distanceToPlanet < FightScreen.mapRadius - 400 && distanceToPlanet > 150));
        return new PVector(x,y);
    }


}
