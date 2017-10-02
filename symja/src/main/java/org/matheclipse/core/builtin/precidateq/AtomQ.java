package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractCorePredicateEvaluator;
import org.matheclipse.core.interfaces.IExpr;

import java.util.function.Predicate;

/**
 * Returns <code>True</code>, if the given expression is an atomic object (i.e.
 * no AST instance)
 * <p>
 * See the online Symja function reference: <a href=
 * "https://bitbucket.org/axelclk/symja_android_library/wiki/Symbols/AtomQ">
 * AtomQ</a>
 * </p>
 */
public class AtomQ extends AbstractCorePredicateEvaluator implements Predicate<IExpr> {

    @Override
    public boolean evalArg1Boole(final IExpr arg1, EvalEngine engine) {
        return arg1.isAtom();
    }

    @Override
    public boolean test(final IExpr obj) {
        return obj.isAtom();
    }

}