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

        createButton("PART REPAIR\n10 GC", repairCenterX - 60, y + 90, 100, 80, (state.getPlayerCredits()<10  || repairNeeded == 0) ? null : Stage.HANGAR_PART_REPAIR);
        createButton("FULL REPAIR\n"+fullRepairCost+" GC", repairCenterX + 60, y + 90, 100, 80, (state.getPlayerCredits()<fullRepairCost  || repairNeeded == 0) ? null : Stage.HANGAR_FULL_REPAIR);

        //missiles
        int missilesCenterX = x+(w/2-10)/2;
        app.textAlign(PConstants.CENTER);
        app.text("BUY MISSILES", missilesCenterX , y+h/2+30);
        createButton("BUY 1 MISSILE\n50 GC", missilesCenterX - 60, y+h/2 + 90, 100, 80, (state.getPlayerCredits()<50) ? null : Stage.HANGAR_BUY_1_MISSILE);
        createButton("BUY 3 MISSILES\n100 GC", missilesCenterX + 60, y+h/2 + 90, 100, 80, (state.getPlayerCredits()<100) ? null : Stage.HANGAR_BUY_3_MISSILES);

        //upgrades
        int upgradesCenterX = x+3*w/4;
        app.text("SHIP UPGRADES", upgradesCenterX, y+30);

        createButton("INCREASE MAX HULL\n500 GC", upgradesCenterX, y+60, 250, 40, (state.getPlayerCredits()<500) ? null : Stage.HANGAR_UPGRADE_HULL, 15);
        createButton("INCREASE LASER RANGE\n1000 GC", upgradesCenterX, y+115, 250, 40, (state.getPlayerCredits()<1000) ? null : Stage.HANGAR_UPGRADE_LASER_RANGE, 15);
        createButton("REDUCE LASER RECHARGE TIME\n1000 GC", upgradesCenterX, y+170, 250, 40, (state.getPlayerCredits()<1000) ? null : Stage.HANGAR_UPGRADE_LASER_RECHARGE, 15);
        createButton("INCREASE ENGINE STRENGTH\n1000 GC", upgradesCenterX, y+225, 250, 40, (state.getPlayerCredits()<1000) ? null : Stage.HANGAR_UPGRADE_ENGINE, 15);
        createButton("INCREASE TURNING SPEED\n1000 GC", upgradesCenterX, y+280, 250, 40, (state.getPlayerCredits()<1000) ? null : Stage.HANGAR_UPGRADE_TURNING_SPEED, 15);

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
        app.text("Turn Speed: "  + playerShip.getTurningSpeed()*5, 60, 300);
        app.text("Missiles: " + playerShip.getMissiles(), 60, 330);
        app.text("Laser Recharge: "  + playerShip.getLaserRechargeSpeed(), 60, 360);
        app.text("Laser Range: "  + playerShip.getLaserDistance(), 60, 390);
    }

    public void processOption(Stage userSelection) {
        int availableGC = state.getPlayerCredits();
        int hull = playerShip.getHull(), maxHull = playerShip.getMaxHull();
        switch (userSelection){
            case HANGAR_PART_REPAIR:
                if(availableGC < 10 || hull == maxHull) break;
                playerShip.setHull((hull + 100 > maxHull) ? maxHull : (hull + 100));
                state.setPlayerCredits(availableGC - 10);
                break;
            case HANGAR_FULL_REPAIR:
                int repairNeeded = maxHull - hull;
                int fullRepairCost = (repairNeeded % 100 == 0) ? (repairNeeded/10) : (repairNeeded/10 + 1);
                if(repairNeeded == 0 || availableGC < fullRepairCost) break;
                state.setPlayerCredits(availableGC - fullRepairCost);
                playerShip.setHull(maxHull);
                break;
            case HANGAR_BUY_1_MISSILE:
                if(availableGC < 50) break;
                playerShip.setMissiles(playerShip.getMissiles() + 1);
                state.setPlayerCredits(availableGC - 50);
                break;
            case HANGAR_BUY_3_MISSILES:
                if(availableGC < 100) break;
                state.setPlayerCredits(availableGC - 100);
                playerShip.setMissiles(playerShip.getMissiles() + 3);

                break;
            case HANGAR_UPGRADE_HULL:
                if(availableGC < 500) break;
                state.setPlayerCredits(availableGC - 500);
                int increaseSize = maxHull / 10; //increase by 10%
                playerShip.setMaxHull(maxHull + increaseSize);
                playerShip.setHull(hull + increaseSize);
                break;
            case HANGAR_UPGRADE_LASER_RANGE:
                if(availableGC < 1000) break;
                state.setPlayerCredits(availableGC - 1000);
                playerShip.setLaserDistance((int)(playerShip.getLaserDistance()* 1.1)); //increase by 10%
                break;
            case HANGAR_UPGRADE_LASER_RECHARGE:
                if(availableGC < 1000) break;
                state.setPlayerCredits(availableGC - 1000);
                playerShip.setLaserRechargeSpeed((int)(playerShip.getLaserRechargeSpeed() * 1.25)); //increase by 25%
                break;
            case HANGAR_UPGRADE_ENGINE:
                if(availableGC < 1000) break;
                state.setPlayerCredits(availableGC - 1000);
                playerShip.setEngineStrength((float)(playerShip.getEngineStrength() * 1.10)); //increase by 10%
                break;
            case HANGAR_UPGRADE_TURNING_SPEED:
                if(availableGC < 1000) break;
                state.setPlayerCredits(availableGC - 1000);
                playerShip.setTurningSpeed((int)(playerShip.getTurningSpeed() * 1.20)); //increase by 20%
                break;
            default:
                System.out.println(userSelection.name() + " is not a hangar option");
        }
    }
}
