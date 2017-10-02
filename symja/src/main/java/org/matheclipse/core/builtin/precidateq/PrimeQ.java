package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractCorePredicateEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Test if a number is prime. See:
 * <a href="http://en.wikipedia.org/wiki/Prime_number">Wikipedia:Prime
 * number</a>
 *
 * @see org.matheclipse.core.reflection.system.NextPrime
 */
public class PrimeQ extends AbstractCorePredicateEvaluator implements Predicate<IInteger> {

    public PrimeQ() {
        super(F.PrimeQ);
    }

    @Override
    public boolean evalArg1Boole(final IExpr arg1, EvalEngine engine) {
        if (!arg1.isInteger()) {
            return false;
        }
        return ((IInteger) arg1).isProbablePrime();
    }

    @Override
    public boolean test(final IInteger obj) {
        return obj.isProbablePrime();
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }
}