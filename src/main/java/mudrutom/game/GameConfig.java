package mudrutom.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Class used for representing the configurations of the game.
 */
public class GameConfig implements GameConstants {

	/** The number of bandits on this maze. */
	private final int numberOfBandits;
	/** The probability of successful attack of a bandit. */
	private final double attackProbability;
	/** List of dangerous cells on the maze. */
	private final List<Cell> dangers;

	/** List of all possible bandit positions. */
	private List<BanditPositions> possibleBanditPositions;

	/** The GameConfig class constructor. */
	public GameConfig(int numberOfBandits, double attackProbability, List<Cell> dangers) {
		this.numberOfBandits = numberOfBandits;
		this.attackProbability = attackProbability;
		this.dangers = new ArrayList<Cell>(dangers);
		possibleBanditPositions = null;
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
	public List<BanditPositions> getPossibleBanditPositions() {
		return (possibleBanditPositions == null) ? generateAllPossiblePositions() : possibleBanditPositions;
	}

	/**
	 * @return an expected utility value for given node
	 *         and actual positions of the bandits
	 */
	public double computeExpectedUtility(GameNode gameNode, BanditPositions banditPositions) {
		final double utility = gameNode.getUtility();
		final List<Cell > dangersOnPath = gameNode.getDangersOnPath();
		if (banditPositions.isEmpty()) {
			return 0.0;
		} else if (dangersOnPath.isEmpty() || utility == 0.0) {
			return utility;
		} else {
			int banditsCrossed = banditPositions.getBanditsCrossed(dangersOnPath);
			return utility * Math.pow(1.0 - attackProbability, banditsCrossed);
		}
	}

	/** @return generated list of all possible bandit positions */
	protected List<BanditPositions> generateAllPossiblePositions() {
		possibleBanditPositions = new LinkedList<BanditPositions>();
		possibleBanditPositions.add(new BanditPositions(Collections.<Cell>emptyList()));

		// special cases
		if (dangers.isEmpty() || numberOfBandits < 1) {
			return possibleBanditPositions;
		} else if (dangers.size() <= numberOfBandits) {
			possibleBanditPositions.add(new BanditPositions(dangers));
			return possibleBanditPositions;
		}

		// find all possible combinations (selecting k elements from n elements)
		final int n = dangers.size();
		final int k = n - numberOfBandits;
		final int size = binomialCoefficient(n, k) + 1;

		// initialize
		final boolean[] index = new boolean[n];
		Arrays.fill(index, 0, k, false);
		Arrays.fill(index, k, n, true);

		// generate all combinations
		while (possibleBanditPositions.size() < size) {
			possibleBanditPositions.add(new BanditPositions(getDangersByIndex(index)));
			nextCombination(index);
		}

		return possibleBanditPositions;
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
	private static void nextCombination(boolean[] index) {
		nextCombination(index, index.length, 0);
	}

	/** Makes next combination from given input. */
	private static void nextCombination(boolean[] index, int n, int j) {
		int i = n - 1 - j;
		while (i >= 0 && index[i]) i--;
		if (i == n - 1 - j) {
			nextCombination(index, n, ++j);
		} else if (i >= 0) {
			index[i] = true;
			index[++i] = false;
			if (j > 0) {
				i++;
				Arrays.fill(index, i, i + j, false);
				Arrays.fill(index, i + j, n, true);
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
