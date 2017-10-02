package org.matheclipse.core.builtin.listfunction;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.patternmatching.IPatternMatcher;

import java.util.function.Function;

public class CountFunctor implements Function<IExpr, IExpr> {
    protected final IPatternMatcher matcher;
    protected int counter;

    public CountFunctor(final IPatternMatcher patternMatcher) {
        this.matcher = patternMatcher; // new PatternMatcher(pattern);
        counter = 0;
    }

    /**
     * @return the counter
     */
    public int getCounter() {
        return counter;
    }

    @Override
    public IExpr apply(final IExpr arg) {
        if (matcher.test(arg)) {
            counter++;
        }
        return F.NIL;
    }

}
