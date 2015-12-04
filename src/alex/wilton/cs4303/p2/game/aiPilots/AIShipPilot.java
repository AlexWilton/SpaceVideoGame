package alex.wilton.cs4303.p2.game.aiPilots;

import alex.wilton.cs4303.p2.game.FightState;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;

public abstract class AIShipPilot {

    protected DrawableShip ship;
    protected FightState fightState;

    public AIShipPilot(DrawableShip ship, FightState fightState) {
        this.ship = ship;
        this.fightState = fightState;
    }

    public abstract void checkForAiMove();
}
