package org.matheclipse.core.builtin.listfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.generic.functors.Functors;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.patternmatching.PatternMatcher;
import org.matheclipse.core.patternmatching.PatternMatcherEvalEngine;
import org.matheclipse.core.visit.VisitorLevelSpecification;

/**
 * <p>
 * See the online Symja function reference: <a href=
 * "https://bitbucket.org/axelclk/symja_android_library/wiki/Symbols/Cases">
 * Cases</a>
 * </p>
 */
public final class Cases extends AbstractCoreFunctionEvaluator {
    public static IAST cases(final IAST ast, final IExpr pattern, EvalEngine engine) {
        if (pattern.isRuleAST()) {
            Function<IExpr, IExpr> function = Functors.rules((IAST) pattern, engine);
            IAST[] results = ast.filter(function);
            return results[0];
        }
        final PatternMatcher matcher = new PatternMatcherEvalEngine(pattern, engine);
        return ast.filter(F.List(), matcher);
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.isAST1()) {
            return F.operatorFormAST1(ast);
        }
        Validate.checkRange(ast, 3, 5);

        final IExpr arg1 = engine.evaluate(ast.arg1());
        if (arg1.isAST()) {
            final IExpr arg2 = engine.evalPattern(ast.arg2());
            if (ast.isAST3() || ast.size() == 5) {
                final IExpr arg3 = engine.evaluate(ast.arg3());
                int maximumResults = -1;
                if (ast.size() == 5) {
                    maximumResults = Validate.checkIntType(ast, 4);
                }
                IAST result = F.List();
                if (arg2.isRuleAST()) {
                    try {
                        Function<IExpr, IExpr> function = Functors.rules((IAST) arg2, engine);
                        CasesRulesFunctor crf = new CasesRulesFunctor(function, result, maximumResults);
                        VisitorLevelSpecification level = new VisitorLevelSpecification(crf, arg3, false, engine);
                        arg1.accept(level);

                    } catch (StopException se) {
                        // reached maximum number of results
                    }
                    return result;
                }

                try {
                    final PatternMatcher matcher = new PatternMatcherEvalEngine(arg2, engine);
                    CasesPatternMatcherFunctor cpmf = new CasesPatternMatcherFunctor(matcher, result,
                            maximumResults);
                    VisitorLevelSpecification level = new VisitorLevelSpecification(cpmf, arg3, false, engine);
                    arg1.accept(level);
                } catch (StopException se) {
                    // reached maximum number of results
                }
                return result;
            } else {
                return cases((IAST) arg1, arg2, engine);
            }
        }
        return F.List();
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL);
    }




}