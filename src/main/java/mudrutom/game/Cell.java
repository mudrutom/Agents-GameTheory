package mudrutom.game;

public class Cell {

	private final int x, y;

	private final char cell;

	private final Direction direction;

	public Cell(int x, int y, char cell, Direction direction) {
		this.x = x;
		this.y = y;
		this.cell = cell;
		this.direction = direction;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public char getCell() {
		return cell;
	}

	public Direction getDirection() {
		return direction;
	}

	public boolean isFree() {
		return cell == Maze.FREE;
	}

	public boolean isGold() {
		return cell == Maze.GOLD;
	}

	public boolean isDanger() {
		return cell == Maze.DANGER;
	}

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
