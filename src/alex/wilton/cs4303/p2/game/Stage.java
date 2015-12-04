package alex.wilton.cs4303.p2.game;

public enum Stage {

    MAIN_MENU,              //done
    CUSTOM_PLAY,            //done
    HOST_CUSTOM_PLAY,
    JOIN_CUSTOM_PLAY,
    CAMPAIGN,               //done
    NEW_CAMPAIGN,           //done
    SYSTEM,                 //done
    HYPER_JUMP,             //done

    MISSION,
    MISSION_ACCEPTED,
    DISCARD_MISSION,
    ABANDON_MISSION,

    FIGHT,

    BRIBE,                  //done
    OFFER_BRIBE,            //done

    GAME_OPTIONS,
    LOAD_SAVED_GAME,
    LOADING,
    HANGAR,
    GOTO_GC_WEBSITE,
    MAKE_JUMP, FIGHT_WON, PROCESS_FIGHT_WIN, FACTION_LEADER_OFFER, GAME_VICTORY, BECOME_LEADER, REJECT_LEADERSHIP_OFFER, RESET, GAME_STALEMATE, EXIT_GAME

}
