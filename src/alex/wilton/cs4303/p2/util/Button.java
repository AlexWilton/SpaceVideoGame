package alex.wilton.cs4303.p2.util;

import alex.wilton.cs4303.p2.game.Stage;

public class Button {
    private String text;
    private int x,y,width, height;
    private Stage newStageOnClick;
    private boolean disabled = false;

    public Button(String text, int x, int y, int width, int height, Stage newStageOnClick) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.newStageOnClick = newStageOnClick;
    }

    public Button(String text, int x, int y, int width, int height, Stage gameStage, boolean disabled) {
        this(text, x, y, width, height, gameStage);
        this.disabled = disabled;
    }

    public boolean containsPoint(int ptX, int ptY) {
        /* Check X co-ordinate*/
        if(!(x - width/2 <= ptX && ptX <= x + width/2)) return false;

        /* Check Y co-ordinate*/
        if(!(y - height/2 <= ptY && ptY <= y + height/2)) return false;

        return true;
    }

    public String getText() {
        return text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Stage getNewStageOnClick() {
        return newStageOnClick;
    }

    public boolean getDisabled() {
        return disabled;
    }

}
