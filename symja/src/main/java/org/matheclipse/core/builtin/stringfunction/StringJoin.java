package org.matheclipse.core.builtin.stringfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class StringJoin extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3);

        StringBuilder buf = new StringBuilder();
        for (int i = 1; i < ast.size(); i++) {
            if (ast.get(i).isString()) {
                buf.append(ast.get(i).toString());
            } else {
                return F.NIL;
            }
        }
        return F.$str(buf.toString());

    }
}
