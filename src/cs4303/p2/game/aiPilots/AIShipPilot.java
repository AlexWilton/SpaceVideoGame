package cs4303.p2.game.aiPilots;

import cs4303.p2.game.FightState;
import cs4303.p2.game.drawable.DrawableShip;

public abstract class AIShipPilot {

    protected DrawableShip ship;
    protected FightState fightState;

    public AIShipPilot(DrawableShip ship, FightState fightState) {
        this.ship = ship;
        this.fightState = fightState;
    }

    public abstract void checkForAiMove();
}
