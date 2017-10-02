package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.expression.F.Negate;

/**
 * <pre>
 * Ceiling(expr)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the first integer greater than or equal <code>expr</code>.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * Ceiling(expr, a)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the first multiple of <code>a</code> greater than or equal to <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Ceiling(1/3)
 * 1
 *
 * &gt;&gt; Ceiling(-1/3)
 * 0
 *
 * &gt;&gt; Ceiling(1.2)
 * 2
 *
 * &gt;&gt; Ceiling(3/2)
 * 2
 * </pre>
 * <p>
 * For complex <code>expr</code>, take the ceiling of real and imaginary parts.<br />
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Ceiling(1.3 + 0.7*I)
 * 2+I
 *
 * &gt;&gt; Ceiling(10.4, -1)
 * 10
 *
 * &gt;&gt; Ceiling(-10.4, -1)
 * -11
 * </pre>
 */
public class Ceiling extends AbstractFunctionEvaluator implements INumeric {

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.ceil(stack[top]);
    }

    /**
     * Returns the smallest (closest to negative infinity) <code>ISignedNumber</code> value that is not less than
     * <code>this</code> and is equal to a mathematical integer.
     * <p>
     * See <a href="http://en.wikipedia.org/wiki/Floor_and_ceiling_functions">Wikipedia - Floor and ceiling
     * functions</a>
     */
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        try {
            if (ast.isAST2()) {
                return F.Times(F.Ceiling(F.Divide(ast.arg1(), ast.arg2())), ast.arg2());
            }
            IExpr arg1 = engine.evaluateNull(ast.arg1());
            if (arg1.isPresent()) {
                return evalCeiling(arg1).orElse(F.Ceiling(arg1));
            }
            return evalCeiling(ast.arg1());
        } catch (ArithmeticException ae) {
            // ISignedNumber#floor() or #ceil() may throw ArithmeticException
        }
        return F.NIL;
    }

    public IExpr evalCeiling(IExpr arg1) {
        if (arg1.isNumber()) {
            return ((INumber) arg1).ceilFraction();
        }
        INumber number = arg1.evalNumber();
        if (number != null) {
            return number.ceilFraction();
        }

        if (arg1.isIntegerResult()) {
            return arg1;
        }

        if (arg1.isPlus()) {
            IAST[] splittedPlus = ((IAST) arg1).filter(new CeilingPlusFunction());
            if (splittedPlus[0].size() > 1) {
                if (splittedPlus[1].size() > 1) {
                    splittedPlus[0].append(F.Ceiling(splittedPlus[1].getOneIdentity(F.C0)));
                }
                return splittedPlus[0];
            }
        }
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return Negate(F.Floor(negExpr));
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL | ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }

}
