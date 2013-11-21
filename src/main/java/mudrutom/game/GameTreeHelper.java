package mudrutom.game;

import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;
import mudrutom.utils.Visitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for working with game trees.
 */
public class GameTreeHelper implements GameConstants {
	
	private GameTreeHelper() {}

	/** Constructs and returns a game tree for provided maze. */
	public static Tree<GameNode> buildGameTree(Maze maze) {
		// init the game tree
		final Tree<GameNode> tree = new Tree<GameNode>();
		tree.setBreadthFirstSearch();
		tree.getRoot().setNode(new GameNode(maze.getStart(), new Direction[0]));

		TreeNode<GameNode> current = tree.getRoot();
		while (current != null) {
			// expand till possible
			List<Cell> childCells = maze.expandCell(current.getNode());
			for (Cell cell : childCells) {
				if (!isVisited(current, cell)) {
					// insert unvisited cells
					tree.insertNodes(current, createChildNode(current.getNode(), cell));
				}
			}

			current = tree.nextNode();
		}

		return tree;
	}

	/** @return <tt>true</tt> iff given cell in on a path to given node */
	private static boolean isVisited(TreeNode<GameNode> treeNode, Cell cell) {
		return treeNode != null && (cell.equals(treeNode.getNode()) || isVisited(treeNode.getParent(), cell));
	}

	/** @return new child game node for given parent node and cell content */
	private static GameNode createChildNode(GameNode parent, Cell childCell) {
		final Direction[] prevSeq = parent.getSequence();
		final Direction[] sequence = Arrays.copyOf(prevSeq, prevSeq.length + 1);
		sequence[prevSeq.length] = childCell.getDirection();
		return new GameNode(childCell, sequence);
	}

	/** Analyzes and returns all nodes from given game tree. */
	public static List<GameNode> analyzeAllNodes(Tree<GameNode> tree) {
		final List<GameNode> nodes = new LinkedList<GameNode>();
		final Visitor<TreeNode<GameNode>> nodeVisitor = new Visitor<TreeNode<GameNode>>() {
			@Override
			public void visit(TreeNode<GameNode> treeNode) {
				treeNode.getNode().setUtility(getUtilityValue(treeNode));
				treeNode.getNode().setDangersOnPath(findDangersOnPath(treeNode));
				nodes.add(treeNode.getNode());
			}
		};
		tree.applyVisitor(nodeVisitor);
		return nodes;
	}

	/** Returns a list of dangers on a path to given tree node. */
	public static List<Cell> findDangersOnPath(TreeNode<GameNode> treeNode) {
		final List<Cell> dangers = new LinkedList<Cell>();
		findDangersOnPath(treeNode, dangers);
		return dangers;
	}

	/** Collects all dangers on a path to given tree node. */
	private static void findDangersOnPath(TreeNode<GameNode> treeNode, List<Cell> dangers) {
		if (treeNode.getNode().isDanger()) dangers.add(treeNode.getNode());
		if (treeNode.getParent() != null) findDangersOnPath(treeNode.getParent(), dangers);
	}

	/** Returns utility value <tt>u()</tt> of given tree node. */
	public static double getUtilityValue(TreeNode<GameNode> treeNode) {
		return (!treeNode.isLeaf() || !treeNode.getNode().isDestination()) ? 0.0 : computeUtility(treeNode);
	}

	/** @return utility value of given tree node computed from its parents */
	private static double computeUtility(TreeNode<GameNode> treeNode) {
		return treeNode.getNode().getCellUtility() + ((treeNode.getParent() == null) ? 0.0 : computeUtility(treeNode.getParent()));
	}
}
