package mudrutom.game;

/**
 * Class representing a cell (square) in the game map.
 */
public class Cell {

	/** Cell <tt>x</tt> and <tt>y</tt> coordinates. */
	private final int x, y;

	/** The content of the cell. */
	private final char cell;

	/** A direction from the previous cell to this one. */
	private final Direction direction;

	/** The Cell class constructor. */
	public Cell(int x, int y, char cell, Direction direction) {
		this.x = x;
		this.y = y;
		this.cell = cell;
		this.direction = direction;
	}

	/** @return the <tt>x</tt> coordinate of this cell */
	public int getX() {
		return x;
	}

	/** @return the <tt>y</tt> coordinate of this cell */
	public int getY() {
		return y;
	}

	/** @return the content of this cell */
	public char getCell() {
		return cell;
	}

	/** @return the direction to this cell from the previous cell */
	public Direction getDirection() {
		return direction;
	}

	/** <tt>true</tt> iff this cell is free */
	public boolean isFree() {
		return cell == Maze.FREE;
	}

	/** <tt>true</tt> iff this cell contains gold */
	public boolean isGold() {
		return cell == Maze.GOLD;
	}

	/** <tt>true</tt> iff this cell is dangerous (could contain bandit) */
	public boolean isDanger() {
		return cell == Maze.DANGER;
	}

	/** <tt>true</tt> iff this cell is a destination cell */
	public boolean isDestination() {
		return cell == Maze.DESTINATION;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof Cell)) return false;

		final Cell c = (Cell) o;
		return (cell == c.cell) && (x == c.x) && (y == c.y);
	}

	@Override
	public int hashCode() {
		int result = cell;
		result = 31 * result + x;
		result = 31 * result + y;
		return result;
	}

	@Override
	public String toString() {
		return "(" + String.valueOf(cell) + ")";
	}
}
