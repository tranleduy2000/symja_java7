package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.interfaces.AbstractArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.expression.F.Power;

public class Sqrt extends AbstractArg1 implements INumeric {

    public Sqrt() {
    }

    @Override
    public IExpr e1ObjArg(final IExpr o) {
        return Power(o, F.C1D2);
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.sqrt(stack[top]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
    }
}