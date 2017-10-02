package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.interfaces.AbstractCorePredicateEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Predicate function
 * <p>
 * Returns <code>True</code> if the 1st argument is <code>0</code>;
 * <code>False</code> otherwise
 */
public class PossibleZeroQ extends AbstractCorePredicateEvaluator {

    public PossibleZeroQ() {
        super(F.PossibleZeroQ);
    }

    @Override
    public boolean evalArg1Boole(final IExpr arg1, EvalEngine engine) {
        return possibleZeroQ(arg1, engine);
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }

}
