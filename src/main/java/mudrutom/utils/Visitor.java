package mudrutom.utils;

/**
 * An interface used for visitor design patten.
 * @param <T> type of visited elements
 * @param <R> type of visitor return value
 */
public interface Visitor<T, R> {

	/** Performs visit operation on provided element. */
	public R visit(T element, R previous);

}
