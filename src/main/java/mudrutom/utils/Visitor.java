package mudrutom.utils;

/**
 * An interface used for visitor design patten.
 * @param <T> type of visited elements
 * @param <E> type of exception that could be thrown
 */
public interface Visitor<T, E extends Throwable> {

	/** Performs visit operation on provided element. */
	public void visit(T element) throws E;

}
