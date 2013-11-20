package mudrutom;

import mudrutom.game.BanditsConfig;
import mudrutom.game.Cell;
import mudrutom.game.GameNode;
import mudrutom.game.GameTreeHelper;
import mudrutom.game.Maze;
import mudrutom.linprog.LPBuilder;
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

		final BanditsConfig banditsConfig = new BanditsConfig(maze);
		sb.append("\nBandits Config:\n");
		for (List<Cell> positions : banditsConfig.getPossiblePositions()) {
			sb.append(positions).append('\n');
		}

		final List<TreeNode<GameNode>> leafNodes = GameTreeHelper.findLeafNodes(gameTree);
		sb.append("\nLeafs:\n");
		for (TreeNode<GameNode> leafNode : leafNodes) {
			double utility = GameTreeHelper.getUtilityValue(leafNode);
			List<Cell> dangers = GameTreeHelper.findDangersOnPath(leafNode);
			sb.append(leafNode.getNode().getSequenceString());
			sb.append(" u=").append(utility);
			sb.append(" |dangers|=").append(dangers.size());
			sb.append('\n');
		}

		System.out.println(sb.toString());

		LPBuilder.solveLPForAgent(gameTree, banditsConfig);
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
