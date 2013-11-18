package mudrutom.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a node in the tree.
 * @param <T> type of node
 */
public class TreeNode<T> {

	/** The parent node of this node. */
	private final TreeNode<T> parent;

	/** The children nodes og this node. */
	private final List<TreeNode<T>> children;

	/** Actual node itself. */
	private T node;

	/** Constructor of the TreeNode class. */
	public TreeNode() {
		this(null, null);
	}

	/** Constructor of the TreeNode class. */
	public TreeNode(TreeNode<T> parent, T value) {
		this.parent = parent;
		children = new LinkedList<TreeNode<T>>();
		this.node = value;
	}

	/** @return the parent node of this node */
	public TreeNode<T> getParent() {
		return parent;
	}

	/** @return the children nodes of this node */
	public List<TreeNode<T>> getChildren() {
		return children;
	}

	/** Adds given node into children of this node. */
	public void addChild(TreeNode<T> child) {
		if (child != null) {
			children.add(child);
		}
	}

	/** @return the node itself */
	public T getNode() {
		return node;
	}

	/** sets th node itself. */
	public void setNode(T node) {
		this.node = node;
	}

	/** Uses provided Visitor to visit itself and all its children. */
	public <R> void apply(Visitor<TreeNode<T>, R> visitor, R previous) {
		if (visitor != null) {
			final R next = visitor.visit(this, previous);
			for (TreeNode<T> child : children) {
				child.apply(visitor, next);
			}
		}
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
