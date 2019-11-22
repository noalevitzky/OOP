/**
 * This class represents an RunnerShip
 */
public class RunnerShip extends SpaceShip {

    private static final double TELEPORT_DISTANCE = 0.25;
    private static final double TELEPORT_ANGLE = 0.23;


    /**
     * represents one iteration of the game, and enables the actions for the runner ship
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        startRound();

        SpaceShip closestShip = game.getClosestShipTo(this);
        double distanceFromClosestShip = this.getPhysics().distanceFrom(closestShip.getPhysics());
        double angleToClosestShip = this.getPhysics().angleTo(closestShip.getPhysics());


        // Teleport
        if (distanceFromClosestShip < TELEPORT_DISTANCE && angleToClosestShip < TELEPORT_ANGLE)
            teleport();

        // Accelerate and turn (happen at the same time)
        int turn = 0;
        if (angleToClosestShip > 0)
            turn = RIGHT_TURN;
        if (angleToClosestShip < 0)
            turn = LEFT_TURN;
        getPhysics().move(true, turn);

        endRound();
    }

}
