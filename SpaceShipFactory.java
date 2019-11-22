/**
 * this class creates the spaceship in the game
 */
public class SpaceShipFactory {

    /**
     * an array representation of the game's ships
     */
    private static SpaceShip[] gameShips;

    /**
     * An array of available ship types, ordered by the indices stated in SpaceWar class
     */
    private static final char[] types = {'h', 'd', 'r', 'a', 'b', 's'};

    /**
     * Creates game ships
     *
     * @param args A string representation of the game's ships, supplied by the user.
     * @return An array of SpaceShip objects
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        gameShips = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++) {
            SpaceShip ship = null;

            // validate input inorder to be able to compare string with char
            if (args[i] != null && args[i].length() == 1) {
                char userInput = args[i].charAt(0);

                if (userInput == types[SpaceWars.HUMAN_CONTROLLED_SHIP]) {
                    ship = addHumanShip();
                } else if (userInput == types[SpaceWars.DRUNKARD_SHIP]) {
                    ship = addDrunkardShip();
                } else if (userInput == types[SpaceWars.RUNNER_SHIP]) {
                    ship = addRunnerShip();
                } else if (userInput == types[SpaceWars.AGGRESSIVE_SHIP]) {
                    ship = addAggressiveShip();
                } else if (userInput == types[SpaceWars.BASHER_SHIP]) {
                    ship = addBasherShip();
                } else if (userInput == types[SpaceWars.SPECIAL_SHIP]) {
                    ship = addSpecialShip();
                }
            }
            gameShips[i] = ship;
        }
        return gameShips;
    }

    /**
     * creates a new HumanShip
     *
     * @return HumanShip
     */
    private static HumanShip addHumanShip() {
        HumanShip ship = new HumanShip();
        return ship;
    }

    /**
     * creates a new DrunkardShip
     *
     * @return DrunkardShip
     */
    private static DrunkardShip addDrunkardShip() {
        DrunkardShip ship = new DrunkardShip();
        return ship;
    }

    /**
     * creates a new RunnerShip
     *
     * @return RunnerShip
     */
    private static RunnerShip addRunnerShip() {
        RunnerShip ship = new RunnerShip();
        return ship;
    }

    /**
     * creates a new AggressiveShip
     *
     * @return AggressiveShip
     */
    private static AggressiveShip addAggressiveShip() {
        AggressiveShip ship = new AggressiveShip();
        return ship;
    }

    /**
     * creates a new BasherShip
     *
     * @return BasherShip
     */
    private static BasherShip addBasherShip() {
        BasherShip ship = new BasherShip();
        return ship;
    }

    /**
     * creates a new SpecialShip
     *
     * @return SpecialShip
     */
    private static SpecialShip addSpecialShip() {
        SpecialShip ship = new SpecialShip();
        return ship;
    }

}
