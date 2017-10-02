package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.expression.F.Negate;

/**
 * <pre>
 * Round(expr)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * round a given <code>expr</code> to nearest integer.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Round(-3.6)
 * -4
 * </pre>
 */
public class Round extends AbstractFunctionEvaluator implements INumeric {

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.round(stack[top]);
    }

    /**
     * Round a given value to nearest integer.
     * <p>
     * See <a href="http://en.wikipedia.org/wiki/Floor_and_ceiling_functions">Wikipedia - Floor and ceiling
     * functions</a>
     */
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        try {
            IExpr arg1 = engine.evaluate(ast.arg1());
            ISignedNumber signedNumber = arg1.evalSignedNumber();
            if (signedNumber != null) {
                return signedNumber.round();
            }

            if (arg1.isIntegerResult()) {
                return arg1;
            }

            if (arg1.isPlus()) {
                IAST[] result = ((IAST) arg1).filter(new RoundPlusFunction());
                if (result[0].size() > 1) {
                    if (result[1].size() > 1) {
                        result[0].append(F.Round(result[1]));
                    }
                    return result[0];
                }
            }
            IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
            if (negExpr.isPresent()) {
                return Negate(F.Round(negExpr));
            }
        } catch (ArithmeticException ae) {
            // ISignedNumber#round() may throw ArithmeticException
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL | ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }


}
