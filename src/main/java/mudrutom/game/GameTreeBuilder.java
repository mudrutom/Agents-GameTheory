package mudrutom.game;

import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;

import java.util.List;

public class GameTreeBuilder {

	public static Tree<Cell> buildGameTree(Maze maze) {
		maze.analyzeMaze();
		final Tree<Cell> tree = new Tree<Cell>();
		tree.setBreadthFirstSearch();
		tree.getRoot().setNode(maze.getStart());

		TreeNode<Cell> current = tree.getRoot();
		do {
			List<Cell> childNodes = maze.expandCell(current.getNode());
			for (Cell childNode : childNodes) {
				if (!isVisited(current, childNode)) {
					tree.insertNodes(current, childNode);
				}
			}

			current = tree.nextNode();
		} while (current != null);

		return tree;
	}

	private static boolean isVisited(TreeNode<Cell> treeNode, Cell cell) {
		return treeNode != null && (cell.equals(treeNode.getNode()) || isVisited(treeNode.getParent(), cell));
	}

}
