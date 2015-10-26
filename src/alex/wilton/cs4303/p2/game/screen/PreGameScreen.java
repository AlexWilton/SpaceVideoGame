package alex.wilton.cs4303.p2.game.screen;

import processing.core.PImage;

/**
 * Class for displaying Pre Game Start Screen
 */
public class PreGameScreen extends Screen{
    private static PImage missileLauncherImg = alex.wilton.cs4303.p2.game.App.app.loadImage("images/missileLauncher.jpg");;

    public PreGameScreen(alex.wilton.cs4303.p2.game.GameModel gameModel){
        super(gameModel);
    }

    public void draw(){
        app.cursor(); //enable default cursor
        app.background(255);
        app.textSize(35);
        app.textAlign(alex.wilton.cs4303.p2.game.App.CENTER);
        app.fill(0);
        app.text("PARTICLE COMMANDER", alex.wilton.cs4303.p2.game.App.WINDOW_WIDTH / 2, 50);

        app.fill(0, 0, 255);
        app.textSize(16);
        app.text("Use missiles to defend your cities.", alex.wilton.cs4303.p2.game.App.WINDOW_WIDTH / 2, 95);
        app.text("Trade in points in-between levels to buy missiles or rebuild cities.", alex.wilton.cs4303.p2.game.App.WINDOW_WIDTH / 2, 115);



        app.imageMode(alex.wilton.cs4303.p2.game.App.CENTER);
        app.image(missileLauncherImg, alex.wilton.cs4303.p2.game.App.WINDOW_WIDTH / 2, 300, alex.wilton.cs4303.p2.game.App.WINDOW_WIDTH / 2, 300);


      /*START LEVEL BUTTON*/
        app.textSize(25);
        app.fill(0, 100, 0);
        app.rect(230, 500, 180, 50, 15);
        app.fill(255);
        app.textAlign(app.LEFT);
        app.text("START", 282, 532);

        app.textSize(13);
        app.textAlign(app.CENTER);
        app.fill(0, 0, 255);
        app.text("HINT: Only use missiles to defend cities or when more than one particle can be destroyed.", alex.wilton.cs4303.p2.game.App.WINDOW_WIDTH / 2, 600);


    }

    public void mousePressed(){
        /*Listener for START LEVEL button*/
        if( 230 <= app.mouseX && app.mouseX <= 230 + 180 && 500 <= app.mouseY && app.mouseY <= 500 + 50){
            gameModel.startLevel();
        }
    }


}
