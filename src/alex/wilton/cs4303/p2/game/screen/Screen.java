package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.util.Button;
import processing.core.PConstants;
import processing.event.KeyEvent;

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

    protected static int BUTTON_TEXT_PADDING = 1;

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
            if(button.getNewStageOnClick() == null) continue;
            if(button.containsPoint(x,y)){
                state.setGameStage(button.getNewStageOnClick());
                return;
            }
        }
    }

    /**
     * Respond to key press events (Method to be overridden)
     * @param e
     */
    public void keyPressed(KeyEvent e){}

    private void drawButtons(){
        app.rectMode(App.CENTER);
        app.strokeWeight(3);
        app.stroke(Color.WHITE.getRGB());
        app.textAlign(App.CENTER, App.CENTER);
        app.textSize(18);
        for(Button button : screenButtons){
            boolean disabled = button.getDisabled();
            app.stroke(Color.WHITE.getRGB(), (disabled) ? 100 : 1000);

            boolean mouseOver = button.containsPoint(app.mouseX, app.mouseY);
            if(mouseOver && !disabled){
                app.fill(Color.WHITE.getRGB());
            }else{
                app.fill(Color.WHITE.getRGB());
                app.noFill();
            }

            app.rect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
            app.fill(((mouseOver && !disabled) ? (Color.BLACK) : (Color.WHITE)).getRGB()); app.noFill();
            app.text(   button.getText(), button.getX() + BUTTON_TEXT_PADDING,
                        button.getY() + BUTTON_TEXT_PADDING,
                        button.getWidth() - BUTTON_TEXT_PADDING*2,
                        button.getHeight() - BUTTON_TEXT_PADDING*2);
        }
    }

    protected void createButton(String text, int x, int y, int width, int height, Stage newStageOnClick){
        screenButtons.add(new Button(text, x, y, width, height, newStageOnClick));
    }

    protected void createdDisabledButton(String text, int x, int y, int width, int height){
        screenButtons.add(new Button(text, x, y, width, height, state.getGameStage(), true));
        app.noFill(); app.stroke(Color.WHITE.getRGB(), 100);
        app.rectMode(PConstants.CENTER);
        app.rect(130, (int) (app.height * 0.75), 180, 50);
    }

    protected void createTextBox(String text, int x, int y, int width, int height){
       createButton(text, x, y, width, height, null);
    }

}
