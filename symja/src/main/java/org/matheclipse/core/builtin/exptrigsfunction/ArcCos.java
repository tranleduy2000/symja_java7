package org.matheclipse.core.builtin.exptrigsfunction;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.hipparchus.complex.Complex;
import org.matheclipse.core.eval.exception.ComplexResultException;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.ArcCosRules;

import java.util.function.DoubleUnaryOperator;

import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;

/**
 * Arccosine
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Inverse_trigonometric functions" >
 * Inverse_trigonometric functions</a>
 */
public class ArcCos extends AbstractTrigArg1 implements INumeric, ArcCosRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        double val = Math.acos(operand);
        if (Double.isNaN(val)) {
            throw new ComplexResultException("ArcCos(NaN)");
        }
        return val;
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        return F.complexNum(ApcomplexMath.acos(arg1));
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        return F.num(ApfloatMath.acos(arg1));
    }

    @Override
    public IExpr e1ComplexArg(final Complex arg1) {
        return F.complexNum(arg1.acos());
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        double val = Math.acos(arg1);
        if (Double.isNaN(val)) {
            return F.complexNum(Complex.valueOf(arg1).acos());
        }
        return F.num(val);

    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.acos(stack[top]);
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return Plus(F.Negate(Pi), F.ArcCos(negExpr));
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