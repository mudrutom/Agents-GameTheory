package mudrutom.game;

public class Cell {

	private final int x, y;

	private final char cell;

	public Cell(int x, int y, char cell) {
		this.x = x;
		this.y = y;
		this.cell = cell;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final Cell cell1 = (Cell) o;

		if (cell != cell1.cell) return false;
		if (x != cell1.x) return false;
		if (y != cell1.y) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = x;
		result = 31 * result + y;
		result = 31 * result + (int) cell;
		return result;
	}

	@Override
	public String toString() {
		return String.valueOf(cell);
	}
}
