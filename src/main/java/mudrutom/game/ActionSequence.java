package mudrutom.game;

import mudrutom.utils.TreeNode;

import java.util.Arrays;

/**
 * Class representing an action-sequence in the game.
 */
public class ActionSequence implements GameConstants {

	/** The actual game action-sequence. */
	private final Direction[] sequence;

	/** The tree node to which this sequence leads to. */
	private final TreeNode<Cell> treeNode;

	/** The ActionSequence class constructor. */
	public ActionSequence(Direction[] sequence, TreeNode<Cell> treeNode) {
		this.sequence = sequence;
		this.treeNode = treeNode;
	}

	/** @return the tree node to which this sequence leads to */
	public TreeNode<Cell> getTreeNode() {
		return treeNode;
	}

	/** @return the actual game action-sequence */
	public Direction[] getSequence() {
		return sequence;
	}

	/**
	 * @return <tt>true</tt> iff this sequence is complete,
	 *         i.e. it leads from the root node to a leaf node
	 */
	public boolean isComplete() {
		return treeNode.isLeaf();
	}

	@Override
	public String toString() {
		return Arrays.toString(sequence);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || !(o instanceof ActionSequence)) return false;

		final ActionSequence as = (ActionSequence) o;
		return Arrays.equals(sequence, as.sequence);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(sequence);
	}
}
