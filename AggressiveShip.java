/**
 * This class represents an AggressiveShip
 */
public class AggressiveShip extends SpaceShip {
    private static final double SHOT_ANGLE = 0.21;

    /**
     * represents one iteration of the game, and enables the actions for the aggressive ship
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        startRound();

        // set variables and constants
        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleToClosestShip = this.getPhysics().angleTo(closestShip.getPhysics());

        // Accelerate and turn (happen at the same time)
        int turn = 0;
        if (angleToClosestShip > 0)
            turn = LEFT_TURN;
        if (angleToClosestShip < 0)
            turn = RIGHT_TURN;
        getPhysics().move(true, turn);

        // Firing a shot
        if (angleToClosestShip < SHOT_ANGLE)
            fire(game);

        endRound();
    }
}
