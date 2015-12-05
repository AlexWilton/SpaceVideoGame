package alex.wilton.cs4303.p2.game.entity.staticImage;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.DrawableObject;
import alex.wilton.cs4303.p2.util.ImageCache;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Planet extends DrawableObject{
    private static App app = App.app;
    private PImage image;

    private static Map<Integer, Planet> planets = new HashMap<>();

    public Planet(int planetNumber){
        image = ImageCache.getImage("images/planets/planet" + planetNumber +".png");
    }

    public void drawInTopRight(){
        app.imageMode(App.CENTER);
        app.image(image, app.width-70, 70);
    }

    public void drawInCenter(){
        app.imageMode(App.CENTER);
        app.image(image, 0, 0, image.width*2, image.height*2);
    }

    public static void draw(int systemId){
        Planet planet = getPlanet(systemId);
        planet.drawInTopRight();
    }

    public static Planet getPlanet(int id){
        int planetNumber = id % 10;
        Planet planet;
        if(planets.containsKey(planetNumber)){
            planet = planets.get(planetNumber);
        }else{
            planet = new Planet(planetNumber);
            planets.put(planetNumber, planet);
        }
        return planet;
    }


    private static ArrayList<PVector> circumPts = null;
    @Override
    public List<PVector> getCircumferencePts() {
        if(circumPts != null) return circumPts;

        circumPts = new ArrayList<>();
        for(int degrees=0; degrees<360; degrees += 10){
            float rads = App.radians(degrees);
            circumPts.add(new PVector((float) (image.width * Math.cos(rads)), (float) (image.width * Math.sin(rads))));
        }
        return circumPts;
    }

    @Override
    public boolean containsPt(PVector pt) {
        return App.dist(0,0, pt.x, pt.y) < image.width;
    }

    @Override
    public PVector getCenterPosition() {
        return new PVector(0,0);
    }

    public static int getDiameter(){
        Planet p = getPlanet(0);
        return p.image.width*2;
    }
}
