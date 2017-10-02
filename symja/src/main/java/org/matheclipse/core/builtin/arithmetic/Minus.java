package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class Minus extends AbstractCoreFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.size() == 2) {
            IExpr arg1 = engine.evaluate(ast.arg1());
            return F.Times(F.CN1, arg1);
        }
        engine.printMessage("Minus: exactly 1 argument expected");
        return F.NIL;
    }

}
