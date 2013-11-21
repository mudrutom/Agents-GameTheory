package mudrutom.game;

/**
 * Extension of the Cell class representing a node in the game tree.
 */
public class GameNode extends Cell {

	/** The game action-sequence leading to this node. */
	protected final Direction[] sequence;
	/** The sequence of this node as a string. */
	protected final String sequenceString;

	/** Utility value of this node. */
	protected double utility;

	/** The GameNode class constructor. */
	public GameNode(Cell cell, Direction[] sequence) {
		super(cell.getX(), cell.getY(), cell.getCell(), cell.getDirection());
		this.sequence = sequence;
		sequenceString = getSequenceString(sequence);
		this.utility = Double.NaN;
	}

	/** @return the game action-sequence leading to this node */
	public Direction[] getSequence() {
		return sequence;
	}

	/** @return the sequence of this node as a string */
	public String getSequenceString() {
		return sequenceString;
	}

	/** @return utility value of this node. */
	public double getUtility() {
		return utility;
	}

	/** Sets utility value of this node. */
	public void setUtility(double utility) {
		this.utility = utility;
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
}
