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
}
