package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.expression.F.Arg;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.E;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.x;

/**
 * Get the real part of an expression
 * <p>
 * See: <a href="http://en.wikipedia.org/wiki/Real_part">Real part</a>
 */
public class Re extends AbstractEvaluator {

    public static IExpr evalRe(IExpr expr, EvalEngine engine) {
        if (expr.isDirectedInfinity()) {
            IAST directedInfininty = (IAST) expr;
            if (directedInfininty.isComplexInfinity()) {
                return F.Indeterminate;
            }
            if (directedInfininty.isAST1()) {
                if (directedInfininty.isInfinity()) {
                    return F.CInfinity;
                }
                IExpr re = directedInfininty.arg1().re();
                if (re.isNumber()) {
                    if (re.isZero()) {
                        return F.C0;
                    }
                    return F.Times(F.Sign(re), F.CInfinity);
                }
            }
        }
        if (expr.isNumber()) {
            return ((INumber) expr).re();
        }
        if (expr.isRealResult()) {
            return expr;
        }

        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(expr);
        if (negExpr.isPresent()) {
            return Negate(F.Re(negExpr));
        }
        if (expr.isTimes()) {
            if (expr.getAt(1).isSignedNumber()) {
                IAST temp = ((IAST) expr).removeAtClone(1);
                return F.Times(expr.getAt(1), F.Re(temp));
            }
            if (expr.getAt(1).isImaginaryUnit()) {
                // Re(I*temp) -> -Im(temp)
                IAST temp = ((IAST) expr).removeAtClone(1);
                return F.Times(F.CN1, F.Im(temp));
            }
        }
        if (expr.isPlus()) {
            return ((IAST) expr).mapThread((IAST) F.Re(null), 1);
        }
        if (expr.isPower()) {
            IAST astPower = (IAST) expr;
            IExpr base = astPower.arg1();
            if (base.isRealResult()) {
                // test for x^(a+I*b)
                IExpr exponent = astPower.arg2();
                if (exponent.isNumber()) {
                    // (x^2)^(a/2)*E^(-b*Arg[x])*Cos[a*Arg[x]+1/2*b*Log[x^2]]
                    return rePowerComplex(x, ((INumber) exponent).re(), ((INumber) exponent).im());
                }
                // (x^2)^(a/2)*E^(-b*Arg[x])*Cos[a*Arg[x]+1/2*b*Log[x^2]]
                return rePowerComplex(x, exponent.re(), exponent.im());
            }
        }
        return F.NIL;
    }

    /**
     * Evaluate <code>Re(x^(a+I*b))</code>
     *
     * @param x
     * @param a the real part of the exponent
     * @param b the imaginary part of the exponent
     * @return
     */
    private static IExpr rePowerComplex(IExpr x, IExpr a, IExpr b) {
        if (x.isE()) {
            // Re(E^(a+I*b)) -> E^a*Cos[b]
            return Times(Power(F.E, a), Cos(b));
        }
        return Times(Times(Power(Power(x, C2), Times(C1D2, a)), Power(E, Times(Negate(b), Arg(x)))),
                Cos(Plus(Times(a, Arg(x)), Times(Times(C1D2, b), Log(Power(x, C2))))));
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        IExpr arg1 = ast.arg1();
        return evalRe(arg1, engine);
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
    }

}