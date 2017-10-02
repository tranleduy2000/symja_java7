package org.matheclipse.core.builtin.arithmetic;

import org.apfloat.Apcomplex;
import org.apfloat.ApcomplexMath;
import org.apfloat.Apfloat;
import org.apfloat.ApfloatMath;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.ComplexNum;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.AbsRules;

import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

/**
 * Absolute value of a number. See
 * <a href="http://en.wikipedia.org/wiki/Absolute_value">Wikipedia:Absolute
 * value</a>
 */
public class Abs extends AbstractTrigArg1 implements INumeric, AbsRules, DoubleUnaryOperator {

    @Override
    public double applyAsDouble(double operand) {
        return Math.abs(operand);
    }

    @Override
    public IExpr e1ApcomplexArg(Apcomplex arg1) {
        return F.num(ApcomplexMath.abs(arg1));
    }

    @Override
    public IExpr e1ApfloatArg(Apfloat arg1) {
        return F.num(ApfloatMath.abs(arg1));
    }

    @Override
    public IExpr e1ComplexArg(final org.hipparchus.complex.Complex arg1) {
        return F.num(ComplexNum.dabs(arg1));
    }

    @Override
    public IExpr e1DblArg(final double arg1) {
        return F.num(Math.abs(arg1));
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.abs(stack[top]);
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        if (arg1.isDirectedInfinity()) {
            return F.CInfinity;
        }
        if (arg1.isNumber()) {
            return ((INumber) arg1).abs();
        }
        if (arg1.isNumericFunction()) {
            IExpr temp = F.evaln(arg1);
            if (temp.isSignedNumber()) {
                if (temp.isNegative()) {
                    return arg1.negate();
                } else {
                    return arg1;
                }
            }
        }
        if (arg1.isNegativeResult()) {
            return F.Negate(arg1);
        }
        if (arg1.isNonNegativeResult()) {
            return arg1;
        }
        if (arg1.isSymbol()) {
            ISymbol sym = (ISymbol) arg1;
            return sym.mapConstantDouble(new AbsNumericFunction(sym));
        }

        if (arg1.isTimes()) {
            IAST[] result = ((IAST) arg1).filter(new AbsTimesFunction());
            if (result[0].size() > 1) {
                if (result[1].size() > 1) {
                    result[0].append(F.Abs(result[1]));
                }
                return result[0];
            }
        }
        if (arg1.isPower() && arg1.getAt(2).isSignedNumber()) {
            return F.Power(F.Abs(arg1.getAt(1)), arg1.getAt(2));
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