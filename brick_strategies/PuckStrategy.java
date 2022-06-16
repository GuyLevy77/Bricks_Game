package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.util.Vector2;
import src.gameobjects.Puck;

import java.util.Random;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces several pucks instead of brick once removed.
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator {

    private static final String PUCK_IMAGE = "assets/mockBall.png";
    private static final String SOUND_FILE = "assets/blop_cut_silenced.wav";
    private static final String PUCK = "Puck";


    private ImageReader imageReader;
    private SoundReader soundReader;
    private Random rand;
    private boolean alreadyCollidedWith;


    /**
     * Constructor
     *
     * @param toBeDecorated
     * @param imageReader
     * @param soundReader
     */
    public PuckStrategy(CollisionStrategy toBeDecorated,
                        danogl.gui.ImageReader imageReader,
                        danogl.gui.SoundReader soundReader) {

        super(toBeDecorated);

        this.imageReader = imageReader;
        this.soundReader = soundReader;
        rand = new Random();
        alreadyCollidedWith = false;

    }

    /**
     * Add pucks to game on collision and delegate to held CollisionStrategy.
     *
     * @param thisObj
     * @param otherObj
     * @param counter  - global brick counter.
     */
    @Override //onCollision in class RemoveBrickStrategyDecorator
    public void onCollision(danogl.GameObject thisObj, danogl.GameObject otherObj,
                            danogl.util.Counter counter) {
        if (alreadyCollidedWith)
            return;
        alreadyCollidedWith = true;

        super.onCollision(thisObj, otherObj, counter);
        float ballSpeed = (float) Math.sqrt((float) Math.pow(otherObj.getVelocity().x(), 2)
                + (float) Math.pow(otherObj.getVelocity().y(), 2));
        float puckSize = thisObj.getDimensions().x() / 3;
        createThreePucks(thisObj, puckSize, ballSpeed);
    }

    private void createThreePucks(GameObject thisObj, float puckSize, float ballSpeed) {
        for (int i = 0; i < 3; i++) {
            GameObject puck = createPuck(thisObj, puckSize);
            getGameObjectCollection().addGameObject(puck);
            puck.setTag(PUCK);
            setVelocityPuck(puck, ballSpeed);
        }
    }

    private GameObject createPuck(GameObject thisObj, float puckSize) {
        return new Puck(
                new Vector2(thisObj.getCenter().x() - (puckSize / 2), thisObj.getCenter().y()),
                new Vector2(puckSize, puckSize),
                imageReader.readImage(PUCK_IMAGE, true),
                soundReader.readSound(SOUND_FILE));
    }

    private void setVelocityPuck(GameObject puck, float ballSpeed) {
        float ballVelX = 1;
        float ballVelY = 1;

        if (rand.nextBoolean())
            ballVelX *= -1;

        if (rand.nextBoolean())
            ballVelY *= -1;

        puck.setVelocity(new Vector2(ballVelX, ballVelY).normalized().mult(ballSpeed));
    }
}
