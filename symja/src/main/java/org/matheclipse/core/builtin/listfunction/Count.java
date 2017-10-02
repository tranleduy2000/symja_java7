package org.matheclipse.core.builtin.listfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorLevelSpecification;

/**
 * Count the number of elements in an expression which match the given pattern.
 */
public final class Count extends AbstractCoreFunctionEvaluator {
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3, 4);

        final IExpr arg1 = engine.evaluate(ast.arg1());

        final VisitorLevelSpecification level;
        CountFunctor mf = new CountFunctor(engine.evalPatternMatcher(ast.arg2()));
        if (ast.isAST3()) {
            final IExpr arg3 = engine.evaluate(ast.arg3());
            level = new VisitorLevelSpecification(mf, arg3, false, engine);
        } else {
            level = new VisitorLevelSpecification(mf, 1);
        }
        arg1.accept(level);
        return F.integer(mf.getCounter());
    }

}