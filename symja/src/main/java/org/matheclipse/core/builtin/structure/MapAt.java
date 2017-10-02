package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;

public class MapAt extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 4);

        final IExpr arg2 = ast.arg2();
        if (arg2.isAST()) {
            try {
                final IExpr arg3 = ast.arg3();
                if (arg3.isInteger()) {
                    final IExpr arg1 = ast.arg1();
                    IInteger i3 = (IInteger) arg3;
                    int n = i3.toInt();
                    return ((IAST) arg2).setAtCopy(n, F.unaryAST1(arg1, ((IAST) arg2).get(n)));
                }
            } catch (RuntimeException ae) {
            }
        }
        return F.NIL;
    }

}
