package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Operator *=
 */
public class TimesBy extends AddTo {

    @Override
    protected IAST getAST(final IExpr value) {
        return F.Times(null, value);
    }

    @Override
    protected ISymbol getFunctionSymbol() {
        return F.TimesBy;
    }
}