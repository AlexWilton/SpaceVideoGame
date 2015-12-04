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
        setShipConfig();
    }

    /* SHIP CONFIGURATION */
    private float engineStrength;
    private float maxSpeed;
    private int hullStrength;
    private int laserDistance;

    private void setShipConfig() {
        if(this instanceof PlayerShip){
            engineStrength = 0.5f;
            maxSpeed = 2.5f;
            hullStrength = 1000;
            laserDistance = 150;
        }else{
            String fullShipClass = this.getClass().getSimpleName();
            String shipClass = fullShipClass.substring(fullShipClass.length()-1, fullShipClass.length());
            switch (shipClass){
                case "A":
                    engineStrength = 0.1f;
                    maxSpeed = 0.5f;
                    hullStrength = 100;
                    laserDistance = 90;
                    break;
                case "B":
                    engineStrength = 0.3f;
                    maxSpeed = 1f;
                    hullStrength = 300;
                    laserDistance = 150;
                    break;
                case "C":
                    engineStrength = 0.6f;
                    maxSpeed = 1.5f;
                    hullStrength = 700;
                    laserDistance = 150;
                    break;
                case "D":
                    engineStrength = 0.9f;
                    maxSpeed = 2.0f;
                    hullStrength = 1200;
                    laserDistance = 150;
                    break;
                default:
                    System.out.println("Error! Unknown Ship Class Type");
            }
        }
    }




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

    public int getHullStrength() {
        return hullStrength;
    }

    public int getLaserDistance() {
        return laserDistance;
    }

    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
    }

    public void setLaserDistance(int laserDistance) {
        this.laserDistance = laserDistance;
    }
}
