package org.matheclipse.core.builtin.listfunction; import java.util.function.Consumer; import java.util.function.Function; import java.util.function.Predicate;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorRemoveLevelSpecification;

import java.util.function.Function;

public class CasesRulesFunctor implements Function<IExpr, IExpr> {
    protected final Function<IExpr, IExpr> function;
    final int maximumResults;
    protected IAST resultCollection;
    private int resultsCounter;

    /**
     * @param function         the funtion which should determine the results
     * @param resultCollection
     * @param maximumResults   maximum number of results. -1 for for no limitation
     */
    public CasesRulesFunctor(final Function<IExpr, IExpr> function, IAST resultCollection, int maximumResults) {
        this.function = function;
        this.resultCollection = resultCollection;
        this.maximumResults = maximumResults;
    }

    @Override
    public IExpr apply(final IExpr arg) throws VisitorRemoveLevelSpecification.StopException {
        IExpr temp = function.apply(arg);
        if (temp.isPresent()) {
            resultCollection.append(temp);
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