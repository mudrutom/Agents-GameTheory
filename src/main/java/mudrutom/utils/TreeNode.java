package mudrutom.utils;

import java.util.Arrays;
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

	/** @return <tt>true</tt> iff this node is the root */
	public boolean isRoot() {
		return parent == null;
	}

	/** @return <tt>true</tt> iff this node is a leaf */
	public boolean isLeaf() {
		return children.isEmpty();
	}

	/** @return the node itself */
	public T getNode() {
		return node;
	}

	/** sets the node itself. */
	public void setNode(T node) {
		this.node = node;
	}

	/** Uses provided Visitor to visit itself and all its children. */
	public void apply(Visitor<TreeNode<T>> visitor) {
		if (visitor != null) {
			visitor.visit(this);
			for (TreeNode<T> child : children) {
				child.apply(visitor);
			}
		}
	}

	@Override
	public String toString() {
		return toString(0);
	}

	/** @return value of <tt>toString()</tt> for given depth */
	private String toString(int depth) {
		final char[] indent = new char[2 * depth];
		Arrays.fill(indent, ' ');

		final StringBuilder sb = new StringBuilder();
		sb.append(indent).append("node {").append(node.toString()).append("}\n");
		for (TreeNode<T> child : children) {
			sb.append(indent).append("child {\n");
			sb.append(child.toString(depth + 1));
			sb.append(indent).append("}\n");
		}
		return sb.toString();
	}
}
