package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractArg2;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <pre>
 * Mod(x, m)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns <code>x</code> modulo <code>m</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Mod(14, 6)
 * 2
 * &gt;&gt; Mod(-3, 4)
 * 1
 * &gt;&gt; Mod(-3, -4)
 * -3
 * </pre>
 * <p>
 * The argument 0 should be nonzero
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Mod(5, 0)
 * Mod(5, 0)
 * </pre>
 */
public class Mod extends AbstractArg2 {

    /**
     * See: <a href="http://en.wikipedia.org/wiki/Modular_arithmetic">Wikipedia - Modular arithmetic</a>
     */
    @Override
    public IExpr e2IntArg(final IInteger i0, final IInteger i1) {
        try {
            if (i1.isNegative()) {
                return i0.negate().mod(i1.negate()).negate();
            }
            return i0.mod(i1);
        } catch (ArithmeticException ae) {
            EvalEngine.get().printMessage("Mod: " + ae.getMessage());
            return F.NIL;
        }
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }

}