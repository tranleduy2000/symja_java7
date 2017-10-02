package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;

import java.util.function.Function;

public final class AbsTimesFunction implements Function<IExpr, IExpr> {
    @Override
    public IExpr apply(IExpr expr) {
        if (expr.isNumber()) {
            return ((INumber) expr).abs();
        }
        IExpr temp = F.eval(F.Abs(expr));
        if (!temp.topHead().equals(F.Abs)) {
            return temp;
        }
        return F.NIL;
    }
}
