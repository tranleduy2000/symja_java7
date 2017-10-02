package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import java.util.function.Function;

public class RoundPlusFunction implements Function<IExpr, IExpr> {
    @Override
    public IExpr apply(IExpr expr) {
        if (expr.isInteger()) {
            return expr;
        }
        return F.NIL;
    }
}