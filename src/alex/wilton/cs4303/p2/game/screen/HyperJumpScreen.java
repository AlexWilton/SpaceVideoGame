package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.*;
import alex.wilton.cs4303.p2.game.entity.staticImage.PlanetLogo;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.util.ImageCache;
import processing.core.PConstants;
import processing.core.PImage;
import processing.core.PVector;
import processing.event.KeyEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;


public class HyperJumpScreen extends Screen {

    private final GalaxySystem currentSystem;
    private final GalaxySystem destinationSystem;
    private final Galaxy galaxy;

    public HyperJumpScreen(GameState state) {
        super(state);
        currentSystem = state.getPlayerLocation();
        destinationSystem = state.getDestinationSystem();
        galaxy = state.getGalaxy();
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
        app.stroke(Color.WHITE.getRGB(), 1000);
        if(destinationSystem != null){
            destinationSystem.drawWhiteLineOnMap(currentSystem.getMapLocation());
            destinationSystem.drawAsWhiteOnMap(); //draw around destination
        }
        galaxy.drawSystemsOnMap();
        currentSystem.drawAsBlackOnMap(); //draw around origin
        drawOnHoverForAllowedJumps();

        drawButtons();
    }

    private void drawButtons() {
        if(destinationSystem != null)
            createButton("MAKE JUMP", 130, (int) (app.height * 0.75), 180, 50, Stage.MAKE_JUMP);
        else
            createdDisabledButton("MAKE JUMP", 130, (int) (app.height * 0.75), 180, 50);
        createButton("RETURN TO\nSYSTEM VIEW", 130, (int) (app.height * 0.9), 180, 50, Stage.SYSTEM);
    }

    private void drawOnHoverForAllowedJumps() {
        ArrayList<Galaxy.Link> allowedLinks = galaxy.findLinks(currentSystem.getId());
        for(Galaxy.Link link : allowedLinks){
            int otherSysId = (link.leftId == currentSystem.getId()) ? link.rightId : link.leftId;
            GalaxySystem otherSystem = galaxy.getSystems()[otherSysId];
            otherSystem.drawOnHoverOnMap();
        }
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


        app.rectMode(App.CORNER); app.stroke(Color.WHITE.getRGB(), 1000); app.noFill(); app.strokeWeight(4);
        app.rect(40, 8, app.textWidth(info) + 20, 38, 10); app.strokeWeight(3);
    }

    private void drawGalaxyMapBackground() {
        PImage img = ImageCache.getImage("images/galaxy.png");
        app.imageMode(PConstants.CORNER);
        app.image(img, 0, 0, app.width, app.height);
    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        /* Allow a destination system to be selected*/
        ArrayList<Galaxy.Link> allowedLinks = galaxy.findLinks(currentSystem.getId());
        for(Galaxy.Link link : allowedLinks){
            int otherSysId = (link.leftId == currentSystem.getId()) ? link.rightId : link.leftId;
            GalaxySystem otherSystem = galaxy.getSystems()[otherSysId];
            if(otherSystem.mouseOver()) {
                if(destinationSystem != otherSystem)
                    state.setDestinationSystem(otherSystem);
                else
                    state.setDestinationSystem(null);
                break;
            }
        }
    }

    /**
     * Respond to key press events
     * @param e Key Event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        /* Allowing cycling through options using arrow keys */
        if(e.getKeyCode() == App.LEFT || e.getKeyCode() == App.UP || (e.getKeyCode() == App.RIGHT || e.getKeyCode() == App.DOWN)){
            ArrayList<Galaxy.Link> allowedLinks = galaxy.findLinks(currentSystem.getId());
            Galaxy.Link selectedLink;
            if(destinationSystem == null){
                selectedLink = allowedLinks.get(allowedLinks.size()-1);
            }else{
                int currentIndex = 0;
                for(int i=0; i<allowedLinks.size(); i++){
                    if(allowedLinks.get(i).containsSystem(destinationSystem.getId())){
                        currentIndex = i; break;
                    }
                }
                int newIndex;
                if(e.getKeyCode() == App.LEFT || e.getKeyCode() == App.UP)
                    newIndex = (allowedLinks.size() + currentIndex - 1) % allowedLinks.size();
                else
                    newIndex = (currentIndex + 1) % allowedLinks.size();

                selectedLink = allowedLinks.get(newIndex);
            }

            int otherSysId = (selectedLink.leftId == currentSystem.getId()) ? selectedLink.rightId : selectedLink.leftId;
            GalaxySystem otherSystem = galaxy.getSystems()[otherSysId];
            state.setDestinationSystem(otherSystem);
        }


        /* Allow progression to next stage when enter/return is hit and minimum player name is reached */
        if((e.getKey() == App.ENTER || e.getKey() == App.RETURN) ){
            if(destinationSystem != null)
                state.setGameStage(Stage.MAKE_JUMP);
        }
    }

}
