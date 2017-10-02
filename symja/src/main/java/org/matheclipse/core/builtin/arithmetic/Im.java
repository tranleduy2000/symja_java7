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
import static org.matheclipse.core.expression.F.E;
import static org.matheclipse.core.expression.F.Im;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sin;
import static org.matheclipse.core.expression.F.Times;

/**
 * Get the imaginary part of an expression
 * <p>
 * See: <a href="http://en.wikipedia.org/wiki/Imaginary_part">Imaginary part</a>
 */
public class Im extends AbstractEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        IExpr arg1 = ast.arg1();
        if (arg1.isDirectedInfinity()) {
            IAST directedInfininty = (IAST) arg1;
            if (directedInfininty.isComplexInfinity()) {
                return F.Indeterminate;
            }
            if (directedInfininty.isAST1()) {
                if (directedInfininty.isInfinity()) {
                    return F.C0;
                }
                IExpr im = engine.evaluate(F.Im(directedInfininty.arg1()));
                if (im.isNumber()) {
                    if (im.isZero()) {
                        return F.C0;
                    }
                    return F.Times(F.Sign(im), F.CInfinity);
                }
            }
        }
        if (arg1.isNumber()) {
            return ((INumber) arg1).im();
        }
        if (arg1.isRealResult()) {
            return F.C0;
        }
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return Negate(Im(negExpr));
        }
        if (arg1.isTimes()) {
            if (arg1.getAt(1).isSignedNumber()) {
                IAST temp = ((IAST) arg1).removeAtClone(1);
                return F.Times(arg1.getAt(1), F.Im(temp));
            }
            if (arg1.getAt(1).isImaginaryUnit()) {
                // Im(I*temp) -> Re(temp)
                return ((IAST) arg1).removeAtClone(1).re();
            }
        }
        if (arg1.isPlus()) {
            return ((IAST) arg1).mapThread((IAST) F.Im(null), 1);
        }
        if (arg1.isPower()) {
            IAST astPower = (IAST) arg1;
            IExpr base = astPower.arg1();
            if (base.isRealResult()) {
                // test for x^(a+I*b)
                IExpr x = base;
                IExpr exponent = astPower.arg2();
                if (exponent.isNumber()) {
                    // (x^2)^(a/2)*E^(-b*Arg[x])*Sin[a*Arg[x]+1/2*b*Log[x^2]]
                    IExpr a = ((INumber) exponent).re();
                    IExpr b = ((INumber) exponent).im();
                    return imPowerComplex(x, a, b);
                }
                if (exponent.isNumericFunction()) {
                    // (x^2)^(a/2)*E^(-b*Arg[x])*Sin[a*Arg[x]+1/2*b*Log[x^2]]
                    IExpr a = engine.evaluate(F.Re(exponent));
                    IExpr b = engine.evaluate(F.Im(exponent));
                    return imPowerComplex(x, a, b);
                }
            }
        }
        return F.NIL;
    }

    /**
     * Evaluate <code>Im(x^(a+I*b))</code>
     *
     * @param x
     * @param a the real part of the exponent
     * @param b the imaginary part of the exponent
     * @return
     */
    private IExpr imPowerComplex(IExpr x, IExpr a, IExpr b) {
        if (x.isE()) {
            // Im(E^(a+I*b)) -> E^a*Sin[b]
            return Times(Power(F.E, a), Sin(b));
        }
        return Times(Times(Power(Power(x, C2), Times(C1D2, a)), Power(E, Times(Negate(b), Arg(x)))),
                Sin(Plus(Times(a, Arg(x)), Times(Times(C1D2, b), Log(Power(x, C2))))));
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
    }

}
