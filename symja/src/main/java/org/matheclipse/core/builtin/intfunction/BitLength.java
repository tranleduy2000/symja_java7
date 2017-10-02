package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

import java.math.BigInteger;

/**
 * <pre>
 * BitLengthi(x)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the number of bits needed to represent the integer <code>x</code>. The sign of <code>x</code> is ignored.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; BitLength(1023)
 * 10
 *
 * &gt;&gt; BitLength(100)
 * 7
 *
 * &gt;&gt; BitLength(-5)
 * 3
 *
 * &gt;&gt; BitLength(0)
 * 0
 * </pre>
 */
public class BitLength extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        if (ast.arg1().isInteger()) {
            IInteger iArg1 = (IInteger) ast.arg1();
            BigInteger big = iArg1.toBigNumerator();
            return F.integer(big.bitLength());
        }
        return F.NIL;
    }

    @Override
    public void setUp(ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }
}