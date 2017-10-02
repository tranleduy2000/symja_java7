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
 * Floor(expr)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the smallest integer less than or equal <code>expr</code>.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * Floor(expr, a)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the smallest multiple of <code>a</code> less than or equal to <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Floor(1/3)
 * 0
 *
 * &gt;&gt; Floor(-1/3)
 * -1
 *
 * &gt;&gt; Floor(10.4)
 * 10
 *
 * &gt;&gt; Floor(10/3)
 * 3
 *
 * &gt;&gt; Floor(10)
 * 10
 *
 * &gt;&gt; Floor(21, 2)
 * 20
 *
 * &gt;&gt; Floor(2.6, 0.5)
 * 2.5
 *
 * &gt;&gt; Floor(-10.4)
 * -11
 * </pre>
 * <p>
 * For complex <code>expr</code>, take the floor of real an imaginary parts.<br />
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Floor(1.5 + 2.7*I)
 * 1+I*2
 * </pre>
 * <p>
 * For negative <code>a</code>, the smallest multiple of <code>a</code> greater than or equal to <code>expr</code>
 * is returned.<br />
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Floor(10.4, -1)
 * 11
 *
 * &gt;&gt; Floor(-10.4, -1)
 * -10
 * </pre>
 */
public class Floor extends AbstractFunctionEvaluator implements INumeric {

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.floor(stack[top]);
    }

    /**
     * Returns the largest (closest to positive infinity) <code>ISignedNumber</code> value that is not greater than
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
                return F.Times(F.Floor(F.Divide(ast.arg1(), ast.arg2())), ast.arg2());
            }
            IExpr arg1 = engine.evaluateNull(ast.arg1());
            if (arg1.isPresent()) {
                return evalFloor(arg1).orElse(F.Floor(arg1));
            }
            return evalFloor(ast.arg1());
        } catch (ArithmeticException ae) {
            // ISignedNumber#floor() may throw ArithmeticException
        }
        return F.NIL;
    }

    public IExpr evalFloor(IExpr arg1) {
        if (arg1.isNumber()) {
            return ((INumber) arg1).floorFraction();
        }
        INumber number = arg1.evalNumber();
        if (number != null) {
            return number.floorFraction();
        }
        if (arg1.isIntegerResult()) {
            return arg1;
        }
        if (arg1.isPlus()) {
            IAST[] splittedPlus = ((IAST) arg1).filter(new FloorPlusFunction());
            if (splittedPlus[0].size() > 1) {
                if (splittedPlus[1].size() > 1) {
                    splittedPlus[0].append(F.Floor(splittedPlus[1].getOneIdentity(F.C0)));
                }
                return splittedPlus[0];
            }
        }
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return Negate(F.Ceiling(negExpr));
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL | ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }

}