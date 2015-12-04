package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.DrawableObject;
import alex.wilton.cs4303.p2.game.Faction;
import alex.wilton.cs4303.p2.game.aiPilots.AIShipPilot;
import alex.wilton.cs4303.p2.game.aiPilots.MoveTowardAndShootPilot;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class DrawableShip extends DrawableObject{
    private App app = App.app;
    private Ship ship;
    private PImage img;
    private int width = 80, height = 100;

    private PVector centerPosition; //(x,y) of ship's center point.
    private PVector velocity;
    private PVector acceleration;
    private float orientation;
    private static final float IMAGE_ROTATE_SHIFT = PConstants.HALF_PI;
    private AIShipPilot aiPilot = null;

    public void takeDamage(int mag) {
        ship.setHullStrength(ship.getHullStrength() - mag);
    }

    public void impulseAwayFrom(PVector repulsionPt) {
        PVector dir = new PVector(repulsionPt.x - centerPosition.x, repulsionPt.y - centerPosition.y);
        dir.normalize();
        dir.mult(-0.1f);
        dir.setMag(velocity.mag());
        velocity.add(dir);
    }

    public void assignAiPilot(AIShipPilot aiPilot) {
        this.aiPilot = aiPilot;
    }


    enum TurningStatus { LEFT, RIGHT, NOT_TURNING}
    private TurningStatus turningStatus = TurningStatus.NOT_TURNING;

    enum SideThrusterStatus { LEFT_THRUST, RIGHT_THRUST, NO_SIDE_THRUST}
    private SideThrusterStatus sideThrusterStatus = SideThrusterStatus.NO_SIDE_THRUST;

    private boolean weaponFiring = false;

    private int frameLeftOfExplosion = (int) (2 * app.frameRate);

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
        if(ship.getHullStrength() > 0) {
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
        app.strokeWeight(5);
        app.stroke(Faction.Villt.getFactionColour().getRGB(), 100);
        for(int i=100; i<ship.getLaserDistance(); i++){
            int x = 0, y = -i;
            if(app.blue(app.get(x, y)) != 0) break;

            app.line(0, 0, x, y);
        }

    }

    private void update() {
        if(aiPilot != null) aiPilot.checkForAiMove();
        centerPosition.add(velocity);
        velocity.add(acceleration);

        //apply drag
        velocity.mult(0.99f);


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
        }while(damagePts.get(damagePts.size()-1).dist(centerPosition) < ship.getLaserDistance());
        return damagePts;
    }

    private static ArrayList<PVector> ptsAroundOrigin = null;
    public ArrayList<PVector> getCircumferencePts(){
        if(ptsAroundOrigin == null){
            ptsAroundOrigin = new ArrayList<>();
            double halfWidth = width/2, halfHeight = height/2;
            for(int degrees=0; degrees<360; degrees += 10) {
                float rads = App.radians(degrees);
                float x1 = (float) (halfWidth*halfHeight/Math.sqrt(Math.pow(halfHeight,2) + Math.pow(halfWidth,2) * Math.pow(Math.tan((double)rads),2)));
                float x2 = -x1;
                float y1 = (float) (halfHeight * Math.sqrt(1 - Math.pow(x1,2)/Math.pow(halfWidth,2)));
                float y2 = -y1;
                ptsAroundOrigin.add(new PVector(x1,  y1));
                ptsAroundOrigin.add(new PVector(x1,  y2));
                ptsAroundOrigin.add(new PVector(x2,  y1));
                ptsAroundOrigin.add(new PVector(x2,  y2));
            }
        }
        ArrayList<PVector> pts = new ArrayList<>();
        for(PVector originPt : ptsAroundOrigin) {
            //rotate pt based on ship orientation
            double x = originPt.x, y =originPt.y;
            double[] pt = {x, y};
            AffineTransform.getRotateInstance(orientation + IMAGE_ROTATE_SHIFT, 0,0)
                    .transform(pt, 0, pt, 0, 1); // specifying to use this double[] to hold coords
            pts.add(new PVector((float) (pt[0] + centerPosition.x), (float) (pt[1] + centerPosition.y)));
        }
        return pts;
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

    public int getWeaponRange(){
        return ship.getLaserDistance();
    }


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
