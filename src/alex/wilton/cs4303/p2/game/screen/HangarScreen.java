package alex.wilton.cs4303.p2.game.screen;

import alex.wilton.cs4303.p2.game.App;
import alex.wilton.cs4303.p2.game.GameState;
import alex.wilton.cs4303.p2.game.Stage;
import alex.wilton.cs4303.p2.game.ships.DrawableShip;
import alex.wilton.cs4303.p2.game.ships.playerShip.PlayerShip;
import processing.core.PConstants;
import processing.core.PVector;

import java.awt.*;

public class HangarScreen extends Screen {
    PlayerShip playerShip;
    public HangarScreen(GameState gameState) {
        super(gameState);
        playerShip = gameState.getPlayerShip();
    }

    /**
     * Draw Screen Elements (excluding buttons)
     */
    @Override
    protected void draw() {
        app.background(Color.BLACK.getRGB());
        createdDisabledButton("Credits: " + state.getPlayerCredits() + " GC", 145, 30, 175, 40);

        app.textSize(30); app.fill(Color.WHITE.getRGB());
        app.text("Hangar", app.width / 2, 30);



        drawShipStatusPanel();
        drawOptionsPanel();
        createButton("System Screen", app.width/2, app.height-50, 200, 50, Stage.SYSTEM);
    }

    private void drawOptionsPanel(){
        app.rectMode(App.CORNER); app.noFill();
        app.rect((float) (0.3 * app.width), 100, (float) (0.6 * app.width), app.height - 200);

        //draw rectangles borders
        int x=298, y=110, w=556, h=320;
        app.rect(x, y, w/2-10, h/2-10);
        app.rect(x, y+h/2, w/2-10, h/2);
        app.rect(x+(w/2), y, w/2, h);


        //repairs
        int repairCenterX = x+(w/2-10)/2;
        app.textAlign(PConstants.CENTER);
        app.text("HULL REPAIRS", repairCenterX , y+30);
        int repairNeeded = playerShip.getMaxHull() - playerShip.getHull();
        int fullRepairCost = (repairNeeded % 100 == 0) ? (repairNeeded/10) : (repairNeeded/10 + 1);
        if(repairNeeded != 0) {
            createButton("PART REPAIR\n10 GC", repairCenterX - 60, y + 90, 100, 80, Stage.HANGAR_PART_REPAIR);
            createButton("FULL REPAIR\n"+fullRepairCost+" GC", repairCenterX + 60, y + 90, 100, 80, Stage.HANGAR_FULL_REPAIR);
        }else{
            createdDisabledButton("PART REPAIR\n10 GC", repairCenterX - 60, y + 90, 100, 80);
            createdDisabledButton("FULL REPAIR\n"+fullRepairCost+" GC", repairCenterX + 60, y + 90, 100, 80);
        }

        //missiles
        int missilesCenterX = x+(w/2-10)/2;
        app.textAlign(PConstants.CENTER);
        app.text("BUY MISSILES", repairCenterX , y+h/2+30);
        if(state.getPlayerCredits() >= 50)
            createButton("BUY 1 MISSILE\n50 GC", repairCenterX - 60, y+h/2 + 90, 100, 80, Stage.HANGAR_BUY_1_MISSILE);
        else
            createdDisabledButton("BUY 1 MISSILE\n50 GC", repairCenterX - 60, y + h / 2 + 90, 100, 80);

        if(state.getPlayerCredits() >= 100)
            createButton("BUY 3 MISSILES\n50 GC", repairCenterX + 60, y+h/2 + 90, 100, 80, Stage.HANGAR_BUY_3_MISSILES);
        else
            createdDisabledButton("BUY 3 MISSILES\n100 GC", repairCenterX + 60, y + h / 2 + 90, 100, 80);


    }

    private void drawShipStatusPanel(){
        DrawableShip ship = state.getPlayerShip().createDrawableShipInstance();
        ship.setCenterPosition(new PVector((int)(app.width/6.7), 150));
        ship.draw();
        app.rectMode(App.CORNER); app.fill(Color.WHITE.getRGB()); app.noFill();
        app.rect(50, 100, (float) (0.2 * app.width), app.height - 200);

        app.textSize(18); app.textAlign(PConstants.LEFT);
        app.text("Ship Status".toUpperCase(), 85, 210);
        app.text("Hull: " + playerShip.getHull() + "/" + playerShip.getMaxHull(), 60, 240);
        app.text("Engine: " + (int)(playerShip.getEngineStrength()*100), 60, 270);
        app.text("Turn Speed: "  + playerShip.getTurningSpeed(), 60, 300);
        app.text("Missiles: " + playerShip.getMissiles(), 60, 330);
        app.text("Laser Recharge: "  + playerShip.getLaserCoolDown(), 60, 360);
        app.text("Laser Range: "  + playerShip.getLaserDistance(), 60, 390);
    }

    public void processOption(Stage userSelection) {
        int availableGC = state.getPlayerCredits();
        int hull = playerShip.getHull(), maxHull = playerShip.getMaxHull();
        switch (userSelection){
            case HANGAR_PART_REPAIR:
                if(availableGC >=10 && hull < maxHull){
                    playerShip.setHull((hull+100>maxHull) ? maxHull : (hull+100));
                    state.setPlayerCredits(availableGC - 10);
                }
                break;
            case HANGAR_FULL_REPAIR:
                int repairNeeded = maxHull - hull;
                int fullRepairCost = (repairNeeded % 100 == 0) ? (repairNeeded/10) : (repairNeeded/10 + 1);
                if(repairNeeded > 0 && availableGC >= fullRepairCost){
                    playerShip.setHull(maxHull);
                    state.setPlayerCredits(availableGC - fullRepairCost);
                }

                break;
            case HANGAR_BUY_1_MISSILE:
                if(availableGC >= 50){
                    playerShip.setMissiles(playerShip.getMissiles() + 1);
                    state.setPlayerCredits(availableGC - 50);
                }
                break;
            case HANGAR_BUY_3_MISSILES:
                if(availableGC >= 100){
                    playerShip.setMissiles(playerShip.getMissiles() + 3);
                    state.setPlayerCredits(availableGC - 100);
                }
                break;
            case HANGAR_UPGRADE_HULL:

                break;
            case HANGAR_UPGRADE_LASER_RANGE:

                break;
            case HANGAR_UPGRADE_LASER_RECHARGE:

                break;
            case HANGAR_UPGRADE_ENGINE:

                break;
            case HANGAR_UPGRADE_TURNING_SPEED:

                break;
            default:
                System.out.println(userSelection.name() + " is not a hangar option");
        }
    }
}
