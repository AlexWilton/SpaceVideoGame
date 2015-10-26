package alex.wilton.cs4303.p2.game.entity;

import processing.core.PImage;

/**
 * Class for representing and drawing the CrossHair
 */
public class CrossHair extends Entity{
    private static PImage crossHairImg = alex.wilton.cs4303.p2.game.App.app.loadImage("images/crosshair.gif");

    public CrossHair(){super();}

    @Override
    public void draw() {
        app.imageMode(alex.wilton.cs4303.p2.game.App.CENTER);
        app.image(crossHairImg, app.mouseX, app.mouseY, 30, 30);
    }
}
