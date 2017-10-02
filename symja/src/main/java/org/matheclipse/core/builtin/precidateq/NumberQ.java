package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * Returns <code>True</code>, if the given expression is an number object
 */
public class NumberQ extends AbstractCoreFunctionEvaluator {
    /**
     * Returns <code>True</code> if the 1st argument is a number; <code>False</code>
     * otherwise
     */
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.isAST1()) {
            IExpr arg1 = engine.evaluate(ast.arg1());
            return F.bool(arg1.isNumber());
        }
        Validate.checkSize(ast, 2);
        return F.NIL;
    }
}