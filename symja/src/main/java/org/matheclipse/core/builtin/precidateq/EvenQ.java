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
 * Returns <code>True</code> if the 1st argument is an even integer number;
 * <code>False</code> otherwise
 */
public class EvenQ extends AbstractCorePredicateEvaluator implements Predicate<IExpr> {
    public EvenQ() {
        super(F.EvenQ);
    }

    @Override
    public boolean evalArg1Boole(final IExpr arg1, EvalEngine engine) {
        return arg1.isInteger() && ((IInteger) arg1).isEven();
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }

    @Override
    public boolean test(final IExpr expr) {
        return (expr.isInteger()) && ((IInteger) expr).isEven();
    }
}