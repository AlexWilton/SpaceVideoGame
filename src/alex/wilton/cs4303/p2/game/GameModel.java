package alex.wilton.cs4303.p2.game;

import alex.wilton.cs4303.p2.game.entity.Missile;
import alex.wilton.cs4303.p2.game.entity.Particle;
import alex.wilton.cs4303.p2.game.entity.Planet;
import alex.wilton.cs4303.p2.game.entity.collection.MissileSet;

/**
 * Game Class contains the full state of the game.
 * State includes player, planet, particles, missiles, bombers and stage/screen information
 */
public class GameModel {
    private Planet planet;

    private alex.wilton.cs4303.p2.game.entity.collection.ParticleWave particleWave;
    private MissileSet missilesInMotion;
    private alex.wilton.cs4303.p2.game.entity.collection.BomberWave bomberWave;
    private int lvlNumber = 1;

    private int score = 0;
    private int numberOfMissiles = 15;
    private int particlesDestroyed = 0;
    /**
     *  Time (in frames) till end of level.
     */
    private int lvlTimeRemaining;

    enum Stage {PREGAME, PLAY, LEVELEND, GAMEOVER, SHOP}
    private Stage gameStage = Stage.PREGAME; //stage is used to determine which screen to show
    private alex.wilton.cs4303.p2.game.screen.Screen screen;

    public GameModel(Planet planet){
        this.planet = planet;
        setupLvl();
    }

    /**
     * Reset game components. Makes game ready to start a new level
     */
    public void setupLvl(){
        particleWave = new alex.wilton.cs4303.p2.game.entity.collection.ParticleWave();
        missilesInMotion = new MissileSet();
        bomberWave = new alex.wilton.cs4303.p2.game.entity.collection.BomberWave();
        particlesDestroyed = 0;
        lvlTimeRemaining = (10 + lvlNumber * 5) * 60;
    }

    /**
     * Draw the correct screen. Game Stage is used to determine which screen to show
     */
    public void draw(){
        switch(gameStage){
            default:
            case PREGAME:
                screen = new alex.wilton.cs4303.p2.game.screen.PreGameScreen(this);
                break;
            case LEVELEND:
                screen = new alex.wilton.cs4303.p2.game.screen.EndOfLvlScreen(this);
                break;
            case GAMEOVER:
                screen = new alex.wilton.cs4303.p2.game.screen.GameOverScreen(this);
                break;
            case SHOP:
                screen = new alex.wilton.cs4303.p2.game.screen.ShopScreen(this);
                break;
            case PLAY:
                screen = new alex.wilton.cs4303.p2.game.screen.GamePlayScreen(this);
                lvlTimeRemaining--; //decrement game timer
                if(lvlTimeRemaining == 0) gameStage = Stage.LEVELEND;
                break;
        }
        screen.draw();
    }

    /**
     * Use screen specific mouse pressed handler.
     */
    public void mousePressed(){
        screen.mousePressed();
    }

    /**
     * Use cities remaining, missiles remaining and particles destroyed to determine subtotal
     * @return subtotal
     */
    public int calculateSubTotal(){
        return planet.citiesRemaining() * 50 + numberOfMissiles * 2 + particlesDestroyed * 5;
    }



    /* GAME TRANSITIONS (to a new stage) */
    /**
     * Calculate score from last level. Setup next level. Visit shop (before starting next level)
     */
    public void calculateScoreSetupNextLvlAndVisitShop(){
        score += calculateSubTotal();
        lvlNumber++;
        setupLvl();
        gameStage = Stage.SHOP;
    }
    public void startLevel(){gameStage = Stage.PLAY;}
    public void gameOver() {
        gameStage = Stage.GAMEOVER;
    }




    /* GAME ACTIONS*/
    /**
     * If missile available, fire missile at target co-ordinates
     * @param targetX Target X Co-ordinate
     * @param targetY Target Y Co-ordinate
     */
    public void fireMissileAt(int targetX, int targetY) {
        if(numberOfMissiles > 0) {
            missilesInMotion.addMissile(new Missile(planet.getMissileBase(), targetX, targetY));
            numberOfMissiles--;
            SoundEffects.playMissileLaunch();
        }
    }

    /**
     * Particle has been shot down.
     * Increase particle destroyed count and destroy particle
     * @param particle Particle shot down
     */
    public void particleShotDown(Particle particle){
        particlesDestroyed++;
        particleWave.remove(particle);
        SoundEffects.playSmallExplosion();
    }

    /**
     * Attempt to rebuild a city at the cost of 200 points
     */
    public void attemptToBuyCity() {
        if(score >= 200 && planet.citiesRemaining() < planet.getMaxNumberOfCities()){
            score -= 200;
            planet.rebuildOneCity();
        }
    }

    /**
     * Attempt to buy 10 missiles at the cost of 25 points
     */
    public void attemptToBuyMissiles() {
        if(score >= 25){
            score -= 25;
            numberOfMissiles += 10;
        }
    }



    public Planet getPlanet(){
        return planet;
    }

    public int getLvlNumber() {
        return lvlNumber;
    }

    public int getScore() {
        return score;
    }

    public int getNumberOfMissiles() {
        return numberOfMissiles;
    }

    public int getParticlesDestroyed() {
        return particlesDestroyed;
    }

    public int getLvlTimeRemaining() {
        return lvlTimeRemaining;
    }

    public alex.wilton.cs4303.p2.game.entity.collection.ParticleWave getParticleWave() {
        return particleWave;
    }

    public MissileSet getMissilesInMotion() { return missilesInMotion;}

    public alex.wilton.cs4303.p2.game.entity.collection.BomberWave getBomberWave() { return bomberWave; }
}