package mudrutom.utils;

/**
 * An interface used for visitor design patten.
 * @param <T> type of visited elements
 */
public interface Visitor<T> {

	/** Performs visit operation on provided element. */
	public void visit(T element);

}
