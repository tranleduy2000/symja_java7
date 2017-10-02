package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Predicate function
 * <p>
 * Returns <code>True</code> if the 1st argument is a matrix; <code>False</code>
 * otherwise
 */
public class MatrixQ extends AbstractCoreFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        final IExpr arg1 = engine.evaluate(ast.arg1());
        int[] dims = arg1.isMatrix();
        if (dims == null) {
            return F.False;
        }

        if (ast.isAST2()) {
            final IExpr arg2 = engine.evaluate(ast.arg2());
            IAST temp = F.ast(arg2);
            temp.append(F.Slot1);
            for (int i = 1; i < dims[0]; i++) {
                IAST row = (IAST) arg1.getAt(i);
                for (int j = 1; j < dims[1]; j++) {
                    temp.set(1, row.getAt(j));
                    if (!engine.evalTrue(temp)) {
                        return F.False;
                    }
                }
            }
        }
        return F.True;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
    }

}