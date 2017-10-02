package org.matheclipse.core.reflection.system;

import org.matheclipse.core.interfaces.IExpr;

/**
 * Check an expression, if it's an allowed object.
 */
public final class IsWrongSolveExpression implements Predicate<IExpr> {
    IExpr wrongExpr;

    public IsWrongSolveExpression() {
        wrongExpr = null;
    }

    public IExpr getWrongExpr() {
        return wrongExpr;
    }

    @Override
    public boolean test(IExpr input) {
        if (input.isDirectedInfinity() || input.isIndeterminate()) {
            // input is representing a DirectedInfinity() or Indeterminate
            // object
            wrongExpr = input;
            return true;
        }
        return false;
    }
}