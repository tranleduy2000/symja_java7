package org.matheclipse.core.generic.functors;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.patternmatching.PatternMatcherAndEvaluator;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

public class RulesPatternFunctor implements Function<IExpr, IExpr> {
    private final Map<IExpr, IExpr> fEqualRules;
    private final List<PatternMatcherAndEvaluator> fMatchers;
    private final EvalEngine fEngine;

    /**
     * @param plusAST  the complete AST which should be cloned in the {@code apply}
     *                 method
     * @param position the position which should be replaced in the <code>apply()</code>
     *                 method.
     */
    public RulesPatternFunctor(Map<IExpr, IExpr> equalRules, List<PatternMatcherAndEvaluator> matchers,
                               EvalEngine engine) {
        fEqualRules = equalRules;
        fMatchers = matchers;
        fEngine = engine;
    }

    @Override
    @Nonnull
    public IExpr apply(final IExpr arg) {
        IExpr temp = fEqualRules.get(arg);
        if (temp != null) {
            return temp;
        }
        for (int i = 0; i < fMatchers.size(); i++) {
            temp = fMatchers.get(i).eval(arg, fEngine);
            if (temp.isPresent()) {
                return temp;
            }
        }
        return F.NIL;
    }
}
