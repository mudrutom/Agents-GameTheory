package mudrutom.game;

import java.util.List;

/**
 * Class representing positions of the bandits.
 */
public class BanditPositions implements GameConstants {

	/** Positions of the bandits. */
	protected final List<Cell> banditPositions;

	/** The BanditPositions class constructor. */
	public BanditPositions(List<Cell> banditPositions) {
		this.banditPositions = banditPositions;
	}

	/** @return <tt>true</tt> iff a bandit is at given cell */
	public boolean contains(Cell cell) {
		return cell.isDanger() && banditPositions.contains(cell);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof BanditPositions)) return false;

		final BanditPositions b = (BanditPositions) o;
		return banditPositions.equals(b.banditPositions);
	}

	@Override
	public int hashCode() {
		return banditPositions.hashCode();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (Cell cell : banditPositions) {
			sb.append(String.format("(%d,%d)", cell.getX(), cell.getY()));
		}
		return sb.toString();
	}
}
