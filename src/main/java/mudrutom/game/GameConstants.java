package mudrutom.game;

/**
 * An interface providing the game constants.
 */
public interface GameConstants {

	/** Maze cell content constants. */
	public static final char FREE = '-', OBSTACLE = '#', START = 'S', DESTINATION = 'D', GOLD = 'G', DANGER = 'E';

	/** Constants for number of received points. */
	public static final double POINTS_FOR_DESTINATION = 10.0, POINTS_FOR_GOLD = 1.0;

}
