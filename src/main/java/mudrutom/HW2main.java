package mudrutom;

import mudrutom.game.Maze;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HW2main {

	public static void main(String[] args) {

		final Maze maze = loadInputMaze(args);
		System.out.println(maze.toString());

	}

	private static Maze loadInputMaze(String[] args) {
		try {
			final InputStream input;
			if (args.length < 1) {
				System.out.println("Using STD-IN as an input.");
				input = System.in;
			} else {
				System.out.println("Using input FILE: " + args[0]);
				input = new FileInputStream(args[0]);
			}

			final BufferedReader reader = new BufferedReader(new InputStreamReader(input));
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
		}

		return null;
	}
}
