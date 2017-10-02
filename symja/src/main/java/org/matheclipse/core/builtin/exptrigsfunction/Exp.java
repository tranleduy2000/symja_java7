package org.matheclipse.core.builtin.exptrigsfunction;

import org.matheclipse.core.eval.interfaces.AbstractArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import static org.matheclipse.core.expression.F.Power;

public class Exp extends AbstractArg1 implements INumeric {
    public Exp() {
    }

    @Override
    public IExpr e1ObjArg(final IExpr o) {
        return Power(F.E, o);
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.exp(stack[top]);
    }
}

