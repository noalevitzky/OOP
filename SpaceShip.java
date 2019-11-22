import java.awt.Image;

import oop.ex2.*;

/**
 * The API spaceships need to implement for the SpaceWars game. This is an abstract class.
 *
 * @author oop
 */
public abstract class SpaceShip {

    /**
     * represents the position, direction and velocity of the ship.
     */
    private SpaceShipPhysics shipPhysics;

    /**
     * starts at 210 and acts as the energy limit.
     */
    private int maxEnergy;

    /**
     * starts at 190. Actions reduce the current energy level, and could not be preformed when require more
     * than the cur energy level.
     */
    private int curEnergy;

    /**
     * starts at 22, and reduced by 1 when ship is being shot/ collides with other ship, while not having a
     * shield. When 0 is reached, ship is dead.
     */
    private int health;

    /**
     * true if shield is on in this round, false otherwise
     */
    private boolean shield;

    /**
     * represents the remaining rounds in which the ship can't fire.
     * the max value is 7, and min is 0 (which means the ship can fire)
     */
    private int coolDownCounter;

    /**
     * magic numbers of the game
     */
    private static final int COOL_DOWN_PERIOD = 7;
    private static final int ROUND_ENERGY = 1;
    private static final int COLLIDE_SHIELD_ENERGY = 18;
    private static final int COLLIDE_ENERGY = -10;
    private static final int GOT_HIT_ENERGY = -10;
    private static int HEALTH_LOSS = -1;
    private static final int SHIELD_COST = 3;
    private static final int FIRE_COST = 19;
    private static final int TELEPORT_COST = 140;
    private static final int MAX_ENERGY = 210;
    private static final int CUR_ENERGY = 190;
    private static final int HEALTH = 22;
    public static final int RIGHT_TURN = -1;
    public static final int LEFT_TURN = 1;


    /**
     * creates a new spaceship.
     */
    public SpaceShip() {
        this.shipPhysics = new SpaceShipPhysics();
        this.maxEnergy = MAX_ENERGY;
        this.curEnergy = CUR_ENERGY;
        this.health = HEALTH;
        this.shield = false;
        this.coolDownCounter = 0;
    }

    /**
     * Does the actions of this ship for this round.
     * This is called once per round by the SpaceWars game driver.
     * this is an abstract method, which is implemented in its subclasses.
     *
     * @param game the game object to which this ship belongs.
     */
    public abstract void doAction(SpaceWars game);

    /**
     * this method is called at the beginning of each round
     */
    public void startRound() {
        setShieldOff();
        startRoundCoolDownUpdate();
    }

    /**
     * this method is called at the end of each round
     */
    public void endRound() {
        endRoundEnergyUpdate();
    }

    /**
     * This method is called every time a collision with this ship occurs
     */
    public void collidedWithAnotherShip() {
        if (isShieldOn()) {
            setMaxEnergy(COLLIDE_SHIELD_ENERGY);
            setCurEnergy(COLLIDE_SHIELD_ENERGY);
        } else {
            setHealth(HEALTH_LOSS);
            setMaxEnergy(COLLIDE_ENERGY);
            if (getCurEnergy() > getMaxEnergy()) {
                setMaxEnergy(getCurEnergy());
            }
        }
    }

    /**
     * This method is called whenever a ship has died. It resets the ship's
     * attributes, and starts it at a new random position.
     */
    public void reset() {
        this.shipPhysics = new SpaceShipPhysics();
        this.maxEnergy = MAX_ENERGY;
        this.curEnergy = CUR_ENERGY;
        this.health = HEALTH;
        this.shield = false;
        this.coolDownCounter = 0;
    }

    /**
     * Checks if this ship is dead.
     *
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return getHealth() == 0;
    }

    /**
     * Gets the physics object that controls this ship.
     *
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return this.shipPhysics;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!isShieldOn()) {
            setHealth(HEALTH_LOSS);
            setMaxEnergy(GOT_HIT_ENERGY);
            if (getCurEnergy() > getMaxEnergy())
                setCurEnergy(getMaxEnergy());
        }
    }

    /**
     * Gets the image of this ship. This method should return the image of the
     * ship with or without the shield. This will be displayed on the GUI at
     * the end of the round.
     *
     * @return the image of this ship.
     */
    public Image getImage() {
        if (this.isShieldOn())
            return GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD;
        return GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     *
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (getCurEnergy() >= FIRE_COST && canFire()) {
            game.addShot(getPhysics());
            setCurEnergy(-FIRE_COST);
            setCoolDown();
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (getCurEnergy() >= SHIELD_COST) {
            this.shield = true;
            setCurEnergy(-SHIELD_COST);
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
        if (getCurEnergy() >= TELEPORT_COST) {
            this.shipPhysics = new SpaceShipPhysics();
            setCurEnergy(-TELEPORT_COST);
        }
    }

    /**
     * set shild off at the end of every round
     */
    public void setShieldOff() {
        this.shield = false;
    }

    /**
     * get the health of the ship
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Set health
     *
     * @param num The added/ reduced value
     */
    public void setHealth(int num) {
        if (this.health + num >= 0)
            this.health += num;
        else this.health = 0;
    }

    /**
     * Get max energy
     */
    public int getMaxEnergy() {
        return this.maxEnergy;
    }

    /**
     * set max energy
     *
     * @param num The added/ reduced value
     */
    public void setMaxEnergy(int num) {
        if (this.maxEnergy + num >= 0)
            this.maxEnergy += num;
        else this.maxEnergy = 0;
    }

    /**
     * Get current energy
     */
    public int getCurEnergy() {
        return this.curEnergy;
    }

    /**
     * set current energy
     *
     * @param num The added/ reduced value
     */
    public void setCurEnergy(int num) {
        if (this.curEnergy + num >= 0)
            this.curEnergy += num;
        else this.curEnergy = 0;
    }

    /**
     * @return true upon shield is active, false otherwise
     */
    public boolean isShieldOn() {
        return this.shield;
    }

    /**
     * @return true if the ship can fire a shot, false if a shot is still active (7 rounds of cooldown period)
     */
    public boolean canFire() {
        return this.coolDownCounter == 0;
    }

    /**
     * update the cooldown counter. this method is called when the ship fires.
     */
    private void setCoolDown() {
        this.coolDownCounter = COOL_DOWN_PERIOD;
    }

    /**
     * update the cooldown counter. this method is called at the beginning of each round.
     */
    public void startRoundCoolDownUpdate() {
        if (this.coolDownCounter > 0)
            this.coolDownCounter--;
    }

    /**
     * raise the cur energy in every round by the roundEnergy
     */
    public void endRoundEnergyUpdate() {
        if (getCurEnergy() < getMaxEnergy())
            setCurEnergy(ROUND_ENERGY);
    }

}
