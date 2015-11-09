package alex.wilton.cs4303.p2.game;


import java.util.prefs.Preferences;

public class Mission {
    private GalaxySystem targetSystem;
    private Faction targetFaction;
    private Faction originFaction;
    private int distance, reward;

    public Mission(Faction originFaction, GalaxySystem targetSystem, int distance, int reward) {
        this.originFaction = originFaction;
        this.targetSystem = targetSystem;
        this.targetFaction = targetSystem.getFaction();
        this.distance = distance;
        this.reward = reward;
        status = Status.OFFERED;
    }

    public GalaxySystem getTargetSystem() {
        return targetSystem;
    }

    public int getDistance() {
        return distance;
    }

    public int getReward() {
        return reward;
    }

    public enum Status {OFFERED, ACCEPTED;}
    private Status status;

    public Status getStatus() {
        return status;
    }

    public Faction getTargetFaction() {
        return targetFaction;
    }

    public Faction getOriginFaction() {
        return originFaction;
    }

    public static Mission createMission(GameState state) {
        GalaxySystem currentLocation = state.getPlayerLocation();
        Faction originFaction = currentLocation.getFaction();

        //choose target
        GalaxySystem targetSystem;
        do{
            targetSystem = state.getGalaxy().selectRandomSystem();
        } while(targetSystem.getFaction() == originFaction);

        //calculate distance+reward
        int distance = state.getGalaxy().calculateMinimumNumberOfJumps(currentLocation, targetSystem);
        int reward = 100 + distance * 11;

        return new Mission(originFaction, targetSystem, distance, reward);
    }


}
