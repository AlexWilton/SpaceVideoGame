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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void abandonMission(GameState state) {
        status = Status.ABANDONED;
        state.setPlayersMission(null);
        int standing = state.getPlayerStanding(originFaction);
        standing -= 5; //damage standing by 5
        //don't lose rep with you lead this faction
        if(state.getLeadsFaction() != originFaction) state.setPlayerStanding(originFaction, standing);
    }

    public enum Status {OFFERED, ACCEPTED, ABANDONED, COMPLETED}
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
        int reward = 100 + distance * 115;

        return new Mission(originFaction, targetSystem, distance, reward);
    }

    public void processMissionAsWon(GameState state){
        if(status != Status.ACCEPTED) return;
        status = Status.COMPLETED;

        //get reward
        state.setPlayerCredits(state.getPlayerCredits() + reward);

        //update standing
        int newOriginStanding = state.getPlayerStanding(originFaction);
        if(newOriginStanding < 45) newOriginStanding = 45;
        newOriginStanding += 10;
        int newTargetStanding = state.getPlayerStanding(targetFaction);
        newTargetStanding -= 10;
        state.setPlayerStanding(originFaction, newOriginStanding);
        state.setPlayerStanding(targetFaction, newTargetStanding);

        //set new owner of system
        state.getPlayerLocation().setFaction(originFaction);
    }


}
