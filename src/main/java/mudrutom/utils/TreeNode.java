package mudrutom.utils;

import java.util.LinkedList;
import java.util.List;

public class TreeNode<T> {

	private final TreeNode<T> parent;

	private final List<TreeNode<T>> children;

	private T node;

	public TreeNode() {
		this(null, null);
	}

	public TreeNode(TreeNode<T> parent, T value) {
		this.parent = parent;
		children = new LinkedList<TreeNode<T>>();
		this.node = value;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public void addChild(TreeNode<T> child) {
		if (child != null) {
			children.add(child);
		}
	}

	public T getNode() {
		return node;
	}

	public void setNode(T node) {
		this.node = node;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("node {").append(node.toString()).append("}\n");
		for (TreeNode<T> child : children) {
			sb.append("  child {").append(child.toString()).append("}");
		}
		return sb.toString();
	}
}
