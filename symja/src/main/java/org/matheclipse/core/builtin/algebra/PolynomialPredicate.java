package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import java.util.function.Predicate;

/**
 * This predicate identifies polynomial expressions. It requires that the given
 * expression is already expanded for <code>Plus,Power and Times</code>
 * operations.
 */
public final class PolynomialPredicate implements Predicate<IExpr> {

    @Override
    public boolean test(IExpr expr) {
        return expr.isPolynomial(F.List());
    }
}