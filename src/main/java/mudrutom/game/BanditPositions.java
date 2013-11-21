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

	/** @return <tt>true</tt> iff the bandit positions is empty */
	public boolean isEmpty() {
		return banditPositions.isEmpty();
	}

	/** @return the number of bandits at given cells */
	public int getBanditsCrossed(List<Cell> cell) {
		int bandits = 0;
		for (Cell banditPosition : banditPositions) {
			if (cell.contains(banditPosition)) bandits++;
		}
		return bandits;
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
		if (banditPositions.isEmpty()) {
			return "{}";
		}

		final StringBuilder sb = new StringBuilder();
		for (Cell cell : banditPositions) {
			sb.append(String.format("(%d,%d)", cell.getX(), cell.getY()));
		}
		return sb.toString();
	}
}
