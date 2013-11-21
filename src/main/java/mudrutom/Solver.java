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
		agentLP = LPBuilder.buildAgentLP(gameTree, utilityTable);

		if (exportFile != null) {
			// export the agent LP to file
			agentLP.export(exportFile + "_agent");
		}

		// solves the agent LP
		final boolean feasible = agentLP.solve();
		extractAgentRealizationPlans(feasible);
		agentLP.close();
		System.out.println("\n====================== END ======================\n");

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

		state = SolverState.INIT;
	}

	/** Exports the utility table to provided output. */
	public void exportUtilityTable(StringBuilder output, boolean excludeZeros) {
		if (state == SolverState.INIT) return;

		// print all sequences for each player
		output.append("\nAGENT:\n");
		int s = 0;
		for (GameNode node : gameNodes) {
			output.append(" S").append(++s).append(": ");
			output.append(node.getSequenceString()).append('\n');
		}
		output.append("\nATTACKER:\n");
		int q = 0;
		for (BanditPositions positions : possiblePositions) {
			output.append(" Q").append(++q).append(": ");
			output.append(positions.toString()).append('\n');
		}

		final char[] rowLabel = new char[13];
		Arrays.fill(rowLabel, ' ');
		final char[] columnLabel = new char[8];
		Arrays.fill(columnLabel, ' ');
		final char[] line = new char[18 + (possiblePositions.size() - (excludeZeros ? 1 : 0)) * 12];
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
		for (s = 0; nodeIterator.hasNext(); s++) {
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
			output.append("[NOTE: zero utility values are omitted!]\n");
		}
	}

	/** Exports calculated realization plans to provided output. */
	public void exportRealizationPlans(StringBuilder output) {
		if (state != SolverState.DONE) return;

		// print all realization plans for each player
		output.append("\nSOLUTION_AGENT:\n");
		int s = 0;
		for (GameNode node : gameNodes) {
			output.append(" S").append(++s).append(": ");
			output.append(agentRealizationPlans.get(node)).append('\n');
		}

		// print the value of the game
		output.append("\nSOLUTION_VALUE:\n");
		final double agentGameValue = agentLP.getObjectiveValue();
		output.append(" agent = ").append(agentGameValue).append('\n');
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
