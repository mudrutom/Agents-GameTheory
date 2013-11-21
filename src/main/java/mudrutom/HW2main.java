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

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Main class used for running the game solver.
 */
public class HW2main {

	/** Debug flag. */
	private static final boolean DEBUG = true;

	private HW2main() {}

	/** The main method, entry point for the solver. */
	public static void main(String[] args) {

		final Maze maze = loadInputMaze(args);
		final Tree<GameNode> gameTree = GameTreeHelper.buildGameTree(maze);
		final List<GameNode> nodes = GameTreeHelper.analyzeAllNodes(gameTree);
		final GameConfig gameConfig = maze.getGameConfig();
		final List<BanditPositions> possiblePositions = gameConfig.getPossibleBanditPositions();

		final Table<GameNode, BanditPositions, Double> utilityTable = new Table<GameNode, BanditPositions, Double>(nodes, possiblePositions, Double.class);
		for (GameNode node : nodes) {
			for (BanditPositions positions : possiblePositions) {
				double utility = gameConfig.computeExpectedUtility(node, positions);
				utilityTable.put(node, positions, utility);
			}
		}

		if (DEBUG) {
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
			for (GameNode node : nodes) {
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

		try {
			System.out.println("\n================= SOLVER START ==================\n");
			final LinearProgram lpAgent = LPBuilder.buildPForAgent(gameTree, utilityTable);
			lpAgent.export("lp");
			lpAgent.solve();
			System.out.println(String.format("\nvalue of the game = %f", lpAgent.getObjectiveValue()));
			lpAgent.close();
			System.out.println("\n====================== END ======================\n");
		} catch (IloException e) {
			System.err.println("IloException: " + e.getMessage());
			System.exit(10);
		}


		final StringBuilder output = new StringBuilder();
		exportUtilityTable(utilityTable, output, true);
		printOutputResults(args, output.toString());
	}

	/** Exports provided utility table. */
	private static void exportUtilityTable(Table<GameNode, BanditPositions, Double> utilityTable, StringBuilder output, boolean excludeZeros) {
		final List<GameNode> nodeList = utilityTable.getRowIndices();
		final List<BanditPositions> positionsList = utilityTable.getColumnIndices();

		// print all sequences for each player
		output.append("\nAGENT:\n");
		int s = 0;
		for (GameNode node : nodeList) {
			output.append('S').append(++s).append(": ");
			output.append(node.getSequenceString()).append('\n');
		}
		output.append("\nATTACKER:\n");
		int q = 0;
		for (BanditPositions positions : positionsList) {
			output.append('Q').append(++q).append(": ");
			output.append(positions.toString()).append('\n');
		}

		final char[] rowLabel = new char[13];
		Arrays.fill(rowLabel, ' ');
		final char[] columnLabel = new char[8];
		Arrays.fill(columnLabel, ' ');
		final char[] line = new char[18 + (positionsList.size() - (excludeZeros ? 1 : 0)) * 12];
		Arrays.fill(line, '-');

		// print utility table itself
		output.append('\n').append(line).append('\n');
		output.append("| AGENT\\ATTACKER ");
		for (q = excludeZeros ? 2 : 1; q <= positionsList.size(); q++) {
			output.append("| Q").append(fixedLengthString(String.valueOf(q), columnLabel, true)).append(' ');
		}
		output.append("|\n");
		output.append(line).append('\n');
		final Iterator<GameNode> nodeIterator = nodeList.iterator();
		for (s = 0; nodeIterator.hasNext(); s++) {
			GameNode node = nodeIterator.next();
			if (excludeZeros && node.getUtility() == 0.0) continue;

			// print table row
			output.append("| S").append(fixedLengthString(String.valueOf(s), rowLabel, true)).append(' ');
			for (BanditPositions positions : positionsList) {
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

	/** @return given string extended/shortened using given pad */
	private static String fixedLengthString(String string, char[] pad, boolean leftAligned) {
		if (leftAligned) {
			return (string + new String(pad)).substring(0, pad.length);
		} else {
			final int length = string.length() + pad.length;
			return (new String(pad) + string).substring(length - pad.length, length);
		}
	}

	/** Loads the maze (game instance) from the input. */
	private static Maze loadInputMaze(String[] args) {
		BufferedReader reader = null;
		try {
			final InputStream input;
			if (args.length < 1) {
				System.out.println("Using STD-IN as an input.");
				input = System.in;
			} else {
				System.out.println("Using input FILE: " + args[0]);
				input = new FileInputStream(args[0]);
			}

			reader = new BufferedReader(new InputStreamReader(input));
			return new Maze(reader);
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			System.exit(2);
		} catch (NumberFormatException e) {
			System.err.println("NumberFormatException: " + e.getMessage());
			System.exit(3);
		} finally {
			closeQuiet(reader);
		}

		return null;
	}

	/** Prints the result of the solver. */
	private static void printOutputResults(String[] args, String results) {
		Writer writer = null;
		try {
			final OutputStream output;
			if (args.length < 2) {
				System.out.println("Using STD-OUT as an output.");
				output = System.out;
			} else {
				System.out.println("Using output FILE: " + args[1]);
				output = new FileOutputStream(args[1]);
			}

			writer = new OutputStreamWriter(output);
			writer.write(results);
			writer.flush();
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException: " + e.getMessage());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
			System.exit(2);
		} finally {
			closeQuiet(writer);
		}
	}

	/** Quietly closes given closeable resource. */
	private static void closeQuiet(Closeable closeable) {
		try {
			if (closeable != null) closeable.close();
		} catch (IOException e) {
			// ignore
		}
	}
}
