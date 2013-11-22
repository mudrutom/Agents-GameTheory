package mudrutom;

import ilog.concert.IloException;
import mudrutom.game.BanditPositions;
import mudrutom.game.GameConfig;
import mudrutom.game.GameNode;
import mudrutom.game.GameTreeHelper;
import mudrutom.game.Maze;
import mudrutom.linprog.LPBuilder;
import mudrutom.linprog.LinearProgram;
import mudrutom.utils.Table;
import mudrutom.utils.Tree;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Solver class used for solving the game instances,
 * i.e. finding the Nash equilibrium of the game.
 */
public class Solver {

	/** Current state of the solver. */
	private SolverState state;

	/** The maze instance to solve. */
	private Maze maze;
	/** Current game configuration. */
	private GameConfig gameConfig;
	/** Possible positions of the bandits. */
	private List<BanditPositions> possiblePositions;
	/** The game tree for current problem. */
	private Tree<GameNode> gameTree;
	/** All game-nodes in current game tree. */
	private List<GameNode> gameNodes;
	/** Current table with utility values. */
	private Table<GameNode, BanditPositions, Double> utilityTable;

	/** The Linear Program for the agent. */
	private LinearProgram agentLP;
	/** Calculated realization plans for the agent. */
	private Map<GameNode, Double> agentRealizationPlans;
	/** The Linear Program for the bandits. */
	private LinearProgram banditsLP;
	/** Calculated realization plans for the bandits. */
	private Map<BanditPositions, Double> banditsRealizationPlans;

	/** The Solver class constructor. */
	public Solver() {
		clear();
	}

	/** @return current state of the solver */
	public SolverState getState() {
		return state;
	}

	/** Initializes the solver for given maze instance. */
	public void initialize(Maze maze) {
		this.maze = maze;

		// first analysis of the maze
		maze.analyzeMaze();
		gameConfig = maze.getGameConfig();

		// generate possible bandit positions
		possiblePositions = gameConfig.getPossibleBanditPositions();

		// construct and analyze the game tree
		gameTree = GameTreeHelper.buildGameTree(maze);
		gameNodes = GameTreeHelper.analyzeAllNodes(gameTree);

		// create the utility table
		utilityTable = new Table<GameNode, BanditPositions, Double>(gameNodes, possiblePositions, Double.class);
		for (GameNode node : gameNodes) {
			for (BanditPositions positions : possiblePositions) {
				double utility = gameConfig.computeExpectedUtility(node, positions);
				utilityTable.put(node, positions, utility);
			}
		}

		state = SolverState.READY;
	}

	/**
	 * Solves underlying Linear Program for current game instance.
	 * This LP aims to find the Nash equilibrium of the game, i.e.
	 * computing the value of the game and an optimal realization
	 * plans for the agent player and the bandits.
	 */
	public boolean solveLP(String exportFile) throws IloException {
		System.out.println("\n================= SOLVER START ==================\n");

		// first the agent LP
		agentLP = LPBuilder.buildAgentLP(gameTree, utilityTable);

		if (exportFile != null) {
			// export the agent LP to file
			agentLP.export(exportFile + "_agent");
		}

		// solves the agent LP
		final boolean agentFeasible = agentLP.solve();
		extractAgentRealizationPlans(agentFeasible);
		agentLP.close();

		// second the bandits LP
		banditsLP = LPBuilder.buildBanditsLP(gameTree, utilityTable);

		if (exportFile != null) {
			// export the bandits LP to file
			banditsLP.export(exportFile + "_bandits");
		}

		// solves the bandits LP
		final boolean banditsFeasible = banditsLP.solve();
		extractBanditsRealizationPlans(banditsFeasible);
		banditsLP.close();

		System.out.println("\n====================== END ======================\n");

		final boolean feasible = agentFeasible && banditsFeasible;
		if (feasible) state = SolverState.DONE;
		return feasible;
	}

	/** Extracts all realizations plans of the agent. */
	private void extractAgentRealizationPlans(boolean feasible) throws IloException {
		if (feasible) {
			agentRealizationPlans = new HashMap<GameNode, Double>(gameNodes.size());
			for (GameNode node : gameNodes) {
				double probability = agentLP.getValue(node);
				agentRealizationPlans.put(node, probability);
			}
		}
	}

	/** Extracts all realizations plans of the bandits. */
	private void extractBanditsRealizationPlans(boolean feasible) throws IloException {
		if (feasible) {
			banditsRealizationPlans = new HashMap<BanditPositions, Double>(possiblePositions.size());
			for (BanditPositions positions : possiblePositions) {
				double probability = banditsLP.getValue(positions);
				banditsRealizationPlans.put(positions, probability);
			}
		}
	}

	/** Preforms clean-up of the solver. */
	public void clear() {
		// clean-up
		maze = null;
		gameConfig = null;
		possiblePositions = null;
		gameTree = null;
		gameNodes = null;
		utilityTable = null;
		agentLP = null;
		agentRealizationPlans = null;
		banditsLP = null;
		banditsRealizationPlans = null;

		state = SolverState.INIT;
	}

	/** Exports the utility table to provided output. */
	public void exportUtilityTable(StringBuilder output, boolean excludeZeros) {
		if (state == SolverState.INIT) return;

		// print all sequences for each player
		output.append("\nAGENT:\n");
		int s = 1;
		for (GameNode node : gameNodes) {
			output.append(" S").append(s++).append(": ");
			output.append(node.getSequenceString());
			output.append(node.isTerminal() ? " (*)" : "").append('\n');
		}
		output.append("\nATTACKER:\n");
		int q = 1;
		for (BanditPositions positions : possiblePositions) {
			output.append(" Q").append(q++).append(": ");
			output.append(positions.toString()).append('\n');
		}

		final char[] rowLabel = new char[13];
		Arrays.fill(rowLabel, ' ');
		final char[] columnLabel = new char[8];
		Arrays.fill(columnLabel, ' ');
		final char[] line = new char[18 + 12 * (possiblePositions.size() - (excludeZeros ? 1 : 0))];
		Arrays.fill(line, '-');

		// print the utility table itself
		output.append('\n').append(line).append('\n');
		output.append("| AGENT\\ATTACKER ");
		for (q = excludeZeros ? 2 : 1; q <= possiblePositions.size(); q++) {
			output.append("| Q").append(fixedLengthString(q, columnLabel, true)).append(' ');
		}
		output.append("|\n");
		output.append(line).append('\n');
		final Iterator<GameNode> nodeIterator = gameNodes.iterator();
		for (s = 1; nodeIterator.hasNext(); s++) {
			GameNode node = nodeIterator.next();
			if (excludeZeros && node.getUtility() == 0.0) continue;

			// print row of the table
			output.append("| S").append(fixedLengthString(s, rowLabel, true)).append(' ');
			for (BanditPositions positions : possiblePositions) {
				if (excludeZeros && positions.isEmpty()) continue;
				double utility = utilityTable.get(node, positions);
				output.append("|  ").append(fixedLengthString(String.format("%.3f", utility), columnLabel, false)).append(' ');
			}
			output.append("|\n");
		}
		output.append(line).append('\n');
		if (excludeZeros) {
			output.append(" NOTE: zero utility values are omitted\n");
		}
	}

	/** Exports calculated realization plans to provided output. */
	public void exportRealizationPlans(StringBuilder output) {
		if (state != SolverState.DONE) return;

		// print all realization plans for each player
		output.append("\nSOLUTION_AGENT:\n");
		int s = 1;
		for (GameNode node : gameNodes) {
			output.append(" S").append(s++).append(": ");
			output.append(agentRealizationPlans.get(node));
			output.append(node.isTerminal() ? " (*)" : "").append('\n');
		}
		output.append("\nSOLUTION_ATTACKER:\n");
		int q = 1;
		for (BanditPositions positions : possiblePositions) {
			output.append(" Q").append(q++).append(": ");
			output.append(banditsRealizationPlans.get(positions)).append('\n');
		}

		// print the value of the game
		output.append("\nSOLUTION_VALUE:\n");
		final double agentGameValue = agentLP.getObjectiveValue();
		output.append(" agent = ").append(agentGameValue).append('\n');
		final double banditsGameValue = banditsLP.getObjectiveValue();
		output.append(" bandits = ").append(banditsGameValue).append('\n');
		if (Double.compare(agentGameValue, banditsGameValue) == 0) {
			output.append(" OK - game values coincides\n");
		} else {
			output.append(" !! - game values differs\n");
		}
	}

	/** Prints debug information. */
	public void printDebugInfo() {
		if (state == SolverState.INIT) return;

		final StringBuilder debug = new StringBuilder();
		debug.append("\n===================== DEBUG =====================\n\n");

		debug.append(maze.toString());

		debug.append("\nGame tree:\n");
		debug.append(gameTree.toString());

		debug.append("\nBandit positions:\n");
		for (BanditPositions positions : gameConfig.getPossibleBanditPositions()) {
			debug.append(positions).append('\n');
		}

		debug.append("\nLeaf nodes:\n");
		for (GameNode node : gameNodes) {
			if (node.isDestination()) {
				debug.append(node.getSequenceString());
				debug.append(" u=").append(node.getUtility());
				debug.append('\n');
			}
		}

		debug.append("\nUtility table:\n");
		debug.append(utilityTable.toString());

		debug.append("\n====================== END ======================\n\n");
		System.out.print(debug.toString());
	}

	/** @return given number as a string extended/shortened using given pad */
	private static String fixedLengthString(int number, char[] pad, boolean leftAligned) {
		return fixedLengthString(String.valueOf(number), pad, leftAligned);
	}

	/** @return given string extended/shortened using given pad */
	private static String fixedLengthString(String string, char[] pad, boolean leftAligned) {
		if (leftAligned) {
			return (string + new String(pad)).substring(0, pad.length);
		} else {
			final int length = string.length() + pad.length;
			return (new String(pad) + string).substring(length - pad.length, length);
		}
	}

	/** Solver state enumerations type. */
	public static enum SolverState {
		INIT, READY, DONE
	}
}
