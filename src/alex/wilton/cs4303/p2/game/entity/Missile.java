package alex.wilton.cs4303.p2.game.entity;

import alex.wilton.cs4303.p2.game.Vector;

/**
 * Class for modelling and drawing a Missile (and its explosion when it reaches its target)
 */
public class Missile extends Entity{
    public static final int SPEED = 15;

    private Vector position;
    private Vector velocity;

    private int startX, startY,  targetX, targetY;
    private double angleFromBaseToTarget;
    private double distanceFromBaseToTarget;

    private boolean exploding = false;
    private int currentExplosionRadius = 60; //1 second (typically 60 frames a second)

    /**
     * Missile Constructor
     * @param base Origin Missile Base
     * @param targetX Target X Co-ordinate
     * @param targetY Target Y Co-ordinate
     */
    public Missile(MissileBase base, int targetX, int targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        startX = base.getCentralX();
        startY = base.getY();
        position = new Vector(startX, startY);
        angleFromBaseToTarget = Math.atan2(targetY - startY, targetX - startX);
        distanceFromBaseToTarget = distance(startX, startY, targetX, targetY);

        velocity = new Vector(SPEED * Math.cos(angleFromBaseToTarget), SPEED * Math.sin(angleFromBaseToTarget));
    }

    @Override
    public void draw() {
        if(exploding) {
            if(currentExplosionRadius > 0) drawExplosion();
        }
        else
            drawMissile();
    }

    /**
     * Draw Explosion, radius decreases based on frames left till end of explosion
     */
    private void drawExplosion() {
        //alternate colour between red and yellow
        if(currentExplosionRadius % 2 == 0)
            app.fill(255,0,0);
        else
            app.fill(255,255,0);
        app.strokeWeight(0);
        app.ellipse(targetX, targetY, currentExplosionRadius * 2, currentExplosionRadius * 2);
        app.fill(255); //reset colour to white
    }

    /**
     * Draw at its current location
     */
    private void drawMissile() {
        int trailLength = 8;
        int trailStartX = (int) (position.x - trailLength * Math.cos(angleFromBaseToTarget));
        int trailStartY = (int) (position.y - trailLength * Math.sin(angleFromBaseToTarget));
        app.strokeWeight(1);
        app.ellipse((float) position.x, (float) position.y, 2, 2);
        app.line((float) trailStartX, (float) trailStartY, (float) position.x, (float) position.y);
    }


    /**
     * Update Missile for a single unit of time
     */
    public void integrate() {
        if(exploding){
            if(currentExplosionRadius > 0) currentExplosionRadius--;
        }else{
            updatePositionAndCheckIfTargetReached();
        }
    }


    /**
     * Update Position of Missile and set missile to explode if target reached
     */
    private void updatePositionAndCheckIfTargetReached(){
        /* Update position */
        position.add(velocity);

        /* Check to see if target is reached */
        if(isTargetReached())
            exploding = true;
    }


    /**
     * Checks to see if the current position is within 5 units distance of the target position
     * @return true if close, else false.
     */
    private boolean isTargetReached() {
        return distanceTravelled() > distanceFromBaseToTarget;
    }

    /**
     * Caluclate distance the missile has travelled
     * @return Distance
     */
    public double distanceTravelled(){
        return distance(startX, startY, position.x, position.y);
    }

    /**
     * Calculate the distance between two points
     * @param x1 Point 1 x co-ordinate
     * @param y1 Point 1 u co-ordinate
     * @param x2 Point 2 x co-ordinate
     * @param y2 Point 2 y co-ordinate
     * @return Distance between the two points
     */
    private static double distance(int x1, int y1, double x2, double y2){
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }


    /**
     * If missile is in an exploding state and the particle/bomber is within the current blast radius
     * true will be returned (else false)
     * @param positionX x co-ordinate of object
     * @param positionY y co-ordinate of object
     * @return true if particle will be destroyed by missile
     */
    public boolean willDestroyObject(double positionX, double positionY) {
        if(!exploding || currentExplosionRadius == 0)
            return false;

        return distance((int) position.x, (int) position.y, positionX, positionY) < currentExplosionRadius;
    }

    public Vector getPosition() {
        return position;
    }

}
