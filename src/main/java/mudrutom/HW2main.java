package mudrutom;

import mudrutom.game.Cell;
import mudrutom.game.Direction;
import mudrutom.game.GameTreeHelper;
import mudrutom.game.Maze;
import mudrutom.utils.Tree;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * Main class used for running the game solver.
 */
public class HW2main {

	/** The main method, entry point for the solver. */
	public static void main(String[] args) {

		final Maze maze = loadInputMaze(args);
		System.out.println(maze.toString());

		final Tree<Cell> gameTree = GameTreeHelper.buildGameTree(maze);
		System.out.println(gameTree.toString());

		final List<Direction[]> sequences = GameTreeHelper.findAllSequences(gameTree, false);
		final StringBuilder sb = new StringBuilder();
		for (Direction[] sequence : sequences) {
			sb.append(Arrays.toString(sequence)).append('\n');
		}
		System.out.println(sb.toString());

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
