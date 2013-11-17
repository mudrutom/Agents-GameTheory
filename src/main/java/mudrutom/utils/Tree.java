package mudrutom.utils;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Class representing an <tt>n</tt>-ary tree.
 * @param <T> type of nodes in a tree
 */
public class Tree<T> {

	/** The root node of the tree. */
	private final TreeNode<T> root;

	/** The current fringe of the tree. */
	private final Deque<TreeNode<T>> fringe;

	/** Current tree search type. */
	private SearchType searchType;

	/** Constructor of the Tree class. */
	public Tree() {
		root = new TreeNode<T>();
		fringe = new LinkedList<TreeNode<T>>();
		searchType = SearchType.BREADTH_FIRST;
	}

	/** @return the root node of this tree */
	public TreeNode<T> getRoot() {
		return root;
	}

	/** Sets breadth-first search to be used. */
	public void setBreadthFirstSearch() {
		searchType = SearchType.BREADTH_FIRST;
	}

	/** Sets depth-first search to be used. */
	public void setDepthFirstSearch() {
		searchType = SearchType.DEPTH_FIRST;
	}

	/**
	 * Inserts all given values into the tree for given parent node
	 * and inserts them into the fringe of this tree.
	 */
	public void insertNodes(TreeNode<T> parent, T... values) {
		if (parent != null && values != null) {
			for (T value : values) {
				TreeNode<T> child = new TreeNode<T>(parent, value);
				parent.addChild(child);
				fringe.add(child);
			}
		}
	}

	/** @return the next node in the fringe or <tt>null</tt> if it's empty */
	public TreeNode<T> nextNode() {
		if (!fringe.isEmpty()) {
			switch (searchType) {
				case BREADTH_FIRST: return fringe.pollFirst();
				case DEPTH_FIRST: return fringe.pollLast();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return root.toString();
	}

	/**
	 * Tree search enumeration type.
	 */
	public static enum SearchType {
		BREADTH_FIRST, DEPTH_FIRST
	}
}
