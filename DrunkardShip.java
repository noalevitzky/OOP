import java.util.Random;

/**
 * This class represents an DrunkardShip
 */
public class DrunkardShip extends SpaceShip {

    private Random rand = new Random();

    /**
     * represents one iteration of the game, and enables the actions for the drunkard ship
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        startRound();

        boolean random = rand.nextBoolean();

        // Accelerate and turn (happen at the same time)
        getPhysics().move(random, LEFT_TURN);

        // Firing a shot
        if (random)
            fire(game);

        endRound();
    }
}

