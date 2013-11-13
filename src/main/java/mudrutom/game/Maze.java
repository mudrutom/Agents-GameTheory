package mudrutom.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Maze {

	public static final char FREE = '-', OBSTACLE = '#', START = 'S', DESTINATION = 'D', GOLD = 'G', DANGER = 'E';

	private final char[][] maze;
	private final int numberOfBandits;
	private final double attackProbability;

	public Maze(BufferedReader input) throws IOException, NumberFormatException {
		this.maze = parseMaze(input);
		this.numberOfBandits = parseInt(input);
		this.attackProbability = parseDouble(input);
	}

	public int height() {
		return maze.length;
	}

	public int width() {
		return maze[0].length;
	}

	public Cell findStart() {
		for (int y = 0, height = maze.length; y < height; y++) {
			for (int x = 0, width = maze[y].length; x < width; x++) {
				if (maze[y][x] == START) {
					return new Cell(x, y, START, null);
				}
			}
		}
		throw new IllegalStateException("the game has no start position!");
	}

	public List<Cell> expandCell(Cell cell) {
		final int x = cell.getX(), y = cell.getY();
		if (maze[y][x] == DESTINATION) {
			// the destination is reached,
			return Collections.emptyList();
		}

		final List<Cell> nextCells = new LinkedList<Cell>();

		// NORTH
		final int north = y - 1;
		if (y > 0 && maze[north][x] != OBSTACLE) {
			nextCells.add(new Cell(x, north, maze[north][x], Direction.NORTH));
		}
		// SOUTH
		final int south = y + 1;
		if (y < maze.length - 1 && maze[south][x] != OBSTACLE) {
			nextCells.add(new Cell(x, south, maze[south][x], Direction.SOUTH));
		}
		// WEST
		final int west = x - 1;
		if (x > 0 && maze[y][west] != OBSTACLE) {
			nextCells.add(new Cell(west, y, maze[y][west], Direction.WEST));
		}
		// EAST
		final int east = x + 1;
		if (x < maze[y].length - 1 && maze[y][east] != OBSTACLE) {
			nextCells.add(new Cell(east, y, maze[y][east], Direction.EAST));
		}

		return nextCells;
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
			final String line = input.readLine();
			if (line == null || line.length() < n) {
				throw new IOException("unexpected line '" + line + "'");
			}
			maze[i] = line.substring(0, n).toCharArray();
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
