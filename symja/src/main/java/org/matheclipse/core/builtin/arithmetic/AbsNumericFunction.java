package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.DoubleFunction;

public final class AbsNumericFunction implements DoubleFunction<IExpr> {
    final ISymbol symbol;

    public AbsNumericFunction(ISymbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public IExpr apply(double value) {
        if (value < Integer.MAX_VALUE && value > Integer.MIN_VALUE) {
            double result = Math.abs(value);
            if (result > 0.0) {
                return symbol;
            }
        }
        return F.NIL;
    }
}