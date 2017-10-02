package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractCorePredicateEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.Predicate;

/**
 * Predicate function
 * <p>
 * Returns <code>True</code> if the 1st argument is an odd integer number;
 * <code>False</code> otherwise
 */
public class OddQ extends AbstractCorePredicateEvaluator implements Predicate<IExpr> {
    public OddQ() {
        super(F.OddQ);
    }

    @Override
    public boolean evalArg1Boole(final IExpr arg1, EvalEngine engine) {
        return arg1.isInteger() && ((IInteger) arg1).isOdd();
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }

    @Override
    public boolean test(final IExpr expr) {
        return expr.isInteger() && ((IInteger) expr).isOdd();
    }

}