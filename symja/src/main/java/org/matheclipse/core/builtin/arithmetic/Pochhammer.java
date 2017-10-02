package org.matheclipse.core.builtin.arithmetic;

import org.hipparchus.fraction.BigFraction;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractArg2;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.IRational;
import org.matheclipse.core.interfaces.ISymbol;

import java.math.BigInteger;


/**
 * Compute Pochhammer's symbol (this)_n.
 *
 * @param n The number of product terms in the evaluation.
 * @return Gamma(this+n)/Gamma(this) = this*(this+1)*...*(this+n-1).
 */
public class Pochhammer extends AbstractArg2 {// implements PochhammerRules {

    /**
     * Compute Pochhammer's symbol (this)_n.
     *
     * @param n The number of product terms in the evaluation.
     * @return Gamma(this+n)/Gamma(this) = this*(this+1)*...*(this+n-1).
     */
    public static BigFraction pochhammer(BigFraction th, final BigInteger n) {
        if (n.compareTo(BigInteger.ZERO) < 0) {
            BigFraction res = BigFraction.ONE;
            BigInteger i = BigInteger.valueOf(-1);
            for (; i.compareTo(n) >= 0; i = i.subtract(BigInteger.ONE)) {
                res = res.multiply(th.add(i));
            }
            return res.reciprocal();
        } else if (n.equals(BigInteger.ZERO)) {
            return BigFraction.ONE;
        } else {
            BigFraction res = new BigFraction(th.getNumerator(), th.getDenominator());
            BigInteger i = BigInteger.ONE;
            for (; i.compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
                res = res.multiply(th.add(i));
            }
            return res;
        }
    }

    @Override
    public IExpr e2ObjArg(final IExpr a, final IExpr n) {
        if (n.isZero()) {
            return F.C1;
        }
        if (n.isOne()) {
            return a;
        }
        if (a.isRational() && n.isInteger()) {
            BigFraction bf = ((IRational) a).getFraction();
            BigFraction ph = pochhammer(bf, ((IInteger) n).toBigNumerator());
            if (ph != null) {
                return F.fraction(ph);
            }
        }
        if (a.isInteger() && a.isPositive()) {
            IExpr temp = EvalEngine.get().evaluate(F.Plus(((IInteger) a).subtract(F.C1), n));
            if (temp.isSymbol()) {
                return F.Divide(F.Factorial(temp), F.Gamma(a));
            }
        }

        if (n.isInteger()) {
            int ni = n.toIntDefault(Integer.MIN_VALUE);
            if (ni > Integer.MIN_VALUE) {
                if (ni > 0) {
                    // Product(a + k, {k, 0, n - 1})
                    return F.product(x -> F.Plus(a, x), 0, ni - 1);
                }
                if (ni < 0) {
                    // Product(1/(a - k), {k, 1, -n})
                    return F.Power(F.product(x -> F.Plus(a, x.negate()), 1, -ni), -1);
                }
            }
        }
        if (!a.isInteger() && !n.isInteger()) {
            return F.Divide(F.Gamma(F.Plus(a, n)), F.Gamma(a));
        }
        return F.NIL;
    }

    /**
     * Compute pochhammer's symbol (this)_n.
     *
     * @param n The number of product terms in the evaluation.
     * @return Gamma(this+n)/GAMMA(this).
     */
    // public static BigFraction pochhammer(BigFraction th, int n) {
    // return pochhammer(th, BigInteger.valueOf(n));
    // }
    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
    }

}

   

  