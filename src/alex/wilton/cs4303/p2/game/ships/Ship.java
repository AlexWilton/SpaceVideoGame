package alex.wilton.cs4303.p2.game.ships;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.ships.DoloeShip.DoloeShip;
import alex.wilton.cs4303.p2.game.ships.QalzShip.QalzShip;
import alex.wilton.cs4303.p2.game.ships.VilltShip.VilltShip;
import alex.wilton.cs4303.p2.game.ships.playerShip.PlayerShip;
import alex.wilton.cs4303.p2.util.ImageCache;
import processing.core.PImage;

public abstract class Ship {
    App app = App.app;
    public PImage image;

    public Ship(){
        image = ImageCache.getImage(imageFilePath());
    }

    /**
     * Determine Filepath of Ship image. (Based on
     * @return
     */
    private String imageFilePath(){
        String imgFilePath = "images/ships/";
        if(this instanceof PlayerShip) imgFilePath += "playerShip/";
        if(this instanceof VilltShip) imgFilePath += "VilltShip/";
        if(this instanceof QalzShip) imgFilePath += "QalzShip/";
        if(this instanceof DoloeShip) imgFilePath += "DoloeShip/";
        imgFilePath += this.getClass().getSimpleName() + ".png";
        return imgFilePath;
    }

    public void drawShip(int x, int y){
        app.image(image, x,  y, 80, 100);
    }
}
