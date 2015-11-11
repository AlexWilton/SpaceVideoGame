package alex.wilton.cs4303.p2.game.entity.staticImage;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.util.ImageCache;
import processing.core.PImage;

import java.util.HashMap;
import java.util.Map;

public class Planet {
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

    public static void draw(int systemId){
        int planetNumber = systemId % 10;
        Planet planet;
        if(planets.containsKey(planetNumber)){
            planet = planets.get(planetNumber);
        }else{
            planet = new Planet(planetNumber);
            planets.put(planetNumber, planet);
        }
        planet.drawInTopRight();
    }



}
