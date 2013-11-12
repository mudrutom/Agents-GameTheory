package mudrutom.utils;

import java.util.Deque;
import java.util.LinkedList;

public class Tree<T> {

	private final TreeNode<T> root;

	private final Deque<TreeNode<T>> fringe;

	private SearchType searchType;

	public Tree() {
		root = new TreeNode<T>();
		fringe = new LinkedList<TreeNode<T>>();
		searchType = SearchType.BREADTH_FIRST;
	}

	public TreeNode<T> getRoot() {
		return root;
	}

	public void setBreadthFirstSearch() {
		searchType = SearchType.BREADTH_FIRST;
	}

	public void setDepthFirstSearch() {
		searchType = SearchType.DEPTH_FIRST;
	}

	public void insertNodes(TreeNode<T> parent, T... values) {
		if (parent != null && values != null) {
			for (T value : values) {
				TreeNode<T> child = new TreeNode<T>(parent, value);
				parent.addChild(child);
				fringe.add(child);
			}
		}
	}

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

	public static enum SearchType {
		BREADTH_FIRST, DEPTH_FIRST
	}
}
