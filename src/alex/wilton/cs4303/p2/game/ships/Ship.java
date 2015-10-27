package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.ships.playerShip.PlayerShip;
import processing.core.PImage;

public abstract class Ship {
    App app = App.app;
    public PImage image;

    public Ship(){
        String imgFilePath = "images/ships/";
        if(this instanceof PlayerShip) imgFilePath += "playerShip/";
        imgFilePath += this.getClass().getSimpleName() + ".png";
        image = app.loadImage(imgFilePath);
    }

    public void drawShip(int x, int y){
        app.image(image, x,  y, 80, 100);
    }
}
