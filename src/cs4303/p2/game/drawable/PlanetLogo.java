package cs4303.p2.game.drawable;

import cs4303.p2.game.App;
import processing.core.PImage;

public class PlanetLogo {
    private static App app = App.app;
    private static PImage image;

    public static void draw(){
        if(image == null){
            image = app.loadImage("images/planetLogoWhite.png");
        }
        app.imageMode(App.CENTER);
        app.image(image, app.width-30, 30);
    }

}
