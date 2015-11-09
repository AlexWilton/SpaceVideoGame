package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
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

    public DrawableShip(Ship ship) {
        this.ship = ship;
        img = ship.getImage();
        orientation = 0;
    }

    public void draw(){
        /*Rotate image around its center then draw it*/
        app.translate(centerPosition.x, centerPosition.y);
        app.rotate(orientation);

        /* Draw */
        app.imageMode(App.CENTER);
        app.image(img, 0, 0, width, height);

        /* Return to normal orientation/translation*/
        app.rotate(-orientation);
        app.translate(-centerPosition.x, -centerPosition.y);

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


}
