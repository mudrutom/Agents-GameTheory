package mudrutom;

import ilog.concert.IloException;
import mudrutom.game.BanditPositions;
import mudrutom.game.Cell;
import mudrutom.game.GameConfig;
import mudrutom.game.GameNode;
import mudrutom.game.GameTreeHelper;
import mudrutom.game.Maze;
import mudrutom.linprog.LPBuilder;
import mudrutom.linprog.LinearProgram;
import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Main class used for running the game solver.
 */
public class HW2main {

	/** The main method, entry point for the solver. */
	public static void main(String[] args) {
		final StringBuilder sb = new StringBuilder();

		final Maze maze = loadInputMaze(args);
		sb.append("\nMaze:\n");
		sb.append(maze.toString()).append('\n');

		final Tree<GameNode> gameTree = GameTreeHelper.buildGameTree(maze);
		sb.append("\nGame Tree:\n");
		sb.append(gameTree.toString()).append('\n');

		final GameConfig gameConfig = maze.getGameConfig();
		sb.append("\nBandit positions:\n");
		for (BanditPositions positions : gameConfig.getPossibleBanditPositions()) {
			sb.append(positions).append('\n');
		}

		final List<TreeNode<GameNode>> nodes = GameTreeHelper.analyzeAllNodes(gameTree);
		sb.append("\nLeafs:\n");
		for (TreeNode<GameNode> node : nodes) {
			if (node.isLeaf()) {
				List<Cell> dangers = GameTreeHelper.findDangersOnPath(node);
				sb.append(node.getNode().getSequenceString());
				sb.append(" u=").append(node.getNode().getUtility());
				sb.append(" |dangers|=").append(dangers.size());
				sb.append('\n');
			}
		}

		System.out.println(sb.toString());

		try {
			final LinearProgram lpAgent = LPBuilder.buildPForAgent(nodes, gameConfig);
			lpAgent.export("lp");
			lpAgent.solve();
			System.out.println(String.format("value of the game = %f", lpAgent.getObjectiveValue()));
			lpAgent.close();
		} catch (IloException e) {
			System.err.println("IloException: " + e.getMessage());
			System.exit(10);
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

	/** Quietly closes given closeable resource. */
	private static void closeQuiet(Closeable closeable) {
		try {
			if (closeable != null) closeable.close();
		} catch (IOException e) {
			// ignore
		}
	}
}
