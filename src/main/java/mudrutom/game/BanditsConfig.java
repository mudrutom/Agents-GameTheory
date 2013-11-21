package mudrutom.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Class used for representing the bandits configurations.
 */
public class BanditsConfig implements GameConstants {

	/** The number of bandits on this maze. */
	private final int numberOfBandits;
	/** The probability of successful attack of a bandit. */
	private final double attackProbability;
	/** List of dangerous cells on the maze. */
	private final List<Cell> dangers;

	/** List of all possible bandit positions. */
	private List<List<Cell>> possiblePositions;

	/** The BanditsConfig class constructor. */
	public BanditsConfig(Maze maze) {
		this(maze.getNumberOfBandits(), maze.getAttackProbability(), maze.getDangers());
	}

	/** The BanditsConfig class constructor. */
	public BanditsConfig(int numberOfBandits, double attackProbability, List<Cell> dangers) {
		this.numberOfBandits = numberOfBandits;
		this.attackProbability = attackProbability;
		this.dangers = new ArrayList<Cell>(dangers);
		possiblePositions = null;
	}

	/** @return the number of bandits */
	public int getNumberOfBandits() {
		return numberOfBandits;
	}

	/** @return probability of successful attack of a bandit */
	public double getAttackProbability() {
		return attackProbability;
	}

	/** @return a list of all dangerous cells */
	public List<Cell> getDangers() {
		return dangers;
	}

	/** @return a list of all possible bandit positions */
	public List<List<Cell>> getPossiblePositions() {
		return (possiblePositions == null) ? generateAllPossiblePositions() : possiblePositions;
	}

	/**
	 * @return an expected utility value for given dangers on a path
	 *         and actual positions of the bandits
	 */
	public double computeExpectedUtility(double utility, List<Cell> dangersOnPath, List<Cell> banditPositions) {
		if (dangersOnPath.isEmpty() || utility == 0.0) {
			return utility;
		} else {
			int banditsCrossed = 0;
			for (Cell danger : dangersOnPath) {
				if (banditPositions.contains(danger)) banditsCrossed++;
			}
			return utility * Math.pow(1.0 - attackProbability, banditsCrossed);
		}
	}

	/** @return generated list of all possible bandit positions */
	protected List<List<Cell>> generateAllPossiblePositions() {
		possiblePositions = new LinkedList<List<Cell>>();

		// special case
		if (dangers.size() <= numberOfBandits) {
			possiblePositions.add(new ArrayList<Cell>(dangers));
			return possiblePositions;
		}

		// find all possible combinations (selecting k elements from n elements)
		final int n = dangers.size(), k = n - numberOfBandits;
		final int size = binomialCoefficient(n, k);

		// initialize
		final boolean[] index = new boolean[n];
		Arrays.fill(index, 0, k, false);
		Arrays.fill(index, k, n, true);

		// generate all combinations
		while (possiblePositions.size() < size) {
			possiblePositions.add(getDangersByIndex(index));
			nextCombination(index, n, 0);
		}

		return possiblePositions;
	}

	/** @return a list of danger cells by provided index */
	private List<Cell> getDangersByIndex(boolean[] index) {
		final List<Cell> result = new ArrayList<Cell>(numberOfBandits);
		for (int i = 0, n = index.length; i < n; i++) {
			if (index[i]) result.add(dangers.get(i));
		}
		return result;
	}

	/** Makes next combination from given index. */
	private static void nextCombination(boolean[] index, int n, int k) {
		int i = n - 1 - k;
		while (i >= 0 && index[i]) i--;
		if (i == n - 1 - k) {
			nextCombination(index, n, ++k);
		} else if (i >= 0) {
			index[i] = true;
			index[++i] = false;
			if (k > 0) {
				i++;
				Arrays.fill(index, i, i + k, false);
				Arrays.fill(index, i + k, n, true);
			}
		}
	}

	/** @return Binomial coefficient of <tt>n</tt> over <tt>k</tt> */
	private static int binomialCoefficient(int n, int k) {
		int result = 1;
		for (int i = n - k + 1; i <= n; i++) result *= i;
		for (int i = 1; i <= k; i++) result /= i;
		return result;
	}
}
