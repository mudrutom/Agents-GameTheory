package mudrutom.game;

/**
 * Direction enumeration type.
 */
public enum Direction {

	UP, RIGHT, DOWN, LEFT;

	/** @return short string representation of this direction */
	public String toShortString() {
		switch (this) {
			case UP: return "U";
			case RIGHT: return "R";
			case DOWN: return "D";
			case LEFT: return "L";
			default: return null;
		}
	}
}
