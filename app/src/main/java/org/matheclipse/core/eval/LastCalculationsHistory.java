package org.matheclipse.core.eval;

import java.io.Serializable;

import org.matheclipse.core.interfaces.IExpr;

/**
 * Contains the last <code>N</code> entries of the calculation history.
 * 
 */
public class LastCalculationsHistory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5003679826481359674L;

	final private IExpr[] fOutHistory;
	final private int fMaximumCapacity;

	private int fIndex;

	/**
	 * The overall counter for all calculations.
	 */
	private int fAllEntriesCounter;

	/**
	 * Contains the last <code>N</code> output entries of the calculation
	 * history.
	 * 
	 * @param n
	 *            maximum capacity of the last calculated entries which the
	 *            history could contain.
	 */
	public LastCalculationsHistory(int n) {
		fMaximumCapacity = n;
		fOutHistory = new IExpr[fMaximumCapacity];
		fIndex = 0;
		fAllEntriesCounter = 0;
	}

	/**
	 * Add a new calculation to the history. Only the last <code>N</code>
	 * calculations are stored in the history. It may be that the last
	 * <code>N+1</code> entry will be overriden by the new entry.
	 * 
	 * @param entry
	 */
	public void add(IExpr entry) {
		fOutHistory[fIndex++] = entry;
		fAllEntriesCounter++;
		if (fIndex == fMaximumCapacity) {
			fIndex = 0;
		}
	}

	/**
	 * Get the history entry at the given <code>index</code>. If the
	 * <code>index</code> is positive it ranges from <code>1</code> to
	 * <code>fAllEntriesCounter</code>. If the <code>index</code> is negative it
	 * ranges from <code>-1</code> to <code>-fAllEntriesCounter</code>.
	 * 
	 * @param index
	 * @return <code>null</code> if the output history isn't available for the
	 *         given <code>index</code>.
	 */
	public IExpr get(int index) {
		if (index > fAllEntriesCounter || index == 0) {
			return null;
		}
		if (index > 0) {
			// determine the negative index from the end
			index -= (fAllEntriesCounter + 1);
		}
		if (index <= 0) {
			index *= -1;
			if (fMaximumCapacity < index) {
				return null;
			}
			if (index > fIndex) {
				return fOutHistory[fMaximumCapacity + fIndex - index];
			}
			return fOutHistory[fIndex - index];
		}
		return null;
	}

	public int size() {
		return fOutHistory.length;
	}

}
