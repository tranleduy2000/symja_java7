package org.matheclipse.core.builtin.listfunction; import java.util.function.Consumer; import java.util.function.Function; import java.util.function.Predicate;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.patternmatching.IPatternMatcher;
import org.matheclipse.core.visit.VisitorRemoveLevelSpecification;

public final class DeleteCases extends AbstractCoreFunctionEvaluator {

    public static IAST deleteCases(final IAST ast, final IPatternMatcher matcher) {
        // final IPatternMatcher matcher = new PatternMatcher(pattern);
        IAST[] results = ast.filter(matcher);
        return results[1];

    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3, 5);

        final IExpr arg1 = engine.evaluate(ast.arg1());
        if (arg1.isAST()) {
            final IPatternMatcher matcher = engine.evalPatternMatcher(ast.arg2());
            if (ast.isAST3() || ast.size() == 5) {
                final IExpr arg3 = engine.evaluate(ast.arg3());
                int maximumRemoveOperations = -1;
                if (ast.size() == 5) {
                    maximumRemoveOperations = Validate.checkIntType(ast, 4);
                }
                IAST arg1RemoveClone = ((IAST) arg1).clone();

                try {
                    DeleteCasesPatternMatcherFunctor cpmf = new DeleteCasesPatternMatcherFunctor(matcher);
                    VisitorRemoveLevelSpecification level = new VisitorRemoveLevelSpecification(cpmf, arg3,
                            maximumRemoveOperations, false, engine);
                    arg1RemoveClone.accept(level);
                    if (level.getRemovedCounter() == 0) {
                        return arg1;
                    }
                    return arg1RemoveClone;
                } catch (VisitorRemoveLevelSpecification.StopException se) {
                    // reached maximum number of results
                }
                return arg1RemoveClone;
            } else {
                return deleteCases((IAST) arg1, matcher);
            }
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL);
    }


}