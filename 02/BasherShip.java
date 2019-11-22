/**
 * This class represents an BasherShip
 */
public class BasherShip extends SpaceShip {
    private static final double SHIELD_DISTANCE = 0.19;

    /**
     * represents one iteration of the game, and enables the actions for the basher ship
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        startRound();

        SpaceShip closestShip = game.getClosestShipTo(this);
        double distanceFromClosestShip = this.getPhysics().distanceFrom(closestShip.getPhysics());
        double angleToClosestShip = this.getPhysics().angleTo(closestShip.getPhysics());

        // Accelerate and turn (happen at the same time)
        int turn = 0;
        if (angleToClosestShip > 0)
            turn = LEFT_TURN;
        if (angleToClosestShip < 0)
            turn = RIGHT_TURN;
        getPhysics().move(true, turn);

        // Shield activation
        if (distanceFromClosestShip <= SHIELD_DISTANCE)
            shieldOn();

        endRound();
    }
}
