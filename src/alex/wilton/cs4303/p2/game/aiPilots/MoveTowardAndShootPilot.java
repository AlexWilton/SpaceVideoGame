package alex.wilton.cs4303.p2.game.aiPilots;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.FightState;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

public class MoveTowardAndShootPilot extends AIShipPilot {


    public MoveTowardAndShootPilot(DrawableShip ship, FightState fightState) {
        super(ship, fightState);
    }

    @Override
    public void checkForAiMove() { //called once per frame
        //rotate to face player's ship
        PVector playerLoc = fightState.player.getCenterPosition();
        PVector aiLoc = ship.getCenterPosition();
        double desiredOrientation = Math.atan2(playerLoc.y - aiLoc.y, playerLoc.x - aiLoc.x);
        double currentOrientation = ship.getOrientation();
        if(desiredOrientation < 0) desiredOrientation += Math.PI*2;
        if(currentOrientation < 0) currentOrientation += Math.PI*2;
        double angleBetween1 = Math.abs(desiredOrientation - currentOrientation);
        double angleBetween2 = Math.PI*2 - angleBetween1;
        double minBefore = Math.min(angleBetween1, angleBetween2);
        ship.turnLeft(); //tests whether turning left makes us closer to our desired angle compared.
        currentOrientation = ship.calucaleNewOrientationAfterRotation();
        if(currentOrientation < 0) currentOrientation += Math.PI*2;
        angleBetween1 = Math.abs(desiredOrientation - currentOrientation);
        angleBetween2 = Math.PI*2 - angleBetween1;
        double minAfter = Math.min(angleBetween1, angleBetween2);
        if(minAfter > minBefore) ship.turnRight(); //if it doesn't, turn right instead


        //move towards player if out of weapons range, move away if too close
        double distToPlayer = App.dist(aiLoc.x, aiLoc.y, playerLoc.x, playerLoc.y);
        if(Math.abs(desiredOrientation - ship.getOrientation()) < 50) {
            if (distToPlayer > ship.getWeaponRange() + 5) {
                ship.accelerate();
            } else if (distToPlayer < ship.getWeaponRange() - 5) {
                ship.accelarateBackwards();
            }
        }

        //check if weapon should fire/stop firing
        if(distToPlayer < ship.getWeaponRange() + ship.getWidth())
            ship.fireWeapon();
        else
            ship.stopFiringWeapon();

    }
}
