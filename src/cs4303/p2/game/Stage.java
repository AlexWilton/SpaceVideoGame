package cs4303.p2.game;

public enum Stage {

    MAIN_MENU,
    CUSTOM_PLAY,
    HOST_CUSTOM_PLAY,
    JOIN_CUSTOM_PLAY,
    CAMPAIGN,
    NEW_CAMPAIGN,
    SYSTEM,
    HYPER_JUMP,

    HANGAR,
    HANGAR_PART_REPAIR,
    HANGAR_FULL_REPAIR,
    HANGAR_BUY_1_MISSILE,
    HANGAR_BUY_3_MISSILES,
    HANGAR_UPGRADE_HULL,
    HANGAR_UPGRADE_LASER_RANGE,
    HANGAR_UPGRADE_LASER_RECHARGE,
    HANGAR_UPGRADE_ENGINE,
    HANGAR_UPGRADE_TURNING_SPEED,

    MISSION,
    MISSION_ACCEPTED,
    DISCARD_MISSION,
    ABANDON_MISSION,

    BRIBE,
    OFFER_BRIBE,

    GAME_OPTIONS,
    LOAD_SAVED_GAME,
    LOADING,
    GOTO_GC_WEBSITE,
    MAKE_JUMP,

    FIGHT,
    FIGHT_WON,
    PROCESS_FIGHT_WIN,
    UNDO_FIGHT,

    FACTION_LEADER_OFFER,
    BECOME_LEADER,
    REJECT_LEADERSHIP_OFFER,

    GAME_VICTORY,
    GAME_STALEMATE,
    GAME_LOST,

    RESET,
    EXIT_GAME
}
