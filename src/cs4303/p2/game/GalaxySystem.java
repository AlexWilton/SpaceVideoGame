package cs4303.p2.game;

import cs4303.p2.game.drawable.Planet;
import cs4303.p2.util.JSONconvertable;
import processing.core.PConstants;
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
        app.stroke(Color.white.getRGB(), 1000); app.fill(Color.white.getRGB());
        app.strokeWeight(0);
        app.ellipse(mapLocation.x, mapLocation.y, 25, 25);


        app.stroke(faction.getFactionColour().getRGB(), 1000);
        app.fill(faction.getFactionColour().getRGB());
        app.ellipse(mapLocation.x, mapLocation.y, 20, 20);


//        drawOnMap(Color.WHITE);
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
        app.ellipseMode(PConstants.CENTER);
        app.ellipse(mapLocation.x, mapLocation.y, 20, 20);
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public void drawPlanetInCenterForFightScreen() {
        Planet planet = Planet.getPlanet(id);
        planet.drawInCenter();
    }

    public Planet getPlanet() {
        return Planet.getPlanet(id);
    }
}
