package mudrutom.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class representing two-dimensional table of values that
 * are accessed by provided row and column indices.<br/>
 * It provides <tt>O(1)</tt> read/write value access times.
 * @param <R> type of row indices
 * @param <C> type of column indices
 * @param <V> type of values in the table
 */
public class Table<R, C, V> {

	/** Actual values in the table. */
	private final V[][] values;
	/** The row elements index. */
	private final Map<R, Integer> rowIndex;
	/** The row indices. */
	private final List<R> rowIndices;
	/** The column elements index. */
	private final Map<C, Integer> columnIndex;
	/** The column indices. */
	private final List<C> columnIndices;

	/** Constructor of the Table class. */
	public Table(List<R> rows, List<C> columns, Class<V> valueType) {
		@SuppressWarnings("unchecked")
		final V[][] values = (V[][]) Array.newInstance(valueType, rows.size(), columns.size());
		this.values = values;
		rowIndex = buildIndex(rows);
		rowIndices = Collections.unmodifiableList(rows);
		columnIndex = buildIndex(columns);
		columnIndices = Collections.unmodifiableList(columns);
	}

	/** @return height of the table */
	public int height() {
		return values.length;
	}

	/** @return width of the table */
	public int width() {
		return values[0].length;
	}

	/** Inserts given value into the table at specified row and column. */
	public void put(R row, C column, V value) {
		final Integer r = rowIndex.get(row);
		final Integer c = columnIndex.get(column);
		if (r != null && c != null) {
			values[r][c] = value;
		}
	}

	/** @return value in the table at specified row and column */
	public V get(R row, C column) {
		final Integer r = rowIndex.get(row);
		final Integer c = columnIndex.get(column);
		return (r == null || c == null) ? null : values[r][c];
	}

	/** @return the row indices of the table */
	public List<R> getRowIndices() {
		return rowIndices;
	}

	/** @return the column indices of the table */
	public List<C> getColumnIndices() {
		return columnIndices;
	}

	/** Applies given Visitor to specified row of the table. */
	public <E extends Throwable> void applyRowVisitor(R row, Visitor<V, E> visitor) throws E {
		final Integer r = rowIndex.get(row);
		if (r != null) {
			for (int i = 0; i < values[r].length; i++) {
				visitor.visit(values[r][i]);
			}
		}
	}

	/** Applies given Visitor to specified column of the table. */
	public <E extends Throwable> void applyColumnVisitor(C column, Visitor<V, E> visitor) throws E {
		final Integer c = columnIndex.get(column);
		if (c != null) {
			for (final V[] row : values) {
				visitor.visit(row[c]);
			}
		}
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		for (V[] row : values) {
			sb.append(Arrays.toString(row)).append('\n');
		}
		return sb.toString();
	}

	/** @return an index for given indices */
	private static <T> Map<T, Integer> buildIndex(List<T> indices) {
		final Map<T, Integer> index = new HashMap<T, Integer>(indices.size());
		final Iterator<T> iterator = indices.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			index.put(iterator.next(), i);
		}
		return Collections.unmodifiableMap(index);
	}
}
