package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShip;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShip;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShip;
import alex.wilton.cs4303.p2.game.ships.playerShip.PlayerShip;
import alex.wilton.cs4303.p2.util.ImageCache;
import alex.wilton.cs4303.p2.util.JSONconvertable;
import processing.core.PImage;
import processing.core.PVector;
import processing.data.JSONObject;

public abstract class Ship{
    App app = App.app;
    public PImage image;
    public Ship(){
        image = ImageCache.getImage(imageFilePath());
    }

    private float engineStrength = 0.5f;
    private float maxSpeed = 1.5f;

    /**
     * Determine Filepath of Ship image. (Based on
     * @return
     */
    private String imageFilePath(){
        String imgFilePath = "images/ships/";
        if(this instanceof PlayerShip) imgFilePath += "playerShip/";
        if(this instanceof VilltShip) imgFilePath += "VilltShip/";
        if(this instanceof QalzShip) imgFilePath += "QalzShip/";
        if(this instanceof DoloeShip) imgFilePath += "DoloeShip/";
        imgFilePath += this.getClass().getSimpleName() + ".png";
        return imgFilePath;
    }

    public void drawShip(int x, int y){
        app.image(image, x,  y, 80, 100);
    }

    public DrawableShip createDrawableShipInstance(){
        return new DrawableShip(this);
    }

    public PImage getImage() {
        return image;
    }

    public JSONObject asJsonObject(){
        JSONObject ship = new JSONObject();
        ship.setString("imageFilePath", imageFilePath());
        ship.setString("shipName", this.getClass().getSimpleName());
        return ship;
    }

    public float getEngineStrength() {
        return engineStrength;
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }
}
