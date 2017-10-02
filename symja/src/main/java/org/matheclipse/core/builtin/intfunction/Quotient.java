package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractArg2;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <pre>
 * Quotient(m, n)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * computes the integer quotient of <code>m</code> and <code>n</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Quotient(23, 7)
 * 3
 * </pre>
 * <p>
 * Infinite expression Quotient(13, 0) encountered.
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Quotient(13, 0)
 * ComplexInfinity
 *
 * &gt;&gt; Quotient(-17, 7)
 * -3
 *
 * &gt;&gt; Quotient(-17, -4)
 * 4
 *
 * &gt;&gt; Quotient(19, -4)
 * -5
 * </pre>
 */
public class Quotient extends AbstractArg2 {

    @Override
    public IExpr e2IntArg(final IInteger i0, final IInteger i1) {
        if (i1.isZero()) {
            EvalEngine.get().printMessage("Quotient: division by zero");
            return F.NIL;
        }
        return i0.quotient(i1);
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
    }
}