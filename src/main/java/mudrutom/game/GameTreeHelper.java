package mudrutom.game;

import mudrutom.utils.Tree;
import mudrutom.utils.TreeNode;
import mudrutom.utils.Visitor;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class for working with game trees.
 */
public class GameTreeHelper implements GameConstants {
	
	private GameTreeHelper() {}

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

	/** Constructs all action-sequences for given game tree. */
	public static List<ActionSequence> findAllSequences(Tree<Cell> tree, boolean sorted) {
		final List<ActionSequence> sequences = new LinkedList<ActionSequence>();

		// create and apply a visitor that will generate all sequences
		final Visitor<TreeNode<Cell>, Direction[]> cellVisitor = new Visitor<TreeNode<Cell>, Direction[]>() {
			@Override
			public Direction[] visit(TreeNode<Cell> treeNode, Direction[] prevSeq) {
				if (treeNode.getNode().getDirection() == null) {
					// root node of the tree
					sequences.add(new ActionSequence(prevSeq, treeNode));
					return prevSeq;
				}

				// append previous sequence with the next action
				final Direction[] sequence = Arrays.copyOf(prevSeq, prevSeq.length + 1);
				sequence[prevSeq.length] = treeNode.getNode().getDirection();
				sequences.add(new ActionSequence(sequence, treeNode));
				return sequence;
			}
		};
		tree.applyVisitor(cellVisitor, new Direction[0]);

		if (sorted) {
			// sort all sequences by their length
			Collections.sort(sequences, new Comparator<ActionSequence>() {
				@Override
				public int compare(ActionSequence one, ActionSequence two) {
					return one.getSequence().length - two.getSequence().length;
				}
			});
		}

		return sequences;
	}

	/** Returns utility value <tt>u()</tt> of given tree node. */
	public static double getUtilityValue(TreeNode<Cell> treeNode) {
		return (!treeNode.getChildren().isEmpty() || !treeNode.getNode().isDestination()) ? 0.0 : computeUtility(treeNode);
	}

	/** @return utility value of given tree node computed from its parents */
	private static double computeUtility(TreeNode<Cell> treeNode) {
		return treeNode.getNode().getUtility() + ((treeNode.getParent() == null) ? 0.0 : computeUtility(treeNode.getParent()));
	}
}
