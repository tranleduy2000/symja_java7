package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractCorePredicateEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import java.util.function.Predicate;

/**
 * Predicate function
 * <p>
 * Returns <code>True</code> if the 1st argument is a list expression;
 * <code>False</code> otherwise
 */
public class ListQ extends AbstractCorePredicateEvaluator implements Predicate<IExpr> {

    @Override
    public boolean evalArg1Boole(final IExpr arg1, EvalEngine engine) {
        return arg1.isList();
    }

    @Override
    public boolean test(final IExpr expr) {
        return expr.isList();
    }
}