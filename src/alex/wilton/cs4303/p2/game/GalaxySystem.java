package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.util.JSONconvertable;
import processing.data.JSONObject;

public class GalaxySystem implements JSONconvertable {
    private int id;
    private Faction faction;
    private String name;

    public GalaxySystem(int id, Faction faction, String name) {
        this.id = id;
        this.faction = faction;
        this.name = name;
    }


    @Override
    public JSONObject asJSONObject() {
        JSONObject system = new JSONObject();
        system.setInt("id", id);
        system.setString("faction", faction.name());
        return system;
    }

    public int getId() {
        return id;
    }

    public Faction getFaction() {
        return faction;
    }

    public String getName() {
        return name;
    }

    public void drawPlanetInTopRight() {

    }
}
