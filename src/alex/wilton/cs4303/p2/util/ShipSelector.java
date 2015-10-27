package alex.wilton.cs4303.p2.util;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.ships.Ship;

import java.util.List;

public class ShipSelector {
    App app = App.app;
    public ShipSelector(List<? extends Ship> ships, int x, int y, int width, int height) {
//        x = x - width/2; y = y - height/2;
        app.rectMode(App.CORNER);
        app.rect(x,y,width,height);
        app.imageMode(App.CORNER);
        for(int i=0; i<ships.size(); i++){
            ships.get(i).drawShip(x + 30 + i * (width/ships.size()), y + height/4);
        }
    }


}
