package alex.wilton.cs4303.p2.game.aiPilots;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.FightState;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
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
        double angle = Math.atan2(playerLoc.y - aiLoc.y, playerLoc.x - aiLoc.x);

        if((ship.getOrientation() > App.PI-0.03 && angle < -(App.PI-0.03))
            || (ship.getOrientation() < -(App.PI-0.03)  &&  angle > App.PI-0.03 )){
            ship.setOrientation((float)angle);
        }
        if(ship.getOrientation() > angle)
            ship.turnLeft();
        else
            ship.turnRight();


        //move towards player if out of weapons range, move away if too close
        double distToPlayer = App.dist(aiLoc.x, aiLoc.y, playerLoc.x, playerLoc.y);
        if(distToPlayer > ship.getWeaponRange() + 5){
            ship.accelerate(0.05f);
        }else if(distToPlayer < ship.getWeaponRange() - 5){
            ship.brake();
        }

        //check if weapon should fire/stop firing
        if(distToPlayer < ship.getWeaponRange() + ship.getWidth()/2)
            ship.fireWeapon();
        else
            ship.stopFiringWeapon();

    }
}
