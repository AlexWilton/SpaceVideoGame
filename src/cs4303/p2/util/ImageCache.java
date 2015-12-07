package cs4303.p2.util;


import cs4303.p2.game.App;
import processing.core.PImage;

import java.util.HashMap;

public class ImageCache {

    private static HashMap<String, PImage> cache = new HashMap<>();

    public static PImage getImage(String filepath){
        if(!cache.containsKey(filepath)) loadImage(filepath);
        return cache.get(filepath);
    }

    private static void loadImage(String filepath){
        PImage img = App.app.loadImage(filepath);
        cache.put(filepath, img);
    }
}
