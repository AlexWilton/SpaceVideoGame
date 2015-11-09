package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.util.JSONconvertable;
import processing.core.PVector;
import processing.data.JSONObject;

import java.awt.*;

public class GalaxySystem implements JSONconvertable {
    private App app = App.app;
    private int id;
    private Faction faction;
    private String name;
    private PVector mapLocation;

    public GalaxySystem(int id, Faction faction, String name, PVector mapLocation) {
        this.id = id;
        this.faction = faction;
        this.name = name;
        this.mapLocation = mapLocation;
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

    public PVector getMapLocation() {
        return mapLocation;
    }


    public void drawPlanetInTopRight() {

    }


    public void drawAsWhiteOnMap() {
        app.stroke(Color.WHITE.getRGB(), 1000);
        app.strokeWeight(5);
        app.ellipse(mapLocation.x, mapLocation.y, 20, 20);
    }

    public void drawAsBlackOnMap() {
        app.stroke(Color.BLACK.getRGB(), 1000);
        app.strokeWeight(5);
        app.ellipse(mapLocation.x, mapLocation.y, 20, 20);
    }


    public void drawOnHoverOnMap() {
        if(mouseOver()) {
           drawAsWhiteOnMap();
        }
    }

    public boolean mouseOver(){
        return Math.pow(app.mouseX - mapLocation.x,2) + Math.pow(app.mouseY - mapLocation.y,2) < Math.pow(15,2);
    }
}
