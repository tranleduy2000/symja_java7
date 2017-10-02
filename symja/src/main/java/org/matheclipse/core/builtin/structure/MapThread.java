package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorLevelSpecification;

public final class MapThread extends AbstractFunctionEvaluator {

    public MapThread() {
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3, 4);

        VisitorLevelSpecification level = null;
        java.util.function.Function<IExpr, IExpr> umt = new UnaryMapThread(ast.arg1());
        if (ast.isAST3()) {
            level = new VisitorLevelSpecification(umt, ast.arg3(), false, engine);
        } else {
            level = new VisitorLevelSpecification(umt, 0);
        }
        final IExpr result = ast.arg2().accept(level);
        return result.isPresent() ? result : ast.arg2();
    }


}
