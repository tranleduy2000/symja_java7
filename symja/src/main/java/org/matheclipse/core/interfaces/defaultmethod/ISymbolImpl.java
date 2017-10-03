package org.matheclipse.core.interfaces.defaultmethod;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.DoubleFunction;

/**
 * Created by Duy on 10/3/2017.
 */

public abstract class ISymbolImpl extends IExprImpl implements ISymbol {
    /**
     * If this symbol has attribute <code>ISymbol.CONSTANT</code> and the symbol's evaluator is of instance
     * <code>INumericConstant</code>, then apply the constants double value to the given function and return the result,
     * otherwise return <code>F.NIL</code>.
     *
     * @param function applys the function to a <code>double</code> value, resulting in an object of type {@code IExpr}.
     * @return the resulting expression from the function or <code>F.NIL</code>.
     * @see org.matheclipse.core.reflection.system.Abs
     * @see org.matheclipse.core.reflection.system.Ceiling
     * @see org.matheclipse.core.reflection.system.Floor
     */
    @Override
    public IExpr mapConstantDouble(DoubleFunction<IExpr> function) {
        return F.NIL;
    }
}
