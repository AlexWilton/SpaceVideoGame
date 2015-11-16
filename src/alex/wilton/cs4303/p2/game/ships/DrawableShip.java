package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

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

    enum TurningStatus { LEFT, RIGHT, NOT_TURNING}
    private TurningStatus turningStatus = TurningStatus.NOT_TURNING;

    enum SideThrusterStatus { LEFT_THRUST, RIGHT_THRUST, NO_SIDE_THRUST}
    private SideThrusterStatus sideThrusterStatus = SideThrusterStatus.NO_SIDE_THRUST;

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
        app.imageMode(App.CENTER);
        app.image(img, 0, 0, width, height);

        /* Return to normal orientation/translation*/
        app.rotate(-orientation - IMAGE_ROTATE_SHIFT);
        app.translate(-centerPosition.x, -centerPosition.y);

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


}
