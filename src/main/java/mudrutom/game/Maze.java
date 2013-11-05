package mudrutom.game;

import java.io.BufferedReader;
import java.io.IOException;

public class Maze {

	public static final char FREE = '-', OBSTACLE = '#', START = 'S', DESTINATION = 'D', GOLD = 'E', DANGER = 'E';

	private final char[][] maze;
	private final int numberOfBandits;
	private final double attackProbability;

	public Maze(BufferedReader input) throws IOException, NumberFormatException {
		this.maze = parseMaze(input);
		this.numberOfBandits = parseInt(input);
		this.attackProbability = parseDouble(input);
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Maze:\n");
		for (char[] row : maze) {
			sb.append(' ').append(row).append('\n');
		}
		sb.append("numberOfBandits=").append(numberOfBandits).append('\n');
		sb.append("attackProbability=").append(attackProbability).append('\n');
		return sb.toString();
	}

	public static char[][] parseMaze(BufferedReader input) throws IOException, NumberFormatException {
		final int m = parseInt(input);
		final int n = parseInt(input);

		final char[][] maze = new char[m][];
		for (int i = 0; i < m; i++) {
			maze[i] = input.readLine().substring(0, n).toCharArray();
		}
		return maze;
	}

	public static int parseInt(BufferedReader input) throws IOException, NumberFormatException {
		return Integer.parseInt(input.readLine());
	}

	public static double parseDouble(BufferedReader input) throws IOException, NumberFormatException {
		return Double.parseDouble(input.readLine());
	}
}
