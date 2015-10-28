package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.util.JSONconvertable;
import processing.data.JSONObject;

public class GalaxySystem implements JSONconvertable {
    private int id;
    private Faction faction;

    public GalaxySystem(int id, Faction faction) {
        this.id = id;
        this.faction = faction;
    }

    @Override
    public JSONObject asJSONObject() {
        JSONObject system = new JSONObject();
        system.setInt("id", id);
        system.setString("faction", faction.name());
        return system;
    }
}
