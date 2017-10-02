package org.matheclipse.core.builtin.exptrigsfunction;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.hipparchus.complex.Complex;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.ArcCotRules;

import java.util.function.DoubleUnaryOperator;

import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;

/**
 * Arccotangent
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Inverse_trigonometric functions" >
 * Inverse_trigonometric functions</a>
 */
public class ArcCot extends AbstractTrigArg1 implements ArcCotRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        if (F.isZero(operand)) {
            // Pi / 2
            return Math.PI / 2.0;
        }
        return Math.atan(1 / operand);
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        // I/arg1
        Apcomplex ac = Apcomplex.I.divide(arg1);

        // (I/2) (Log(1 - I/arg1) - Log(1 + I/arg1))
        Apcomplex result = Apcomplex.I.divide(new Apfloat(2)).multiply(
                ApcomplexMath.log(Apcomplex.ONE.subtract(ac)).subtract(ApcomplexMath.log(Apcomplex.ONE.add(ac))));
        return F.complexNum(result);
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        if (arg1.equals(Apcomplex.ZERO)) {
            // Pi / 2
            return F.num(ApfloatMath.pi(arg1.precision()).divide(new Apfloat(2)));
        }
        return F.num(ApfloatMath.atan(ApfloatMath.inverseRoot(arg1, 1)));
    }

    @Override
    public IExpr e1ComplexArg(final Complex arg1) {
        // I/arg1
        Complex c = Complex.I.divide(arg1);

        // (I/2) (Log(1 - I/arg1) - Log(1 + I/arg1))
        Complex result = Complex.I.divide(new Complex(2.0))
                .multiply(Complex.ONE.subtract(c).log().subtract(Complex.ONE.add(c).log()));
        return F.complexNum(result);
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        if (F.isZero(arg1)) {
            // Pi / 2
            return F.num(Math.PI / 2.0);
        }
        return F.num(Math.atan(1 / arg1));
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return Plus(F.Negate(Pi), F.ArcCot(negExpr));
        }
        IExpr imPart = AbstractFunctionEvaluator.getPureImaginaryPart(arg1);
        if (imPart.isPresent()) {
            return F.Times(F.CNI, F.ArcCoth(imPart));
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