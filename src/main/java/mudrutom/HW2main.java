package mudrutom;

import ilog.concert.IloException;
import mudrutom.game.Maze;

import java.io.*;

/**
 * Main class used for running the game solver.
 */
public class HW2main {

	/** Debug flag. */
	private static final boolean DEBUG = true;
	/** Flag indicating weather to exclude zero utility values. */
	private static final boolean EXCLUDE_ZERO_UTILITY_VALUES = true;

	private HW2main() {}

	/** The main method, entry point for the solver. */
	public static void main(String[] args) {
		// load the game instance
		final Maze maze = loadInputMaze(args);

		// crate the game solver for this maze
		final Solver gameSolver = new Solver();
		gameSolver.initialize(maze, "GAME THEORY");

		if (DEBUG) gameSolver.printDebugInfo();

		try {
			// solve te Linear Program
			final boolean feasible = gameSolver.solveLP("mudrutom");
			if (!feasible) {
				System.err.println("No feasible solution has been found!");
				System.exit(5);
			}
		} catch (IloException e) {
			System.err.println("IloException: " + e.getMessage());
			System.exit(10);
		}

		// print the results of the game solver
		final StringBuilder output = new StringBuilder();
		gameSolver.exportUtilityTable(output, EXCLUDE_ZERO_UTILITY_VALUES);
		gameSolver.exportRealizationPlans(output);
		printOutput(args, output.toString());
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
	private static void printOutput(String[] args, String results) {
		Writer writer = null;
		try {
			final OutputStream output;
			if (args.length < 2) {
				System.out.println("Using STD-OUT as an output.");
				output = System.out;
			} else {
				System.out.println("Using output FILE: " + args[1]);
				output = new FileOutputStream(args[1]);
				results = "SOLUTION FOR " + args[0] + "\n" + results;
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
