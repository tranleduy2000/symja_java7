package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public class PreDecrement extends Decrement {

    public PreDecrement() {
        super();
    }

    @Override
    protected IAST getAST() {
        return F.Plus(null, F.CN1);
    }

    @Override
    protected IExpr getResult(IExpr symbolValue, IExpr calculatedResult) {
        return calculatedResult;
    }

    @Override
    protected ISymbol getFunctionSymbol() {
        return F.PreDecrement;
    }
}

