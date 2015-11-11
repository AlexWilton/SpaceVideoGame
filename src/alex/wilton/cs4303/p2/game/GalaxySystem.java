package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.entity.staticImage.Planet;
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
        Planet.draw(id);
    }


    public void drawAsWhiteOnMap() {
        drawOnMap(Color.WHITE);
    }

    public void drawWhiteLineOnMap(PVector from){
        app.stroke(Color.WHITE.getRGB(), 1000);
        app.strokeWeight(3);
        app.line(mapLocation.x, mapLocation.y, from.x, from.y);
    }

    public void drawAsBlackOnMap() {
        drawOnMap(Color.BLACK);
    }


    public void drawOnHoverOnMap() {
        if(mouseOver()) {
           drawAsWhiteOnMap();
        }
    }

    public boolean mouseOver(){
        return Math.pow(app.mouseX - mapLocation.x,2) + Math.pow(app.mouseY - mapLocation.y,2) < Math.pow(15,2);
    }

    public void drawOnMap(Color color) {
        app.stroke(color.getRGB(), 1000); app.noFill();
        app.strokeWeight(5);
        app.ellipse(mapLocation.x, mapLocation.y, 20, 20);
    }
}
