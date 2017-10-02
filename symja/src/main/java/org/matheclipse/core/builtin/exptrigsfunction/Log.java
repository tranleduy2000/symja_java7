package org.matheclipse.core.builtin.exptrigsfunction;

import org.matheclipse.core.eval.interfaces.AbstractArg12;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.eval.util.AbstractAssumptions;
import org.matheclipse.core.expression.ComplexNum;
import org.matheclipse.core.expression.ComplexUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.Num;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.LogRules;

import static org.matheclipse.core.expression.F.CI;
import static org.matheclipse.core.expression.F.Times;

/**
 * See <a href="http://en.wikipedia.org/wiki/Logarithm">Wikipedia -
 * Logarithm</a>
 */
public class Log extends AbstractArg12 implements INumeric, LogRules {

    /**
     * Integer logarithm of <code>arg</code> for base <code>b</code>. Gives Log
     * <sub>b</sub>(arg) or <code>Log(arg)/Log(b)</code>.
     *
     * @param b   the base of the logarithm
     * @param arg
     * @return
     */
    public static IExpr baseBLog(final IInteger b, final IInteger arg) {
        try {
            long l1 = b.toLong();
            long l2 = arg.toLong();
            double res = Math.log(l2) / Math.log(l1);
            if (F.isNumIntValue(res)) {
                int r = Double.valueOf(Math.round(res)).intValue();
                if (arg.equals(b.pow(r))) {
                    return F.integer(r);
                }
            }
        } catch (ArithmeticException ae) {
            // toLong() method failed
        }
        return F.NIL;
    }

    /**
     * <pre>
     * { Log[1]=0, Log[E]=1, Log[E^(x_Integer)]:=x, Log[E^(x_Rational)]:=x, Log[E^(I)]=I, Log[Exp[-I]]=(-I),
     * Log[0]=(-Infinity) }
     * </pre>
     */

    @Override
    public IAST getRuleAST() {
        // if (RULES == null) {
        // RULES = List(Set(Log(Power(E, Times(CN1, CI))), Times(CN1, CI)),
        // Set(Log(Power(E, CI)), CI),
        // Set(Log(C0), Times(CN1, CInfinity)), Set(Log(C1), C0), Set(Log(E),
        // C1),
        // SetDelayed(Log(Power(E, $p("x", $s("Integer")))), $s("x")),
        // SetDelayed(Log(Power(E, $p("x", $s("Rational")))), $s("x")));
        // }
        return RULES;
    }

    @Override
    public IExpr e1DblArg(final INum arg1) {
        return Num.valueOf(Math.log(arg1.getRealPart()));
    }

    @Override
    public IExpr e1DblComArg(final IComplexNum arg1) {
        return ComplexUtils.log((ComplexNum) arg1);
    }

    @Override
    public IExpr e2DblArg(final INum arg1, final INum arg2) {
        return Num.valueOf(Math.log(arg2.getRealPart()) / Math.log(arg1.getRealPart()));
    }

    @Override
    public IExpr e2IntArg(final IInteger arg1, final IInteger arg2) {
        return baseBLog(arg1, arg2);
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return Math.log(stack[top]);
    }

    @Override
    public void setUp(ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }

    @Override
    public IExpr e1ObjArg(IExpr expr) {
        if (expr.isPower()) {
            IExpr base = expr.getAt(1);
            IExpr exponent = expr.getAt(2);
            // arg2*Log(arg1)
            IExpr temp = F.eval(Times(exponent, F.Log(base)));
            IExpr imTemp = F.eval(F.Im(temp));
            if (imTemp.isSignedNumber()) {
                if (((ISignedNumber) imTemp).isGreaterThan(F.num(-1 * Math.PI))
                        && ((ISignedNumber) imTemp).isLessThan(F.num(Math.PI))) {
                    // Log(arg1 ^ arg2) == arg2*Log(arg1) ||| -Pi <
                    // Im(arg2*Log(arg1)) < Pi
                    return temp;
                }
            }
            if (AbstractAssumptions.assumePositive(base) && exponent.isRealResult()) {
                // Log(arg1 ^ arg2) == arg2*Log(arg1) ||| arg1 > 0 && arg2 is
                // Real
                return temp;
            }

        }
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(expr);
        if (negExpr.isPresent()) {
            if (negExpr.isPositiveResult()) {
                return F.Plus(F.Log(negExpr), Times(CI, F.Pi));
            }
        }
        return F.NIL;
    }
}
