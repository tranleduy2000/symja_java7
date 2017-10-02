package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.Lambda;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <pre>
 * PowerMod(x, y, m)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * computes <code>x^y</code> modulo <code>m</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; PowerMod(2, 10000000, 3)
 * 1
 * &gt;&gt; PowerMod(3, -2, 10)
 * 9
 * </pre>
 * <p>
 * 0 is not invertible modulo 2.
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; PowerMod(0, -1, 2)
 * PowerMod(0, -1, 2)
 * </pre>
 * <p>
 * The argument 0 should be nonzero.
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; PowerMod(5, 2, 0)
 *  PowerMod(5, 2, 0)
 * </pre>
 */
public class PowerMod extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 4);

        if (Lambda.exists(ast, x -> !x.isInteger(), 1)) {
            return F.NIL;
        }
        IInteger arg1 = (IInteger) ast.get(1);
        IInteger arg2 = (IInteger) ast.get(2);
        IInteger arg3 = (IInteger) ast.get(3);
        try {
            if (arg2.isMinusOne()) {
                return arg1.modInverse(arg3);
            }
            return arg1.modPow(arg2, arg3);
        } catch (ArithmeticException ae) {
            engine.printMessage("PowerMod: " + ae.getMessage());
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
        super.setUp(newSymbol);
    }
}