package org.matheclipse.core.builtin.listfunction;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.patternmatching.PatternMatcher;
import org.matheclipse.core.visit.VisitorRemoveLevelSpecification;

import java.util.function.Function;

public class CasesPatternMatcherFunctor implements Function<IExpr, IExpr> {
    protected final PatternMatcher matcher;
    final int maximumResults;
    protected IAST resultCollection;
    private int resultsCounter;

    /**
     * @param matcher          the pattern-matcher
     * @param resultCollection
     * @param maximumResults   maximum number of results. -1 for for no limitation
     */
    public CasesPatternMatcherFunctor(final PatternMatcher matcher, IAST resultCollection, int maximumResults) {
        this.matcher = matcher;
        this.resultCollection = resultCollection;
        this.maximumResults = maximumResults;
        this.resultsCounter = 0;
    }

    @Override
    public IExpr apply(final IExpr arg) throws VisitorRemoveLevelSpecification.StopException {
        if (matcher.test(arg)) {
            resultCollection.append(arg);
            if (maximumResults >= 0) {
                resultsCounter++;
                if (resultsCounter >= maximumResults) {
                    throw new VisitorRemoveLevelSpecification.StopException();
                }
            }
        }
        return F.NIL;
    }

}
