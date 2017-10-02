package org.matheclipse.core.builtin.listfunction; import java.util.function.Consumer; import java.util.function.Function; import java.util.function.Predicate;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.patternmatching.IPatternMatcher;

import java.util.function.Function;

public class DeleteCasesPatternMatcherFunctor implements Function<IExpr, IExpr> {
    private final IPatternMatcher matcher;

    /**
     * @param matcher the pattern-matcher
     */
    public DeleteCasesPatternMatcherFunctor(final IPatternMatcher matcher) {
        this.matcher = matcher;
    }

    @Override
    public IExpr apply(final IExpr arg) {
        if (matcher.test(arg)) {
            return F.Null;
        }
        return F.NIL;
    }

}