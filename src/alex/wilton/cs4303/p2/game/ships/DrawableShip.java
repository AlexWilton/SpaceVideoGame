package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.DrawableObject;
import alex.wilton.cs4303.p2.game.Faction;
import alex.wilton.cs4303.p2.game.Missile;
import alex.wilton.cs4303.p2.game.aiPilots.AIShipPilot;
import alex.wilton.cs4303.p2.game.entity.staticImage.Planet;
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
    private float orientation;
    private static final float IMAGE_ROTATE_SHIFT = PConstants.HALF_PI;
    private AIShipPilot aiPilot = null;

    public void takeDamage(int mag) {
        ship.setHull(ship.getHull() - mag);
    }

    public void impulseAwayFrom(PVector repulsionPt, boolean impulseAwayFromCenter) {
        PVector dir = new PVector(repulsionPt.x - centerPosition.x, repulsionPt.y - centerPosition.y);
        dir.normalize();
        if(impulseAwayFromCenter) dir.mult(-1);
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

    enum ForwardThrusterStatus { FORWARD_THRUST, BACKWARD_THRUST, NONE }
    private ForwardThrusterStatus forwardThrusterStatus = ForwardThrusterStatus.NONE;

    private boolean laserFiring = false;

    private int frameLeftOfExplosion = (int) (2 * app.frameRate);
    private int weaponFiringTime = 0;
    private int laserRechargeTime = 0;
    private ArrayList<Missile> missiles = new ArrayList<>();

    public DrawableShip(Ship ship) {
        this.ship = ship;
        img = ship.getImage();
        orientation = 0;
        velocity = new PVector(0,0);
    }

    public void draw(){
        update();
        /*Rotate image around its center then draw it*/
        app.translate(centerPosition.x, centerPosition.y);
        app.rotate(orientation + IMAGE_ROTATE_SHIFT);

        /* Draw */
        if(ship.getHull() > 0) {

            drawLaserFire();
            app.imageMode(App.CENTER);
            app.image(img, 0, 0, width, height);

        }else{ //destroyed...
            stopFiringWeapon();

            //exploding
            if(frameLeftOfExplosion > 0) {
                switch (frameLeftOfExplosion % 3){
                    case 0: app.fill(231, 76, 60); break;
                    case 1: app.fill(211, 84, 0); break;
                    case 2: app.fill(241, 196, 15); break;
                }
                app.noStroke();
                app.ellipse(0, 0, width * frameLeftOfExplosion / 100, width * frameLeftOfExplosion / 100);
                frameLeftOfExplosion--;
            }
        }

        /* Return to normal orientation/translation*/
        app.rotate(-orientation - IMAGE_ROTATE_SHIFT);
        app.translate(-centerPosition.x, -centerPosition.y);

    }

    private void drawLaserFire() {
        //LASER
        if(laserFiring) {
            app.strokeWeight(5);
            app.stroke(Faction.Villt.getFactionColour().getRGB());
            app.line(0, 0, 0, -ship.getLaserDistance());
        }
    }

    public void drawAndUpdateMissiles(){
        for(Missile missile : missiles){
            missile.update();
            missile.draw();
        }
    }

    private void update() {
        if(aiPilot != null && ship.getHull() > 0) aiPilot.checkForAiMove();
        centerPosition.add(velocity);

        //apply drag
        velocity.mult(0.99f);

        //apply gravity towards planet
        float distToPlanet = App.dist(0,0,centerPosition.x, centerPosition.y);
        if(distToPlanet > Planet.getDiameter()/2 ) {
            PVector gravity = centerPosition.get();
            gravity.setMag(-5 / distToPlanet);
            velocity.add(gravity);
        }

        //enforce max speed
        if(velocity.mag() > ship.getMaxSpeed())
            velocity.setMag(ship.getMaxSpeed());


        //apply turn
        orientation = calculateNewOrientationAfterRotation();

        //apply main (forward/backward) thrust
        PVector mainThrust = new PVector(App.cos(orientation),App.sin(orientation));
        switch (forwardThrusterStatus){
            case FORWARD_THRUST:  mainThrust.setMag(ship.getEngineStrength()); break;
            case BACKWARD_THRUST: mainThrust.setMag(-ship.getEngineStrength()); break;
            case NONE: mainThrust.setMag(0); break;
        }
        velocity.add(mainThrust);

        //apply side-thrust
        PVector sideThrust = null;
        switch (sideThrusterStatus){
            case LEFT_THRUST:
                double angle = orientation - PConstants.HALF_PI;
                sideThrust = new PVector((float) Math.cos(angle), (float) Math.sin(angle));
                break;
            case RIGHT_THRUST:
                angle = orientation + PConstants.HALF_PI;
                sideThrust = new PVector((float) Math.cos(angle), (float) Math.sin(angle));
                break;
        }
        if(sideThrust != null) {
            sideThrust.setMag(ship.getEngineStrength());
            velocity.add(sideThrust);
        }

        //laser timer 3 sec for firing time, recharge time variable.
        if(laserRechargeTime > 0) laserRechargeTime--;
        if(weaponFiringTime > 0){
            weaponFiringTime--;
            if(weaponFiringTime == 0){
                laserFiring = false;
                laserRechargeTime = (int) ((0.5+500.0/ship.getLaserRechargeSpeed()) * app.frameRate);
            }
        }

    }

    public float calculateNewOrientationAfterRotation(){
        float newOrientation = orientation;
        switch (turningStatus){
            case LEFT: newOrientation -= ship.getTurningSpeed()*0.001; break;
            case RIGHT: newOrientation += ship.getTurningSpeed()*0.001; break;
        }
        if(newOrientation > Math.PI*2) newOrientation -= Math.PI*2;
        if(newOrientation < 0) newOrientation += Math.PI*2;
        return newOrientation;
    }


    public ArrayList<PVector> getWeapaonDamagePts() {
        ArrayList<PVector> damagePts = new ArrayList<>();
        if(!laserFiring) return damagePts;

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

    public void accelerate(){
        forwardThrusterStatus = ForwardThrusterStatus.FORWARD_THRUST;
    }

    public void accelarateBackwards(){
        forwardThrusterStatus = ForwardThrusterStatus.BACKWARD_THRUST;
    }


    public void stopAcceleration() {
        forwardThrusterStatus = ForwardThrusterStatus.NONE;
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


    public void fireWeapon(){
        if(laserRechargeTime == 0 && !laserFiring) {
            weaponFiringTime = (int) (3 * app.frameRate);
            laserFiring = true;
        }
    }

    public void stopFiringWeapon(){ laserFiring = false; }

    public int getWeaponRange(){
        return ship.getLaserDistance();
    }

    public void fireMissile(){
        if(ship.getMissiles() > 0){
            ship.setMissiles(ship.getMissiles()-1);
            PVector velocity = new PVector(App.cos(orientation), App.sin(orientation));
            velocity.setMag(5);
            missiles.add(new Missile(centerPosition.get(), velocity ));
        }
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

    public float getOrientation() {
        return orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public boolean isExploded(){
        return frameLeftOfExplosion <= 0;
    }

    public int getLaserRechargeTime() {
        return laserRechargeTime;
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }
}
