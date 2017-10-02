package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.eval.exception.NoEvalException;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IIterator;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class IteratorImpl implements IIterator<IExpr> {
    /**
     * Set up this iterator.
     *
     * @return
     */
    public boolean setUp() {
        return true;
    }

    /**
     * Set up this iterator.
     *
     * @return
     */
    public boolean setUpThrow() {
        boolean result = setUp();
        if (!isSetIterator() && !isNumericFunction()) {
            throw NoEvalException.CONST;
        }
        return result;
    }

    /**
     * Get a hint of how much iterations probably will occurr.
     *
     * @return
     */
    public int allocHint() {
        return 10;
    }

    /**
     * Get the &quot;upper limit&quot; which is used in this iterator.
     *
     * @return <code>null</code> if no &quot;upper limit&quot; is defined
     */
    public IExpr getUpperLimit() {
        return null;
    }

    /**
     * Get the &quot;lower limit&quot; which is used in this iterator.
     *
     * @return <code>null</code> if no &quot;lower limit&quot; is defined
     */
    public IExpr getLowerLimit() {
        return null;
    }

    /**
     * Get the &quot;step&quot; which is used in this iterator.
     *
     * @return <code>null</code> if no &quot;step&quot; is defined
     */
    public IExpr getStep() {
        return null;
    }

    /**
     * Get the variable which is used in this iterator.
     *
     * @return <code>null</code> if no variable is defined
     */
    public ISymbol getVariable() {
        return null;
    }

    /**
     * Test if &quot;lower limit&quot;, &quot;upper limit&quot; and &quot;step&quot; are numeric functions.
     *
     * @return
     */
    public boolean isNumericFunction() {
        return false;
    }

    /**
     * Test if there's a valid variable set for the iterator.
     *
     * @return
     */
    public boolean isSetIterator() {
        return false;
    }

    /**
     * Test if there's a valid variable set for the iterator and the &quot;upper limit&quot; is a list.
     *
     * @return
     */
    public boolean isValidVariable() {
        return false;
    }

    /**
     * Tear down this iterator.
     */
    public void tearDown() {

    }
}
