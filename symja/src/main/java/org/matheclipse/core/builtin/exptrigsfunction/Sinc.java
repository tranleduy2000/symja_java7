package org.matheclipse.core.builtin.exptrigsfunction;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.SincRules;

import java.util.function.DoubleUnaryOperator;

import static org.matheclipse.core.expression.F.CD1;
import static org.matheclipse.core.expression.F.num;

/**
 * Sinc function.
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Sinc_function">Sinc function</a>
 */
public class Sinc extends AbstractTrigArg1 implements INumeric, SincRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        return Math.sin(operand) / operand;
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        if (arg1.equals(Apcomplex.ZERO)) {
            return num(Apcomplex.ONE);
        }
        return F.complexNum(ApcomplexMath.sin(arg1).divide(arg1));
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        if (arg1.equals(Apcomplex.ZERO)) {
            return num(Apcomplex.ONE);
        }
        return num(ApfloatMath.sin(arg1).divide(arg1));
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        if (arg1 == 0.0) {
            return CD1;
        }
        return num(Math.sin(arg1) / arg1);
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        double a1 = stack[top];
        if (a1 == 0.0) {
            return 1.0;
        }
        return Math.sin(a1) / a1;
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1);
        if (negExpr.isPresent()) {
            return F.Sinc(negExpr);
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

