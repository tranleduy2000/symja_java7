package org.matheclipse.core.builtin.exptrigsfunction;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.hipparchus.complex.Complex;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.SinRules;

import java.util.function.DoubleUnaryOperator;

import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Sin;
import static org.matheclipse.core.expression.F.Subtract;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.num;

/**
 * Sine function.
 * <p>
 * See
 * <a href="http://en.wikipedia.org/wiki/Trigonometric_functions">Trigonometric
 * functions</a>
 */
public class Sin extends AbstractTrigArg1 implements INumeric, SinRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        return Math.sin(operand);
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        return F.complexNum(ApcomplexMath.sin(arg1));
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        return num(ApfloatMath.sin(arg1));
    }

    @Override
    public IExpr e1ComplexArg(final Complex arg1) {
        return F.complexNum(arg1.sin());
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        return num(Math.sin(arg1));
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.sin(stack[top]);
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return Negate(Sin(negExpr));
        }
        IExpr imPart = AbstractFunctionEvaluator.getPureImaginaryPart(arg1);
        if (imPart.isPresent()) {
            return Times(F.CI, F.Sinh(imPart));
        }
        IAST parts = AbstractFunctionEvaluator.getPeriodicParts(arg1, Pi);
        if (parts.isPresent()) {
            if (parts.arg2().isInteger()) {
                // period n*Pi
                IInteger i = (IInteger) parts.arg2();
                if (i.isEven()) {
                    return Sin(parts.arg1());
                } else {
                    return Times(CN1, Sin(parts.arg1()));
                }
            }

            if (parts.arg2().isFraction()) {
                // period (n/m)*Pi
                IFraction f = (IFraction) parts.arg2();
                IInteger[] divRem = f.divideAndRemainder();
                IFraction rest = F.fraction(divRem[1], f.getDenominator());
                if (!divRem[0].isZero()) {

                    if (divRem[0].isEven()) {
                        return Sin(Plus(parts.arg1(), Times(rest, Pi)));
                    } else {
                        return Times(CN1, Sin(Plus(parts.arg1(), Times(rest, Pi))));
                    }
                }

                if (rest.equals(C1D2)) {
                    // Sin(z) == Cos(Pi/2 - z)
                    return F.Cos(Subtract(F.Divide(Pi, C2), arg1));
                }
            }

            if (parts.arg2().isIntegerResult()) {
                // period n*Pi
                return Times(F.Power(CN1, parts.arg2()), Sin(parts.arg1()));
            }

        }
        return F.NIL;
    }

    @Override
    public IAST getRuleAST() {
        return RULES;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }

}
