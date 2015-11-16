package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShipA;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShipA;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShipA;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;

public class FightState {
    public final GalaxySystem location;

    public final DrawableShip player;
    public final ArrayList<DrawableShip> enemies;

    public PVector cameraLocation = new PVector(0,0);

//    public final int MAP_SIZE = 5; //width&height of (square) map in MapSquares
//
//    public HashMap<PVector, FightMapSquare> mapSquares = new HashMap<>();

    public FightState(GalaxySystem location, DrawableShip player, ArrayList<DrawableShip> enemies) {
        this.location = location;
        this.player = player;
        this.enemies = enemies;
    }

    public static FightState setupFight(GameState state){
        ArrayList<DrawableShip> enemies = new ArrayList<>();
        switch (state.getPlayerLocation().getFaction()){
            case Doloe: enemies.add(new DoloeShipA().createDrawableShipInstance());
            case Qalz:  enemies.add(new QalzShipA().createDrawableShipInstance());
            case Villt: enemies.add(new VilltShipA().createDrawableShipInstance());
        }

        DrawableShip playerShip = state.getPlayerFleet().get(0).createDrawableShipInstance();
        playerShip.setCenterPosition(new PVector(App.app.width/4,App.app.height/4));
        return new FightState(state.getPlayerLocation(), playerShip, enemies);
    }

    public void drawRelevantMapSquares() {
//        PVector loc = player.getCenterPosition();
//        int x = ((int) loc.x) / FightMapSquare.width;
//        int y = ((int) loc.y) / FightMapSquare.height;
//        PVector gridLoc = new PVector(x,y);
//
    }
}
