package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <pre>
 * IntegerLength(x)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the number of digits in the base-10 representation of <code>x</code>.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * IntegerLength(x, b)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the number of base-<code>b</code> digits in <code>x</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; IntegerLength(123456)
 * 6
 *
 * &gt;&gt; IntegerLength(10^10000)
 * 10001
 *
 * &gt;&gt; IntegerLength(-10^1000)
 * 1001
 * </pre>
 * <p>
 * <code>IntegerLength</code> with base <code>2</code>:
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; IntegerLength(8, 2)
 * 4
 * </pre>
 * <p>
 * Check that <code>IntegerLength</code> is correct for the first 100 powers of 10:
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; IntegerLength /@ (10 ^ Range(100)) == Range(2, 101)
 * True
 * </pre>
 * <p>
 * The base must be greater than <code>1</code>:
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; IntegerLength(3, -2)
 * IntegerLength(3, -2)
 * </pre>
 * <p>
 * '0' is a special case:
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; IntegerLength(0)
 * 0
 *
 * &gt;&gt; IntegerLength /@ (10 ^ Range(100) - 1) == Range(1, 100)
 * True
 * </pre>
 */
public class IntegerLength extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        if (ast.arg1().isInteger()) {
            IInteger radix = F.C10;
            if (ast.isAST2()) {
                if (ast.arg2().isInteger()) {
                    radix = ((IInteger) ast.arg2());
                } else {
                    return F.NIL;
                }
            }
            if (radix.isLessThan(F.C1)) {
                engine.printMessage("IntegerLength: The base must be greater than 1");
                return F.NIL;
            }
            IInteger iArg1 = (IInteger) ast.arg1();
            if (iArg1.isZero()) {
                return F.C1;
            }
            long l = iArg1.integerLength(radix);

            return F.integer(l);
        }
        return F.NIL;
    }

    @Override
    public void setUp(ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }
}

