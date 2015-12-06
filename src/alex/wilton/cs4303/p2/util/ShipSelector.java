package alex.wilton.cs4303.p2.util;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.screen.Screen;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.game.ships.Ship;
import alex.wilton.cs4303.p2.game.ships.playerShip.*;
import processing.core.PVector;
import processing.data.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class allows Player to select a ship from a selection of ships.
 * Similar to radio buttons in usage.
 */
public class ShipSelector implements JSONconvertable{
    private App app = App.app;
    private List<DrawableShip> drawableShips;
    private int x, y, width, height;
    private int selectedShipIndex = 0;

    private final int LEFT_PADDING = 30, SELECTOR_PADDING = 20;

    public ShipSelector(List<? extends Ship> ships, int x, int y, int width, int height) {
        drawableShips = new ArrayList<>();
        for(Ship s : ships) drawableShips.add(s.createDrawableShipInstance());
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        app.stroke(Color.white.getRGB());
        app.rectMode(App.CORNER);
        app.rect(x, y, width, height);
        app.imageMode(App.CORNER);
        for(int i=0; i<drawableShips.size(); i++){
            int shipX = x + LEFT_PADDING + i * (width/drawableShips.size());
            int shipY = y + height/4;
            DrawableShip drawableShip = drawableShips.get(i);
            int shipCenterX = shipX + drawableShip.getWidth() / 2;
            int shipCenterY = shipY + drawableShip.getHeight() / 2;
            drawableShip.setCenterPosition(new PVector(shipCenterX, shipCenterY));
            drawableShip.setOrientation(-App.HALF_PI);
            drawableShip.draw();

            if(i == selectedShipIndex)
               app.rect(shipX-5, y + SELECTOR_PADDING, drawableShip.getWidth()+10, height - 2*SELECTOR_PADDING, 20);

        }
    }

    /**
     * If player has selected a ship, return true, else false.
     */
    public boolean mousePressed() {
        int mouseX = app.mouseX, mouseY = app.mouseY;
        for(int i=0; i<drawableShips.size(); i++){
            DrawableShip ship = drawableShips.get(i);
            int shipX = x + LEFT_PADDING + i * (width/drawableShips.size());
            int shipY = y + height/4;
            int rectX = shipX, rectY = y + SELECTOR_PADDING, rectWidth = ship.getWidth(), rectHeight = height - 2*SELECTOR_PADDING;
            if(rectX <= mouseX && mouseX <= rectX + rectWidth && rectY <= mouseY && mouseY <= rectY + rectHeight) {
                selectedShipIndex = i;
                return true;
            }
        }
        return false;
    }

    public void shiftSelectorRight(){
        selectedShipIndex = (selectedShipIndex + 1) % drawableShips.size();
    }

    public void shiftSelectorLeft(){
        selectedShipIndex = (selectedShipIndex + drawableShips.size() - 1) % drawableShips.size();
    }


    public Ship getSelectedShip(){
        return drawableShips.get(selectedShipIndex).getShip();
    }

    public static ShipSelector createDefaultSelector() {
        List<? extends Ship> shipOptions = Arrays.asList(new ShipA(), new ShipB(), new ShipC(), new ShipD(), new ShipE());
        return new ShipSelector(shipOptions, App.SCREEN_WIDTH / 16, 250, App.SCREEN_WIDTH * 7 / 8, App.SCREEN_HEIGHT - 350);
    }

    @Override
    public JSONObject asJSONObject() {
        JSONObject selector = new JSONObject();
        selector.setInt("selectShipIndex", selectedShipIndex);
        selector.setBoolean("useDefault", true);
        return selector;
    }
}
