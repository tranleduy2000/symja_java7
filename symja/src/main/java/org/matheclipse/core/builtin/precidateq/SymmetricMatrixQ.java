package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public class SymmetricMatrixQ extends AbstractCoreFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        final IExpr arg1 = engine.evaluate(ast.arg1());
        int[] dims = arg1.isMatrix();
        if (dims == null || dims[0] != dims[1]) {
            // no square matrix
            return F.False;
        }

        final IAST matrix = (IAST) arg1;
        for (int i = 1; i <= dims[0]; i++) {
            IAST row = matrix.getAST(i);
            for (int j = i + 1; j <= dims[1]; j++) {
                IExpr expr = row.get(j);
                IExpr symmetricExpr = matrix.getPart(j, i);
                if (!compareElements(expr, symmetricExpr, engine)) {
                    return F.False;
                }
            }
        }
        return F.True;
    }

    protected boolean compareElements(IExpr expr1, IExpr expr2, EvalEngine engine) {
        if (expr1.isNumber() && expr2.isNumber()) {
            if (expr1.equals(expr2)) {
                return true;
            }
            return false;
        }
        if (!engine.evalTrue(F.Equal(expr1, expr2))) {
            return false;
        }
        return true;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
    }

}
