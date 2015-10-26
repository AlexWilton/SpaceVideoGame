package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.util.Button;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Screen.
 * Show information to player and wait for input (Mouse click)
 */
public abstract class Screen{
    protected GameState state;
    protected App app = App.app;

    public Screen(GameState state){this.state = state;}

    private Set<Button> screenButtons = new HashSet<>();

    /**
     * Draw Screen Elements (excluding buttons)
     */
    protected abstract void draw();

    /**
     * Draw All Screen Elements (including buttons)
     */
    public void drawScreen(){
        draw();
        drawButtons();
    }

    /**
     * Respond to mouse click events (e.g. listen for button clicks)
     */
    public void mousePressed(){
        int x = app.mouseX, y = app.mouseY;
        for(Button button : screenButtons){
            if(button.containsPoint(x,y)){
                state.setGameStage(button.getNewStageOnClick());
                return;
            }
        }
    }

    /**
     * Respond to key press events
     */
    public abstract void keyPressed();

    private void drawButtons(){
        app.rectMode(App.CENTER);
        app.strokeWeight(3);
        app.noFill();
        app.stroke(Color.WHITE.getRGB());
        app.textAlign(App.CENTER, App.CENTER);
        app.textSize(20);
        for(Button button : screenButtons){
            app.rect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
            app.text(button.getText(), button.getX(), button.getY());
        }
    }

    protected void createButton(String text, int x, int y, int width, int height, Stage newStageOnClick){
        screenButtons.add(new Button(text, x, y, width, height, newStageOnClick));

    }

}
