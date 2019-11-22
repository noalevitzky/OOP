import oop.ex2.GameGUI;

import java.awt.*;

/**
 * This class represents a HumanShip that is controlled by the user's gestures
 */
public class HumanShip extends SpaceShip {

    /**
     * This method overrides the one in the super class
     *
     * @return the current image that represents the human ship
     */
    public Image getImage() {
        if (this.isShieldOn())
            return GameGUI.SPACESHIP_IMAGE_SHIELD;
        return GameGUI.SPACESHIP_IMAGE;
    }

    /**
     * represents one iteration of the game, and enables the actions for the human ship
     *
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        startRound();

        // Teleport
        if (game.getGUI().isTeleportPressed())
            teleport();

        // Accelerate and turn (happen at the same time)
        boolean accel = false;
        int turn = 0;
        if (game.getGUI().isUpPressed())
            accel = true;
        if (game.getGUI().isLeftPressed())
            turn = LEFT_TURN;
        if (game.getGUI().isRightPressed())
            turn = RIGHT_TURN;
        getPhysics().move(accel, turn);

        // Shield activation
        if (game.getGUI().isShieldsPressed())
            shieldOn();

        // Firing a shot
        if (game.getGUI().isShotPressed())
            fire(game);

        endRound();
    }
}




