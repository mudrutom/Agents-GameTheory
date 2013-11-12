package mudrutom.game;

import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;

import java.util.List;

public class GameTreeBuilder {

	public static Tree<Cell> buildGameTree(Maze maze) {
		final Tree<Cell> tree = new Tree<Cell>();
		tree.setBreadthFirstSearch();

		final Cell start = maze.findStart();
		tree.getRoot().setNode(start);

		final boolean[][] visited = new boolean[maze.height()][maze.width()];
		visited[start.getX()][start.getY()] = true;

		TreeNode<Cell> current = tree.getRoot();
		do {
			final List<Cell> childNodes = maze.expandCell(current.getNode());
			for (Cell childNode : childNodes) {
				if (!visited[childNode.getX()][childNode.getY()]) {
					tree.insertNodes(current, childNode);
					visited[childNode.getX()][childNode.getY()] = true;
				}
			}

			current = tree.nextNode();
		} while (current != null);

		return tree;
	}

}
