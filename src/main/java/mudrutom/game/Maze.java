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

	private Cell start;
	private List<Cell> dangers;

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

	public Cell getStart() {
		return start;
	}

	public List<Cell> getDangers() {
		return dangers;
	}

	public void analyzeMaze() {
		dangers = new LinkedList<Cell>();
		for (int y = 0, height = maze.length; y < height; y++) {
			for (int x = 0, width = maze[y].length; x < width; x++) {
				if (maze[y][x] == START) {
					start = new Cell(x, y, START, null);
				} else if (maze[y][x] == DANGER) {
					dangers.add(new Cell(x, y, DANGER, null));
				}
			}
		}
	}

	public List<Cell> expandCell(Cell cell) {
		if (cell.getCell() == DESTINATION) {
			// the destination is reached,
			return Collections.emptyList();
		}

		final int x = cell.getX(), y = cell.getY();
		final List<Cell> nextCells = new LinkedList<Cell>();

		// UP
		final int up = y - 1;
		if (y > 0 && maze[up][x] != OBSTACLE) {
			nextCells.add(new Cell(x, up, maze[up][x], Direction.UP));
		}
		// DOWN
		final int down = y + 1;
		if (y < maze.length - 1 && maze[down][x] != OBSTACLE) {
			nextCells.add(new Cell(x, down, maze[down][x], Direction.DOWN));
		}
		// LEFT
		final int left = x - 1;
		if (x > 0 && maze[y][left] != OBSTACLE) {
			nextCells.add(new Cell(left, y, maze[y][left], Direction.LEFT));
		}
		// RIGHT
		final int right = x + 1;
		if (x < maze[y].length - 1 && maze[y][right] != OBSTACLE) {
			nextCells.add(new Cell(right, y, maze[y][right], Direction.RIGHT));
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
