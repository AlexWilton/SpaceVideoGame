package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.*;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.util.ImageCache;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;

import java.awt.*;


public class HyperJumpScreen extends Screen {

    private GalaxySystem currentSystem;
    private GalaxySystem destinationSystem;
    private Galaxy galaxy;

    public HyperJumpScreen(GameState state) {
        super(state);
        currentSystem = state.getPlayerLocation();
        galaxy = state.getGalaxy();
        destinationSystem = currentSystem;
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());
        drawGalaxyMapBackground();
        drawInfoBar();
        galaxy.drawConnectionsOnMap();
        galaxy.drawSystemsOnMap();

//        app.fill(system.getFaction().getFactionColour().getRGB());
//        app.text("System Controlled by " + system.getFaction().name(), app.width / 2, 60);
//
//
//
//        app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT); app.textSize(20);
//        app.text("Message from the " + system.getFaction().name() + " Faction:", 10 + app.width / 5, 135);

        createButton("RETURN TO\nSYSTEM VIEW", 130, (int) (app.height * 0.9), 180, 50, Stage.SYSTEM);

    }

    private void drawInfoBar() {
        String info; //info bar text (needed for sizing border rectangle)
        app.textSize(20);
        if(destinationSystem == null){
            app.fill(Color.WHITE.getRGB()); app.textAlign(PConstants.LEFT);
            info = "SELECT JUMP DESTINATION";
            app.text(info, 50, 35);
        }else {
            app.fill(Color.WHITE.getRGB());
            app.textAlign(PConstants.LEFT);
            info = "HYPER-SPACE LINK ";
            app.text(info, 50, 35);

            app.fill(currentSystem.getFaction().getFactionColour().getRGB());

            String from = currentSystem.getName().toUpperCase();
            app.text(from, 50 + app.textWidth(info), 35);
            info += from;

            app.fill(Color.WHITE.getRGB());
            app.text(" TO ", 50 + app.textWidth(info), 35);
            info += " TO ";
            String to = "[PLEASE SELECT DESIRED JUMP]";
            if (destinationSystem != null) {
                to = destinationSystem.getName().toUpperCase();
                app.fill(destinationSystem.getFaction().getFactionColour().getRGB());
            }

            app.text(to, 50 + app.textWidth(info), 35);
            info += to;

            if (destinationSystem != null) {
                app.fill(Color.WHITE.getRGB());
                String selected = " SELECTED";
                app.text(selected, 50 + app.textWidth(info), 35);
                info += selected;
            }
        }

        app.rectMode(App.CORNER); app.noFill(); app.strokeWeight(4);
        app.rect(40, 8, app.textWidth(info) + 20, 38, 10); app.strokeWeight(3);
    }

    private void drawGalaxyMapBackground() {
        PImage img = ImageCache.getImage("images/galaxy.png");
        app.imageMode(PConstants.CORNER);
        app.image(img, 0, 0, app.width, app.height);
    }

//    @Override
//    public void mousePressed(){
//        System.out.print("new PVector("+app.mouseX+","+app.mouseY+"), ");
//    }

}
