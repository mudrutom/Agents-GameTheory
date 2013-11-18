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
public class GameTreeHelper {
	
	private GameTreeHelper() {}

	/** Constructs all action-sequences for given game tree. */
	public static List<Direction[]> findAllSequences(Tree<Cell> tree, boolean sorted) {
		final List<Direction[]> sequences = new LinkedList<Direction[]>();

		// create and apply a visitor that will generate all sequences
		final Visitor<Cell, Direction[]> cellVisitor = new Visitor<Cell, Direction[]>() {
			@Override
			public Direction[] visit(Cell element, Direction[] prevSeq) {
				if (element.getDirection() == null) {
					// root node of the tree
					sequences.add(prevSeq);
					return prevSeq;
				}

				// append previous sequence with the next action
				final Direction[] sequence = Arrays.copyOf(prevSeq, prevSeq.length + 1);
				sequence[prevSeq.length] = element.getDirection();
				sequences.add(sequence);
				return sequence;
			}
		};
		tree.applyVisitor(cellVisitor, new Direction[0]);

		if (sorted) {
			// sort all sequences by their length
			Collections.sort(sequences, new Comparator<Direction[]>() {
				@Override
				public int compare(Direction[] one, Direction[] two) {
					return one.length - two.length;
				}
			});
		}

		return sequences;
	}

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
