package org.matheclipse.core.interfaces;

import org.matheclipse.core.patternmatching.PatternMap;

/**
 * Interface for pattern sequence objects (i.e. x__)
 */
public interface IPatternSequence extends IPatternObject, IExpr {

	/**
	 * Get the additional pattern sequences condition expression
	 * 
	 * @return may return null;
	 */
	@Override
	public IExpr getCondition();

	/**
	 * Check if this pattern sequence object matches the given <code>sequence</code>.
	 * 
	 * @param sequence
	 *            the sequence which should be matched.
	 * @param patternMap
	 *            a map from a pattern to a possibly found value during pattern-matching.
	 * 
	 * @return
	 */
	public boolean matchPatternSequence(final IAST sequence, PatternMap patternMap);
	
	/**
	 * Return <code>true</code>, if all of the elements in the <code>sequence</code> fulfill the pattern sequences additional
	 * condition
	 * 
	 * @param sequence
	 * @return
	 */
	public boolean isConditionMatchedSequence(IAST sequence, PatternMap patternMap);

	/**
	 * Return <code>true</code>, if the expression is a pattern sequence with an associated default value,
	 * 
	 * @return
	 */
	public boolean isDefault();
}
