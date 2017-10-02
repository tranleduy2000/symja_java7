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
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.SinhRules;

import java.util.function.DoubleUnaryOperator;

/**
 * Hyperbolic sine
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Hyperbolic_function">Hyperbolic
 * function</a>
 */
public class Sinh extends AbstractTrigArg1 implements INumeric, SinhRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        return Math.sinh(operand);
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        return F.complexNum(ApcomplexMath.sinh(arg1));
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        return F.num(ApfloatMath.sinh(arg1));
    }

    @Override
    public IExpr e1ComplexArg(final Complex arg1) {
        return F.complexNum(arg1.sinh());
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        return F.num(Math.sinh(arg1));
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.sinh(stack[top]);
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return F.Negate(F.Sinh(negExpr));
        }
        IExpr imPart = AbstractFunctionEvaluator.getPureImaginaryPart(arg1);
        if (imPart.isPresent()) {
            return F.Times(F.CI, F.Sin(imPart));
        }
        if (arg1.isZero()) {
            return F.C0;
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
