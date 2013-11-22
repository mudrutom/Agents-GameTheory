package mudrutom.game;

import java.util.Collections;
import java.util.List;

/**
 * Extension of the Cell class representing a node in the game tree.
 */
public class GameNode extends Cell {

	/** The game action-sequence leading to this node. */
	protected final Direction[] sequence;
	/** The sequence of this node as a string. */
	protected final String sequenceString;

	/** Indicates weather it's a terminal node. */
	protected boolean isTerminal;
	/** Utility value of this node. */
	protected double utility;
	/** Dangers crossed by a path to this node. */
	protected List<Cell> dangersOnPath;

	/** The GameNode class constructor. */
	public GameNode(Cell cell, Direction[] sequence) {
		super(cell.getX(), cell.getY(), cell.getCell(), cell.getDirection());
		this.sequence = sequence;
		sequenceString = getSequenceString(sequence);
		isTerminal = false;
		utility = Double.NaN;
		dangersOnPath = Collections.emptyList();
	}

	/** @return the game action-sequence leading to this node */
	public Direction[] getSequence() {
		return sequence;
	}

	/** @return the sequence of this node as a string */
	public String getSequenceString() {
		return sequenceString;
	}

	/** @return <tt>true</tt> iff the sequence of this node is empty */
	public boolean isEmptySequence() {
		return sequence == null || sequence.length < 1;
	}

	/** @return <tt>true</tt> iff it's a terminal node */
	public boolean isTerminal() {
		return isTerminal;
	}

	/** Sets weather it's a terminal node. */
	public void setTerminal(boolean terminal) {
		isTerminal = terminal;
	}

	/** @return utility value of this node. */
	public double getUtility() {
		return utility;
	}

	/** Sets utility value of this node. */
	public void setUtility(double utility) {
		this.utility = utility;
	}

	/** @return a list of dangers on a path to this node */
	public List<Cell> getDangersOnPath() {
		return dangersOnPath;
	}

	/** Sets dangers crossed by a path to this node. */
	public void setDangersOnPath(List<Cell> dangersOnPath) {
		this.dangersOnPath = dangersOnPath;
	}

	/** @return a string representation of given sequence */
	public static String getSequenceString(Direction[] sequence) {
		if (sequence.length < 1) {
			return "{}";
		}

		final StringBuilder sb = new StringBuilder();
		for (Direction d : sequence) {
			sb.append(d.toShortString());
		}
		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GameNode) || !super.equals(o)) return false;

		final GameNode n = (GameNode) o;
		return sequenceString.equals(n.sequenceString);
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + sequenceString.hashCode();
		return result;
	}
}
