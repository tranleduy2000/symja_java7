package org.matheclipse.core.builtin.listfunction;

import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.util.function.Function;

public class AppendToFunction implements Function<IExpr, IExpr> {
    private final IExpr value;

    public AppendToFunction(final IExpr value) {
        this.value = value;
    }

    @Override
    public IExpr apply(final IExpr symbolValue) {
        if (!symbolValue.isAST()) {
            return null;
        }
        return ((IAST) symbolValue).appendClone(value);
    }

}