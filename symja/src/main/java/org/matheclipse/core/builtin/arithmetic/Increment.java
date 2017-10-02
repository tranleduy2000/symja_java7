package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.ISymbol;

public class Increment extends Decrement {

    @Override
    protected IAST getAST() {
        return F.Plus(null, F.C1);
    }

    @Override
    protected ISymbol getFunctionSymbol() {
        return F.Increment;
    }
}

