package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShip;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShip;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShip;
import alex.wilton.cs4303.p2.game.ships.playerShip.PlayerShip;
import alex.wilton.cs4303.p2.util.ImageCache;
import processing.core.PImage;
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
    private int maxHull;

    private int laserDistance;
    private int laserCoolDown;

    private int hull;
    private int missiles;

    private int turningSpeed;



    private void setShipConfig() {
        if(this instanceof PlayerShip){
            engineStrength = 0.5f;
            maxHull = 2000;
            laserDistance = 160;
        }else{
            String fullShipClass = this.getClass().getSimpleName();
            String shipClass = fullShipClass.substring(fullShipClass.length()-1, fullShipClass.length());
            switch (shipClass){
                case "A":
                    engineStrength = 0.1f;
                    maxHull = 100;
                    laserDistance = 120;
                    break;
                case "B":
                    engineStrength = 0.2f;
                    maxHull = 200;
                    laserDistance = 125;
                    break;
                case "C":
                    engineStrength = 0.3f;
                    maxHull = 300;
                    laserDistance = 130;
                    break;
                case "D":
                    engineStrength = 0.5f;
                    maxHull = 500;
                    laserDistance = 135;
                    break;
                default:
                    System.out.println("Error! Unknown Ship Class Type");
            }
        }
        hull = maxHull;
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


    public DrawableShip createDrawableShipInstance(){
        return new DrawableShip(this);
    }

    public PImage getImage() {
        return image;
    }

    public JSONObject asJsonObject(){
        JSONObject ship = new JSONObject();
        ship.setInt("hull", hull);
        ship.setString("shipName", this.getClass().getName());
        return ship;
    }

    public static Ship parseJson(JSONObject jsonState) {
        try {
            Class c = Class.forName(jsonState.getString("shipName"));
            Ship ship = (Ship) c.newInstance();
            ship.setHull(jsonState.getInt("hull"));
            return ship;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public float getEngineStrength() {
        return engineStrength;
    }

    public float getMaxSpeed() {
        return engineStrength*5;
    }

    public int getHull() {
        return hull;
    }

    public int getMaxHull() {
        return maxHull;
    }

    public void setMaxHull(int maxHull) {
        this.maxHull = maxHull;
    }

    public int getLaserDistance() {
        return laserDistance;
    }

    public void setHull(int hull) {
        this.hull = hull;
    }

    public void setLaserDistance(int laserDistance) {
        this.laserDistance = laserDistance;
    }

    public void setEngineStrength(float engineStrength) {
        this.engineStrength = engineStrength;
    }

    public int getLaserCoolDown() {
        return laserCoolDown;
    }

    public void setLaserCoolDown(int laserCoolDown) {
        this.laserCoolDown = laserCoolDown;
    }

    public int getMissiles() {
        return missiles;
    }

    public void setMissiles(int missiles) {
        this.missiles = missiles;
    }

    public int getTurningSpeed() {
        return turningSpeed;
    }

    public void setTurningSpeed(int turningSpeed) {
        this.turningSpeed = turningSpeed;
    }
}
