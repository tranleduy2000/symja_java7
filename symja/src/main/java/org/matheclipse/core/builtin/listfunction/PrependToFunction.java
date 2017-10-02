package org.matheclipse.core.builtin.listfunction;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.util.function.Function;

public class PrependToFunction implements Function<IExpr, IExpr> {
    private final IExpr value;

    public PrependToFunction(final IExpr value) {
        this.value = value;
    }

    @Override
    public IExpr apply(final IExpr symbolValue) {
        if (!symbolValue.isAST()) {
            return F.NIL;
        }
        return ((IAST) symbolValue).appendAtClone(1, value);
    }

}