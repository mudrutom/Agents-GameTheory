package mudrutom.game;

import java.io.BufferedReader;
import java.io.IOException;
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
		for (int i = 0, height = maze.length; i < height; i++) {
			for (int j = 0, width = maze[i].length; j < width; j++) {
				if (maze[i][j] == START) {
					return new Cell(i, j, START);
				}
			}
		}
		throw new IllegalStateException("the game has no start position!");
	}

	public List<Cell> expandCell(Cell cell) {
		final List<Cell> nextCells = new LinkedList<Cell>();
		final int x = cell.getX(), y = cell.getY();

		// WEST
		final int west = x - 1;
		if (x > 0 && maze[west][y] != OBSTACLE) {
			nextCells.add(new Cell(west, y, maze[west][y]));
		}
		// EAST
		final int east = x + 1;
		if (x < maze.length - 1 && maze[east][y] != OBSTACLE) {
			nextCells.add(new Cell(east, y, maze[east][y]));
		}
		// NORTH
		final int north = y - 1;
		if (y > 0 && maze[x][north] != OBSTACLE) {
			nextCells.add(new Cell(x, north, maze[x][north]));
		}
		// SOUTH
		final int south = y + 1;
		if (y < maze[x].length - 1 && maze[x][south] != OBSTACLE) {
			nextCells.add(new Cell(x, south, maze[x][south]));
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
