package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.Faction;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShip;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;
import java.util.ArrayList;

public class DrawableShip {
    private App app = App.app;
    private Ship ship;
    private PImage img;
    private int width = 80, height = 100;

    private PVector centerPosition; //(x,y) of ship's center point.
    private PVector velocity;
    private PVector acceleration;
    private float orientation;
    private static final float IMAGE_ROTATE_SHIFT = PConstants.HALF_PI;

    public void takeDamage(int mag) {
        hullStrength -= mag;
    }


    enum TurningStatus { LEFT, RIGHT, NOT_TURNING}
    private TurningStatus turningStatus = TurningStatus.NOT_TURNING;

    enum SideThrusterStatus { LEFT_THRUST, RIGHT_THRUST, NO_SIDE_THRUST}
    private SideThrusterStatus sideThrusterStatus = SideThrusterStatus.NO_SIDE_THRUST;

    private boolean weaponFiring = false;
    private int hullStrength = 1000;
    private int frameLeftOfExplosion = (int) (3 * app.frameRate);
    private int laserDistance = 150;

    public DrawableShip(Ship ship) {
        this.ship = ship;
        img = ship.getImage();
        orientation = 0;
        velocity = new PVector(0,0);
        acceleration = new PVector(0,0);

    }

    public void draw(){
        update();
        /*Rotate image around its center then draw it*/
        app.translate(centerPosition.x, centerPosition.y);
        app.rotate(orientation + IMAGE_ROTATE_SHIFT);

        /* Draw */
        if(hullStrength > 0) {
            //not destroyed
            if (weaponFiring) drawWeaponFire();
            app.imageMode(App.CENTER);
            app.image(img, 0, 0, width, height);
        }else{
            //exploding
            app.ellipse(0, 0, width * frameLeftOfExplosion / 100, height * frameLeftOfExplosion / 100);
            frameLeftOfExplosion--;
        }

        /* Return to normal orientation/translation*/
        app.rotate(-orientation - IMAGE_ROTATE_SHIFT);
        app.translate(-centerPosition.x, -centerPosition.y);

    }

    private void drawWeaponFire() {
        int distance = 150;
        app.strokeWeight(5);
        app.stroke(Faction.Villt.getFactionColour().getRGB(), 100);
        app.line(0, 0, 0, -distance);

    }

    private void update() {
        centerPosition.add(velocity);
        velocity.add(acceleration);

        //enforce max speed
        if(velocity.mag() > ship.getMaxSpeed())
            velocity.setMag(ship.getMaxSpeed());


        //apply turn
        switch (turningStatus){
            case LEFT: orientation -= 0.02; break;
            case RIGHT: orientation += 0.02; break;
        }

        //apply side-thrust
        PVector thrust = new PVector(0,0);
        switch (sideThrusterStatus){
            case LEFT_THRUST:
                double angle = orientation - PConstants.HALF_PI;
                thrust = new PVector((float) Math.cos(angle), (float) Math.sin(angle));
                break;
            case RIGHT_THRUST:
                angle = orientation + PConstants.HALF_PI;
                thrust = new PVector((float) Math.cos(angle), (float) Math.sin(angle));
                break;
        }
        thrust.normalize();
        thrust.mult(0.1f);
        velocity.add(thrust);

    }




    public ArrayList<PVector> getWeapaonDamagePts() {
        ArrayList<PVector> damagePts = new ArrayList<>();
        if(!weaponFiring) return damagePts;

        int dist = 0;
        do{
            PVector pt = new PVector((float) (centerPosition.x + dist * Math.cos(orientation)),
                    (float) (centerPosition.y + dist * Math.sin(orientation)));
            damagePts.add(pt);
            dist++;
        }while(damagePts.get(damagePts.size()-1).dist(centerPosition) < laserDistance);
        return damagePts;
    }


    public boolean containsPt(PVector pt){
        return      Math.pow(pt.x - centerPosition.x,2)/Math.pow(width/2,2)
                +   Math.pow(pt.y - centerPosition.y, 2)/Math.pow(height/2,2) < 1;
    }

    public void accelerate(float mag){
        float x = (float) Math.cos(orientation);
        float y = (float) Math.sin(orientation);
        acceleration = new PVector(x,y);
        acceleration.normalize();
        acceleration.mult(ship.getEngineStrength());
        acceleration.mult(mag);
    }

    public void brake(){
        accelerate(-0.1f);
        velocity.mult(0.8f); //apply more break drag
    }


    public void turnLeft(){
        turningStatus = TurningStatus.LEFT;
    }

    public void turnRight(){
        turningStatus = TurningStatus.RIGHT;
    }

    public void stopTurning(){
        turningStatus = TurningStatus.NOT_TURNING;
    }

    public void leftThrust(){ sideThrusterStatus = SideThrusterStatus.LEFT_THRUST; }

    public void rightThrust(){ sideThrusterStatus = SideThrusterStatus.RIGHT_THRUST; }

    public void stopSideThrust(){ sideThrusterStatus = SideThrusterStatus.NO_SIDE_THRUST; }


    public void fireWeapon(){ weaponFiring = true; }

    public void stopFiringWeapon(){ weaponFiring = false; }


    public Ship getShip(){return ship;}

    public void setCenterPosition(PVector centerPosition) {
        this.centerPosition = centerPosition;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PVector getCenterPosition() {
        return centerPosition;
    }

    public PVector getVelocity() {
        return velocity;
    }

    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    public PVector getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(PVector acceleration) {
        this.acceleration = acceleration;
    }

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public boolean isExploded(){
        return frameLeftOfExplosion <= 0;
    }


}
