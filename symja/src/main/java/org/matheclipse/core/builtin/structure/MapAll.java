package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorLevelSpecification;

public class MapAll extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3);

        final IExpr arg1 = ast.arg1();
        final VisitorLevelSpecification level = new VisitorLevelSpecification(x -> F.unaryAST1(arg1, x), 0,
                Integer.MAX_VALUE, false);

        final IExpr result = ast.arg2().accept(level);
        return result.isPresent() ? result : ast.arg2();
    }

}