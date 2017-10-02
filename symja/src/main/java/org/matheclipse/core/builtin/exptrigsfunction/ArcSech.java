package org.matheclipse.core.builtin.exptrigsfunction;

import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.ArcSechRules;

/**
 * Inverse hyperbolic secant
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Inverse_hyperbolic_function">
 * Inverse hyperbolic functions</a>
 */
public class ArcSech extends AbstractTrigArg1 implements ArcSechRules {

    @Override
    public IAST getRuleAST() {
        return RULES;
    }

    @Override
    public IExpr e1DblArg(final double d) {
        // log(1+Sqrt(1-d^2) / d)
        if (F.isZero(d)) {
            return F.Indeterminate;
        }
        return F.num(Math.log((1 + Math.sqrt(1 - d * d)) / d));
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }
}