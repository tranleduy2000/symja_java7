package org.matheclipse.core.reflection.system.function;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;

import static org.matheclipse.core.reflection.system.Sign.numberSign;

public final class SignTimesFunction implements Function<IExpr, IExpr> {
    @Override
    public IExpr apply(IExpr expr) {
        if (expr.isNumber()) {
            return numberSign((INumber) expr);
        }
        IExpr temp = F.eval(F.Sign(expr));
        if (!temp.topHead().equals(F.Sign)) {
            return temp;
        }
        return F.NIL;
    }
}