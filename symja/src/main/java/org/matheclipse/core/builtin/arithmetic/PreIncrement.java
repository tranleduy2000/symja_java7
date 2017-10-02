package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.ISymbol;

public class PreIncrement extends PreDecrement {

    @Override
    protected IAST getAST() {
        return F.Plus(null, F.C1);
    }

    @Override
    protected ISymbol getFunctionSymbol() {
        return F.PreIncrement;
    }

}

  
