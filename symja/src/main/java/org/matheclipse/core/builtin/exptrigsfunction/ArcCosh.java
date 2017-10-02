package org.matheclipse.core.builtin.exptrigsfunction;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.hipparchus.complex.Complex;
import org.hipparchus.util.FastMath;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.ArcCoshRules;

import java.util.function.DoubleUnaryOperator;

/**
 * Inverse hyperbolic cosine
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Inverse_hyperbolic_function">
 * Inverse hyperbolic functions</a>
 */
public class ArcCosh extends AbstractTrigArg1 implements INumeric, ArcCoshRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        return FastMath.acosh(operand);
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        return F.complexNum(ApcomplexMath.acosh(arg1));
    }

    @Override
    public IExpr e1ComplexArg(final Complex arg1) {
        double re = arg1.getReal();
        double im = arg1.getImaginary();
        Complex temp = new Complex(re * re - im * im - 1, 2 * re * im).sqrt();
        return F.complexNum(new Complex(temp.getReal() + re, temp.getImaginary() + im).log());
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        return F.num(ApfloatMath.acosh(arg1));
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        double val = FastMath.acosh(arg1);
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
        return FastMath.acosh(stack[top]);
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
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