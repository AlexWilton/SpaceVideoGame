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
    private int laserRechargeSpeed;

    private int hull;
    private int missiles;

    private int turningSpeed;



    private void setShipConfig() {
        if(this instanceof PlayerShip){
            engineStrength = 0.5f;
            maxHull = 2000;
            laserDistance = 160;
            turningSpeed = 20;
            laserRechargeSpeed = 100;
        }else{
            String fullShipClass = this.getClass().getSimpleName();
            String shipClass = fullShipClass.substring(fullShipClass.length()-1, fullShipClass.length());
            switch (shipClass){
                case "A":
                    engineStrength = 0.15f;
                    maxHull = 100;
                    laserDistance = 120;
                    turningSpeed = 10;
                    laserRechargeSpeed = 100;
                    break;
                case "B":
                    engineStrength = 0.2f;
                    maxHull = 200;
                    laserDistance = 125;
                    turningSpeed = 10;
                    laserRechargeSpeed = 200;
                    break;
                case "C":
                    engineStrength = 0.3f;
                    maxHull = 300;
                    laserDistance = 130;
                    turningSpeed = 10;
                    laserRechargeSpeed = 250;
                    break;
                case "D":
                    engineStrength = 0.4f;
                    maxHull = 500;
                    laserDistance = 140;
                    turningSpeed = 10;
                    laserRechargeSpeed = 300;
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
        ship.setFloat("engineStrength", engineStrength);
        ship.setInt("hull", hull);
        ship.setInt("maxHull", maxHull);
        ship.setInt("laserDistance", laserDistance);
        ship.setInt("laserRechargeSpeed", laserRechargeSpeed);
        ship.setInt("missiles", missiles);
        ship.setInt("turningSpeed", turningSpeed);
        ship.setString("shipName", this.getClass().getName());
        return ship;
    }

    public static Ship parseJson(JSONObject jsonState) {
        try {
            Class c = Class.forName(jsonState.getString("shipName"));
            Ship ship = (Ship) c.newInstance();
            ship.setHull(jsonState.getInt("hull"));
            ship.setEngineStrength(jsonState.getFloat("engineStrength"));
            ship.setMaxHull(jsonState.getInt("maxHull"));
            ship.setLaserDistance(jsonState.getInt("laserDistance"));
            ship.setLaserRechargeSpeed(jsonState.getInt("laserRechargeSpeed"));
            ship.setHull(jsonState.getInt("hull"));
            ship.setMissiles(jsonState.getInt("missiles"));
            ship.setTurningSpeed(jsonState.getInt("turningSpeed"));
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

    public int getLaserRechargeSpeed() {
        return laserRechargeSpeed;
    }

    public void setLaserRechargeSpeed(int laserRechargeSpeed) {
        this.laserRechargeSpeed = laserRechargeSpeed;
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
