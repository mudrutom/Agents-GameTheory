package mudrutom.game;

import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;

import java.util.List;

/**
 * Utility class for work with game trees.
 */
public class GameTreeBuilder {
	
	private GameTreeBuilder() {}

	/** Constructs and returns a game tree for provided maze. */
	public static Tree<Cell> buildGameTree(Maze maze) {
		maze.analyzeMaze();

		// init the game tree
		final Tree<Cell> tree = new Tree<Cell>();
		tree.setBreadthFirstSearch();
		tree.getRoot().setNode(maze.getStart());

		TreeNode<Cell> current = tree.getRoot();
		do {
			// expand till possible
			List<Cell> childNodes = maze.expandCell(current.getNode());
			for (Cell childNode : childNodes) {
				if (!isVisited(current, childNode)) {
					// insert unvisited cells
					tree.insertNodes(current, childNode);
				}
			}

			current = tree.nextNode();
		} while (current != null);

		return tree;
	}

	/** @return <tt>true</tt> iff given cell in on a path to given tree node */
	private static boolean isVisited(TreeNode<Cell> treeNode, Cell cell) {
		return treeNode != null && (cell.equals(treeNode.getNode()) || isVisited(treeNode.getParent(), cell));
	}
}
