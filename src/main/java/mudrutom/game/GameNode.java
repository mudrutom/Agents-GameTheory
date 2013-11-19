package mudrutom.game;

/**
 * Extension of the Cell class representing a node in the game tree.
 */
public class GameNode extends Cell {

	/** The game action-sequence leading to this node. */
	protected final Direction[] sequence;

	/** The GameNode class constructor. */
	public GameNode(Cell cell, Direction[] sequence) {
		super(cell.getX(), cell.getY(), cell.getCell(), cell.getDirection());
		this.sequence = sequence;
	}

	/** @return the game action-sequence leading to this node */
	public Direction[] getSequence() {
		return sequence;
	}

	/** @return the sequence of this node as a string */
	public String getSequenceString() {
		final StringBuilder sb = new StringBuilder();
		for (Direction d : sequence) {
			sb.append(d.toShortString());
		}
		return sb.toString();
	}
}