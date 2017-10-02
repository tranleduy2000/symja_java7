package org.matheclipse.core.builtin.exptrigsfunction;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.hipparchus.complex.Complex;
import org.hipparchus.util.FastMath;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.ArcTanhRules;

import java.util.function.DoubleUnaryOperator;

import static org.matheclipse.core.expression.F.Negate;

/**
 * Inverse hyperbolic tangent
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Inverse_hyperbolic_function">
 * Inverse hyperbolic functions</a>
 */
public class ArcTanh extends AbstractTrigArg1 implements INumeric, ArcTanhRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        return FastMath.atanh(operand);
    }

    @Override
    public IExpr e1ComplexArg(final Complex z) {
        // 1/2 * Ln( (1+z) / (1-z) )
        Complex temp = Complex.ONE.add(z).divide(Complex.ONE.subtract(z)).log();
        return F.complexNum(new Complex(temp.getReal() / 2, temp.getImaginary() / 2));
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        return F.complexNum(ApcomplexMath.atanh(arg1));
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        return F.num(ApfloatMath.atanh(arg1));
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        double val = FastMath.atanh(arg1);
        if (Double.isNaN(val)) {
            return e1ComplexArg(new Complex(arg1));
        }
        return F.num(val);

    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return FastMath.atanh(stack[top]);
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return Negate(F.ArcTanh(negExpr));
        }
        IExpr imPart = AbstractFunctionEvaluator.getPureImaginaryPart(arg1);
        if (imPart.isPresent()) {
            return F.Times(F.CI, F.ArcTan(imPart));
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