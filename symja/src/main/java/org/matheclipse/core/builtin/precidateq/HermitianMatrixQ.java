package org.matheclipse.core.builtin.precidateq;


import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public class HermitianMatrixQ extends SymmetricMatrixQ {
    @Override
    protected boolean compareElements(IExpr expr1, IExpr expr2, EvalEngine engine) {
        if (expr1.isSignedNumber() && expr2.isSignedNumber()) {
            if (expr1.equals(expr2)) {
                return true;
            }
            return false;
        }
        if (expr1.isNumber() && expr2.isNumber()) {
            if (expr1.conjugate().equals(expr2)) {
                return true;
            }
            return false;
        }
        if (!engine.evalTrue(F.Equal(F.Conjugate(expr1), expr2))) {
            return false;
        }
        return true;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
    }

}
