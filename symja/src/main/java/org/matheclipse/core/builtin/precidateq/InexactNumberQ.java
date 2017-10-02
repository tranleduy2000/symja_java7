package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class InexactNumberQ extends AbstractCoreFunctionEvaluator {
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.isAST1()) {
            IExpr arg1 = engine.evaluate(ast.arg1());
            return F.bool(arg1.isInexactNumber());
        }

        Validate.checkSize(ast, 2);
        return F.NIL;
    }
}
