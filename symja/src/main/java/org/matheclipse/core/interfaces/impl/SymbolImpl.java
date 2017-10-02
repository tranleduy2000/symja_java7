package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.DoubleFunction;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class SymbolImpl extends ExprImpl implements ISymbol {
    @Override
    public IExpr mapConstantDouble(DoubleFunction<IExpr> function) {
        return F.NIL;
    }
}
