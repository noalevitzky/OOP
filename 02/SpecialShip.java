/**
 * This class represents an SpecialShip
 */
public class SpecialShip extends SpaceShip {

    /**
     * represents one iteration of the game, and enables the actions for the special ship
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        startRound();

        SpaceShip closestShip = game.getClosestShipTo(this);
        double angleToClosestShip = this.getPhysics().angleTo(closestShip.getPhysics());

        if (closestShip instanceof HumanShip)
            attackHumanShip(game, angleToClosestShip);

        else
            improveLocation();

        endRound();
    }

    /**
     * the ship tries to get closer to the Humanship.
     */
    private void improveLocation() {
        // Teleport
        teleport();

        // Accelerate and turn (happen at the same time)
        getPhysics().move(false, 0);
    }

    /**
     * follows the humanship and attacks it
     *
     * @param game the game object to which this ship belongs.
     * @param angleToClosestShip indicates the direction in which the ship should turn to face the humanship
     */
    private void attackHumanShip(SpaceWars game, double angleToClosestShip) {
        // Accelerate and turn (happen at the same time)
        int turn = 0;
        if (angleToClosestShip > 0)
            turn = LEFT_TURN;
        if (angleToClosestShip < 0)
            turn = RIGHT_TURN;
        getPhysics().move(true, turn);

        // Firing a shot
        fire(game);
    }
}
