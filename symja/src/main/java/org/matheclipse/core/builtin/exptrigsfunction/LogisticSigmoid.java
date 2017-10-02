package org.matheclipse.core.builtin.exptrigsfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Logistic function
 * <p>
 * See <a href="https://en.wikipedia.org/wiki/Logistic_function">Wikipedia:
 * Logistic function</a>
 */
public class LogisticSigmoid extends AbstractEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);
        IExpr arg1 = ast.arg1();
        if (arg1.isZero()) {
            return F.C1D2;
        }
        if (arg1.isInfinity()) {
            return F.C1;
        }
        if (arg1.isNegativeInfinity()) {
            return F.C0;
        }
        if (arg1.isNumber()) {
            // 1 / (1 + Exp(-arg1))
            return F.Power(F.Plus(F.C1, F.Power(F.E, F.Times(F.CN1, arg1))), F.CN1);
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
    }

}
